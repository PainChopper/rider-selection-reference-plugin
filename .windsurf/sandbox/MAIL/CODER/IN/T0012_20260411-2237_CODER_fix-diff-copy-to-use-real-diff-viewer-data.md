# TASK — тикет

Role: CODER
TicketId: T0012
CreatedAt: 2026-04-11 22:37

## Контекст

После реализации `T0011` выяснилось, что задача diff-copy фактически не решена.

Фактический пользовательский результат:
- при копировании из diff/commit viewer в Rider в буфер попадает просто выделенный текст;
- ожидаемый diff-format не формируется.

Это означает, что текущая реализация diff-mode слишком слаба: она пытается определить unified diff по тексту документа редактора и по строкам с префиксами `+/-/@@`, но в реальном side-by-side diff viewer пользователь выделяет правую/current версию, где текст может не выглядеть как unified diff в документе редактора.

Итог: текущий fallback на plain selected text срабатывает именно там, где пользователь как раз ожидает нормальный diff-output.

## Scope

- Исправить diff-copy так, чтобы оно работало в реальном JetBrains diff/commit viewer, включая side-by-side сценарий пользователя.
- Перестать опираться на "угаданный unified diff по тексту текущего editor document" как на основной механизм.
- Использовать реальные diff/commit viewer данные JetBrains API, если они доступны.
- Сохранить обычный file-context без деградации.
- Не трогать несвязанные build/UX-задачи.

## Что именно считается багом

Текущая ситуация считается дефектом, а не допустимым fallback:
- пользователь находится в diff/commit viewer;
- вызывает action на выделении в правой/current версии diff;
- вместо diff-format получает просто plain text.

Для этого сценария задача считается невыполненной.

## Целевое поведение

### 1. Если action вызвана в diff/commit viewer и API даёт доступ к реальным данным diff
- Нужно формировать diff-format:

`[DIFF_FILE] <path-or-file-label>`
`[DIFF_RANGE] <selected-line-range-in-view-or-unknown>`
`[LANG] diff`
`[DIFF_FORMAT] unified`
`[DIFF]`
`@@ ... @@`
`<diff lines with prefixes>`
`[/DIFF]`

- Для side-by-side viewer правая/current колонка не должна приводить к деградации в plain text только потому, что в её document нет `+/-/@@` как обычного текста.
- Нужно извлекать изменение из diff-структуры viewer-а, а не только из визуального текста текущего editor document.

### 2. Если пользователь находится в правой/current версии diff и нажимает action
- Это по-прежнему diff-context, а не обычный file-context.
- Наличие перехода по `F4` в обычный файл не должно автоматически переключать action в `[FILE]/[CODE]` формат.
- Приоритет за diff-mode, если diff-данные доступны.

### 3. Если реальный diff-контекст через API извлечь нельзя
- Только тогда допустим безопасный fallback.
- Но fallback должен быть осознанным и явно соответствовать спецификации, а не возникать из-за слишком слабой попытки парсить текст документа.

## Техническое направление

- Найти и использовать API JetBrains diff/commit viewer, которые дают доступ к структуре изменений, а не только к editor document текущей стороны.
- Учесть side-by-side diff viewer как основной реальный сценарий.
- При необходимости использовать данные viewer/context/request/fragment, связанные с diff chain, hunk-ами, ranges и current file.
- Не строить основную реализацию на эвристике `строка начинается с '+'/'-'/'@@'`.

## Не делать

- Не объявлять задачу выполненной только на основании того, что код "может" собрать diff в искусственном текстовом документе.
- Не деградировать diff viewer в обычный file-context только потому, что правая сторона связана с реальным файлом.
- Не трогать hotkey, build migration и прочие параллельные тикеты.
- Не менять файлы вне `D:\.DEV\JetBrainsPlagins`.

## Входные артефакты

- текущий тикет/реализация: `.windsurf/sandbox/MAIL/CODER/IN/T0011_20260411-2220_CODER_implement-ide-language-detection-and-diff-copy-format.md`
- отчёт CODER: `.windsurf/sandbox/MAIL/CODER/OUT/T0011_20260411-2220_CODER_implement-ide-language-detection-and-diff-copy-format_report.md`
- текущая реализация action: `src/main/kotlin/com/jetbrainsplagins/ridercopy/CopySelectionWithPathAndLinesAction.kt`
- фактический пользовательский результат: в diff viewer копируется plain text вместо diff-format

## Шаги

1. Прочитать `T0011`, его OUT-отчёт и текущую реализацию.
2. Найти, почему в реальном side-by-side diff viewer action уходит в plain text fallback.
3. Переписать diff-mode так, чтобы он брал данные из реального diff viewer API.
4. Подтвердить, что правая/current сторона diff больше не деградирует в plain text в штатном сценарии.
5. Сохранить обычный file-context без регрессии.
6. Подготовить OUT-отчёт с честным описанием, что именно теперь работает и что, если что, ещё ограничено.

## Проверки

- Проверить, что в реальном diff/commit viewer action больше не копирует просто selected text в основном пользовательском сценарии.
- Проверить, что для side-by-side diff формируется diff-format, а не `[FILE]/[CODE]` и не plain text.
- Проверить, что diff-mode использует реальные diff viewer данные, а не только анализирует текст document текущего editor.
- Проверить, что обычный file-context не сломан.
- Проверить минимальную сборку проекта.

## Критерии готовности

- [ ] Основной пользовательский сценарий diff viewer исправлен.
- [ ] Diff-format формируется из реального diff-context.
- [ ] Plain text fallback больше не маскирует штатный diff-сценарий.
- [ ] Подготовлен OUT-отчёт с результатами проверок.

## Артефакты OUT

- отчёт CODER: `.windsurf/sandbox/MAIL/CODER/OUT/T0012_20260411-2237_CODER_fix-diff-copy-to-use-real-diff-viewer-data_report.md`

## Рекомендованная модель и режим

- model: GPT-5.3-Codex
- reasoning effort: medium
- комментарий: нужен целевой фикс реального дефекта, а не косметическая доработка прежней эвристики
