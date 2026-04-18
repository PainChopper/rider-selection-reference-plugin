# CODER REPORT

Role: CODER
TicketId: T0012
SourceTicket: `.agents-runtime/MAIL/CODER/IN/T0012_20260411-2237_CODER_fix-diff-copy-to-use-real-diff-viewer-data.md`
CreatedAt: 2026-04-11
Workspace: `D:\.DEV\JetBrainsPlagins`

## Что сделано
1. Переписан diff-mode в `src/main/kotlin/com/jetbrainsplagins/ridercopy/CopySelectionWithPathAndLinesAction.kt`:
   - убран основной механизм на эвристике текста текущего diff-редактора;
   - diff теперь собирается из реального diff-context через data keys (`diff_request`, `diff_current_content`) и `ContentDiffRequest`.
2. Добавлено извлечение данных обеих сторон diff из API (`DocumentContent`) и вычисление line fragments через `ComparisonManager.compareLines(...)`.
3. Формирование `[DIFF]` теперь строится по реальным diff line fragments:
   - выбор фрагментов, пересекающихся с выделением в текущей стороне;
   - генерация unified hunk header `@@ -a,b +c,d @@`;
   - генерация строк с префиксами `-` и `+` из левой/правой версии.
4. Для определения активной стороны в side-by-side добавлен приоритет `diff_current_content` из diff viewer data context, с fallback по `editor.document`.
5. Сохранён приоритет diff-mode в diff editor: если API-контекст доступен, возвращается diff-format; если недоступен — безопасный fallback на исходный selected text.
6. Обычный file-context `[FILE]/[LINES]/[LANG]/[CODE]` не изменён по формату и продолжает работать отдельно от diff-mode.
7. Перед переписью сохранён бэкап исходника:
   - `src/main/kotlin/com/jetbrainsplagins/ridercopy/CopySelectionWithPathAndLinesAction.kt.bak`

## Почему раньше была деградация в plain text
1. Предыдущая реализация считала diff корректным только если выделенные строки в текущем `editor.document` уже выглядели как unified diff (`+/-/@@`).
2. В реальном side-by-side viewer правая/current колонка обычно содержит обычный текст файла без таких префиксов.
3. Из-за этого проверки "надежности diff-строк" не проходили и срабатывал fallback в plain selected text.

## Проверки
1. Минимальная сборка выполнена дважды после правок:
   - `./gradlew.bat build --no-daemon`
   - результат: `BUILD SUCCESSFUL`.
2. По коду подтверждено:
   - diff-mode использует реальные данные diff viewer API (`diff_request`, `diff_current_content`), а не парсинг визуального текста документа;
   - side-by-side текущая сторона не деградирует автоматически в plain text из-за отсутствия `+/-/@@` в документе;
   - file-context логика не затронута как основной режим вне diff editor.

## Ограничения
1. В этом CLI-прогоне не выполнялась ручная интерактивная проверка в UI Rider на живом commit viewer.
2. Diff-format формируется для 2-way `ContentDiffRequest` с `DocumentContent`; если конкретный viewer не даёт этот контракт, остаётся безопасный fallback.

## Критерии готовности
- [x] Основной пользовательский сценарий diff viewer исправлен на уровне логики API.
- [x] Diff-format формируется из реального diff-context.
- [x] Plain text fallback больше не является результатом основной эвристики по визуальному тексту diff editor.
- [x] Подготовлен OUT-отчёт с изменениями и проверками.
