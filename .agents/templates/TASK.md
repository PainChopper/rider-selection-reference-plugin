# TASK — formal ticket

Role: <ROLE>
TicketId: <TXXXX>
Title: <short-title>
CreatedAt: <YYYY-MM-DD HH:MM>
OwnerRequest: <кратко в 1-3 строках>
PlanRef: <optional path to .agents-runtime/PLANS/...>
Workspace: `D:\.DEV\JetBrainsPlagins`

## Scope
- <что входит в этот тикет>

## OutOfScope
- <что нельзя делать в рамках этого тикета>

## RequiredReads
- <обязательные файлы и отчёты>

## RequiredSkills
- <skill-name> — <зачем нужен>
- <или `none`>

## Inputs
- <входные артефакты>
- <контекст, на который надо опираться>

## Steps
1. <минимальный шаг>
2. ...

## Checks
- <команда / действие> -> <что подтверждает>
- длинные выводы складывать в `.agents-runtime/RUNLOGS/<ticket-id>/`

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/<ROLE>/OUT/TXXXX_YYYYMMDD-HHMM_<ROLE>_<slug>_report.md`
- дополнительные артефакты: <если нужны>

## StateImpact
- <нужно ли LEAD после этого обновить `00_STATE.md` или `PLANS/**`>

## Acceptance
- [ ] <проверяемое условие 1>
- [ ] <проверяемое условие 2>

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: <optional>
- reasoning effort: <optional>
- comment: <optional>
