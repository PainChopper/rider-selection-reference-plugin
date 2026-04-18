# TASK — тикет

Role: ANALYST
TicketId: T0014
CreatedAt: 2026-04-17 02:20

## Контекст

Нужно подготовить первый аналитический проход: собрать факты, evidence и компактный пакет входных артефактов для следующего аналитического синтеза.

По текущему состоянию репозитория уже подтверждено:

- `AGENTS.md` и `.agents/rules/00_COMMON.md` образуют неоднозначный bootstrap.
- Явная и полная иерархия правил не зафиксирована как один однозначный источник истины.
- `.agents/rules/specs/CONTROL_FLOW.md` уже считает нормой печать process-файлов в чате, а historical process-артефакты местами тащат устаревший `Start-Process`.
- В истории есть drift по workspace path.
- `.agents/skills/**` пока отсутствует как слой.
- `.agents-runtime/00_STATE.md` отстаёт от фактического состояния серии.
- Независимая verifier-проверка по `TEMPLATES/*` уже выявила, что шаблоны пока не подтверждены как нормализованный template layer и содержат подтверждённый конфликт по `Start-Process`.

Нужен однозначный пакет фактов и evidence, пригодный как вход для следующего прохода `ANALYST` и затем для более сильной рассуждающей модели ChatGPT Pro.
Этот тикет не должен тащить на себе весь архитектурный синтез сразу: его задача сначала собрать и структурировать доказательную базу.
На этом этапе серия остаётся в аналитической фазе: параллельный запуск `CODER` не допускается, даже если по ходу анализа появятся предварительные идеи реализации.

## Scope

- Проанализировать `AGENTS.md`, `.agents/rules/**`, `.agents/rules/specs/**`, `.agents-runtime/00_STATE.md` и только те historical MAIL-артефакты, которые нужны как evidence конфликтов.
- Проанализировать `.agents/templates/*.md` как новый слой template-semantics.
- Собрать карту конфликтов, повторов, drift и ownership-проблем с привязкой к конкретным файлам.
- Собрать candidate inventory для:
  - bootstrap-конфликтов;
  - hierarchy-конфликтов;
  - rules vs templates;
  - rules vs history;
  - skill-кандидатов;
  - factual cleanup items.
- Подготовить материалы так, чтобы следующий `ANALYST` мог на их основе сделать архитектурный синтез без повторного раскопа всего репозитория.

## Не делать

- Не редактировать repo-файлы правил, state или MAIL-артефакты.
- Не запускать `CODER`, `VERIFIER`, `REVIEWER` в этом же проходе.
- Не рекомендовать параллельный старт `CODER` до завершения аналитического OUT и отдельного следующего тикета.
- Не предлагать несколько конкурирующих архитектур без одной финальной рекомендации.
- Не переписывать старые MAIL-артефакты ради косметики.
- Не тащить в этот тикет иконку, статью и продуктовые изменения плагина.
- Не пытаться закрыть весь архитектурный дизайн в этом же проходе.
- Не готовить тикет для `CODER`; это будет отдельный следующий шаг после второго аналитического прохода.

## Входные артефакты

- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/LEAD.md`
- `.agents/roles/ANALYST.md`
- `.agents/roles/CODER.md`
- `.agents/roles/TESTER.md`
- `.agents/roles/VERIFIER.md`
- `.agents/roles/REVIEWER.md`
- `.agents-runtime/00_STATE.md`
- `.agents/templates/TASK.md`
- `.agents/templates/REPORT.md`
- `.agents/templates/VERIFY.md`
- `.agents/templates/LEAD_PROMPT.md`
- `.agents-runtime/MAIL/LEAD/IN/T0001_20260410-0108_LEAD_rider-plugin-bootstrap.md`
- `.agents-runtime/MAIL/CODER/OUT/T0013_20260412-0010_CODER_log-failures-and-refactor-copy-action-orchestration_report.md`
- `.agents-runtime/MAIL/VERIFIER/OUT/T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md`
- другие historical MAIL-артефакты только если они реально нужны для подтверждения drift или conflict

## Шаги

1. Прочитать актуальный rule-layer и state.
2. Прочитать слой `TEMPLATES` и зафиксировать его семантическую роль относительно rules и historical samples.
3. Прочитать verifier-отчёт `T0016` как независимый evidence-источник по `TEMPLATES/*`.
4. Составить карту конфликтов, повторов, ownership-проблем и устаревших инструкций с привязкой к файлам.
5. Собрать candidate list по bootstrap-flow, rule hierarchy, skills boundary и factual cleanup.
6. Отдельно перечислить historical artifacts, которые не нужно переписывать.
7. Подготовить компактный evidence pack для следующего `ANALYST`.
8. При необходимости подготовить supporting artifacts с цитатами, фрагментами и точечными ссылками.
9. Добавить созданные OUT-артефакты в git index.

## Обязательная форма OUT-отчёта

Основной OUT-отчёт должен содержать секции:

1. `Карта конфликтов и повторов`
2. `Карта template-semantics`
3. `Кандидаты для bootstrap-flow`
4. `Кандидаты для иерархии правил`
5. `Кандидаты для rules vs skills boundary`
6. `Исторические артефакты, которые не нужно переписывать`
7. `Factual cleanup items`
8. `Пакет артефактов для следующего ANALYST`

Допустимо дополнительно подготовить supporting artifacts, если это повышает пригодность пакета для ChatGPT Pro:

- приложение с точечными цитатами и фрагментами из rule/process-файлов;
- appendix со списком historical evidence по `Start-Process`, path drift и state drift;
- короткую подборку релевантных путей и артефактов для быстрой загрузки в внешний review.

Если дополнительные файлы создаются, они должны оставаться компактными, ссылаться на конкретные источники и не дублировать основной отчёт целиком.

## Обязательные ожидания

- Канонический workspace для новых правил и новых process-артефактов: `D:\.DEV\JetBrainsPlagins`.
- Старый путь `D:\!_DEV_!\JetBrainsPlagins` трактуется только как historical drift, а не как новая норма.
- `skills/*` в целевой схеме являются reusable recipes и не могут быть источником истины при конфликте.
- Исторические process-артефакты остаются историей и не подлежат массовой ретро-правке.
- Пакет должен быть пригоден для внешнего review в ChatGPT Pro без необходимости заново перечитывать весь репозиторий.
- Этот проход не обязан окончательно утвердить архитектуру; он обязан подготовить для неё качественную доказательную базу.

## Проверки

- В отчёте есть разложенные кандидаты и evidence по bootstrap, hierarchy и templates.
- В отчёте явно учтены findings из verifier-отчёта `T0016`, но без слепого замещения собственного анализа.
- Есть явный список skill-кандидатов.
- Есть явный список factual cleanup items для следующего шага.
- OUT-артефакты не подменяют реализацию и не лезут в правки repo-файлов.
- Пакет содержит достаточно evidence, чтобы следующий `ANALYST` и внешняя сильная модель могли работать по фактам, а не по догадкам.
- Созданные OUT-артефакты добавлены в git index.

## Критерии готовности

- [ ] Зафиксирована карта конфликтов, повторов и устаревших инструкций.
- [ ] Зафиксирована карта template-semantics.
- [ ] Собран candidate inventory для bootstrap, hierarchy, skills boundary и cleanup.
- [ ] OUT пригоден как evidence pack для ChatGPT Pro.
- [ ] OUT пригоден как прямой вход для следующего одиночного прохода `ANALYST`.
- [ ] Созданные OUT-артефакты добавлены в git index.

## Артефакты OUT

- основной отчёт ANALYST: `.agents-runtime/MAIL/ANALYST/OUT/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction_report.md`
- optional supporting artifacts в `.agents-runtime/MAIL/ANALYST/OUT/`, если они реально повышают пригодность пакета для внешнего review

## Следующий маршрут

- После этого тикета `LEAD` готовит второй аналитический тикет на архитектурный синтез и упаковку финального материала для ChatGPT Pro.

## Git

- После подготовки OUT-артефактов добавить их в git index.
- Добавлять в index только новые или обновлённые OUT-артефакты этого тикета; не трогать чужие несвязанные изменения.

## Рекомендованная модель и режим

- model: GPT-5.4
- reasoning effort: high
- комментарий: нужен аккуратный аналитический проход, который соберёт компактный evidence pack для следующего ANALYST-прохода и дальнейшей загрузки в ChatGPT Pro
