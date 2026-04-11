# TASK — тикет

Role: CODER
TicketId: T0008
CreatedAt: 2026-04-10 23:43

## Контекст

Текущий проект Rider-плагина собирается и локально работает, но build-конфигурация использует устаревший `Gradle IntelliJ Plugin 1.x`:

- `build.gradle.kts`: `id("org.jetbrains.intellij") version "1.17.4"`
- `gradle.properties`: `platformVersion = 2024.3`

При сборке `buildPlugin` уже выводится предупреждение, что `Gradle IntelliJ Plugin 1.x does not support building plugins against the IntelliJ Platform 2024.2+ (242+)` и требуется переход на `IntelliJ Platform Gradle Plugin 2.x`.

У пользователя целевой Rider уже версии `2025+`, поэтому этот build-stack нужно привести в поддерживаемое состояние, пока проект маленький и миграция остаётся локальной.

## Scope

- Перевести проект с `org.jetbrains.intellij` 1.x на актуальный `IntelliJ Platform Gradle Plugin 2.x` или выше.
- Сохранить сборку Rider-плагина внутри текущего репозитория.
- Сохранить текущую функциональность action и структуру проекта.
- Обновить build-конфигурацию минимально достаточно, без лишнего рефакторинга.
- Добиться состояния, в котором `buildPlugin` больше не сообщает о неподдерживаемом `Gradle IntelliJ Plugin 1.x` для платформы `2024.2+`.

## Не делать

- Не менять бизнес-логику action, хоткей, формат буфера и поведение плагина, если это не требуется самой миграцией build-конфига.
- Не расширять scope до публикации в Marketplace, подписей, CI, release pipeline и прочей инфраструктуры.
- Не менять файлы вне `D:\.DEV\JetBrainsPlagins`.
- Не обновлять зависимости “по максимуму” без необходимости; только то, что нужно для совместимой сборки.

## Входные артефакты

- текущий build-script: `build.gradle.kts`
- параметры платформы: `gradle.properties`
- wrapper/gradle config в корне репозитория
- текущая сборочная диагностика из `buildPlugin`: предупреждение о необходимости миграции на IntelliJ Platform Gradle Plugin 2.x

## Шаги

1. Изучить минимально необходимую миграцию с `org.jetbrains.intellij` 1.x на `IntelliJ Platform Gradle Plugin 2.x` для Rider plugin.
2. Обновить `build.gradle.kts` и связанные настройки build-конфига.
3. Сохранить возможность сборки плагина через `gradlew.bat buildPlugin`.
4. Проверить, что предупреждение про неподдерживаемый `Gradle IntelliJ Plugin 1.x` исчезло.
5. Зафиксировать в отчёте все изменения и результат проверки.

## Проверки

- Проверить, что проект собирается через `.\gradlew.bat buildPlugin`.
- Проверить, что warning про `Gradle IntelliJ Plugin 1.x does not support building plugins against the IntelliJ Platform 2024.2+` больше не появляется.
- Проверить, что итоговая конфигурация по-прежнему ориентирована на Rider / текущую платформу проекта.
- Если в ходе миграции потребуется скорректировать версию Gradle, Kotlin или другие build-only настройки, явно зафиксировать это и причину.

## Критерии готовности

- [ ] Проект мигрирован на поддерживаемый IntelliJ Platform Gradle Plugin 2.x+.
- [ ] `buildPlugin` проходит успешно.
- [ ] Предупреждение про неподдерживаемый `Gradle IntelliJ Plugin 1.x` исчезло.
- [ ] Подготовлен OUT-отчёт с перечислением изменений и проверок.

## Артефакты OUT

- отчёт CODER: `.windsurf/sandbox/MAIL/CODER/OUT/T0008_20260410-2343_CODER_migrate-build-to-intellij-platform-gradle-plugin-2x_report.md`

## Рекомендованная модель и режим

- model: GPT-5.3-Codex
- reasoning effort: medium
- комментарий: задача локальная, кодовая и сборочная; нужен аккуратный migration-патч без расползания scope
