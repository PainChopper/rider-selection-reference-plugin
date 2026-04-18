# CODER REPORT

Role: CODER
TicketId: T0018
SourceTicket: `.agents-runtime/MAIL/CODER/IN/T0018_20260417-1856_CODER_normalize-rule-layer-and-template-contracts.md`
CreatedAt: 2026-04-17
Workspace: `D:\.DEV\JetBrainsPlagins`

## Что сделано
- Нормализован bootstrap/router-слой: `AGENTS.md` оставлен entrypoint-only без дублирования нормативной логики и с явным разделением helper/history слоёв.
- Устранён bootstrap-cycle: из `00_COMMON.md` убрана обратная инструкция читать `AGENTS.md`; зафиксирована единая каноническая иерархия слоёв и приоритетов.
- Приведён кросс-ролевой process/domain contract в `specs/*`: централизована runtime-policy для process-артефактов и добавлен ownership/update-flow для `00_STATE.md` и staging process-артефактов.
- Согласован template-layer с процессной практикой и helper-ролью:
  - обновлён `TEMPLATES/INDEX.md` (усилена operational-оговорка);
  - обновлён `TEMPLATES/TASK.md` (добавлены `Scope`, `Не делать`, `Входные артефакты`, секция для обязательной формы OUT);
  - обновлён `TEMPLATES/REPORT.md` под фактический `SourceTicket`/`Workspace` контракт;
  - обновлён `TEMPLATES/VERIFY.md` с приоритетом структуры из активного verifier-тикета;
  - из `TEMPLATES/LEAD_PROMPT.md` убрана самостоятельная конфликтующая process-норма.
- Создан physical skills-layer `.agents/skills/` с минимальным набором:
  - `PROCESS_ARTIFACT_WORKFLOW.md`
  - `PROCESS_CONSISTENCY_LINT.md`
  - `INTELLIJ_PLATFORM_BUILD_COMPAT.md`
  - `RIDER_DIFF_CONTEXT_COPY.md`
  - `IDE_LANGUAGE_DETECTION.md`
  Во всех skill-файлах зафиксировано, что skills не переопределяют `rules/specs/active ticket`.
- Обновлён `.agents-runtime/00_STATE.md` по фактическому состоянию после завершения `T0018`.

## Изменённые файлы
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/LEAD.md`
- `.agents/templates/INDEX.md`
- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`
- `.agents/templates/VERIFY.md`
- `.agents/templates/LEAD_PROMPT.md`
- `.agents/skills/process-artifact-workflow/SKILL.md`
- `.agents/skills/process-consistency-lint/SKILL.md`
- `.agents/skills/intellij-platform-build-compat/SKILL.md`
- `.agents/skills/rider-diff-context-copy/SKILL.md`
- `.agents/skills/ide-language-detection/SKILL.md`
- `.agents-runtime/00_STATE.md`
- `.agents-runtime/MAIL/CODER/OUT/T0018_20260417-1856_CODER_normalize-rule-layer-and-template-contracts_report.md`

## Проверки
- Проверено отсутствие bootstrap-cycle по содержимому `AGENTS.md` и `00_COMMON.md`.
- Проверено, что единый layer-priority и helper-subordination явно зафиксированы в source-of-truth слое.
- Проверено, что `TEMPLATES/*` больше не несут конфликтующей runtime-нормы по `Start-Process`.
- Проверено существование всех новых skill-файлов в `.agents/skills/`.
- Проверено, что historical `MAIL/**` массово не переписывался.

## Ограничения и риски
- Проверка ограничена source-of-truth/process-слоем; продуктовый код плагина не менялся.
- `NextTicket: T0019` в `00_STATE.md` выставлен как последовательный следующий идентификатор после завершения `T0018`.

## Итог
- DONE
