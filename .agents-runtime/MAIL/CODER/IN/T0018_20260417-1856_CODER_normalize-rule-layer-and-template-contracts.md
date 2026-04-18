# TASK — тикет

Role: CODER
TicketId: T0018
CreatedAt: 2026-04-17 18:56

## Контекст

После аналитических проходов `T0014` и `T0015`, а также verifier-проверки `T0016`, нужно привести в порядок актуальный source-of-truth слой репозитория.

К этому моменту уже зафиксировано:

- `AGENTS.md` должен быть первым bootstrap-entrypoint.
- Текущий bootstrap содержит цикл между `AGENTS.md` и `.agents/rules/00_COMMON.md`.
- Иерархия `runtime/task -> role -> specs -> common -> helper -> history` должна быть зафиксирована явно.
- `TEMPLATES/*` должны остаться helper/scaffolding layer и не могут переопределять rules/specs или active ticket.
- Конфликт по `Start-Process` должен быть централизованно решён в source-of-truth слое, а не размазан между templates и history.
- History не нужно переписывать задним числом.

Нужно внести правки именно в актуальные rules/templates/skills/state-артефакты, не расползаясь в старые `MAIL/**`.

## Scope

- Нормализовать bootstrap и rule hierarchy.
- Привести актуальные rules/specs/templates к одной согласованной схеме.
- Ввести physical `skills`-layer.
- Подтянуть templates к фактической process-практике.
- Явно закрепить ownership и process expectations для `.agents-runtime/00_STATE.md` и process-артефактов.
- Не трогать продуктовый код плагина.
- Не выполнять массовую ретро-правку historical `MAIL/**`.

## Не делать

- Не переписывать старые `MAIL/**` ради косметики.
- Не менять функциональность Rider-плагина.
- Не уходить в redesign за пределами уже утверждённой схемы из `T0015`.
- Не создавать helper-слои, которые могут переопределять rules/specs.

## Входные артефакты

- `.agents-runtime/MAIL/ANALYST/OUT/T0015_20260417-1758_ANALYST_synthesize-rule-architecture-from-evidence-pack_report.md`
- `.agents-runtime/MAIL/ANALYST/OUT/T0015_20260417-1758_ANALYST_synthesize-rule-architecture-from-evidence-pack_file-pack.md`
- `.agents-runtime/MAIL/ANALYST/OUT/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction_report.md`
- `.agents-runtime/MAIL/ANALYST/OUT/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction_evidence-pack.md`
- `.agents-runtime/MAIL/VERIFIER/OUT/T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md`
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/LEAD.md`
- `.agents/roles/ANALYST.md`
- `.agents/roles/CODER.md`
- `.agents/roles/TESTER.md`
- `.agents/roles/VERIFIER.md`
- `.agents/roles/REVIEWER.md`
- `.agents/templates/INDEX.md`
- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`
- `.agents/templates/VERIFY.md`
- `.agents/templates/LEAD_PROMPT.md`
- `.agents-runtime/00_STATE.md`

## Шаги

1. Прочитать `T0015` как основной change-plan и архитектурную цель.
2. Исправить `AGENTS.md` так, чтобы он остался bootstrap-only entrypoint без дублирования полной нормативной логики.
3. Исправить `.agents/rules/00_COMMON.md`, убрав цикл чтения и оставив только repo-wide инварианты.
4. Исправить `specs/*`, чтобы там были кросс-ролевые process/domain contracts без дублирования bootstrap.
5. Явно зафиксировать layer-stack и rule priority в актуальном source-of-truth слое.
6. Нормализовать канонический workspace для новых правил и process-артефактов на `D:\.DEV\JetBrainsPlagins`.
7. Исправить `TEMPLATES/*`:
   - оставить их helper/scaffolding layer;
   - убрать из `LEAD_PROMPT.md` самостоятельную конфликтующую process-норму;
   - дотянуть `TASK.md`, `REPORT.md`, `VERIFY.md` до фактической process-практики.
8. Создать `.agents/skills/` и добавить в него минимальный physical набор skill-файлов:
   - `PROCESS_ARTIFACT_WORKFLOW.md`
   - `PROCESS_CONSISTENCY_LINT.md`
   - `INTELLIJ_PLATFORM_BUILD_COMPAT.md`
   - `RIDER_DIFF_CONTEXT_COPY.md`
   - `IDE_LANGUAGE_DETECTION.md`
9. Явно зафиксировать в rules/specs, что `skills/*` не переопределяют rules/specs/active ticket.
10. Закрепить ownership и expected update-flow для `.agents-runtime/00_STATE.md` и staging process-артефактов.
11. Обновить `.agents-runtime/00_STATE.md` только по фактическому состоянию после выполнения этого тикета.
12. Проверить внутренние ссылки, существование путей и отсутствие нового bootstrap-cycle.
13. Подготовить OUT-отчёт с точным списком правок и проверок.
14. Добавить созданные/обновлённые process-артефакты этого тикета в git index.

## Обязательные ожидания от реализации

- `AGENTS.md` остаётся первым entrypoint и больше не участвует в цикле.
- В репозитории есть одна явная иерархия слоёв.
- `TEMPLATES/*` остаются helper-layer и больше не конфликтуют с rules/specs.
- `skills/*` появляются как physical layer и не объявляются источником истины.
- `00_STATE.md` отражает фактическое состояние серии.
- Historical `MAIL/**` не переписаны задним числом.

## Проверки

- Проверить, что цикл `AGENTS.md -> 00_COMMON.md -> AGENTS.md` устранён.
- Проверить, что в актуальных repo-файлах явно задан один порядок приоритета слоёв.
- Проверить, что `TEMPLATES/*` больше не содержат конфликтующей нормативной логики.
- Проверить, что все новые skill-файлы существуют по указанным путям.
- Проверить, что `skills/*` нигде не объявлены сильнее rules/specs.
- Проверить, что `00_STATE.md` синхронизирован с фактическим состоянием после выполнения тикета.
- Проверить, что старые `MAIL/**` не были массово переписаны.

## Критерии готовности

- [ ] Rule-layer нормализован по change-plan `T0015`.
- [ ] Template-layer согласован с rules/specs.
- [ ] Создан minimal physical `skills`-layer.
- [ ] Bootstrap-flow читается однозначно.
- [ ] `00_STATE.md` приведён к фактическому состоянию.
- [ ] Historical artifacts не переписаны задним числом.
- [ ] Подготовлен OUT-отчёт как вход для следующей независимой проверки.

## Артефакты OUT

- отчёт CODER: `.agents-runtime/MAIL/CODER/OUT/T0018_20260417-1856_CODER_normalize-rule-layer-and-template-contracts_report.md`

## Рекомендованная модель и режим

- model: GPT-5.4
- reasoning effort: high
- комментарий: задача про аккуратную правку source-of-truth слоя, где важны дисциплина изменений, связность артефактов и отсутствие regressions в process-layer
