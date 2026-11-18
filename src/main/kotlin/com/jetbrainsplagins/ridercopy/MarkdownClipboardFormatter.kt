package com.jetbrainsplagins.ridercopy

import java.util.Locale

internal object MarkdownClipboardFormatter {
    fun formatEditorContext(
        filePath: String,
        lineStart: Int,
        lineEnd: Int,
        languageTag: String?,
        body: String
    ): String = formatEnvelope(
        metadata = listOf(
            "File" to filePath,
            "Lines" to "$lineStart-$lineEnd"
        ),
        languageTag = languageTag,
        body = body
    )

    fun formatDiffContext(
        fileLabel: String,
        fileLanguageTag: String?,
        body: String
    ): String {
        val metadata = buildList {
            add("File" to fileLabel)
            add("Context" to "diff")
            if (fileLanguageTag != null) {
                add("File language" to fileLanguageTag)
            }
        }

        return formatEnvelope(metadata, "diff", body)
    }

    fun normalizeLanguageTag(raw: String?): String? {
        if (raw.isNullOrBlank()) {
            return null
        }

        val rawLower = raw.trim().lowercase(Locale.ROOT)
        val normalized = rawLower
            .replace(Regex("[^a-z0-9]+"), "_")
            .trim('_')

        return when (normalized) {
            "go" -> "go"
            "c" -> if (rawLower.contains('#')) "csharp" else if (rawLower.contains("++")) "cpp" else "c"
            "c_sharp", "csharp" -> "csharp"
            "f", "f_sharp", "fsharp" -> if (rawLower.contains('#') || normalized != "f") "fsharp" else null
            "cpp" -> "cpp"
            "java" -> "java"
            "kotlin", "kt", "kts" -> "kotlin"
            "js", "javascript" -> "javascript"
            "javascript_jsx", "jsx" -> "jsx"
            "ts", "typescript" -> "typescript"
            "typescript_jsx", "tsx" -> "tsx"
            "python", "py" -> "python"
            "rust", "rs" -> "rust"
            "sql" -> "sql"
            "powershell", "ps1" -> "powershell"
            "shell", "shell_script", "sh", "bash" -> "bash"
            "json" -> "json"
            "yaml", "yml" -> "yaml"
            "html" -> "html"
            "css" -> "css"
            "scss" -> "scss"
            "markdown", "md" -> "markdown"
            "xml" -> "xml"
            else -> null
        }
    }

    private fun formatEnvelope(
        metadata: List<Pair<String, String>>,
        languageTag: String?,
        body: String
    ): String {
        val normalizedBody = normalizeText(body)
        val fence = "`".repeat(maxOf(3, longestBacktickRun(normalizedBody) + 1))

        return buildString {
            metadata.forEach { (label, value) ->
                append("- **").append(label).append(":** ").append(codeSpan(value)).append('\n')
            }
            append('\n')
            append(fence)
            if (languageTag != null) {
                append(languageTag)
            }
            append('\n')
            append(normalizedBody)
            if (!normalizedBody.endsWith('\n')) {
                append('\n')
            }
            append(fence)
        }
    }

    private fun codeSpan(value: String): String {
        val delimiter = "`".repeat(maxOf(1, longestBacktickRun(value) + 1))
        val isAllSpaces = value.isNotEmpty() && value.all { it == ' ' }
        val needsPadding = value.startsWith('`') ||
            value.endsWith('`') ||
            (!isAllSpaces && (value.startsWith(' ') || value.endsWith(' ')))
        val content = if (needsPadding) " $value " else value
        return delimiter + content + delimiter
    }

    private fun longestBacktickRun(value: String): Int {
        var longest = 0
        var current = 0
        for (character in value) {
            if (character == '`') {
                current += 1
                longest = maxOf(longest, current)
            } else {
                current = 0
            }
        }
        return longest
    }

    private fun normalizeText(value: CharSequence): String = value.toString()
        .replace("\r\n", "\n")
        .replace('\r', '\n')
}
