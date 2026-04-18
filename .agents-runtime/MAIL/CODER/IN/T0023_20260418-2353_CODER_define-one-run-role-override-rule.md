# TASK — formal ticket

Role: CODER
TicketId: T0023
Title: define-one-run-role-override-rule
CreatedAt: 2026-04-18 23:53
OwnerRequest: Owner хочет короткое общее правило для разовых прямых запросов вне обычной роли.
Правило должно требовать явного предупреждения перед выходом за обычные границы активной роли.
Workspace: `.`

## Scope
- Обновить `.agents/rules/specs/OPERATING_MODEL.md`, добавив короткое общее правило для разовых runtime override вне обычной активной роли.
- Явно закрепить, что перед выполнением такого действия агент обязан предупредить в чате, что выходит за обычные границы активной роли.
- Явно закрепить, что такой override действует только для текущего прогона и не становится постоянной ролевой нормой.
- Явно закрепить, что такой override не равен смене роли: после выполнения разового действия агент возвращается к обычным обязанностям своей активной роли, если Owner явно не указал иное.

## OutOfScope
- Не менять `AGENTS.md`.
- Не менять role-files, если не обнаружится прямое противоречие и минимальная правка формулировки не станет строго необходимой.
- Не переписывать historical `MAIL/**`, `RUNLOGS/**`, export/view materials и старые отчёты.
- Не обновлять `.agents-runtime/00_STATE.md`.
- Не создавать дополнительные тикеты или планы.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/LEAD.md`
- `.agents-runtime/MAIL/CODER/OUT/T0022_20260418-2321_CODER_define-staging-rules-for-new-project-and-process-files_report.md`

## RequiredSkills
- `process-consistency-lint` — чтобы не внести новый конфликт между operating/common/role layer.
- `process-artifact-workflow` — чтобы корректно оформить собственный `OUT`.

## Inputs
- Текущее решение Owner в чате:
  - разовые прямые запросы вне обычной роли допустимы как редкие runtime override;
  - такие override нужно трактовать как исключение на один прогон, а не как новую постоянную ролевую норму;
  - агент обязан явно предупредить в чате перед действием вне обычной ролевой границы;
  - такой override не должен трактоваться как автоматическая смена роли.
- Решение LEAD для этого тикета:
  - это правило должно жить в `.agents/rules/specs/OPERATING_MODEL.md` как общая operating-норма для всех ролей.

## Steps
1. Перечитать обязательные live rule/spec files и последний релевантный `CODER OUT`.
2. Добавить в `.agents/rules/specs/OPERATING_MODEL.md` короткое правило, что разовый прямой запрос Owner вне обычной роли допустим только как runtime override для текущего прогона.
3. В том же правиле явно закрепить, что перед таким действием агент обязан предупредить именно в чате, что выходит за обычные границы активной роли.
4. В том же правиле явно закрепить, что такой override не меняет роль постоянно, не переносится автоматически на следующие шаги и сам по себе не считается сменой роли.
5. В том же правиле явно закрепить, что после выполнения разового действия агент возвращается к обычным обязанностям своей активной роли, если Owner явно не указал иное.
6. Сохранить формулировку короткой, общей и применимой ко всем ролям.
7. Выполнить targeted consistency pass, чтобы новое правило не конфликтовало с `00_COMMON.md`, `CONTROL_FLOW.md` и текущей логикой role boundaries.
8. Подготовить `OUT`-отчёт с точными ссылками на файлы, проверками и узким follow-up, если он действительно нужен.

## Checks
- `rg -n "override|границ|role|runtime override|предупред" AGENTS.md .agents -S` -> подтверждает, что новая формулировка появилась в нужном live rule-layer.
- Manual consistency check across `.agents/rules/specs/OPERATING_MODEL.md`, `.agents/rules/00_COMMON.md` и `.agents/rules/specs/CONTROL_FLOW.md` -> подтверждает отсутствие нового противоречия.
- Если понадобится диагностика, складывать её в `.agents-runtime/RUNLOGS/T0023/`.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/CODER/OUT/T0023_20260418-2353_CODER_define-one-run-role-override-rule_report.md`
- дополнительные артефакты: only if genuinely needed for diagnostics

## StateImpact
- LEAD должен проверить resulting `CODER OUT`, после чего снять `T0023` из `Active` в `.agents-runtime/00_STATE.md`.

## Acceptance
- [ ] `.agents/rules/specs/OPERATING_MODEL.md` содержит короткое общее правило для разовых запросов Owner вне обычной роли.
- [ ] Правило явно требует предупреждения именно в чате перед действием вне обычной ролевой границы.
- [ ] Правило явно говорит, что override действует только на текущий прогон и не становится постоянной ролевой нормой.
- [ ] Правило явно говорит, что такой override не является сменой роли и не переносится автоматически на следующие шаги.
- [ ] Нерелевантные source-of-truth files и historical artifacts не переписаны.
- [ ] `CODER OUT` фиксирует точные формулировки правок и проведённые проверки.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: high
- comment: нужен аккуратный spec-layer edit без расползания в role drift и без новых process-конфликтов
