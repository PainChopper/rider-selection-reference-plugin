---
name: process-artifact-workflow
description: Use when the task requires creating or updating .agents-runtime process artifacts such as MAIL tickets, OUT reports, plan files, runlog references, or 00_STATE.md notes. Do not use for ordinary code-only work that does not touch process artifacts.
---

Использовать этот skill, когда шаг затрагивает process-layer.

## Что делать

1. Определить тип process-артефакта: `MAIL`, `PLANS`, `RUNLOGS` или `00_STATE.md`.
2. Проверить, какая роль имеет право обновлять этот артефакт.
3. Использовать актуальный шаблон из `.agents/templates/*`, если текущий тикет не требует более строгую форму.
4. Проверить путь, имя файла и ссылки на связанные артефакты.
5. Если создаются runlogs, складывать их в `.agents-runtime/RUNLOGS/<ticket-id>/`.

## Ограничения по ownership

- `00_STATE.md` обновляет только `LEAD`.
- Plan-файлы по умолчанию ведёт `LEAD`.
- `LEAD` подготавливает formal `IN`-тикеты.
- Исполнительная роль пишет только собственный `OUT`.


Этот skill не переопределяет Owner instruction, rules/specs или active ticket.
