# TASK — formal ticket

Role: ANALYST
TicketId: T0030
Title: assess-ticket-and-report-heading-format
CreatedAt: 2026-04-19 04:48
OwnerRequest: Owner хочет оценить, как лучше изменить заголовки в тикетах и отчётах.
Текущий формат вида `# TASK — formal ticket` и аналогичные шапки кажутся неудачными; Owner предпочитает заголовок по смыслу, например `# <TicketId> — <Human Title>` с нормальными пробелами, регистром и читаемостью.
Нужно аналитически оценить, какие шаблоны и process-правила это затрагивает, что именно стоит поменять, и какие риски/последствия будут у такого перехода.
Workspace: `.`

## Scope
- Проанализировать текущий формат заголовков в live templates и связанных process-артефактах.
- Определить, какие шаблоны и, при необходимости, какие live rules затрагиваются переходом на human-readable heading format для тикетов и отчётов.
- Сформулировать рекомендуемый новый формат заголовков для:
  - formal `IN` tickets;
  - formal `OUT` reports;
  - при необходимости `VERIFY`- и plan-like документов, если это влияет на консистентность.
- Оценить риски и совместимость: file naming, `Title:` field, process readability, handoff clarity, machine stability.
- Подготовить change proposal для следующего `CODER`, но не вносить изменения самому.

## OutOfScope
- Не менять шаблоны, rules или process-файлы.
- Не запускать `CODER`, `REVIEWER` или `VERIFIER`.
- Не расползаться в редизайн всего markdown-style beyond headings.
- Не предлагать несколько равноправных вариантов без одной финальной рекомендации.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`
- `.agents/templates/VERIFY.md`
- `.agents/templates/PLAN.md`
- `.agents/templates/LEAD_PROMPT.md`
- свежие живые примеры:
  - `.agents-runtime/MAIL/CODER/IN/T0028_20260419-0429_CODER_remove-role-override-and-role-switch-language-from-rules.md`
  - `.agents-runtime/MAIL/CODER/OUT/T0028_20260419-0429_CODER_remove-role-override-and-role-switch-language-from-rules_report.md`
  - `.agents-runtime/MAIL/REVIEWER/IN/T0029_20260419-0439_REVIEWER_review-rule-layer-cleanup-from-t0028.md`

## RequiredSkills
- `process-consistency-lint` — чтобы change proposal не сломал process-слой и naming/ownership discipline.

## Inputs
- Owner прямо считает текущие заголовки вроде `TASK — formal ticket` и `REPORT — execution report` слабыми и предпочитает human-readable heading.
- Предварительное направление от `LEAD`: формат вида `# <TicketId> — <Human Title>` выглядит сильным кандидатом.
- Owner отдельно уточнил, что title в heading должен быть с пробелами и нормальным регистром, а не slug с дефисами.
- Важно не “напортачить правилами” и не усложнить process больше, чем нужно.

## Steps
1. Прочитать обязательные templates, live rules и свежие примеры тикетов/отчётов.
2. Определить, какие именно документы сегодня несут бессмысленную типовую шапку вместо полезного заголовка.
3. Сформулировать одну рекомендуемую heading scheme для ticket/report/related documents.
4. Отдельно оценить, должен ли `Title:` field оставаться slug-based или его тоже стоит переводить в human-readable вид.
5. Описать, какие шаблоны и, если нужно, какие правила должен будет менять `CODER`.
6. Подготовить change proposal и handoff для следующего `CODER` без реализации.

## Checks
- Manual consistency check across templates and live examples -> подтверждает, что рекомендуемый heading format применим единообразно.
- Impact check for file naming vs heading content -> подтверждает, что machine-stable filename можно сохранить отдельно от human-readable heading.
- Если понадобятся длинные заметки, складывать их в `.agents-runtime/RUNLOGS/T0030/`.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/ANALYST/OUT/T0030_20260419-0448_ANALYST_assess-ticket-and-report-heading-format_report.md`
- дополнительные артефакты: only if genuinely needed

## StateImpact
- После чтения `ANALYST OUT` `LEAD` должен решить, нужен ли следующий `CODER`-тикет на обновление шаблонов и, возможно, live documents/rules.

## Acceptance
- [ ] Есть одна финальная рекомендация по heading format для тикетов и отчётов.
- [ ] Ясно описано, какие templates и какие live files это затрагивает.
- [ ] Оценены риски для process consistency и machine readability.
- [ ] Подготовлен change proposal для следующего `CODER`.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: high
- comment: нужен короткий, но аккуратный аналитический проход по template/process consistency и human-readable heading scheme
