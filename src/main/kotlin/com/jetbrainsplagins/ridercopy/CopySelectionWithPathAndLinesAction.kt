package com.jetbrainsplagins.ridercopy

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths

class CopySelectionWithPathAndLinesAction : AnAction() {

    override fun update(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR)
        val selectionModel = editor?.selectionModel
        val hasNonEmptySelection = selectionModel?.hasSelection() == true && !selectionModel.selectedText.isNullOrEmpty()
        e.presentation.isEnabledAndVisible = hasNonEmptySelection
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val selectionModel = editor.selectionModel

        if (!selectionModel.hasSelection()) {
            return
        }

        val selectedText = selectionModel.selectedText ?: return
        if (selectedText.isEmpty()) {
            return
        }

        if (!virtualFile.isInLocalFileSystem) {
            return
        }

        val projectRootPath = try {
            val basePath = project.basePath ?: return
            Paths.get(basePath).toAbsolutePath().normalize()
        } catch (_: InvalidPathException) {
            return
        }

        val absoluteFilePath = try {
            Paths.get(virtualFile.path).toAbsolutePath().normalize()
        } catch (_: InvalidPathException) {
            return
        }

        if (!absoluteFilePath.startsWith(projectRootPath)) {
            return
        }

        val relativePath = projectRootPath.relativize(absoluteFilePath).toString().replace('\\', '/')
        if (relativePath.isEmpty()) {
            return
        }

        val selectionStartOffset = selectionModel.selectionStart
        val selectionEndOffset = selectionModel.selectionEnd
        if (selectionEndOffset <= selectionStartOffset) {
            return
        }

        val document = editor.document
        val lineStart = document.getLineNumber(selectionStartOffset) + 1

        val lastIncludedOffset = selectionEndOffset - 1
        val lineEnd = document.getLineNumber(lastIncludedOffset) + 1

        val normalizedSelectedText = selectedText
            .replace("\r\n", "\n")
            .replace('\r', '\n')

        val clipboardPayload = "$relativePath:$lineStart-$lineEnd\n$normalizedSelectedText"
        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(clipboardPayload), null)
    }
}
