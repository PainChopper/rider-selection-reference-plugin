# TASK — тикет

Role: VERIFIER
TicketId: T0016
CreatedAt: 2026-04-17 18:03

## Контекст

В репозитории появился новый слой шаблонов process-артефактов:

- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`
- `.agents/templates/VERIFY.md`
- `.agents/templates/LEAD_PROMPT.md`

Нужно независимо проверить, насколько эти шаблоны совместимы с текущими rules/specs и фактической process-практикой.

Проверка нужна до того, как `TEMPLATES/*` будут восприниматься как нормальный template layer или source для дальнейшей стандартизации.

## Scope

- Прочитать `TEMPLATES/*`.
- Сверить их с:
  - `AGENTS.md`
  - `.agents/rules/00_COMMON.md`
  - `.agents/rules/specs/CONTROL_FLOW.md`
  - `.agents/roles/LEAD.md`
  - `.agents/roles/VERIFIER.md`
- При необходимости точечно сверить шаблоны с фактическими process-артефактами из `.agents-runtime/MAIL/**`.
- Подтвердить или опровергнуть:
  - что шаблоны не конфликтуют с rules/specs;
  - что шаблоны достаточно полны для текущей process-практики;
  - что роль `TEMPLATES/*` в process-layer понятна;
  - что `LEAD_PROMPT.md` не тянет устаревшую process-норму.

## Не делать

- Не переписывать шаблоны.
- Не переписывать rules/specs.
- Не подменять verification архитектурным redesign.
- Не запускать `CODER`, `ANALYST`, `REVIEWER`.

## Входные артефакты

- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`
- `.agents/templates/VERIFY.md`
- `.agents/templates/LEAD_PROMPT.md`
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/LEAD.md`
- `.agents/roles/VERIFIER.md`
- релевантные process-образцы из `.agents-runtime/MAIL/**`, если они нужны как фактический reference

## Шаги

1. Прочитать все template-файлы.
2. Сверить их с актуальными rules/specs.
3. Отдельно проверить, не конфликтует ли `LEAD_PROMPT.md` с process-контрактом по `Start-Process`.
4. Проверить, хватает ли `TASK.md`, `REPORT.md` и `VERIFY.md` для текущей process-практики.
5. Проверить, определена ли роль `TEMPLATES/*` как слоя: source of truth, scaffolding или reference-format.
6. Подготовить verifier-отчёт с чётким разделением подтверждённых и неподтверждённых пунктов.
7. Добавить созданные OUT-артефакты этого тикета в git index.

## Обязательная форма OUT-отчёта

1. `Что подтверждено`
2. `Что не подтверждено или конфликтует`
3. `Проверка LEAD_PROMPT`
4. `Проверка TASK / REPORT / VERIFY`
5. `Проверка роли TEMPLATES`
6. `Итоговое заключение`
7. `Рекомендуемые follow-up`, если без них шаблоны нельзя считать нормализованными

## Проверки

- Отдельно проверен конфликт или отсутствие конфликта между `LEAD_PROMPT.md` и `CONTROL_FLOW.md`.
- Отдельно проверена полнота `TASK.md`, `REPORT.md`, `VERIFY.md` относительно фактической process-практики.
- Есть явный вывод о роли `TEMPLATES/*`.
- OUT-артефакты добавлены в git index.

## Критерии готовности

- [ ] Выполнена независимая проверка `TEMPLATES/*`.
- [ ] Явно перечислены конфликты и неподтверждённые места.
- [ ] Есть итоговое заключение по пригодности шаблонов.
- [ ] Созданные OUT-артефакты добавлены в git index.

## Артефакты OUT

- отчёт VERIFIER: `.agents-runtime/MAIL/VERIFIER/OUT/T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md`

## Рекомендованная модель и режим

- model: GPT-5.4
- reasoning effort: medium
- комментарий: нужен независимый аккуратный проход по шаблонам и их конфликтам с process-rules
