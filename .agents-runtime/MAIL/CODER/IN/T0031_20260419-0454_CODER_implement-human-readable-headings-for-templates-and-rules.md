# TASK — formal ticket

Role: CODER
TicketId: T0031
Title: implement-human-readable-headings-for-templates-and-rules
CreatedAt: 2026-04-19 04:54
OwnerRequest: Owner хочет заменить слабые generic heading-шапки в тикетах и отчётах на human-readable формат.
По итогам `T0030` целевой формат: `# <TicketId> — <Human Title>` с нормальными пробелами и регистром, при этом `Title:` должен оставаться machine-stable slug.
Нужно внедрить это в шаблоны и минимально закрепить в live process-doc, не переписывая исторические `MAIL/**` массово.
Workspace: `.`

## Scope
- Обновить template-примеры в `.agents/templates/TASK.md` и `.agents/templates/REPORT.md` под новый H1-формат `# <TicketId> — <Human Title>`.
- Для консистентности обновить `.agents/templates/VERIFY.md`.
- Оценить и при необходимости обновить `.agents/templates/PLAN.md` только если это действительно нужно для единообразия heading-style.
- Внести минимально достаточную live rule/process формулировку, что formal `IN`/`OUT` используют human-readable H1, а `Title:` остаётся slug-based.
- Не менять filename scheme и не менять смысл поля `Title:`.
- Подготовить `CODER OUT` с примерами до/после и проведёнными проверками.

## OutOfScope
- Не переписывать исторические `MAIL/**` массово.
- Не менять naming файлов тикетов и отчётов.
- Не переводить `Title:` в human-readable вид.
- Не расползаться в полный markdown-style redesign beyond heading guidance.
- Не менять process/state files, кроме собственного `OUT`.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/CODER.md`
- `.agents-runtime/MAIL/ANALYST/OUT/T0030_20260419-0448_ANALYST_assess-ticket-and-report-heading-format_report.md`
- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`
- `.agents/templates/VERIFY.md`
- `.agents/templates/PLAN.md`
- `.agents/templates/LEAD_PROMPT.md`

## RequiredSkills
- `process-consistency-lint` — чтобы не сломать process-layer и не создать противоречие между templates и live rules.
- `process-artifact-workflow` — чтобы корректно оформить собственный `OUT`.

## Inputs
- Аналитик в `T0030` рекомендует единый H1-формат: `# <TicketId> — <Human Title>`.
- Аналитик отдельно рекомендует оставить `Title:` slug-based и не трогать filename.
- Owner хочет human-readable title с пробелами и нормальным регистром, а не kebab-case в heading.
- Минимальный обязательный template scope: `TASK.md` и `REPORT.md`; `VERIFY.md` рекомендован для консистентности.

## Steps
1. Перечитать обязательные live/process files, templates и analyst `OUT`.
2. Обновить `.agents/templates/TASK.md` и `.agents/templates/REPORT.md` под новый human-readable H1-формат.
3. Обновить `.agents/templates/VERIFY.md` по той же heading-логике.
4. Проверить, нужен ли такой же change в `.agents/templates/PLAN.md`; если нужен — внести минимально достаточную правку, если нет — явно зафиксировать это в `OUT`.
5. Добавить минимальную process/rule формулировку про H1 vs `Title:` slug, не разводя лишнюю механику.
6. После изменения project-files прогнать локально-релевантную проверку, затем полный build проекта или solution, затем релевантные тесты; если отдельного build/test сигнала для такого markdown/process change нет, честно зафиксировать это в `OUT`.
7. Подготовить `OUT` с примерами до/после, списком файлов и consistency verdict.

## Checks
- `rg -n "^# " .agents/templates` -> подтверждает новый heading-format в templates.
- `rg -n "^Title:" .agents/templates .agents-runtime/MAIL` -> подтверждает, что `Title:` остаётся отдельным полем и его формат не разрушен.
- Manual consistency check across `.agents/templates/TASK.md`, `.agents/templates/REPORT.md`, `.agents/templates/VERIFY.md`, optional `.agents/templates/PLAN.md`, `.agents/rules/specs/CONTROL_FLOW.md` и analyst `OUT` -> подтверждает единообразие и отсутствие нового противоречия.
- Если отдельного build/test сигнала для таких изменений нет, это должно быть явно сказано в `OUT`, а не замаскировано.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/CODER/OUT/T0031_20260419-0454_CODER_implement-human-readable-headings-for-templates-and-rules_report.md`
- дополнительные артефакты: only if genuinely needed

## StateImpact
- `LEAD` должен прочитать resulting `CODER OUT`, после чего снять `T0031` из `Active` в `.agents-runtime/00_STATE.md`.

## Acceptance
- [ ] `TASK.md` и `REPORT.md` используют новый human-readable H1-формат в template-примерах.
- [ ] `VERIFY.md` приведён к той же heading-логике.
- [ ] `Title:` сохранён как slug-based field и не смешан с display heading.
- [ ] Массовый rewrite исторических `MAIL/**` не выполнен.
- [ ] `CODER OUT` фиксирует точные изменения, примеры до/после и проведённые проверки.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.3-Codex
- reasoning effort: high
- comment: нужен аккуратный template/process edit без массового переписывания history и без смешения display heading с slug metadata
