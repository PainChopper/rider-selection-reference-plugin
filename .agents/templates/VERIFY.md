# VERIFY.md

Этот шаблон используется для верификационного OUT-отчёта.

Шаблон относится к helper/scaffolding layer.
Он не заменяет правила из `AGENTS.md`, `.agents/rules/**` и `.agents/roles/**`.

Правила заполнения:

- Верификационный отчёт должен быть отделён от обычного исполнительного отчёта.
- Нужно фиксировать, что именно проверялось, чем проверялось и какой вердикт получен.
- Если подтверждение не удалось получить из-за среды, недостающего входа или ограничений проверки, использовать `INCONCLUSIVE`.
- Если formal `IN`-тикета не было, в поле `SourceTicket` допустимо указывать `direct-owner-request`.

## Шаблон

```md
# <TicketId> — <Human Title>

Verdict: `<PASS | FAIL | INCONCLUSIVE>`
CreatedAt: `<DD.MM.YYYY HH:mm>`
TicketId: `<TXXXX>`
Title: `<slug-based-short-title>`
SourceTicket: `<.agents-runtime/MAIL/<ROLE>/IN/...md | direct-owner-request>`
Workspace: `D:\.DEV\JetBrainsPlagins`

## VerificationTarget
<что именно проверялось>

## Inputs
- `<какие файлы, отчёты, тикеты или артефакты использовались>`

## Procedure
- `<какие действия, команды или проверки были выполнены>`

## Evidence
- `<какие факты получены по результату проверки>`

## Artifacts
- `<.agents-runtime/RUNLOGS/<ticket-id>/... | нет>`

## Issues
- `<какие ограничения, дефекты или несоответствия найдены | нет>`

## FollowUps
- `<какой следующий шаг нужен, если он нужен | нет>`
```
