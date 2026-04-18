# ANALYST REPORT

Role: ANALYST
TicketId: T0014
SourceTicket: `.windsurf/sandbox/MAIL/ANALYST/IN/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction.md`
CreatedAt: 2026-04-17
Workspace: `D:\.DEV\JetBrainsPlagins`

## 1. Карта конфликтов и повторов

- Bootstrap-конфликт подтверждён между `AGENTS.md` и `.aiassistant/rules/00_COMMON.md`.
  `AGENTS.md` задаёт старт с `00_COMMON.md` и `CONTROL_FLOW.md`, а `00_COMMON.md` в ответ требует сначала читать `AGENTS.md`. Это образует двусторонний bootstrap-cycle, а не один entrypoint.
- Иерархия правил зафиксирована неполно.
  `AGENTS.md` явно называет только частичный приоритет role-файла над `00_COMMON.md`; место для `specs/*`, `TEMPLATES/*`, будущих `skills/*` и historical `MAIL/**` как отдельных слоёв в repo rule-layer сейчас не закреплено.
- Есть подтверждённый conflict family по письменной process-норме `Start-Process`.
  `CONTROL_FLOW.md` считает нормой печать process-файлов в чате вместо `Start-Process`, а ранний LEAD-тикет `T0001` и шаблон `TEMPLATES/LEAD_PROMPT.md` требуют обязательный `Start-Process`.
  Важно: `MAIL/**`-артефакты не являются доказательством факта выполнения или невыполнения `Start-Process`, потому что в текущей агентной системе Codex сам запуск происходит в chat/runtime слое и обычно не оставляет следа в process-файле.
- Есть подтверждённый path drift.
  Repo rules/specs продолжают фиксировать старый workspace `D:\!_DEV_!\JetBrainsPlagins`, тогда как часть более поздних `OUT`-артефактов уже использует `D:\.DEV\JetBrainsPlagins`.
- Слой reusable procedures отсутствует как отдельная сущность.
  `.aiassistant/skills` сейчас отсутствует физически, при том что повторяемые recipes уже размазаны по historical tickets/reports и частично по rules.
- Для текущей среды подтверждён ещё один отдельный фактор: активное пользовательское участие в runtime-цикле Codex.
  Пользователь не только выдаёт стартовый тикет, но и по ходу треда уточняет, останавливает, перенаправляет, просит перепроверки и добивается дополнительных действий; значит, фактический process-contract формируется не только статическими артефактами, но и живым user-agent взаимодействием в чате.
- По `00_STATE.md` зафиксирован именно drift family, а не текущая активная поломка.
  В staged diff в этом проходе видно, что файл ранее отставал (`NextTicket: T0014`, `Active: T0013`) и был исправлен до `NextTicket: T0015`, `Active: T0014`; на момент завершения текущего прохода актуальный файл уже поправлен и сам по себе больше не является открытым конфликтом.
- Независимый verifier-источник `T0016` подтверждает template-side часть той же карты конфликтов, но не подменяет собственный анализ:
  verifier отдельно подтвердил конфликт по `Start-Process`, неполноту `TASK/REPORT/VERIFY` и ненормализованный статус `TEMPLATES/*`.

## 2. Карта template-semantics

- `TEMPLATES/INDEX.md` задаёт базовую семантику шаблонов как scaffolding/reference layer с приоритетом ниже активного тикета и актуальных `rules/specs`.
- Эта семантика частично достаточна для определения места шаблонов в системе, но пока не задаёт полный operational contract.
  В `INDEX.md` есть правило приоритета и оговорка "фиксировать follow-up, а не обходить молча", но нет явной нормы, насколько шаблон обязан совпадать с фактическим рабочим форматом `MAIL/**`.
- `LEAD_PROMPT.md` сейчас несёт не только формат handoff, но и собственную process-норму: обязательный `Start-Process` после обновления `MAIL`-файла.
  Это делает его не нейтральным шаблоном, а носителем конфликтующей process-semantics.
- `TASK.md` является минимальным skeleton-шаблоном, но не покрывает устойчивые секции фактических `IN`-тикетов.
  В реальной практике стабильно встречаются `Scope`, `Не делать`, `Входные артефакты`, а для сложных handoff ещё и `Обязательная форма OUT-отчёта`.
- `REPORT.md` задаёт свой header-contract (`Status`, `FinishedAt`), который расходится с доминирующим фактическим шаблоном `OUT`-отчётов (`SourceTicket`, `Workspace`).
- `VERIFY.md` годится как общий диагностический каркас, но не гарантирует соблюдение более жёсткой структуры заключения, которую задают verifier-тickets серии.
- Независимый verifier `T0016` подтверждает именно такой статус template-semantics:
  `TEMPLATES/*` пока можно трактовать только как вспомогательные заготовки, а не как нормализованный template layer.

## 3. Кандидаты для bootstrap-flow

- Кандидат 1: закрепить `AGENTS.md` как единственный bootstrap-entrypoint.
  Evidence: сейчас только `AGENTS.md` позиционируется как "точка входа", но `00_COMMON.md` повторно возвращает агента к `AGENTS.md`, создавая цикл.
- Кандидат 2: убрать из `00_COMMON.md` любые инструкции вида "сначала прочитать `AGENTS.md`".
  Это не архитектурный redesign, а factual cleanup, который разрывает текущий цикл.
- Кандидат 3: после `AGENTS.md` грузить только repo-wide rule-layer (`00_COMMON.md`, `specs/*`) и только потом role-file.
  Evidence: такая последовательность уже почти описана в `AGENTS.md`, но не замкнута в один однозначный bootstrap-flow.
- Кандидат 4: активный тикет читать после загрузки актуального rule-layer, а historical `MAIL/**` не включать в bootstrap вообще.
  Historical artifacts нужны как evidence и reference, но не как стартовый нормативный слой.
- Кандидат 5: `skills/*` не должны участвовать в bootstrap автоматически.
  Они уместны только как opt-in recipes по ссылке из тикета/rules после загрузки rule-layer.

## 4. Кандидаты для иерархии правил

- Кандидат 1: явно отделить source-of-truth layer от вспомогательных слоёв.
  В source-of-truth должны входить активный запрос/тикет, role-file, `specs/*`, `00_COMMON.md`, bootstrap-функция `AGENTS.md`.
- Кандидат 2: зафиксировать `TEMPLATES/*` ниже rules/specs и ниже активного тикета, но выше pure history только как scaffolding/reference.
  Evidence: именно такую базовую семантику уже задаёт `TEMPLATES/INDEX.md`.
- Кандидат 3: зафиксировать `skills/*` как reusable recipes, которые не могут переопределять rules при конфликте.
  Этот статус прямо ожидается тикетом `T0014`, а физического skill-layer в репозитории пока нет.
- Кандидат 4: закрепить historical `MAIL/**` как evidence/history layer без нормативной силы.
  Это нужно для снятия конфликтов по `Start-Process`, workspace path и ранним процессным практикам без ретро-правки истории.
- Кандидат 5: явно описать ownership-правила внутри process-layer отдельно от иерархии чтения.
  Из evidence видно, что без этого `00_STATE` и staging process-артефактов начинают дрейфовать между ролями и remain implicit.

## 5. Кандидаты для rules vs skills boundary

- В rules остаются только конституционные нормы:
  bootstrap, порядок применения слоёв, repo boundary, process-contract, role duties, conflict resolution, ownership.
- В skills уходят повторяемые execution recipes:
  создание process-артефактов, process-consistency checks, Rider/IntelliJ build compatibility, diff-context copy recipe, IDE language detection recipe.
- Кандидатные skill topics, подтверждённые evidence из history и current scope:
  `PROCESS_ARTIFACTS`
  `PROCESS_CONSISTENCY_LINT`
  `INTELLIJ_PLATFORM_BUILD_COMPAT`
  `RIDER_DIFF_CONTEXT_COPY`
  `IDE_LANGUAGE_DETECTION`
- Граница слоя:
  если инструкция отвечает на вопрос "что обязательно истинно в репозитории" — это rules;
  если инструкция отвечает на вопрос "как повторяемо выполнять типовой класс задач" — это skill.
- `TEMPLATES/*` не являются ни rules, ни skills.
  Это отдельный scaffolding layer для формы process-артефактов, который обязан уступать rules/specs и активному тикету.

## 6. Исторические артефакты, которые не нужно переписывать

- `.windsurf/sandbox/MAIL/LEAD/IN/T0001_20260410-0108_LEAD_rider-plugin-bootstrap.md`
  Полезный historical evidence для раннего process-contract и старого workspace path.
- `.windsurf/sandbox/MAIL/ANALYST/IN/T0002_20260410-0130_ANALYST_specify-rider-plugin-copy-format-bootstrap.md`
- `.windsurf/sandbox/MAIL/ANALYST/OUT/T0002_20260410-0130_ANALYST_specify-rider-plugin-copy-format-bootstrap_report.md`
- `.windsurf/sandbox/MAIL/ANALYST/OUT/T0010_20260411-2202_ANALYST_specify-diff-copy-format-and-ide-language-detection_report.md`
- `.windsurf/sandbox/MAIL/CODER/IN/T0003_20260410-0205_CODER_bootstrap-rider-plugin-and-implement-copy-action.md`
- `.windsurf/sandbox/MAIL/CODER/OUT/T0003_20260410-0205_CODER_bootstrap-rider-plugin-and-implement-copy-action_report.md`
- `.windsurf/sandbox/MAIL/CODER/OUT/T0011_20260411-2220_CODER_implement-ide-language-detection-and-diff-copy-format_report.md`
  Полезный evidence того, что path drift продолжался и после части более новых реализационных тикетов.
- Другие superseded `MAIL/**`-артефакты, где встречаются старый workspace path или устаревший process-contract, тоже остаются history, а не объектом массовой ретро-правки.

## 7. Factual cleanup items

- Разорвать bootstrap-cycle между `AGENTS.md` и `00_COMMON.md`.
- Явно записать одну иерархию слоёв для active ticket, role-file, `specs/*`, common rules, templates, skills и history.
- Нормализовать канонический workspace для новых rules/process-артефактов на `D:\.DEV\JetBrainsPlagins`.
- Явно оставить старый `D:\!_DEV_!\JetBrainsPlagins` только как historical drift, если он вообще нужен в актуальном rule-layer.
- Явно определить `TEMPLATES/*` как scaffolding/reference layer и убрать из шаблонов конфликтующие process-нормы.
- Дотянуть `TASK.md`, `REPORT.md`, `VERIFY.md` хотя бы до минимального совпадения с устойчивой process-практикой `MAIL/**`.
- Завести отдельный `.aiassistant/skills` как physical layer для reusable recipes.
- Явно зафиксировать, что `skills/*` не могут быть источником истины при конфликте.
- Явно закрепить ownership за `.windsurf/sandbox/00_STATE.md` и за staging process-артефактов, чтобы drift не возвращался.
- Не выполнять массовую ретро-правку old `MAIL/**`; cleanup должен происходить только в актуальном rule/template layer.
- Не пытаться доказывать или опровергать факт runtime-выполнения `Start-Process` по содержимому тикетов и отчётов.
  Для следующего прохода это не evidence-path; evidence-path здесь только письменный contract в rules/templates/tickets, явные пользовательские указания и учёт того, что часть действий исполняется в Codex chat/runtime слое вне `MAIL/**`.
- Явно учитывать, что в Codex пользователь может активно управлять исполнением уже после выдачи тикета.
  Следующий `ANALYST` должен трактовать живое user-agent взаимодействие как отдельный runtime layer, который влияет на реальные шаги агента, но не всегда отражается в process-файлах.
- Отдельно учитывать, что chat/runtime действия агента вроде `Start-Process` существуют как операционные шаги среды Codex и не обязаны материализоваться в `MAIL/**`.

## 8. Пакет артефактов для следующего ANALYST

- Основной evidence-first отчёт:
  `.windsurf/sandbox/MAIL/ANALYST/OUT/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction_report.md`
- Supporting appendix с точечными evidence-ссылками:
  `.windsurf/sandbox/MAIL/ANALYST/OUT/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction_evidence-pack.md`
- Независимый verifier-источник по `TEMPLATES/*`:
  `.windsurf/sandbox/MAIL/VERIFIER/OUT/T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md`
- Ключевые repo/rule hotspots для второго прохода без повторного полного раскопа:
  `AGENTS.md`
  `.aiassistant/rules/00_COMMON.md`
  `.aiassistant/rules/specs/CONTROL_FLOW.md`
  `.windsurf/sandbox/TEMPLATES/INDEX.md`
  `.windsurf/sandbox/TEMPLATES/TASK.md`
  `.windsurf/sandbox/TEMPLATES/REPORT.md`
  `.windsurf/sandbox/TEMPLATES/VERIFY.md`
  `.windsurf/sandbox/TEMPLATES/LEAD_PROMPT.md`
  `.windsurf/sandbox/00_STATE.md`
- Граница следующего прохода:
  evidence pack уже достаточно полон для архитектурного синтеза;
  следующий `ANALYST` не должен заново перечитывать весь `MAIL/**`, если не возникнет новой неоднозначности сверх зафиксированных evidence;
  при синтезе нужно явно учитывать Codex как фактическую агентную среду исполнения, а не абстрактный "внешний агент";
  также нужно учитывать активное пользовательское участие в треде как отдельный фактор формирования runtime-contract.
