# TASK — formal ticket

Role: CODER
TicketId: T0028
Title: remove-role-override-and-role-switch-language-from-rules
CreatedAt: 2026-04-19 04:29
OwnerRequest: Owner хочет вычистить из live rule-layer любые упоминания про runtime override, смену роли, переключение роли и допустимость выполнять чужую роль по отдельному разрешению.
В правилах не нужно описывать такие исключения вообще: есть роли, роль статична в рамках прогона, агент обязан выполнять только работу своей роли и не выполнять чужую.
Нужно именно зачистить старые формулировки, а не добавлять новую сложную механику поверх них.
Workspace: `.`

## Scope
- Обновить live rule/spec/role files так, чтобы в них больше не было формулировок про runtime override, переключение роли, смену роли в чате и допустимость выполнять чужую роль по отдельному разрешению.
- Упростить формулировки границ ролей до жёстких, статичных role boundaries без exception-хвостов.
- Сохранить при этом базовую bootstrap/model/process логику репозитория, не ломая ownership state, handoff и process-flow.
- Подготовить `CODER OUT` с точным списком удалённых/упрощённых формулировок и проведённых проверок.

## OutOfScope
- Не менять process-артефакты вне собственного `OUT`.
- Не переписывать historical `MAIL/**`, `RUNLOGS/**`, advisory materials и старые отчёты.
- Не вводить новую механику role migration, explicit re-entry или другой substitute вместо удаляемых override-формулировок.
- Не менять смысл правил, не связанный с role boundaries, handoff discipline и статичностью роли.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/LEAD.md`
- `.agents/roles/ANALYST.md`
- `.agents/roles/CODER.md`
- `.agents/roles/TESTER.md`
- `.agents/roles/VERIFIER.md`
- `.agents/roles/REVIEWER.md`

## RequiredSkills
- `process-consistency-lint` — чтобы после зачистки не осталось противоречий между rules/specs/roles и process-model.
- `process-artifact-workflow` — чтобы корректно оформить собственный `OUT`.

## Inputs
- Owner решение в чате:
  - в правилах не нужно вообще заикаться про runtime override, смену роли, переключение роли и допустимость выйти за границы активной роли;
  - агент должен мыслить роль как статичную в рамках прогона;
  - никто не исполняет чужих ролей.
- `LEAD` exploratory scan уже выявил основные live-path точки риска:
  - `.agents/rules/specs/OPERATING_MODEL.md`
  - `.agents/roles/LEAD.md`
  - возможные conditional-exception хвосты в `Role boundaries` и других role-files.

## Steps
1. Перечитать обязательные live files и найти все формулировки про runtime override, смену/переключение роли и exception-хвосты вида `если это не поручено отдельно` / `без прямого разрешения Owner`, когда речь идёт о выполнении чужой роли.
2. Удалить из live rule-layer упоминания про runtime override authority и смену роли в чате.
3. Упростить role-boundary формулировки до статичной модели: есть активная роль, она выполняет только свою работу и не выполняет чужую.
4. Обновить `LEAD.md`, убрав любые лазейки на исполнение работы других ролей.
5. Проверить остальные role-files и specs, чтобы не осталось рассыпанных exception-формулировок по той же теме.
6. После изменения project-files прогнать локально-релевантную проверку, затем полный build проекта или solution, затем релевантные тесты; найденные проблемы по возможности исправить в пределах scope.
7. Подготовить `OUT` с перечислением конкретно удалённых/заменённых формулировок и итоговым consistency verdict.

## Checks
- `rg -n -S "override|переключить роль|смена роли|вне обычных границ|без прямого разрешения Owner|если это не поручено отдельно" .agents AGENTS.md` -> подтверждает, что опасные формулировки либо удалены, либо остались только там, где не относятся к выполнению чужой роли.
- Manual consistency check across `AGENTS.md`, `.agents/rules/00_COMMON.md`, `.agents/rules/specs/OPERATING_MODEL.md`, `.agents/rules/specs/CONTROL_FLOW.md` и `.agents/roles/*.md` -> подтверждает, что role model стала статичной и без новых противоречий.
- Локально-релевантная проверка изменённых markdown/rule files -> подтверждает, что правки консистентны по смыслу и не оставляют явных обрывков.
- Если отдельного build/test сигнала для таких изменений нет, это нужно честно зафиксировать в `OUT`, а не выдумывать его.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/CODER/OUT/T0028_20260419-0429_CODER_remove-role-override-and-role-switch-language-from-rules_report.md`
- дополнительные артефакты: only if genuinely needed

## StateImpact
- `LEAD` должен прочитать resulting `CODER OUT`, после чего снять `T0028` из `Active` в `.agents-runtime/00_STATE.md`.

## Acceptance
- [ ] В live rule/spec/role files больше нет формулировок про runtime override и смену роли как допустимый рабочий механизм.
- [ ] Убраны exception-хвосты, допускающие выполнение чужой роли по отдельному разрешению.
- [ ] `LEAD` и другие роли описаны как статичные role-boundaries без двусмысленных лазеек.
- [ ] Не введена новая substitute-механика вместо удалённых override-формулировок.
- [ ] `CODER OUT` фиксирует точные изменения и проведённые проверки.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.3-Codex
- reasoning effort: high
- comment: нужен аккуратный live rule-layer cleanup без добавления новой механики поверх удаляемых исключений
