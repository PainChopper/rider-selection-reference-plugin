package com.jetbrainsplagins.ridercopy

import com.intellij.diff.comparison.ComparisonManager
import com.intellij.diff.comparison.ComparisonPolicy
import com.intellij.diff.contents.DiffContent
import com.intellij.diff.contents.DocumentContent
import com.intellij.diff.fragments.LineFragment
import com.intellij.diff.requests.ContentDiffRequest
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.DataKey
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.progress.EmptyProgressIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.nio.file.InvalidPathException
import java.nio.file.Paths
import java.util.Locale

class CopySelectionWithPathAndLinesAction : AnAction() {
    companion object {
        private val LOG = Logger.getInstance(CopySelectionWithPathAndLinesAction::class.java)
    }

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val selectionModel = editor?.selectionModel
        val hasNonEmptySelection = selectionModel?.hasSelection() == true && !selectionModel.selectedText.isNullOrEmpty()
        e.presentation.isEnabledAndVisible = hasNonEmptySelection
    }

    override fun actionPerformed(e: AnActionEvent) {
        val context = resolveActionContext(e) ?: return
        val clipboardPayload = buildClipboardPayload(e, context)
        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(clipboardPayload), null)
    }

    private fun resolveActionContext(e: AnActionEvent): ActionContext? {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return null
        val selectionModel = editor.selectionModel
        if (!selectionModel.hasSelection()) {
            return null
        }

        val selectedText = selectionModel.selectedText ?: return null
        if (selectedText.isEmpty()) {
            return null
        }

        val selectionStartOffset = selectionModel.selectionStart
        val selectionEndOffset = selectionModel.selectionEnd
        if (selectionEndOffset <= selectionStartOffset) {
            return null
        }

        return ActionContext(
            editor = editor,
            selectedText = selectedText,
            selectionStartOffset = selectionStartOffset,
            selectionEndOffset = selectionEndOffset,
            project = e.project,
            virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        )
    }

    private fun buildClipboardPayload(e: AnActionEvent, context: ActionContext): String {
        if (isDiffEditor(context.editor)) {
            return buildDiffClipboardPayload(
                e = e,
                editor = context.editor,
                selectionStartOffset = context.selectionStartOffset,
                selectionEndOffset = context.selectionEndOffset,
                project = context.project,
                virtualFile = context.virtualFile
            ) ?: context.selectedText
        }

        return buildFileClipboardPayload(context) ?: context.selectedText
    }

    private fun buildFileClipboardPayload(context: ActionContext): String? {
        val project = context.project ?: return null
        val virtualFile = context.virtualFile ?: return null
        if (!virtualFile.isInLocalFileSystem) {
            return null
        }

        val relativePath = resolveProjectRelativePath(project, virtualFile) ?: return null
        val document = context.editor.document
        val lineStart = document.getLineNumber(context.selectionStartOffset) + 1
        val lastIncludedOffset = context.selectionEndOffset - 1
        val lineEnd = document.getLineNumber(lastIncludedOffset) + 1
        val normalizedSelectedText = normalizeText(context.selectedText)
        val language = detectLanguage(project, virtualFile)

        return buildString {
            append("[FILE] ").append(relativePath).append('\n')
            append("[LINES] ").append(lineStart).append('-').append(lineEnd).append('\n')
            append("[LANG] ").append(language).append('\n')
            append("[CODE]").append('\n')
            append(normalizedSelectedText).append('\n')
            append("[/CODE]")
        }
    }

    private fun detectLanguage(project: Project, virtualFile: VirtualFile): String {
        val psiFile = PsiManager.getInstance(project).findFile(virtualFile)
        val ideLanguage = normalizeLangToken(psiFile?.language?.id)
        if (ideLanguage != null) {
            return ideLanguage
        }

        val ideFileType = normalizeLangToken(virtualFile.fileType.name)
        if (ideFileType != null) {
            return ideFileType
        }

        return "text"
    }

    private fun normalizeLangToken(raw: String?): String? {
        if (raw.isNullOrBlank()) {
            return null
        }

        val normalized = raw
            .trim()
            .lowercase(Locale.ROOT)
            .replace(Regex("[^a-z0-9]+"), "_")
            .trim('_')

        if (normalized.isEmpty()) {
            return null
        }

        if (normalized == "text" || normalized == "plaintext" || normalized == "plain_text") {
            return "text"
        }

        val rawLower = raw.trim().lowercase(Locale.ROOT)
        if ((normalized == "c" || normalized == "c_sharp" || normalized == "csharp") &&
            (rawLower.contains("#") || rawLower.contains("sharp"))
        ) {
            return "csharp"
        }

        if ((normalized == "f" || normalized == "f_sharp" || normalized == "fsharp") &&
            (rawLower.contains("#") || rawLower.contains("sharp"))
        ) {
            return "fsharp"
        }

        if (normalized == "c" && rawLower.contains("++")) {
            return "cpp"
        }

        return normalized
    }

    private fun isDiffEditor(editor: Editor): Boolean {
        return try {
            val getter = editor.javaClass.methods.firstOrNull { method ->
                method.name == "getEditorKind" && method.parameterCount == 0
            } ?: return false

            getter.invoke(editor)?.toString()?.uppercase(Locale.ROOT) == "DIFF"
        } catch (error: Throwable) {
            LOG.warn("Failed to detect diff editor kind, fallback to regular editor flow.", error)
            false
        }
    }

    private fun buildDiffClipboardPayload(
        e: AnActionEvent,
        editor: Editor,
        selectionStartOffset: Int,
        selectionEndOffset: Int,
        project: Project?,
        virtualFile: VirtualFile?
    ): String? {
        val diffContext = resolveDiffApiContext(e, editor) ?: return null

        val currentDocument = editor.document
        val selectedStartLine = currentDocument.getLineNumber(selectionStartOffset)
        val selectedEndLine = currentDocument.getLineNumber(selectionEndOffset - 1)
        val selectedEndExclusive = selectedEndLine + 1

        val matchingFragments = diffContext.fragments.filter { fragment ->
            val fragmentStart = if (diffContext.currentSideIndex == 0) fragment.startLine1 else fragment.startLine2
            val fragmentEnd = if (diffContext.currentSideIndex == 0) fragment.endLine1 else fragment.endLine2
            fragmentEnd > selectedStartLine && fragmentStart < selectedEndExclusive
        }

        if (matchingFragments.isEmpty()) {
            return null
        }

        val diffLines = mutableListOf<String>()
        matchingFragments.forEach { fragment ->
            diffLines.add(buildUnifiedHeader(fragment))
            appendLinesWithPrefix(diffLines, diffContext.leftLines, fragment.startLine1, fragment.endLine1, '-')
            appendLinesWithPrefix(diffLines, diffContext.rightLines, fragment.startLine2, fragment.endLine2, '+')
        }

        if (diffLines.isEmpty()) {
            return null
        }

        val diffFileLabel = resolveDiffFileLabel(
            project = project,
            virtualFile = virtualFile,
            diffContext = diffContext
        )
        val diffFileLanguage = resolveDiffFileLanguage(
            project = project,
            virtualFile = virtualFile,
            diffContext = diffContext
        )

        val normalizedBody = diffLines
            .joinToString("\n")
            .replace("\r\n", "\n")
            .replace('\r', '\n')

        return buildString {
            append("[DIFF_FILE] ").append(diffFileLabel).append('\n')
            append("[DIFF_RANGE] ").append(selectedStartLine + 1).append('-').append(selectedEndLine + 1).append('\n')
            append("[LANG] diff").append('\n')
            append("[DIFF_FILE_LANG] ").append(diffFileLanguage).append('\n')
            append("[DIFF_FORMAT] unified").append('\n')
            append("[DIFF]").append('\n')
            append(normalizedBody).append('\n')
            append("[/DIFF]")
        }
    }

    private fun resolveDiffApiContext(e: AnActionEvent, editor: Editor): DiffApiContext? {
        val request = getDataByKey<Any>(e, "diff_request") as? ContentDiffRequest ?: return null
        val contents = request.contents
        if (contents.size < 2) {
            return null
        }

        val leftContent = contents[0]
        val rightContent = contents[1]
        val leftDocument = extractDocument(leftContent) ?: return null
        val rightDocument = extractDocument(rightContent) ?: return null

        val leftText = normalizeText(leftDocument.text)
        val rightText = normalizeText(rightDocument.text)
        val fragments = try {
            ComparisonManager.getInstance().compareLines(
                leftText,
                rightText,
                ComparisonPolicy.DEFAULT,
                EmptyProgressIndicator()
            )
        } catch (error: Throwable) {
            LOG.warn("Failed to compare diff lines via ComparisonManager, fallback to selected text.", error)
            return null
        }

        if (fragments.isEmpty()) {
            return null
        }

        val currentContent = getDataByKey<DiffContent>(e, "diff_current_content")
        val sideIndexFromContent = if (currentContent != null) {
            contents.indexOfFirst { content -> content === currentContent }
        } else {
            -1
        }

        val resolvedSideIndex = when {
            sideIndexFromContent in 0..1 -> sideIndexFromContent
            editor.document === rightDocument -> 1
            editor.document === leftDocument -> 0
            else -> 1
        }

        return DiffApiContext(
            request = request,
            currentSideIndex = resolvedSideIndex,
            contents = contents,
            leftLines = splitLines(leftText),
            rightLines = splitLines(rightText),
            fragments = fragments
        )
    }

    private fun extractDocument(content: DiffContent): Document? {
        return (content as? DocumentContent)?.document
    }

    private fun buildUnifiedHeader(fragment: LineFragment): String {
        val oldStart = fragment.startLine1 + 1
        val oldCount = fragment.endLine1 - fragment.startLine1
        val newStart = fragment.startLine2 + 1
        val newCount = fragment.endLine2 - fragment.startLine2
        return "@@ -$oldStart,$oldCount +$newStart,$newCount @@"
    }

    private fun appendLinesWithPrefix(
        output: MutableList<String>,
        lines: List<String>,
        startInclusive: Int,
        endExclusive: Int,
        prefix: Char
    ) {
        val safeStart = startInclusive.coerceAtLeast(0).coerceAtMost(lines.size)
        val safeEnd = endExclusive.coerceAtLeast(safeStart).coerceAtMost(lines.size)
        for (lineIndex in safeStart until safeEnd) {
            output.add(prefix + lines[lineIndex])
        }
    }

    private fun splitLines(text: String): List<String> {
        if (text.isEmpty()) {
            return emptyList()
        }
        return text.split('\n')
    }

    private fun normalizeText(value: CharSequence): String {
        return value.toString()
            .replace("\r\n", "\n")
            .replace('\r', '\n')
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getDataByKey(e: AnActionEvent, keyName: String): T? {
        val key = DataKey.create<Any>(keyName)
        return e.dataContext.getData(key) as? T
    }

    private fun resolveDiffFileLabel(
        project: Project?,
        virtualFile: VirtualFile?,
        diffContext: DiffApiContext
    ): String {
        resolvePathFromVirtualFile(project, virtualFile)?.let { return it }

        val sideContent = diffContext.contents.getOrNull(diffContext.currentSideIndex)
        val highlightFile = extractVirtualFileFromDiffContent(sideContent)
        resolvePathFromVirtualFile(project, highlightFile)?.let { return it }

        val absolutePath = highlightFile?.path?.replace('\\', '/')
        if (!absolutePath.isNullOrBlank()) {
            return absolutePath
        }

        val titleFromRequest = diffContext.request.contentTitles
            .getOrNull(diffContext.currentSideIndex)
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
        if (titleFromRequest != null) {
            return titleFromRequest
        }

        val path = virtualFile?.path?.replace('\\', '/')
        if (!path.isNullOrBlank()) {
            return path
        }

        val requestTitle = diffContext.request.title?.trim()?.takeIf { it.isNotEmpty() }
        if (requestTitle != null) {
            return requestTitle
        }

        return "unknown"
    }

    private fun resolveDiffFileLanguage(
        project: Project?,
        virtualFile: VirtualFile?,
        diffContext: DiffApiContext
    ): String {
        val resolvedFile = resolveDiffVirtualFile(virtualFile, diffContext) ?: return "text"

        if (project != null) {
            return detectLanguage(project, resolvedFile)
        }

        return normalizeLangToken(resolvedFile.fileType.name) ?: "text"
    }

    private fun resolveDiffVirtualFile(virtualFile: VirtualFile?, diffContext: DiffApiContext): VirtualFile? {
        if (virtualFile != null) {
            return virtualFile
        }

        val sideContent = diffContext.contents.getOrNull(diffContext.currentSideIndex)
        return extractVirtualFileFromDiffContent(sideContent)
    }

    private fun resolvePathFromVirtualFile(project: Project?, virtualFile: VirtualFile?): String? {
        resolveProjectRelativePath(project, virtualFile)?.let { return it }

        val path = virtualFile?.path?.replace('\\', '/')
        return path?.takeIf { it.isNotBlank() }
    }

    private fun resolveProjectRelativePath(project: Project?, virtualFile: VirtualFile?): String? {
        if (project == null || virtualFile == null || !virtualFile.isInLocalFileSystem) {
            return null
        }

        val projectRootPath = try {
            val basePath = project.basePath
            if (basePath.isNullOrBlank()) {
                null
            } else {
                Paths.get(basePath).toAbsolutePath().normalize()
            }
        } catch (error: InvalidPathException) {
            LOG.warn("Invalid project base path: '${project.basePath}'.", error)
            null
        }

        val absoluteFilePath = try {
            Paths.get(virtualFile.path).toAbsolutePath().normalize()
        } catch (error: InvalidPathException) {
            LOG.warn("Invalid virtual file path: '${virtualFile.path}'.", error)
            null
        }

        if (projectRootPath == null || absoluteFilePath == null || !absoluteFilePath.startsWith(projectRootPath)) {
            return null
        }

        val relativePath = projectRootPath.relativize(absoluteFilePath).toString().replace('\\', '/')
        return relativePath.takeIf { it.isNotEmpty() }
    }

    private fun extractVirtualFileFromDiffContent(content: DiffContent?): VirtualFile? {
        val documentContent = content as? DocumentContent ?: return null
        documentContent.highlightFile?.let { return it }

        extractFileFromDescriptor(invokeZeroArg(documentContent, "getOpenFileDescriptor"))?.let { return it }
        extractFileFromDescriptor(invokeZeroArg(documentContent, "getNavigatable"))?.let { return it }

        return null
    }

    private fun invokeZeroArg(target: Any, methodName: String): Any? {
        return try {
            val method = target.javaClass.methods.firstOrNull { it.name == methodName && it.parameterCount == 0 }
            method?.invoke(target)
        } catch (error: Throwable) {
            LOG.warn("Failed to invoke '$methodName' on ${target.javaClass.name}.", error)
            null
        }
    }

    private fun extractFileFromDescriptor(value: Any?): VirtualFile? {
        val descriptor = value as? OpenFileDescriptor ?: return null
        return descriptor.file
    }

    private data class DiffApiContext(
        val request: ContentDiffRequest,
        val currentSideIndex: Int,
        val contents: List<DiffContent>,
        val leftLines: List<String>,
        val rightLines: List<String>,
        val fragments: List<LineFragment>
    )

    private data class ActionContext(
        val editor: Editor,
        val selectedText: String,
        val selectionStartOffset: Int,
        val selectionEndOffset: Int,
        val project: Project?,
        val virtualFile: VirtualFile?
    )
}
