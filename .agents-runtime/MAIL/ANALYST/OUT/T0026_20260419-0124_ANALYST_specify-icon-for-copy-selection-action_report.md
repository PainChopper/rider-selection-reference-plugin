# ANALYST OUT REPORT

Status: `DONE`
CreatedAt: `2026-04-19 01:29`
SourceTicket: `.agents-runtime/MAIL/ANALYST/IN/T0026_20260419-0124_ANALYST_specify-icon-for-copy-selection-action.md`
Workspace: `D:\.DEV\JetBrainsPlagins`

## Summary
- Исследован текущий action `Copy Selection with Path and Lines`: он зарегистрирован в `EditorPopupMenu`, сейчас не имеет `icon`, показывается только при непустом выделении и по смыслу копирует не просто текст, а структурированный code-context с путём, диапазоном строк и отдельным diff-flow.
- Через official JetBrains docs подтверждён технически корректный способ подключения icon asset для action и ограничения по формату/размеру SVG.
- Подготовлены 3 жизнеспособные визуальные концепции, выбрана одна финальная рекомендация и дан точный handoff для следующего `CODER` без реализации asset'а и без правок плагина в рамках этого шага.

## Confirmed Facts
- `src/main/resources/META-INF/plugin.xml` регистрирует action `com.jetbrainsplagins.ridercopy.CopySelectionWithPathAndLinesAction` в группе `EditorPopupMenu` и сейчас не содержит `icon=...`.
- `src/main/kotlin/com/jetbrainsplagins/ridercopy/CopySelectionWithPathAndLinesAction.kt` подтверждает смысл команды: action копирует structured payload, включая `[FILE]`, `[LINES]`, `[LANG]`, `[CODE]`, а в diff-контексте — `[DIFF_FILE]`, `[DIFF_RANGE]`, `[DIFF]`.
- В проекте стандартный Gradle layout; отдельного каталога `icons/` в `src/main/resources` пока нет.
- `build.gradle.kts` + `gradle.properties` показывают обычный packaging plugin resources без кастомных `sourceSets`; icon asset в `src/main/resources/icons/` будет корректно попадать в plugin artifact.

## Official Doc Baseline
- Источник official docs через `context7-first`: library ID `/jetbrains/intellij-sdk-docs`.
- JetBrains docs подтверждают, что action icon можно ссылать напрямую из `plugin.xml` путём вида `icon="/icons/myAction.svg"`, если asset лежит в resources.
- Для action icon целевой размер — `16x16`.
- Предпочтительный формат — `SVG`; базовый размер задаётся через `width="16"` и `height="16"`.
- Для dark theme поддерживается отдельный вариант с суффиксом `_dark`; если базовый SVG читается нормально, отдельный dark-файл не обязателен, но допустим и поддерживается платформой.
- Style-guide JetBrains для small icons: простая плоская геометрия, без градиентов и теней, видимая часть внутри примерно `14x14` области у `16x16` иконки, минимальная детализация, выравнивание по пиксельной сетке, основной stroke обычно `2px`.
- Для action icons JetBrains рекомендуют по умолчанию серую монохромную основу; синий допустим как малый accent в сложной иконке, а не как заливка “всего подряд”.

### Official References
- `https://plugins.jetbrains.com/docs/intellij/icons.html`
- `https://plugins.jetbrains.com/docs/intellij/icons-style.html`

## Criteria For This Action Icon
- Иконка должна читаться как `copy structured code context`, а не как абстрактный clipboard/paste.
- Смысл должен держаться формой даже без цвета, потому что action живёт в сером контекстном меню и цвет не должен быть единственным носителем смысла.
- На размере `16x16` нельзя полагаться на мелкий текст, цифры строк или сложный path-string; нужны 1 основной силуэт + 1 ясный secondary cue.
- Иконка не должна спорить по смыслу с обычным Copy/Paste в IDE; ей нужен акцент именно на “кодовый фрагмент с метаданными”.

## Strong Concepts

### 1. Document/Card + Code Lines + Copy Modifier
- Образ: прямоугольная карточка фрагмента кода, внутри 2-3 горизонтальные code-lines; в одном углу маленький modifier, обозначающий copy.
- Плюсы:
  - Лучше всего передаёт “не сырой clipboard, а оформленный snippet”.
  - Хорошо работает в `16x16`: основной силуэт крупный, modifier можно держать маленьким и читаемым.
  - Можно зашить line/context-смысл через короткие полосы или gutter-like marks без перегруза.
- Риски:
  - Если modifier сделать слишком крупным, иконка станет похожа на “duplicate file”.
  - Если попытаться ещё и path показать прямым слэшем, образ может стать перегруженным.

### 2. File Path Header + Code Block
- Образ: документ с короткой верхней header-strip, которая намекает на path/header, и ниже 2-3 code lines.
- Плюсы:
  - Лучше остальных передаёт, что action копирует не только код, но и metadata header.
  - Чуть ближе к фактическому формату вывода (`[FILE]`, `[LINES]`, `[LANG]`, `[CODE]`).
- Риски:
  - На `16x16` header-strip и body легко слепляются в просто “листок”.
  - Без явного copy cue часть пользователей будет читать иконку как “show snippet”, а не “copy snippet”.

### 3. Clipboard + Code Snippet
- Образ: компактный clipboard с 2-3 code lines внутри.
- Плюсы:
  - Мгновенно считывается как операция копирования.
  - Не требует отдельного modifier и визуально очень прост.
- Риски:
  - Слишком generic: теряется отличие от обычного Copy.
  - Clipboard-силуэт семантически тащит в сторону paste/clipboard-management, а не structured code context.

## Final Recommendation
- Рекомендуемая концепция: `Document/Card + Code Lines + small Copy Modifier`.
- Почему именно она:
  - Это лучший компромисс между мгновенной читаемостью и специфичностью действия.
  - Основной документ/card передаёт, что копируется оформленный фрагмент, а не просто команда clipboard.
  - Малый copy-modifier даёт явный action cue и снимает риск, что иконку прочитают как просто “preview snippet”.
  - Внутренние code-lines можно сделать достаточно абстрактными, чтобы они намекали и на строки, и на структурированность, но не распадались в пиксельную кашу.
- Рекомендуемая визуальная формула:
  - Основная форма: простой лист/карточка с прямыми углами.
  - Внутри: 2-3 горизонтальные линии разной длины как code-lines.
  - Modifier: маленький duplicate/copy cue в правом нижнем углу либо в левом нижнем, если так лучше сохраняется читаемость основной формы.
  - Цвет: нейтральная серая база + максимум один маленький синий акцент на modifier или одной линии, но не обязательный.

## Rejected As Final Choice
- `File Path Header + Code Block` оставлен как сильный запасной вариант, но он слабее коммуницирует именно действие copy.
- `Clipboard + Code Snippet` оставлен как самый простой вариант, но он слишком generic и хуже передаёт отличие этой команды от обычного copy.

## Exact Handoff For CODER

### Asset Placement
- Создать каталог `src/main/resources/icons/`.
- Подготовить основной asset:
  - `src/main/resources/icons/copySelectionWithPathAndLines.svg`
- Рекомендуемый dark variant для гарантированной читаемости:
  - `src/main/resources/icons/copySelectionWithPathAndLines_dark.svg`

### Format And Naming
- Формат: чистый `SVG`.
- Базовый размер: `16x16`.
- Именование: camelCase, без пробелов, по схеме из JetBrains docs.
- `@2x` вариант не обязателен, потому что SVG достаточно для этого кейса.

### Visual Constraints
- Держать видимую геометрию внутри безопасной области примерно `14x14`.
- Использовать простые плоские формы, без градиентов, теней и мелкого декоративного мусора.
- Выравнивать формы по пиксельной сетке; избегать off-grid деталей, которые расползутся при растеризации.
- Не использовать цвет как единственный differentiator: силуэт должен быть понятен даже в монохроме.
- Если использовать accent color, ограничить его одной малой зоной; safest choice — нейтральная база + малый blue accent.
- Не пытаться рисовать буквальный `path`, цифры строк или текстовые токены `[FILE]/[LINES]`: это не переживёт `16x16`.

### Plugin Touchpoint
- Изменить только registration action в `src/main/resources/META-INF/plugin.xml`, добавив атрибут:
  - `icon="/icons/copySelectionWithPathAndLines.svg"`
- Дополнительный Kotlin icon-holder (`MyIcons`) для этого кейса не нужен, потому что icon используется ровно в одном месте и может быть referenced by path прямо из `plugin.xml`.

### Validation For CODER
- После добавления asset'а и подключения в `plugin.xml` вручную проверить action в `EditorPopupMenu` минимум в light и dark theme.
- Проверить, что на сером фоне popup menu иконка:
  - не проваливается по контрасту;
  - не путается с generic copy/paste;
  - остаётся читаемой в disabled/neighboring menu environment.
- Прогнать `.\gradlew.bat buildPlugin` после wiring, чтобы убедиться, что resource packaging и `plugin.xml` не сломаны.

### Scope Guardrails
- В рамках следующего CODER-шага не менять текст action, группу меню и семантику команды.
- Не вводить отдельный `IconMappings.json` или `expui`-ветку, если не появится явная потребность в отдельной New UI-specific версии; для текущего menu action это не требуется.
- Не заменять рекомендуемую концепцию на generic clipboard-only icon без явного нового решения Owner.

## Delivered
- Подготовлен формальный аналитический `OUT` с технически корректным способом подключения icon asset к action.
- Сформулированы 3 жизнеспособные концепции и выбрана одна финальная рекомендация.
- Подготовлен точный handoff для следующего `CODER` с путями, форматом, naming и точкой подключения.

## ChangedFiles
- `.agents-runtime/MAIL/ANALYST/OUT/T0026_20260419-0124_ANALYST_specify-icon-for-copy-selection-action_report.md`

## ChecksRun
- Прочитаны обязательные файлы из тикета и bootstrap/rules/role-layer.
- Official-doc pass выполнен через `context7-first` по `/jetbrains/intellij-sdk-docs` и сверке с официальными страницами JetBrains:
  - `icons.html`
  - `icons-style.html`
- Manual repo check выполнен по:
  - `src/main/resources/META-INF/plugin.xml`
  - `src/main/kotlin/com/jetbrainsplagins/ridercopy/CopySelectionWithPathAndLinesAction.kt`
  - `build.gradle.kts`
  - `gradle.properties`
- Build/toolchain sanity check:
  - `.\gradlew.bat buildPlugin --dry-run` -> `BUILD SUCCESSFUL`

## Artifacts
- нет

## Issues
- Скриншот контекстного меню из описания тикета отдельно не анализировался как отдельный file artifact в этом прогоне; blocker отсутствует, так как menu placement и UX-role action подтверждены по `plugin.xml` и самому контракту тикета.

## FollowUps
- `LEAD` может подготовить следующий `CODER`-тикет на создание SVG asset'а и подключение его в `plugin.xml` по handoff из этого отчёта.
