# ANALYST EVIDENCE PACK

Role: ANALYST
TicketId: T0014
Purpose: supporting evidence for next `ANALYST` pass

## Rule-layer hotspots

- `AGENTS.md:7-10`
  Bootstrap начинается с чтения `00_COMMON.md` и `CONTROL_FLOW.md`.
- `AGENTS.md:29-30`
  В `Порядок применения` повторно задана загрузка `00_COMMON.md` и `specs/*`.
- `AGENTS.md:38`
  Явно закреплён только частичный приоритет: role-file выше `00_COMMON.md`.
- `00_COMMON.md:13-17`
  `00_COMMON.md` требует сначала читать `AGENTS.md`, что и образует bootstrap-cycle.
- `00_COMMON.md:3,8`
  Старый workspace path закреплён как repo norm.
- `CONTROL_FLOW.md:25-28`
  Текущая process-норма: печать process-файлов в чате, не `Start-Process` по умолчанию.
  Старый workspace path закреплён и в boundary/spec-layer.

## Template-semantics hotspots

- `TEMPLATES/INDEX.md:7-15`
  Templates заданы как слой ниже active ticket и rules/specs, выше history.
- `TEMPLATES/INDEX.md:48-50`
  Есть оговорка о follow-up при нехватке шаблона, но нет полного operational contract.
- `TEMPLATES/LEAD_PROMPT.md:10`
  Шаблон handoff требует обязательный `Start-Process`.
- `TEMPLATES/TASK.md:7-29`
  Есть только `Контекст`, `Требования`, `Шаги`, `Проверки`, `Критерии готовности`, `Артефакты OUT`, `Рекомендованная модель и режим`.
- `TEMPLATES/REPORT.md:3-6`
  Header-contract шаблона: `Role`, `TicketId`, `Status`, `FinishedAt`.
- `TEMPLATES/VERIFY.md:3-6`
  Header-contract verifier-template: `Role`, `Subject`, `Result`, `FinishedAt`.

## Historical evidence for conflicts

- `T0001_20260410-0108_LEAD_rider-plugin-bootstrap.md:15-16`
  Ранний LEAD-текст фиксирует старый workspace `D:\!_DEV_!\JetBrainsPlagins`.
- `T0001_20260410-0108_LEAD_rider-plugin-bootstrap.md:27`
  Ранний письменный process-contract требует проверять `Start-Process`.
- `T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md:14-18`
  Независимый verifier подтверждает неполноту `TASK/REPORT/VERIFY`.
- `T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md:19-23`
  Независимый verifier подтверждает прямой конфликт письменных норм `LEAD_PROMPT.md` vs `CONTROL_FLOW.md`.
- `T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md:34-43`
  Независимый verifier делает вывод: `TEMPLATES/*` пока не годятся как нормальный template layer.

## Stable process-practice evidence

- `T0014 ANALYST IN: 25,39,50,84`
  В одном актуальном аналитическом тикете уже присутствуют `Scope`, `Не делать`, `Входные артефакты`, `Обязательная форма OUT-отчёта`.
- `T0015 ANALYST IN: 13,23,30,47`
  Тот же набор устойчивых секций повторяется и в следующем аналитическом тикете.
- `T0016 VERIFIER IN: 20,37,44,68`
  Та же структура повторяется и в verifier-ticket.
- `T0004 CODER OUT: 5,7`
  Репрезентативный `OUT` использует `SourceTicket` и `Workspace`.
- `T0013 CODER OUT: 5,7`
  Более поздний `OUT` использует тот же header-pattern.
- `T0005 TESTER OUT: 5,7`
  TESTER-report подтверждает тот же header-pattern.

## Path drift evidence

- `T0002 ANALYST OUT: Workspace -> D:\!_DEV_!\JetBrainsPlagins`
- `T0010 ANALYST OUT: Workspace -> D:\!_DEV_!\JetBrainsPlagins`
- `T0003 CODER OUT: Workspace -> D:\!_DEV_!\JetBrainsPlagins`
- `T0011 CODER OUT: Workspace -> D:\!_DEV_!\JetBrainsPlagins`
- `T0004 CODER OUT: Workspace -> D:\.DEV\JetBrainsPlagins`
- `T0013 CODER OUT: Workspace -> D:\.DEV\JetBrainsPlagins`
- `T0005 TESTER OUT: Workspace -> D:\.DEV\JetBrainsPlagins`

Вывод по drift:
- path drift подтверждён на mixed historical sample; единая норма в rules/specs пока не догнала фактическую практику.

## State drift evidence

- Current file:
  `.agents-runtime/00_STATE.md:3` -> `NextTicket: T0015`
  `.agents-runtime/00_STATE.md:6` -> `Active: T0014`
  `.agents-runtime/00_STATE.md:33-34` -> `T0016` уже в `Done`
- Historical drift inside current staged diff:
  ранее в том же файле было `NextTicket: T0014` и `Active: T0013`;
  staged diff текущей сессии показывает исправление к `T0015` / `T0014` и добавление `T0013 OUT`, `T0016 IN/OUT` в `Done`.

## Candidate inventory summary

- Bootstrap:
  `AGENTS.md` as single entrypoint
  remove reverse read-order from `00_COMMON.md`
  keep history and skills out of bootstrap
- Hierarchy:
  explicit source-of-truth chain
  templates below rules/specs
  history as evidence only
- Skills boundary:
  move repeatable recipes to `.agents/skills`
  keep rules for invariants and conflict resolution
- Cleanup:
  normalize workspace path
  reconcile `Start-Process` rule
  align templates with actual process practice
  keep old `MAIL/**` untouched

## Note about `Start-Process` evidence

- `MAIL/**`-артефакты не позволяют надёжно установить, выполнялся ли `Start-Process` фактически.
- Причина: запуск команды происходит в чате/runtime и обычно не логируется внутрь самого тикета или отчёта.
- В текущем репозитории это нужно читать с учётом фактической среды: работа идёт в Codex agent system, где chat/runtime слой и process-файлы являются разными слоями наблюдаемости.
- Поэтому в этом evidence pack `Start-Process` рассматривается только как written contract issue:
  что именно предписывают `rules/specs`, `TEMPLATES/*`, отдельные тикеты и прямое пользовательское указание.

## Note about active user participation

- Для текущей среды исполнения значим не только стартовый тикет, но и активное участие пользователя в самом треде.
- В Codex пользователь может по ходу выполнения:
  уточнять требования,
  требовать перепроверки,
  останавливать неверную интерпретацию,
  переключать фокус с process-файла на runtime-факт и обратно.
- Это создаёт отдельный runtime layer:
  не вся фактическая истина о ходе задачи живёт в `MAIL/**`, потому что часть process-contract формируется живым user-agent взаимодействием в чате.
- В тот же runtime layer попадают и операционные действия агента внутри Codex, например `Start-Process`, если они выполняются из чата, а не описываются в process-файле.
- Следствие для следующего `ANALYST`:
  при архитектурном синтезе нельзя моделировать систему как чисто ticket-driven без учёта активного диалога пользователя с агентом.
