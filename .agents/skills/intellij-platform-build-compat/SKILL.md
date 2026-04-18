---
name: intellij-platform-build-compat
description: Use for build, toolchain, compatibility, IntelliJ Platform, Gradle plugin, Rider or IDE-version tasks. Do not use for process-only tickets or non-build documentation work.
---

Использовать этот skill для build/toolchain/compatibility задач.

## Что делать

1. Сначала прочитать текущий тикет и зафиксировать целевую платформу, IDE и версионный scope.
2. Проверить build-конфигурацию, plugin/toolchain versions и совместимость диапазонов.
3. Запустить релевантные build/verification команды проекта.
4. Зафиксировать конкретные ошибки совместимости и предложить минимальные исправления в пределах тикета.
5. Если вывод длинный, сохранить его в `.agents-runtime/RUNLOGS/<ticket-id>/`.


Этот skill не переопределяет Owner instruction, rules/specs или active ticket.
