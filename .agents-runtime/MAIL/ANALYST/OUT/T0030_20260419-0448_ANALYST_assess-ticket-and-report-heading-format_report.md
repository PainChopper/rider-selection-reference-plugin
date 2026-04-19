# ANALYST OUT REPORT

Status: `DONE`
CreatedAt: `19.04.2026 06:03`
SourceTicket: `.agents-runtime/MAIL/ANALYST/IN/T0030_20260419-0448_ANALYST_assess-ticket-and-report-heading-format.md`
Workspace: `D:\.DEV\JetBrainsPlagins`

## Summary
Проведён анализ текущего heading-format в template-слое и live `MAIL/**`-артефактах.
Текущая схема неоднородна: в части файлов стоит generic шапка (`# TASK — ...`, `# REPORT — ...`), в части — роль-ориентированные заголовки (`# CODER REPORT`, `# ANALYST OUT REPORT`), что снижает читаемость при последовательном просмотре тикетов.
Рекомендован единый human-readable heading format: первый H1 должен явно содержать `TicketId` и человекочитаемое имя задачи, независимое от slug в имени файла.

## CurrentStateAssessment
- Template `TASK.md` задаёт generic heading: `# TASK — formal ticket`.
- Template `REPORT.md` внутри markdown-примера задаёт generic/role-hybrid heading: `# <ROLE> OUT REPORT`.
- Live `MAIL/**` содержит смешение форматов:
- старые/часть новых IN: `# TASK — тикет` или `# TASK — formal ticket`;
- часть OUT: `# REPORT — execution report`;
- часть OUT: `# <ROLE> REPORT` / `# <ROLE> OUT REPORT`.
- Поле `Title:` в актуальных formal IN/OUT обычно slug-based (`kebab-case`) и совпадает со slug из имени файла.

## FinalRecommendation
Рекомендуемый единый H1-формат для formal тикетов и отчётов:
- `# <TicketId> — <Human Title>`

Где:
- `<TicketId>`: стабильный process-id вида `T0030`;
- `<Human Title>`: человекочитаемая фраза с пробелами и нормальным регистром (без kebab-case).

Примеры:
- IN: `# T0030 — Assess Ticket And Report Heading Format`
- OUT: `# T0030 — Heading Format Assessment Report`

## MarkdownHeadingDecision
- Для formal `MAIL/<ROLE>/IN/*.md` и `MAIL/<ROLE>/OUT/*_report.md` перейти на единый H1 `# <TicketId> — <Human Title>`.
- Для helper-шаблонов (`TASK.md`, `REPORT.md`, `VERIFY.md`, `PLAN.md`) оставить верхний doc-heading файла (`# TASK.md`, `# REPORT.md` и т.п.) как есть, но внутри markdown-template-примеров заменить рабочий H1 под новый формат.
- Для `VERIFY`-отчётов применить ту же логику консистентности: человекочитаемый H1 с `TicketId` и уточнением verification-смысла в `Human Title`.

## TitleFieldDecision
Рекомендация: поле `Title:` оставить и сохранить machine-stable slug-семантику.

Обоснование:
- slug уже синхронизирован с filename (`..._<slug>.md`) и полезен для grep/фильтрации/автогенерации;
- перенос `Title:` в human-readable форму создаст лишнюю двусмысленность между filename-slug и metadata-title, если не вводить отдельное новое поле;
- readability уже закрывается новым H1, поэтому для `Title:` лучше сохранить техническую стабильность.

Итог:
- `H1` = human-readable display-name;
- `Title:` = machine-stable slug.

## ImpactedTemplates
Обязательные изменения шаблонов:
- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`

Рекомендуемые для полной консистентности:
- `.agents/templates/VERIFY.md`
- `.agents/templates/PLAN.md` (если планам нужен тот же стиль человекочитаемого H1)

Технически не обязательно менять в рамках минимального тикета, но проверить на совпадение guidance:
- `.agents/templates/LEAD_PROMPT.md` (если там есть встроенные примеры, где упоминается формат heading)

## ImpactedLiveFiles
Обязательный live-impact (точечно, не массовый rewrite истории):
- новые формальные IN/OUT после внедрения (forward-only policy);
- активный/ближайший pipeline тикет после решения Owner (как первый живой пример).

Опционально (если Owner хочет выровнять последние артефакты для UX):
- `.agents-runtime/MAIL/ANALYST/IN/T0030_20260419-0448_ANALYST_assess-ticket-and-report-heading-format.md`
- `.agents-runtime/MAIL/CODER/IN/T0028_20260419-0429_CODER_remove-role-override-and-role-switch-language-from-rules.md`
- `.agents-runtime/MAIL/REVIEWER/IN/T0029_20260419-0439_REVIEWER_review-rule-layer-cleanup-from-t0028.md`
- соответствующие свежие OUT (`T0028`, `T0029`) только если Owner отдельно запросит backfill.

## ProcessConsistencyLint
По чеклисту `process-consistency-lint` (в рамках данного анализа):
- AGENTS bootstrap ведёт в `.agents/**`: PASS.
- helper/templates не декларируются как source of truth: PASS.
- `00_STATE.md` содержит одну запись `Active`: PASS.
- `Suspended` используется как отложенные тикеты: PASS.
- `BLOCKED` не используется как долговечный статус state: PASS.
- legacy-path ссылки `.aiassistant`/`.windsurf/sandbox` в live rule-layer не выявлены (hits только в historical/helper контексте): PASS.

## ChangeProposalForCoder
Цель следующего `CODER`-шага: внедрить новый heading-format без изменения filename и slug-поля `Title:`.

Предлагаемый scope:
1. Обновить template-примеры в `.agents/templates/TASK.md` и `.agents/templates/REPORT.md`:
- H1 в markdown-template заменить на `# <TicketId> — <Human Title>`.
2. Для полноты консистентности обновить `VERIFY.md` (и при решении Owner — `PLAN.md`) по той же схеме display-heading.
3. Добавить короткое правило в релевантный process-doc (минимально, без redesign):
- H1 в formal IN/OUT человекочитаемый;
- `Title:` остаётся slug-based и должен быть согласован с filename slug.
4. Не переписывать исторический `MAIL/**` массово; применять формат forward-only к новым артефактам.
5. Подготовить `CODER OUT` с примерами до/после и проверками консистентности.

Suggested checks для `CODER`:
- `rg -n "^# " .agents/templates .agents-runtime/MAIL/*/IN .agents-runtime/MAIL/*/OUT`
- `rg -n "^Title:" .agents/templates .agents-runtime/MAIL`
- manual check: H1 human-readable, `Title:` slug-stable, filename unchanged.

## Delivered
- Подготовлена единая финальная рекомендация по heading format.
- Дано отдельное решение по `markdown heading` и по полю `Title:`.
- Зафиксирован список затрагиваемых templates и live-files.
- Подготовлен change proposal для следующего `CODER` без реализации.

## ChangedFiles
- `.agents-runtime/MAIL/ANALYST/OUT/T0030_20260419-0448_ANALYST_assess-ticket-and-report-heading-format_report.md`

## ChecksRun
- `rg -n "^# " .agents/templates .agents-runtime/MAIL`
- `rg -n "^Title:" .agents/templates .agents-runtime/MAIL`
- `rg -n "^TicketId:" .agents/templates .agents-runtime/MAIL`
- `Get-Content -Raw .agents-runtime/00_STATE.md`
- `rg -n -S "\.aiassistant|\.windsurf/sandbox" AGENTS.md .agents .agents-runtime/MAIL`

## Artifacts
- `.agents-runtime/MAIL/ANALYST/OUT/T0030_20260419-0448_ANALYST_assess-ticket-and-report-heading-format_report.md`

## Issues
- Нет.

## FollowUps
- `LEAD` может выдать следующий `CODER`-тикет на template/process update по данному proposal.
