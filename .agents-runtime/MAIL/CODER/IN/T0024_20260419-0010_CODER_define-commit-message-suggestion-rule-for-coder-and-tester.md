# TASK — formal ticket

Role: CODER
TicketId: T0024
Title: define-commit-message-suggestion-rule-for-coder-and-tester
CreatedAt: 2026-04-19 00:10
OwnerRequest: Owner хочет, чтобы `CODER` и `TESTER` держали рядом свежую подсказку для коммита.
Если в текущем прогоне роль изменила хотя бы один проектный файл, в конце каждого своего содержательного сообщения в чате она должна предлагать один вариант commit message на английском.
Этот вариант должен быть коротким, не многословным, не длиннее 80 символов и описывать весь рабочий результат текущего чата, а не только последнее изменение.
Workspace: `.`

## Scope
- Обновить `.agents/roles/CODER.md`, добавив правило про свежую commit-message подсказку после изменения хотя бы одного проектного файла в текущем прогоне.
- Обновить `.agents/roles/TESTER.md`, добавив такое же правило для `TESTER`.
- Явно закрепить, что подсказка даётся в конце каждого содержательного сообщения в чате после того, как роль уже изменила хотя бы один проектный файл в текущем прогоне.
- Явно закрепить, что это должен быть один короткий commit message на английском длиной не более 80 символов.
- Явно закрепить, что подсказка должна суммировать весь релевантный рабочий результат текущего чата/прогона, а не только последнюю локальную правку.

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
- `.agents-runtime/MAIL/CODER/OUT/T0023_20260418-2353_CODER_define-one-run-role-override-rule_report.md`

## RequiredSkills
- `process-consistency-lint` — чтобы не внести новый конфликт между role-layer и live rule/spec layer.
- `process-artifact-workflow` — чтобы корректно оформить собственный `OUT`.

## Inputs
- Текущее решение Owner в чате:
  - правило нужно именно для `CODER` и `TESTER`;
  - если роль изменила хотя бы один проектный файл в текущем прогоне, в конце каждого своего содержательного сообщения в чате она должна давать свежую подсказку для commit message;
  - подсказка должна быть на английском, не длиннее 80 символов и без лишней многословности;
  - подсказка должна покрывать весь рабочий результат текущего чата/прогона, а не только последний локальный шаг.
- Решение LEAD для этого тикета:
  - правило должно жить только в `.agents/roles/CODER.md` и `.agents/roles/TESTER.md`;
  - правило не должно распространяться на случаи, когда в текущем прогоне менялись только process-файлы и не менялись project-файлы;
  - для единообразия правило может задать короткий стабильный префикс вроде `Commit suggestion:`.

## Steps
1. Перечитать обязательные live rule/spec files, текущие `CODER.md` и `TESTER.md`, а также последний релевантный `CODER OUT`.
2. Добавить в `.agents/roles/CODER.md` короткое ролевое правило, что после изменения хотя бы одного project-file в текущем прогоне `CODER` обязан в конце каждого своего содержательного сообщения в чате давать один актуальный вариант commit message.
3. Добавить в `.agents/roles/TESTER.md` такое же правило для `TESTER`.
4. Явно закрепить в этих правилах, что commit message должен:
   - быть на английском;
   - быть не длиннее 80 символов;
   - быть коротким и не многословным;
   - суммировать весь релевантный рабочий результат текущего чата/прогона, а не только последнюю локальную правку.
5. Явно закрепить, что это правило начинает действовать только после изменения хотя бы одного project-file в текущем прогоне и не требует commit-подсказки, если роль меняла только process-файлы.
6. Сохранить формулировки короткими, операционными и одинаково читаемыми в обоих role-files.
7. Выполнить targeted consistency pass, чтобы новое правило не конфликтовало с `OPERATING_MODEL.md`, `00_COMMON.md`, `CONTROL_FLOW.md` и текущими process-boundaries.
8. Подготовить `OUT`-отчёт с точными ссылками на файлы, фактическими формулировками и проведёнными проверками.

## Checks
- `rg -n "commit|Commit suggestion|80|project-file|проект" .agents/roles/CODER.md .agents/roles/TESTER.md -S` -> подтверждает, что правило появилось в обоих role-files.
- Manual consistency check across `.agents/roles/CODER.md`, `.agents/roles/TESTER.md`, `.agents/rules/specs/OPERATING_MODEL.md`, `.agents/rules/00_COMMON.md` и `.agents/rules/specs/CONTROL_FLOW.md` -> подтверждает отсутствие нового противоречия.
- Если понадобится диагностика, складывать её в `.agents-runtime/RUNLOGS/T0024/`.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/CODER/OUT/T0024_20260419-0010_CODER_define-commit-message-suggestion-rule-for-coder-and-tester_report.md`
- дополнительные артефакты: only if genuinely needed for diagnostics

## StateImpact
- LEAD должен проверить resulting `CODER OUT`, после чего снять `T0024` из `Active` в `.agents-runtime/00_STATE.md`.

## Acceptance
- [ ] `.agents/roles/CODER.md` содержит правило про обязательную commit-message подсказку после изменения хотя бы одного project-file в текущем прогоне.
- [ ] `.agents/roles/TESTER.md` содержит такое же правило.
- [ ] Правило явно требует один актуальный commit message на английском длиной не более 80 символов.
- [ ] Правило явно говорит, что подсказка должна суммировать весь релевантный результат текущего чата/прогона, а не только последнюю локальную правку.
- [ ] Правило явно не распространяется на прогоны, где менялись только process-файлы.
- [ ] Нерелевантные source-of-truth files и historical artifacts не переписаны.
- [ ] `CODER OUT` фиксирует точные формулировки правок и проведённые проверки.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: high
- comment: нужен аккуратный role-layer edit без расползания в общий process-layer и без двусмысленности в chat-behavior
