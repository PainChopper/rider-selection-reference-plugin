# T0031 — Implement Human-Readable Headings For Templates And Rules

Status: DONE
CreatedAt: 19.04.2026 15:16
TicketId: T0031
Title: implement-human-readable-headings-for-templates-and-rules
SourceTicket: .agents-runtime/MAIL/CODER/IN/T0031_20260419-0454_CODER_implement-human-readable-headings-for-templates-and-rules.md
Workspace: D:\.DEV\JetBrainsPlagins

## Summary
Выполнен минимально достаточный scope по T0031: template-примеры formal артефактов переведены на H1-формат # <TicketId> — <Human Title>, а в live process-rule добавлена короткая норма про разделение display H1 и slug-based Title:.
Filename scheme не изменялась, массовый rewrite исторических MAIL/** не выполнялся.

## Delivered
- Обновлены .agents/templates/TASK.md, .agents/templates/REPORT.md, .agents/templates/VERIFY.md под human-readable H1 в template-примерах.
- В REPORT.md и VERIFY.md добавлены поля TicketId: и Title: в markdown-template для явной фиксации связки с process-метаданными.
- В .agents/rules/specs/CONTROL_FLOW.md добавлен минимальный раздел Формат heading и Title в formal MAIL.
- .agents/templates/PLAN.md проверен и оставлен без изменений как не-MAIL и не formal IN/OUT артефакт.

## BeforeAfterExamples
- TASK H1:
  - before: # TASK — formal ticket
  - after: # <TicketId> — <Human Title>
- REPORT template H1:
  - before: # <ROLE> OUT REPORT
  - after: # <TicketId> — <Human Title>
- VERIFY template H1:
  - before: # VERIFY REPORT
  - after: # <TicketId> — <Human Title>
- Live rule (new):
  - formal IN/OUT используют H1 # <TicketId> — <Human Title>;
  - Title: в formal IN/OUT остаётся machine-stable slug-based.

## ChangedFiles
- .agents/templates/TASK.md
- .agents/templates/REPORT.md
- .agents/templates/VERIFY.md
- .agents/rules/specs/CONTROL_FLOW.md
- .agents-runtime/MAIL/CODER/OUT/T0031_20260419-0454_CODER_implement-human-readable-headings-for-templates-and-rules_report.md

## ChecksRun
- rg -n "^# " .agents/templates
- rg -n "^Title:" .agents/templates .agents-runtime/MAIL
- Manual consistency check:
  - .agents/templates/TASK.md
  - .agents/templates/REPORT.md
  - .agents/templates/VERIFY.md
  - .agents/templates/PLAN.md
  - .agents/rules/specs/CONTROL_FLOW.md
  - .agents-runtime/MAIL/ANALYST/OUT/T0030_20260419-0448_ANALYST_assess-ticket-and-report-heading-format_report.md
- Build/test не запускались: изменения process/template markdown-only, технического build/test сигнала для этого scope нет.

## ProcessConsistencyLint
- AGENTS.md продолжает вести в .agents/**: PASS.
- Helper templates не подменяют source of truth: PASS.
- Новая формулировка в CONTROL_FLOW не конфликтует с ownership/state/staging правилами: PASS.
- Legacy-path ссылок .aiassistant/.windsurf/sandbox в затронутых live файлах не добавлено: PASS.

## Artifacts
- нет

## Issues
- нет

## FollowUps
- LEAD: прочитать этот CODER OUT и выполнить следующий process-step по 00_STATE.md.
