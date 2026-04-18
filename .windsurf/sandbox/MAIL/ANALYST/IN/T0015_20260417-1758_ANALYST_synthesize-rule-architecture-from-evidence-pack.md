# TASK — тикет

Role: ANALYST
TicketId: T0015
CreatedAt: 2026-04-17 17:58

## Контекст

После `T0014` нужен второй аналитический проход. Его задача — не копать репозиторий с нуля, а взять evidence pack из `T0014` и на его основе собрать одну рекомендуемую архитектурную схему для rule/process-layer и пакет материалов для ChatGPT 5.4 Pro.

Этот тикет запускается только после появления OUT-артефактов `T0014`.
Дополнительно нужно учитывать независимый verifier-отчёт `T0016` по `TEMPLATES/*`.

## Scope

- Прочитать основной OUT-отчёт и supporting artifacts из `T0014`.
- Прочитать verifier-отчёт `T0016`.
- При необходимости точечно дочитать только те repo-файлы, без которых нельзя снять оставшуюся неоднозначность.
- Зафиксировать один рекомендуемый bootstrap-flow.
- Зафиксировать одну рекомендуемую иерархию правил.
- Зафиксировать одну рекомендуемую границу между rules, skills, templates и history.
- Подготовить финальный file-pack для последующей загрузки в ChatGPT 5.4 Pro.
- Уложить итоговый upload pack максимум в 4 файла.
- Подготовить change-plan как вход для следующего `CODER`, но не писать сам `CODER`-тикет.

## Не делать

- Не реализовывать правки в rules, specs, skills или process-артефактах.
- Не перепроверять весь репозиторий с нуля, если нужные факты уже собраны в `T0014`.
- Не запускать `CODER`, `VERIFIER`, `REVIEWER`.
- Не предлагать несколько равноправных архитектур без одной финальной рекомендации.
- Не плодить лишние supporting artifacts, если без них можно удержать upload pack в лимите 4 файлов.

## Входные артефакты

- `.windsurf/sandbox/MAIL/ANALYST/OUT/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction_report.md`
- supporting artifacts из `.windsurf/sandbox/MAIL/ANALYST/OUT/`, созданные в `T0014`
- `.windsurf/sandbox/MAIL/VERIFIER/OUT/T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md`
- repo-файлы и historical artifacts только точечно, если они реально нужны для финального выбора

## Шаги

1. Прочитать основной отчёт и evidence-pack из `T0014`.
2. Прочитать verifier-отчёт `T0016`.
3. Снять оставшиеся неоднозначности точечным чтением источников.
4. Зафиксировать один финальный bootstrap-flow без циклов.
5. Зафиксировать одну финальную иерархию правил.
6. Зафиксировать одну финальную границу между rules, skills, templates и history.
7. Зафиксировать список factual cleanup и change-plan для следующего `CODER`.
8. Подготовить file-pack для ChatGPT 5.4 Pro с лимитом максимум 4 файла.
9. Явно перечислить, какие именно 4 файла нужно загружать в ChatGPT 5.4 Pro и зачем каждый нужен.
10. Добавить созданные OUT-артефакты в git index.

## Обязательная форма OUT-отчёта

1. `Финальный bootstrap-flow`
2. `Финальная иерархия правил`
3. `Финальная граница rules, skills, templates, history`
4. `Что остаётся в rules`
5. `Что выносится в skills`
6. `Как трактовать TEMPLATES`
7. `Исторические артефакты, которые не нужно переписывать`
8. `Factual cleanup`
9. `Change-plan для CODER`
10. `File-pack для ChatGPT 5.4 Pro`
11. `Список файлов для загрузки в ChatGPT 5.4 Pro`

## Проверки

- Есть один финальный bootstrap-flow.
- Есть одна финальная иерархия правил.
- Роль `TEMPLATES` определена явно и без конфликта с rules.
- Есть один change-plan для следующего `CODER`.
- Есть явный upload pack максимум из 4 файлов.
- OUT-артефакты добавлены в git index.

## Критерии готовности

- [ ] Сформирована одна рекомендуемая архитектурная схема.
- [ ] Подготовлен file-pack для ChatGPT 5.4 Pro.
- [ ] Итоговый upload pack укладывается максимум в 4 файла.
- [ ] Подготовлен change-plan для следующего `CODER`.
- [ ] Созданные OUT-артефакты добавлены в git index.

## Артефакты OUT

- основной отчёт ANALYST: `.windsurf/sandbox/MAIL/ANALYST/OUT/T0015_20260417-1758_ANALYST_synthesize-rule-architecture-from-evidence-pack_report.md`
- optional supporting artifacts в `.windsurf/sandbox/MAIL/ANALYST/OUT/` только если без них нельзя собрать сильный upload pack в пределах 4 файлов

## Рекомендованная модель и режим

- model: GPT-5.4
- reasoning effort: high
- комментарий: это второй аналитический проход на синтез и упаковку file-pack для ChatGPT 5.4 Pro, а не на первичный раскоп фактов
