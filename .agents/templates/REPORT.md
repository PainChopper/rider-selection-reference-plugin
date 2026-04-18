# REPORT.md

Этот шаблон используется для фактического OUT-отчёта по выполненному шагу.

Шаблон относится к helper/scaffolding layer.
Он не заменяет правила из `AGENTS.md`, `.agents/rules/**` и `.agents/roles/**`.

Правила заполнения:

- Отчёт должен быть фактическим и коротким.
- Нужно описывать только то, что реально сделано, проверено или зафиксировано.
- Нельзя подменять исходный scope соседней полезной работой.
- Если шаг завершился неуспешно, это нужно назвать прямо и без сглаживания.
- Если formal `IN`-тикета не было, в поле `SourceTicket` допустимо указывать `direct-owner-request`.

## Шаблон

```md
# <ROLE> OUT REPORT

Status: `<DONE | FAILED | CANCELLED | BLOCKED>`
CreatedAt: `<DD.MM.YYYY HH:mm>`
SourceTicket: `<.agents-runtime/MAIL/<ROLE>/IN/...md | direct-owner-request>`
Workspace: `D:\.DEV\JetBrainsPlagins`

## Summary
<2-5 строк: что произошло по результату этого шага>

## Delivered
- `<что реально произведено или изменено>`
- `<что реально подготовлено или зафиксировано>`

## ChangedFiles
- `<project file or runtime file>`
- `<project file or runtime file>`

## ChecksRun
- `<что запускалось>`
- `<что не запускалось, если это важно указать явно>`

## Artifacts
- `<.agents-runtime/RUNLOGS/<ticket-id>/... | нет>`

## Issues
- `<только реальные проблемы, если они есть>`

## FollowUps
- `<что логично делать следующим отдельным шагом | нет>`

## Blocker
<заполнять только если Status = BLOCKED: что именно остановило текущий прогон, чего не хватает и какой внешний ход нужен>

## FailureReason
<заполнять только если Status = FAILED или CANCELLED: почему шаг завершён этим статусом>
```
