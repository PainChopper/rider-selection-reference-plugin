# TASK — formal ticket

Role: CODER
TicketId: T0025
Title: define-build-test-and-fix-flow-after-project-file-changes
CreatedAt: 2026-04-19 01:02
OwnerRequest: Owner хочет, чтобы `CODER` и `TESTER` после изменения project-files не останавливались на одной локальной правке.
Если в текущем прогоне менялись project-files, роль должна прогонять локально-релевантную сборку/проверку по изменённой области, затем полный build проекта или solution целиком, затем релевантные тесты.
Ошибки, найденные в этих шагах, нужно по возможности исправлять по ходу текущего прогона без остановки, пока это остаётся в пределах утверждённого scope и нет реального blocker.
Workspace: `.`

## Scope
- Обновить `.agents/roles/CODER.md`, добавив правило для прогонов с изменением project-files: локально-релевантная сборка/проверка, затем полный build проекта или solution, затем релевантные тесты.
- Обновить `.agents/roles/TESTER.md`, добавив аналогичное правило для `TESTER`.
- Явно закрепить, что правило действует только после изменения хотя бы одного project-file в текущем прогоне и не относится к process-only изменениям.
- Явно закрепить, что найденные build/test ошибки роль должна по возможности исправлять в рамках текущего прогона без остановки, пока это остаётся в пределах утверждённого scope и не возникает реальный blocker.
- Сформулировать правило так, чтобы оно не требовало несуществующего “build одного файла”, если tooling этого не поддерживает; вместо этого использовать локально-релевантную сборку/проверку изменённой области там, где это технически применимо.

## OutOfScope
- Не менять `AGENTS.md`.
- Не менять `.agents/rules/00_COMMON.md`, `.agents/rules/specs/OPERATING_MODEL.md` и `.agents/rules/specs/CONTROL_FLOW.md`, если не обнаружится прямое противоречие и минимальная правка не станет строго необходимой.
- Не вводить общее правило для всех ролей; это изменение нужно только для `CODER` и `TESTER`.
- Не переписывать historical `MAIL/**`, `RUNLOGS/**`, export/view materials и старые отчёты.
- Не обновлять `.agents-runtime/00_STATE.md`.
- Не создавать дополнительные тикеты или планы.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/CODER.md`
- `.agents/roles/TESTER.md`
- `.agents-runtime/MAIL/CODER/OUT/T0024_20260419-0010_CODER_define-commit-message-suggestion-rule-for-coder-and-tester_report.md`

## RequiredSkills
- `process-consistency-lint` — чтобы не внести новый конфликт между role-layer и live rule/spec layer.
- `process-artifact-workflow` — чтобы корректно оформить собственный `OUT`.
- `intellij-platform-build-compat` — чтобы формулировка про build/check/test не противоречила реальным ограничениям IntelliJ/Rider/solution workflow.

## Inputs
- Текущее решение Owner в чате:
  - после изменений project-files `CODER` или `TESTER` должны делать build по изменённой области, затем build проекта/solution целиком;
  - после этого они должны прогонять релевантные тесты;
  - найденные ошибки нужно по возможности чинить по ходу текущего прогона, без преждевременной остановки.
- Решение LEAD для этого тикета:
  - правило должно жить только в `.agents/roles/CODER.md` и `.agents/roles/TESTER.md`;
  - формулировка должна быть операционной и учитывать, что “build отдельного файла” поддерживается не во всех toolchains;
  - если ошибка выводит прогон за утверждённый scope или упирается во внешний blocker, роль всё ещё обязана честно остановиться как `BLOCKED` или `FAILED`, а не бесконечно расползаться.

## Steps
1. Перечитать обязательные live rule/spec files, текущие `CODER.md` и `TESTER.md`, а также последний релевантный `CODER OUT`.
2. Добавить в `.agents/roles/CODER.md` короткое правило, что после изменения хотя бы одного project-file в текущем прогоне `CODER` обязан:
   - прогнать локально-релевантную сборку/проверку изменённой области там, где это технически применимо;
   - затем прогнать полный build проекта или solution целиком;
   - затем прогнать релевантные тесты.
3. Добавить в `.agents/roles/TESTER.md` такое же правило для `TESTER`, адаптированное к testing/validation формулировкам, но с тем же смыслом.
4. Явно закрепить в обоих role-files, что build/test ошибки по возможности нужно исправлять по ходу текущего прогона без остановки, пока исправление остаётся в пределах утверждённого scope и нет реального blocker.
5. Явно закрепить, что правило не распространяется на прогоны, где менялись только process-файлы.
6. Сохранить формулировки короткими, операционными и одинаково читаемыми в обоих role-files.
7. Выполнить targeted consistency pass, чтобы новое правило не конфликтовало с `OPERATING_MODEL.md`, `00_COMMON.md`, `CONTROL_FLOW.md`, текущими process-boundaries и existing blocker/failure policy.
8. Подготовить `OUT`-отчёт с точными ссылками на файлы, фактическими формулировками и проведёнными проверками.

## Checks
- `rg -n "build|solution|test|project-file|process-файл|blocker|FAILED|BLOCKED" .agents/roles/CODER.md .agents/roles/TESTER.md -S` -> подтверждает, что правило появилось в обоих role-files.
- Manual consistency check across `.agents/roles/CODER.md`, `.agents/roles/TESTER.md`, `.agents/rules/specs/OPERATING_MODEL.md`, `.agents/rules/00_COMMON.md` и `.agents/rules/specs/CONTROL_FLOW.md` -> подтверждает отсутствие нового противоречия.
- Если понадобится диагностика, складывать её в `.agents-runtime/RUNLOGS/T0025/`.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/CODER/OUT/T0025_20260419-0102_CODER_define-build-test-and-fix-flow-after-project-file-changes_report.md`
- дополнительные артефакты: only if genuinely needed for diagnostics

## StateImpact
- LEAD должен проверить resulting `CODER OUT`, после чего снять `T0025` из `Active` в `.agents-runtime/00_STATE.md`.

## Acceptance
- [ ] `.agents/roles/CODER.md` содержит правило про локально-релевантную build/check фазу, затем полный build проекта или solution, затем релевантные тесты после изменения project-files.
- [ ] `.agents/roles/TESTER.md` содержит такое же по смыслу правило.
- [ ] Оба правила явно говорят, что найденные build/test ошибки по возможности исправляются в текущем прогоне без остановки, пока это остаётся в пределах scope и нет реального blocker.
- [ ] Оба правила явно не распространяются на прогоны, где менялись только process-файлы.
- [ ] Нерелевантные source-of-truth files и historical artifacts не переписаны.
- [ ] `CODER OUT` фиксирует точные формулировки правок и проведённые проверки.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: high
- comment: нужен аккуратный role-layer edit, который усилит build/test discipline без двусмысленности и без конфликта с blocker policy
