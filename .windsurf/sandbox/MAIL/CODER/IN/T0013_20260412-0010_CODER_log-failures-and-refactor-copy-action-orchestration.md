# TASK — тикет

Role: CODER
TicketId: T0013
CreatedAt: 2026-04-12 00:10

## Контекст

Нужно доработать текущую реализацию action копирования после review по актуальной версии diff/file logic.

Основание для follow-up:
- в коде есть немые проглатывания ошибок через конструкции вида `catch (_: Throwable) { null }` и `catch (_: Throwable) { false }`;
- `actionPerformed` уже перегружен orchestration-логикой;
- вычисление project-relative path дублируется в нескольких местах.

Ключевой риск: при изменениях JetBrains API функциональность может тихо деградировать без видимой причины, если исключения продолжают проглатываться без логирования.

## Scope

- Найти и убрать немые проглатывания ошибок, связанные с текущей логикой action:
  - заменить `catch (_: Throwable) { null/false }` на поведение с явным логированием через `Logger.getInstance(...)`;
  - сохранить безопасный fallback там, где он нужен по UX, но не оставлять его без следа в логах.
- Упростить `actionPerformed`:
  - оставить в action только получение контекста, вызов нужного builder/assembler и запись результата в clipboard;
  - вынести orchestration по обычному файлу, diff-контексту, путям и языку в отдельные private helper-компоненты, private functions или узкие классы.
- Убрать дублирование вокруг вычисления project-relative path:
  - свести вычисление к одному месту/одной абстракции;
  - убедиться, что file-context и связанные fallback-сценарии используют одну и ту же логику.

## Не делать

- Не менять пользовательский формат output.
- Не вводить новые функции, настройки или UI.
- Не переписывать diff/file feature заново, если для выполнения тикета достаточно локального рефакторинга.
- Не менять scope за пределами текущей action и тесно связанных helper-компонентов.

## Входные артефакты

- отчёт CODER: `.windsurf/sandbox/MAIL/CODER/OUT/T0012_20260411-2237_CODER_fix-diff-copy-to-use-real-diff-viewer-data_report.md`
- актуальная реализация в `src/main/kotlin/com/jetbrainsplagins/ridercopy/`

## Шаги

1. Прочитать отчёт `T0012` и сопоставить его с текущим кодом action.
2. Найти места, где исключения сейчас проглатываются без логирования.
3. Ввести logging через `Logger.getInstance(...)` в местах безопасного fallback.
4. Распилить orchestration из `actionPerformed` на более узкие части без изменения пользовательского поведения.
5. Убрать дублирование вычисления project-relative path.
6. Прогнать проверки и подготовить отчёт.

## Проверки

- Проверить, что в актуальном коде не осталось немых `catch (_: Throwable) { null }` и `catch (_: Throwable) { false }` в логике action.
- Проверить, что `actionPerformed` стал тоньше и больше не содержит лишнюю orchestration-логику.
- Проверить, что вычисление project-relative path теперь живёт в одном месте.
- Проверить, что проект по-прежнему собирается через Gradle Wrapper.

## Критерии готовности

- [ ] Немые проглатывания ошибок заменены на поведение с логированием.
- [ ] `actionPerformed` упрощён и стал orchestration entrypoint, а не местом хранения всей логики.
- [ ] Дублирование project-relative path устранено.
- [ ] Сборка через Gradle Wrapper проходит успешно.
- [ ] Подготовлен OUT-отчёт как вход для следующего шага.

## Артефакты OUT

- отчёт CODER: `.windsurf/sandbox/MAIL/CODER/OUT/T0013_20260412-0010_CODER_log-failures-and-refactor-copy-action-orchestration_report.md`

## Рекомендованная модель и режим

- model: gpt-5.3-codex
- reasoning effort: high
- комментарий: задача локальная по scope, но требует аккуратного рефакторинга вокруг JetBrains API и fallback-логики
