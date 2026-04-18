$ErrorActionPreference = "Stop"

$utf8 = [System.Text.UTF8Encoding]::new($false)
$targets = Get-ChildItem -Path "D:\.DEV\JetBrainsPlagins\.agents-runtime\MAIL" -Recurse -File -Filter "*.md"

$replacements = [ordered]@{
    ".aiassistant/rules/specs/CONTROL_FLOW.md" = ".agents/rules/specs/CONTROL_FLOW.md"
    ".aiassistant/rules/00_COMMON.md" = ".agents/rules/00_COMMON.md"
    ".aiassistant/rules/LEAD.md" = ".agents/roles/LEAD.md"
    ".aiassistant/rules/ANALYST.md" = ".agents/roles/ANALYST.md"
    ".aiassistant/rules/CODER.md" = ".agents/roles/CODER.md"
    ".aiassistant/rules/TESTER.md" = ".agents/roles/TESTER.md"
    ".aiassistant/rules/VERIFIER.md" = ".agents/roles/VERIFIER.md"
    ".aiassistant/rules/REVIEWER.md" = ".agents/roles/REVIEWER.md"
    ".aiassistant/skills/PROCESS_ARTIFACT_WORKFLOW.md" = ".agents/skills/process-artifact-workflow/SKILL.md"
    ".aiassistant/skills/PROCESS_CONSISTENCY_LINT.md" = ".agents/skills/process-consistency-lint/SKILL.md"
    ".aiassistant/skills/INTELLIJ_PLATFORM_BUILD_COMPAT.md" = ".agents/skills/intellij-platform-build-compat/SKILL.md"
    ".aiassistant/skills/RIDER_DIFF_CONTEXT_COPY.md" = ".agents/skills/rider-diff-context-copy/SKILL.md"
    ".aiassistant/skills/IDE_LANGUAGE_DETECTION.md" = ".agents/skills/ide-language-detection/SKILL.md"
    ".aiassistant/skills" = ".agents/skills"
    ".aiassistant/rules/specs/" = ".agents/rules/specs/"
    ".aiassistant/rules/" = ".agents/rules/"
    ".aiassistant/skills/" = ".agents/skills/"
    ".windsurf/sandbox/CHATGPT_54_PRO_RULE_LAYER_PACK.zip" = ".agents-runtime/CHATGPT_54_PRO_RULE_LAYER_PACK.zip"
    ".windsurf/sandbox/CHATGPT_54_PRO_RULE_LAYER_PACK" = ".agents-runtime/CHATGPT_54_PRO_RULE_LAYER_PACK"
    ".windsurf/sandbox/TEMPLATES/" = ".agents/templates/"
    ".windsurf/sandbox/00_STATE.md" = ".agents-runtime/00_STATE.md"
    ".windsurf/sandbox/MAIL/" = ".agents-runtime/MAIL/"
    ".windsurf/sandbox/RUNLOGS/" = ".agents-runtime/RUNLOGS/"
    ".windsurf/sandbox/PLANS/" = ".agents-runtime/PLANS/"
    ".windsurf/sandbox/**" = ".agents-runtime/**"
}

$changed = @()
foreach ($target in $targets) {
    $content = [System.IO.File]::ReadAllText($target.FullName)
    $updated = $content
    foreach ($pair in $replacements.GetEnumerator()) {
        $updated = $updated.Replace($pair.Key, $pair.Value)
    }

    if ($updated -ne $content) {
        [System.IO.File]::WriteAllText($target.FullName, $updated, $utf8)
        $changed += $target.FullName
    }
}

$logPath = "D:\.DEV\JetBrainsPlagins\.agents-runtime\RUNLOGS\T0020\rewrite-live-runtime.log"
$log = @(
    "Targets scanned: $($targets.Count)"
    "Files changed: $($changed.Count)"
    ""
) + $changed
[System.IO.File]::WriteAllLines($logPath, $log, $utf8)

Write-Output "Targets scanned: $($targets.Count)"
Write-Output "Files changed: $($changed.Count)"
