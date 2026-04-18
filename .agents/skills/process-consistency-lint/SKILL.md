---
name: process-consistency-lint
description: Use when validating process consistency across AGENTS.md, rules, roles, templates, 00_STATE.md, plans, tickets, OUT reports, or runtime paths. Do not use for ordinary feature implementation.
---

Использовать этот skill для целевой проверки process-layer.

## Чеклист

1. Проверить, что `AGENTS.md` ведёт только в новые пути `.agents/**`.
2. Проверить, что templates и skills не выдают себя за source of truth.
3. Проверить, что `00_STATE.md` содержит не более одной записи в `Active`.
4. Проверить, что `Suspended` используется только для тикетов, отложенных по решению Owner.
5. Проверить, что `BLOCKED` не превращён в долговечное состояние state-файла.
6. Проверить, что ссылки между `IN`, `OUT`, `PLANS`, `RUNLOGS` и `00_STATE.md` указывают на существующие пути.
7. Проверить, что в live layer не осталось legacy-path ссылок `.aiassistant` и `.windsurf/sandbox`, кроме документов migration/advisory назначения.

## Результат

Выдать список factual PASS/FAIL/INCONCLUSIVE и короткий follow-up по каждому несоответствию.


Этот skill не переопределяет Owner instruction, rules/specs или active ticket.
