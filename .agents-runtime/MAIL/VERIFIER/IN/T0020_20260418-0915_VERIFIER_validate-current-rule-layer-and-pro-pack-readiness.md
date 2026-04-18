# TASK — тикет

Role: VERIFIER
TicketId: T0020
CreatedAt: 2026-04-18 09:15

## Контекст

После аналитических проходов `T0014` и `T0015`, verifier-проверки `T0016` и реализационных проходов `T0018` и `T0019` нужно независимо ответить на два вопроса:

1. Выровнен ли текущий source-of-truth слой репозитория достаточно хорошо.
2. Готов ли текущий пакет артефактов для обращения к ChatGPT 5.4 Pro на архитектурный review.

Особенно важно проверить, не сломал ли follow-up `T0019` часть нормализованного слоя после `T0018`.

## Scope

- Прочитать ключевые OUT-артефакты:
  - `T0015` ANALYST report
  - `T0016` VERIFIER report
  - `T0018` CODER report
  - `T0019` CODER report
- Прочитать актуальный source-of-truth слой:
  - `AGENTS.md`
  - `.agents/rules/00_COMMON.md`
  - `.agents/rules/specs/CONTROL_FLOW.md`
  - актуальные role-файлы при необходимости
  - `.agents/templates/*`
  - `.agents/skills/*`
  - `.agents-runtime/00_STATE.md`
- Подтвердить или опровергнуть:
  - что bootstrap-cycle устранён;
  - что иерархия слоёв явно зафиксирована;
  - что `TEMPLATES/*` больше не конкурируют с rules/specs;
  - что physical `skills`-layer существует и остаётся helper-layer;
  - что `T0019` не внёс критический regress в source-of-truth слой;
  - что текущий пакет для ChatGPT 5.4 Pro действительно пригоден для architectural review.

## Не делать

- Не переписывать rules/templates/skills вместо проверки.
- Не подменять verification новым redesign.
- Не запускать `CODER`, `ANALYST`, `REVIEWER`.
- Не уходить в review продуктового кода плагина.

## Входные артефакты

- `.agents-runtime/MAIL/ANALYST/OUT/T0015_20260417-1758_ANALYST_synthesize-rule-architecture-from-evidence-pack_report.md`
- `.agents-runtime/MAIL/VERIFIER/OUT/T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md`
- `.agents-runtime/MAIL/CODER/OUT/T0018_20260417-1856_CODER_normalize-rule-layer-and-template-contracts_report.md`
- `.agents-runtime/MAIL/CODER/OUT/T0019_20260418-0903_CODER_remove-front-backend-contract-links_report.md`
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/LEAD.md`
- `.agents/roles/ANALYST.md`
- `.agents/roles/CODER.md`
- `.agents/roles/VERIFIER.md`
- `.agents/roles/REVIEWER.md`
- `.agents/templates/INDEX.md`
- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`
- `.agents/templates/VERIFY.md`
- `.agents/templates/LEAD_PROMPT.md`
- `.agents/skills/*`
- `.agents-runtime/00_STATE.md`

## Шаги

1. Прочитать четыре ключевых OUT-отчёта `T0015`, `T0016`, `T0018`, `T0019`.
2. Прочитать актуальный source-of-truth слой репозитория.
3. Проверить bootstrap-flow и отсутствие циклов.
4. Проверить иерархию слоёв и helper-subordination.
5. Проверить актуальный статус `TEMPLATES/*` и `skills/*`.
6. Отдельно проверить, не испортил ли `T0019` результат `T0018`.
7. Оценить, готов ли текущий пакет для ChatGPT 5.4 Pro на architectural review.
8. Подготовить verifier-отчёт.
9. Добавить созданные OUT-артефакты этого тикета в git index.

## Обязательная форма OUT-отчёта

1. `Подтверждённые исправления`
2. `Неподтверждённые или спорные пункты`
3. `Проверка bootstrap-flow`
4. `Проверка иерархии слоёв`
5. `Проверка templates и skills`
6. `Проверка follow-up после T0019`
7. `Готовность пакета для ChatGPT 5.4 Pro`
8. `Итоговое заключение`

## Проверки

- Отдельно проверено отсутствие bootstrap-cycle.
- Отдельно проверена helper-подчинённость `TEMPLATES/*` и `skills/*`.
- Отдельно проверен возможный regress после `T0019`.
- Есть явный вывод о готовности пакета для ChatGPT 5.4 Pro.
- OUT-артефакты добавлены в git index.

## Критерии готовности

- [ ] Независимо подтверждено или опровергнуто качество текущего source-of-truth слоя.
- [ ] Независимо проверен regress-risk после `T0019`.
- [ ] Дана явная оценка готовности пакета для ChatGPT 5.4 Pro.
- [ ] Подготовлен OUT-отчёт как вход для следующего решения LEAD/REVIEWER.

## Артефакты OUT

- отчёт VERIFIER: `.agents-runtime/MAIL/VERIFIER/OUT/T0020_20260418-0915_VERIFIER_validate-current-rule-layer-and-pro-pack-readiness_report.md`

## Рекомендованная модель и режим

- model: GPT-5.4
- reasoning effort: high
- комментарий: нужен жёсткий независимый проход по фактическому текущему состоянию rule/process/template слоя и пригодности пакета для Pro
