# CODER REPORT

Role: CODER
TicketId: T0011
SourceTicket: `.agents-runtime/MAIL/CODER/IN/T0011_20260411-2220_CODER_implement-ide-language-detection-and-diff-copy-format.md`
CreatedAt: 2026-04-11
Workspace: `D:\!_DEV_!\JetBrainsPlagins`

## Что сделано
1. Обновлена реализация `CopySelectionWithPathAndLinesAction` в `src/main/kotlin/com/jetbrainsplagins/ridercopy/CopySelectionWithPathAndLinesAction.kt`.
2. Убрана ручная extension-map логика определения `LANG`.
3. Добавлено IDE-based определение `LANG`:
   - приоритет: `PsiFile.language.id`;
   - fallback: `VirtualFile.fileType.name`;
   - финальный fallback: `text`;
   - выполнена нормализация в lower-case machine-friendly токен.
4. Добавлен отдельный diff-mode при вызове action в diff-редакторе (`EditorKind.DIFF`, через API-интроспекцию):
   - формат результата:
     - `[DIFF_FILE]`
     - `[DIFF_RANGE]`
     - `[LANG] diff`
     - `[DIFF_FORMAT] unified`
     - `[DIFF] ... [/DIFF]`
5. В diff-mode выделение на уровне символов расширяется до целых затронутых строк diff (по границам строк документа редактора).
6. Добавлена проверка надёжности diff-строк:
   - допускаются только строки с корректными diff-префиксами/служебными unified-директивами;
   - при ненадёжном контенте diff-format не синтезируется.
7. Добавлена логика hunk header:
   - если `@@ ... @@` уже в выделении, копируется как есть;
   - если отсутствует, но его можно надёжно взять выше по документу, header добавляется.
8. Для diff-контекста, когда надёжный unified fragment собрать нельзя, сохранено безопасное поведение без генерации fake diff.
9. Обычный file-context формат `[FILE]/[LINES]/[LANG]/[CODE]...[/CODE]` сохранён, но с новой IDE-based стратегией `LANG`.

## Проверки
1. Минимальная сборочная проверка выполнена:
   - команда: `./gradlew.bat build --no-daemon`
   - результат: `BUILD SUCCESSFUL`.
2. Проверено по коду, что:
   - extension-map больше не используется;
   - `LANG` определяется через IDE (`Psi`/`FileType`) с fallback в `text`;
   - diff-mode приоритетен над file-format в diff-контексте;
   - при ненадёжном diff action не возвращает синтетический patch.

## Ограничения проверки
1. Интерактивная ручная проверка в UI Rider для всех видов diff viewer (включая side-by-side в реальной IDE-сессии) в этом CLI-прогоне не выполнялась.
2. В рамках текущей реализации diff-format строится только при надёжном представлении строк как unified diff; при сомнительном контенте применяется безопасный fallback без fake diff.

## Критерии готовности
- [x] IDE-based определение `LANG` реализовано.
- [x] Обычный file-context сохранён.
- [x] Добавлен отдельный diff-context формат unified diff.
- [x] Fallback-сценарии без synthetic diff соблюдены.
- [x] Подготовлен OUT-отчёт.
