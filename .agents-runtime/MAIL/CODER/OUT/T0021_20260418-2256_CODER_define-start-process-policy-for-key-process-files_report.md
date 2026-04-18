# REPORT — execution report

Role: CODER
TicketId: T0021
Title: define-start-process-policy-for-key-process-files
SourceTicket: `.agents-runtime/MAIL/CODER/IN/T0021_20260418-2256_CODER_define-start-process-policy-for-key-process-files.md`
CreatedAt: 2026-04-18 23:18
Workspace: `D:\.DEV\JetBrainsPlagins`
Status: DONE

## Summary
Уточнён source-of-truth contract для `Start-Process` и семантики `open file/report/ticket`.
`00_COMMON.md` явно задаёт значение фраз `open file`, `open report` и `open ticket`, а `CONTROL_FLOW.md` задаёт, когда агент обязан это делать.
После основной policy-правки выполнен отдельный узкий проход по стилю: формулировки в новых блоках выровнены, лишняя смесь русского и английского убрана без изменения смысла правил.

## Delivered
- Обновлён `.agents/rules/00_COMMON.md`:
  - добавлен раздел `Что означает "open"`;
  - закреплено, что `open file`, `open report` и `open ticket` по умолчанию означают открытие локального файла через `Start-Process`, а не дамп полного markdown в чат;
  - ограничен scope до formal `MAIL/<ROLE>/IN/*.md` и formal `MAIL/<ROLE>/OUT/*_report.md`;
  - явно исключены `RUNLOGS/**`, `.txt`, другие вспомогательные логи, evidence/file packs, `.agents-runtime/00_STATE.md` и `.agents-runtime/PLANS/**`, если Owner отдельно не запросил иное;
  - в новом блоке выровнен язык и убрана лишняя абсолютщина про путь к репозиторию там, где она не нужна как жёсткая норма.
- Обновлён `.agents/rules/specs/CONTROL_FLOW.md`:
  - добавлен раздел `Открытие ключевых процессных markdown-артефактов`;
  - закреплено обязательное открытие через `Start-Process` для formal `MAIL/<ROLE>/IN/*.md` и formal `MAIL/<ROLE>/OUT/*_report.md` при каждом создании или изменении;
  - явно записано, что повторные правки того же файла тоже требуют `Start-Process`;
  - закреплено, что по умолчанию не нужно дампить полный текст такого тикета/отчёта в чат;
  - англоязычные куски из нового policy-блока заменены на ровные русские формулировки без изменения логики исключений.

## ChangedFiles
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents-runtime/MAIL/CODER/OUT/T0021_20260418-2256_CODER_define-start-process-policy-for-key-process-files_report.md`

## ChecksRun
- `rg -n "Start-Process|open file|open report|open ticket" AGENTS.md .agents -S` -> PASS
- Manual consistency check between `.agents/rules/00_COMMON.md` and `.agents/rules/specs/CONTROL_FLOW.md` -> PASS
- Style/wording check for the updated rule blocks -> PASS
  - PASS: `00_COMMON.md` по-прежнему описывает смысл `open file/report/ticket`, а не момент обязательного действия;
  - PASS: `CONTROL_FLOW.md` по-прежнему описывает, когда агент обязан открыть файл через `Start-Process`;
  - PASS: исключения для `RUNLOGS/**`, `.txt`, evidence/file packs, `.agents-runtime/00_STATE.md` и `.agents-runtime/PLANS/**` сохранены;
  - PASS: англоязычные куски из нового policy-блока заменены на более ровные русские формулировки без смыслового сдвига.
- `process-consistency-lint` targeted pass:
  - PASS: `AGENTS.md` остаётся router/bootstrap и не стал competing source of truth;
  - PASS: helper-layer не переопределяет новый contract, правило зафиксировано в rule/spec layer;
  - PASS: `.agents-runtime/00_STATE.md` содержит одну запись в `Active`;
  - INCONCLUSIVE: полный repo-wide link audit не потребовался для узкого scope тикета и отдельно не запускался.

## Artifacts
- `.agents-runtime/MAIL/CODER/OUT/T0021_20260418-2256_CODER_define-start-process-policy-for-key-process-files_report.md`

## Issues
- Новых process-конфликтов в рамках узкого scope не выявлено.

## FollowUps
- Template-layer сейчас менять не требуется: source-of-truth policy уже закреплена в `.agents/rules/**`.
- Если Owner позже захочет усилить helper ergonomics, можно отдельно синхронизировать нейтральную подсказку в `.agents/templates/LEAD_PROMPT.md`, не превращая templates в конкурирующий нормативный слой.
