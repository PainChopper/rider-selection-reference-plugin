# TASK — formal ticket

Role: REVIEWER
TicketId: T0029
Title: review-rule-layer-cleanup-from-t0028
CreatedAt: 2026-04-19 04:39
OwnerRequest: Owner хочет доскональный review незакоммиченных изменений после `T0028`.
Проверка должна идти по каждому изменению через `git diff`, без коммита и без скрытой доработки вместо review.
Нужно проверить, что зачистка override/role-switch language выполнена полно и без побочных поломок в live rule-layer.
Workspace: `.`

## Scope
- Провести code/process review незакоммиченных изменений, относящихся к `T0028`.
- Проверить каждый изменённый live rule/spec/role file из `T0028` на:
  - полноту зачистки override/role-switch language;
  - отсутствие новых противоречий;
  - отсутствие случайных смысловых регрессий в process-flow и role boundaries.
- Основываться прежде всего на `git diff` и текущем worktree state.
- Подготовить review judgement и findings без внесения правок.

## OutOfScope
- Не исправлять найденные проблемы самому.
- Не редактировать `00_STATE.md`, чужие `OUT` или сами rule files.
- Не расползаться на review несвязанных грязных изменений рабочего дерева, если они не мешают оценке `T0028`.
- Не делать commit, stage или cleanup вместо review.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/LEAD.md`
- `.agents/roles/REVIEWER.md`
- `.agents-runtime/MAIL/CODER/IN/T0028_20260419-0429_CODER_remove-role-override-and-role-switch-language-from-rules.md`
- `.agents-runtime/MAIL/CODER/OUT/T0028_20260419-0429_CODER_remove-role-override-and-role-switch-language-from-rules_report.md`

## RequiredSkills
- `process-consistency-lint` — чтобы проверить, не осталось ли в live layer хвостов override/role-switch language и не появились ли новые process-противоречия.

## Inputs
- `T0028` изменяет live rule-layer:
  - `.agents/rules/specs/OPERATING_MODEL.md`
  - `.agents/rules/specs/CONTROL_FLOW.md`
  - `.agents/roles/LEAD.md`
- У Owner есть требование на доскональный review каждого изменения.
- Worktree грязный; review должен опираться на `git diff` и изолировать именно изменения `T0028`, не смешивая их с несвязанным мусором без необходимости.

## Steps
1. Прочитать обязательные файлы и понять исходный intent `T0028`.
2. Снять `git diff` по изменённым файлам `T0028` и проверить каждое изменение построчно.
3. Проверить, действительно ли из live layer убраны формулировки про runtime override, смену/переключение роли и exception-хвосты на выполнение чужой роли.
4. Проверить, не появились ли новые двусмысленности, процессные дыры или регрессии после упрощения формулировок.
5. Сравнить фактические изменения с тем, что заявлено в `CODER OUT`.
6. Выпустить `REVIEWER OUT` с findings first, затем verdict и residual risks.

## Checks
- `git diff -- .agents/rules/specs/OPERATING_MODEL.md .agents/rules/specs/CONTROL_FLOW.md .agents/roles/LEAD.md` -> основной review surface по `T0028`.
- `rg -n -S "override|переключить роль|смена роли|вне обычных границ|без прямого разрешения Owner|если это не поручено отдельно" .agents AGENTS.md` -> подтверждает остаточные хвосты или их отсутствие.
- Manual consistency check across `AGENTS.md`, `00_COMMON.md`, `OPERATING_MODEL.md`, `CONTROL_FLOW.md`, `LEAD.md` -> подтверждает отсутствие новых противоречий.
- Если понадобятся длинные выписки, складывать их в `.agents-runtime/RUNLOGS/T0029/`.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/REVIEWER/OUT/T0029_20260419-0439_REVIEWER_review-rule-layer-cleanup-from-t0028_report.md`
- дополнительные артефакты: only if genuinely needed

## StateImpact
- После review `LEAD` должен снять `T0029` из `Active` и решить, возвращать ли `T0028` из `Suspended` к доработке или закрывать.

## Acceptance
- [ ] Проверены все изменения `T0028` по `git diff`.
- [ ] Findings, если они есть, перечислены первыми и с опорой на конкретные файлы/строки.
- [ ] Есть ясный review verdict о готовности результата `T0028`.
- [ ] Несвязанные изменения worktree не смешаны с review `T0028` без необходимости.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: high
- comment: нужен внимательный review process/rule changes с focus на scope, contradictions и residual override language
