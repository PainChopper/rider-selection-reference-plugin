---
name: ide-language-detection
description: Use for tasks that need to determine file language, IDE language metadata, editor context, or fallback logic when language context is unavailable. Do not use for unrelated process or build tickets.
---

Использовать этот skill для задач определения языка и file-context в IDE.

## Что делать

1. Определить, какие источники language metadata доступны в текущем IDE-context.
2. Зафиксировать fallback-поведение на случай отсутствующего или неполного контекста.
3. Проверить типовые сценарии из тикета.
4. Отразить ограничения, edge-cases и реальные источники данных в отчёте.


Этот skill не переопределяет Owner instruction, rules/specs или active ticket.
