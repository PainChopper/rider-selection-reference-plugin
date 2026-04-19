# TASK — formal ticket

Role: CODER
TicketId: T0027
Title: add-action-icon-from-owner-svg
CreatedAt: 2026-04-19 01:47
OwnerRequest: Owner хочет добавить иконку к action `Copy Selection with Path and Lines`.
Нужно использовать SVG-мотив, который Owner передал напрямую, а не финальную рекомендованную концепцию из analyst-отчёта.
Иконка должна ориентироваться по тону на соседние menu icons в IDE: для тёмной темы это светло-серый линейный вариант, а не чёрный и не кричаще-белый.
Workspace: `.`

## Scope
- Создать icon assets для action `Copy Selection with Path and Lines` на основе SVG, который Owner передал в чате и как файл `D:\.DEV\_AGENT-SYSTEM-MIGRATION\download.svg`.
- Подготовить как минимум light и dark варианты иконки для корректной читаемости в меню IDE.
- Разместить assets в plugin resources по совместимому с JetBrains Platform пути.
- Подключить иконку к action в `src/main/resources/META-INF/plugin.xml`.
- Прогнать локально-релевантную проверку, затем полный build проекта, затем релевантные тесты или явно зафиксировать, что отдельных тестов для этого изменения нет.
- Подготовить `CODER OUT` с точным описанием файлов, цветов/вариантов и проведённых проверок.

## OutOfScope
- Не менять текст action, его id, группу меню или hotkey.
- Не перепридумывать новую форму иконки вместо owner-provided SVG-мотива.
- Не расползаться в редизайн других иконок, branding плагина или UI вне этого action.
- Не менять код `CopySelectionWithPathAndLinesAction.kt`, если для подключения иконки это не требуется.
- Не менять process/rule files, кроме собственного `OUT`.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/CODER.md`
- `.agents-runtime/MAIL/ANALYST/OUT/T0026_20260419-0124_ANALYST_specify-icon-for-copy-selection-action_report.md`
- `src/main/resources/META-INF/plugin.xml`
- `D:\.DEV\_AGENT-SYSTEM-MIGRATION\download.svg`

## RequiredSkills
- `intellij-platform-build-compat` — чтобы корректно положить resources, не сломать packaging и прогнать совместимую build-проверку.
- `process-artifact-workflow` — чтобы корректно оформить собственный `OUT`.

## Inputs
- Owner явно выбрал SVG-мотив из переданного `download.svg`, даже если он расходится с финальной рекомендацией аналитика.
- Owner отдельно уточнил, что по тону иконки нужно ориентироваться на соседние menu icons на скриншоте IDE.
- По текущему наблюдению в тёмной теме соседние menu icons выглядят как спокойный светло-серый UI-тон, а не pure white.
- Analyst handoff подтверждает корректный technical wiring через `icon="/icons/<name>.svg"` в `plugin.xml` и рекомендует layout `src/main/resources/icons/`.

## Steps
1. Перечитать обязательные live rules, `CODER.md`, analyst `OUT`, текущий `plugin.xml` и owner-provided SVG.
2. Создать каталог `src/main/resources/icons/`, если его ещё нет.
3. На основе owner-provided SVG подготовить основной icon asset для light theme и отдельный `_dark` variant для тёмной темы.
4. Подобрать stroke/color так, чтобы dark variant визуально попадал в тон соседних menu icons и не выглядел ни чёрным, ни чисто белым.
5. Подключить иконку к action `Copy Selection with Path and Lines` в `src/main/resources/META-INF/plugin.xml`.
6. Если в SVG нужны минимальные технические правки ради JetBrains menu readability или theme split, выполнить только минимально достаточные изменения без смены базового мотива.
7. После изменения project-files прогнать локально-релевантную проверку, затем полный build проекта, затем релевантные тесты; найденные проблемы по возможности исправить в пределах этого scope.
8. Подготовить `OUT` с точными путями assets, описанием theme split, фактическими цветовыми решениями и результатами проверок.

## Checks
- Manual review of `src/main/resources/icons/*.svg` -> подтверждает, что созданы light/dark assets на базе owner-provided SVG-мотива.
- `rg -n 'icon=\"|CopySelectionWithPathAndLinesAction' src/main/resources/META-INF/plugin.xml -S` -> подтверждает wiring иконки к нужному action.
- Локально-релевантная resource/plugin.xml sanity check -> подтверждает, что wiring не содержит очевидной ошибки пути.
- `.\gradlew.bat buildPlugin` -> подтверждает, что plugin resources и `plugin.xml` собираются.
- Если отдельных тестов для этого изменения нет, зафиксировать это явно в `OUT` вместо выдуманного тестового прогона.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/CODER/OUT/T0027_20260419-0147_CODER_add-action-icon-from-owner-svg_report.md`
- дополнительные артефакты: only if genuinely needed

## StateImpact
- `LEAD` должен прочитать resulting `CODER OUT`, после чего снять `T0027` из `Active` в `.agents-runtime/00_STATE.md`.

## Acceptance
- [ ] Созданы icon assets для action на основе owner-provided SVG-мотива.
- [ ] Есть отдельный dark variant или иное явно зафиксированное theme-safe решение для тёмного меню IDE.
- [ ] Иконка подключена к `Copy Selection with Path and Lines` в `plugin.xml`.
- [ ] Иконка в тёмной теме ориентирована по тону на соседние menu icons, а не сделана pure black или pure white без необходимости.
- [ ] `buildPlugin` проходит успешно или честно зафиксирован реальный blocker.
- [ ] `CODER OUT` фиксирует точные пути, theme split и проведённые проверки.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: high
- comment: нужен аккуратный resource/plugin.xml edit с owner-driven visual override и нормальной build discipline
