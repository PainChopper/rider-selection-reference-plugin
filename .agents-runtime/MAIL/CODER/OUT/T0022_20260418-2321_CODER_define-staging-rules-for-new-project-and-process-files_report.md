# REPORT — execution report

Role: CODER
TicketId: T0022
Title: define-staging-rules-for-new-project-and-process-files
SourceTicket: `.agents-runtime/MAIL/CODER/IN/T0022_20260418-2321_CODER_define-staging-rules-for-new-project-and-process-files.md`
CreatedAt: 2026-04-18 23:23
Workspace: `D:\.DEV\JetBrainsPlagins`
Status: DONE

## Summary
В live rule-layer добавлены две узкие staging-нормы: общая для новых ключевых process-файлов и role-local нормы для новых project-файлов у `CODER` и `TESTER`.
Правки остались в пределах тикета: `00_COMMON.md` описывает общее правило для новых process-файлов, а role-файлы описывают правила для новых project-файлов внутри соответствующего scope.
Исключения для мусора, временных файлов, `RUNLOGS/**`, `.txt` и других вспомогательных артефактов сохранены как явные оговорки.
После первичной правки выполнен узкий follow-up fix: формулировка уточнена так, чтобы не осталось двусмысленности между "файл просто создан" и "файл добавлен под git"; теперь везде явно закреплено добавление нового значимого файла в `git index`, то есть перевод в tracked-state репозитория.

## Delivered
- Обновлён `.agents/rules/00_COMMON.md`:
  - добавлен раздел `Новые процессные файлы`;
  - закреплено, что новый ключевой процессный файл, который должен стать частью актуального процессного слоя, должен быть добавлен в `git index`, то есть переведён в tracked-state репозитория;
  - по умолчанию правило ограничено новыми формальными тикетами `MAIL/<ROLE>/IN/*.md` и новыми формальными отчётами `MAIL/<ROLE>/OUT/*_report.md`;
  - явно исключены `RUNLOGS/**`, `.txt`, evidence/file packs и другие вспомогательные или промежуточные артефакты, если Owner отдельно не потребовал иное.
- Обновлён `.agents/roles/CODER.md`:
  - добавлено правило, что новый файл проекта, созданный `CODER` в пределах утверждённого scope, должен быть добавлен в `git index`, то есть переведён в tracked-state репозитория;
  - явно исключены мусор, временные файлы, кэши, результаты сборки, `RUNLOGS/**` и другие вспомогательные артефакты.
- Обновлён `.agents/roles/TESTER.md`:
  - добавлено аналогичное правило для нового файла проекта, созданного `TESTER` в пределах утверждённого testing scope: такой файл должен быть добавлен в `git index`, то есть переведён в tracked-state репозитория;
  - явно исключены мусор, временные файлы, кэши, результаты сборки, `RUNLOGS/**` и другие вспомогательные артефакты.

## ChangedFiles
- `.agents/rules/00_COMMON.md`
- `.agents/roles/CODER.md`
- `.agents/roles/TESTER.md`
- `.agents-runtime/MAIL/CODER/OUT/T0022_20260418-2321_CODER_define-staging-rules-for-new-project-and-process-files_report.md`

## ChecksRun
- `rg -n "добав.*репозитор|add.*repository|git index|git add|нов.*project|process-file|process file" .agents -S` -> PASS
- Manual consistency check across `.agents/rules/00_COMMON.md`, `.agents/roles/CODER.md`, and `.agents/roles/TESTER.md` against `.agents/rules/specs/CONTROL_FLOW.md` -> PASS
  - PASS: `00_COMMON.md` описывает только общее правило для новых ключевых process-файлов;
  - PASS: `CODER.md` и `TESTER.md` описывают только role-local правило для новых project-файлов;
  - PASS: правило про `Start-Process` осталось в `CONTROL_FLOW.md` и не было продублировано или изменено;
  - PASS: исключения для `RUNLOGS/**`, `.txt`, evidence/file packs и других вспомогательных артефактов не конфликтуют между common/spec/role layer.
  - PASS: после follow-up fix формулировка во всех трёх файлах однозначно указывает на добавление нового значимого файла в `git index` / tracked-state, а не просто на его наличие в рабочем дереве.
- `process-consistency-lint` targeted pass:
  - PASS: `AGENTS.md` остаётся bootstrap/router и не менялся;
  - PASS: `00_STATE.md` содержит одну запись в `Active`;
  - PASS: новые правила добавлены в нормативный repo-layer, а не в helper-layer;
  - INCONCLUSIVE: repo-wide проверка ссылок между всеми process-артефактами отдельно не запускалась, потому что для узкого scope тикета это не требовалось;
  - INCONCLUSIVE: hit по `.aiassistant` и `.windsurf/sandbox` найден только в advisory/history-like runtime report `T0020`, а не в live rule-layer этого шага.

## Artifacts
- `.agents-runtime/MAIL/CODER/OUT/T0022_20260418-2321_CODER_define-staging-rules-for-new-project-and-process-files_report.md`

## Issues
- Новых противоречий между common/spec/role layer в пределах этого шага не выявлено.
- В рабочем дереве `.agents/rules/00_COMMON.md` уже был staged из предыдущего шага и затем снова изменён в рамках `T0022`; это не влияет на содержательный результат текущего тикета, но важно при последующем `git add`.

## FollowUps
- Если Owner захочет добить единый стиль live rule-layer, можно отдельно русифицировать оставшиеся старые англоязычные слова вроде `scope` и `testing scope` в role-файлах, не меняя policy.
