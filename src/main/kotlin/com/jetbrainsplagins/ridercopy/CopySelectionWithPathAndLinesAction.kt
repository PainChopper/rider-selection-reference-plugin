package com.jetbrainsplagins.ridercopy

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Locale

class CopySelectionWithPathAndLinesAction : AnAction() {

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val selectionModel = editor?.selectionModel
        val hasNonEmptySelection = selectionModel?.hasSelection() == true && !selectionModel.selectedText.isNullOrEmpty()
        e.presentation.isEnabledAndVisible = hasNonEmptySelection
    }

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val selectionModel = editor.selectionModel

        if (!selectionModel.hasSelection()) {
            return
        }

        val selectedText = selectionModel.selectedText ?: return
        if (selectedText.isEmpty()) {
            return
        }

        val selectionStartOffset = selectionModel.selectionStart
        val selectionEndOffset = selectionModel.selectionEnd
        if (selectionEndOffset <= selectionStartOffset) {
            return
        }

        var clipboardPayload = selectedText
        val project = e.project
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)

        if (project != null && virtualFile != null && virtualFile.isInLocalFileSystem) {
            val projectRootPath = try {
                val basePath = project.basePath
                if (basePath.isNullOrBlank()) {
                    null
                } else {
                    Paths.get(basePath).toAbsolutePath().normalize()
                }
            } catch (_: InvalidPathException) {
                null
            }

            val absoluteFilePath = try {
                Paths.get(virtualFile.path).toAbsolutePath().normalize()
            } catch (_: InvalidPathException) {
                null
            }

            if (projectRootPath != null && absoluteFilePath != null && absoluteFilePath.startsWith(projectRootPath)) {
                val relativePath = projectRootPath.relativize(absoluteFilePath).toString().replace('\\', '/')
                if (relativePath.isNotEmpty()) {
                    val document = editor.document
                    val lineStart = document.getLineNumber(selectionStartOffset) + 1
                    val lastIncludedOffset = selectionEndOffset - 1
                    val lineEnd = document.getLineNumber(lastIncludedOffset) + 1

                    val normalizedSelectedText = selectedText
                        .replace("\r\n", "\n")
                        .replace('\r', '\n')

                    val language = detectLanguage(absoluteFilePath)
                    clipboardPayload = buildString {
                        append("[FILE] ").append(relativePath).append('\n')
                        append("[LINES] ").append(lineStart).append('-').append(lineEnd).append('\n')
                        append("[LANG] ").append(language).append('\n')
                        append("[CODE]").append('\n')
                        append(normalizedSelectedText).append('\n')
                        append("[/CODE]")
                    }
                }
            }
        }

        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(clipboardPayload), null)
    }

    private fun detectLanguage(filePath: Path): String {
        val fileName = filePath.fileName?.toString().orEmpty()
        if (fileName.isEmpty()) {
            return "text"
        }

        val extension = fileName.substringAfterLast('.', "").lowercase(Locale.ROOT)
        if (extension.isEmpty()) {
            return "text"
        }

        return EXTENSION_TO_LANGUAGE[extension] ?: "text"
    }

    companion object {
        private val EXTENSION_TO_LANGUAGE: Map<String, String> = mapOf(
            "kt" to "kotlin",
            "kts" to "kotlin",
            "java" to "java",
            "xml" to "xml",
            "json" to "json",
            "yml" to "yaml",
            "yaml" to "yaml",
            "md" to "markdown",
            "txt" to "text",
            "js" to "javascript",
            "ts" to "typescript",
            "jsx" to "jsx",
            "tsx" to "tsx",
            "py" to "python",
            "go" to "go",
            "rs" to "rust",
            "cs" to "csharp",
            "cpp" to "cpp",
            "cc" to "cpp",
            "cxx" to "cpp",
            "c" to "c",
            "h" to "c",
            "hpp" to "cpp",
            "sh" to "bash",
            "ps1" to "powershell",
            "sql" to "sql",
            "html" to "html",
            "css" to "css",
            "scss" to "scss"
        )
    }
}
