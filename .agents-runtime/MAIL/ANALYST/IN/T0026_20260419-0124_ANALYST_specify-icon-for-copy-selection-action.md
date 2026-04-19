# TASK — formal ticket

Role: ANALYST
TicketId: T0026
Title: specify-icon-for-copy-selection-action
CreatedAt: 2026-04-19 01:24
OwnerRequest: Owner хочет добавить иконку к команде `Copy Selection with Path and Lines`.
Иконка может быть цветной, но должна уверенно читаться на сером фоне контекстного меню Rider/IntelliJ.
Нужно определить, какой образ и технический формат лучше выбрать, и подготовить чёткую рекомендацию для следующего шага.
Workspace: `.`

## Scope
- Изучить текущую команду плагина и место её показа в UI.
- Определить технически корректный способ добавить icon asset к action в JetBrains Platform plugin.
- Сформулировать визуальные требования к иконке для читаемости на сером фоне контекстного меню.
- Предложить 2-3 жизнеспособные концепции иконки для этой команды.
- Выбрать одну финальную рекомендуемую концепцию с аргументацией.
- Подготовить конкретный handoff для следующего `CODER`: где хранить asset, в каком формате, как назвать, где подключить и какие ограничения соблюсти.

## OutOfScope
- Не рисовать финальный asset и не редактировать `plugin.xml` или код плагина.
- Не запускать `CODER`, `TESTER`, `VERIFIER` или `REVIEWER`.
- Не расползаться в полный редизайн UI, брендинг всего плагина или маркетинговую упаковку.
- Не предлагать абстрактные идеи без одной финальной рекомендации.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `src/main/resources/META-INF/plugin.xml`
- `src/main/kotlin/com/jetbrainsplagins/ridercopy/CopySelectionWithPathAndLinesAction.kt`

## RequiredSkills
- `context7-first` — чтобы опереться на актуальную официальную документацию JetBrains Platform по action icons и SVG requirements.
- `intellij-platform-build-compat` — чтобы рекомендация не противоречила реальному plugin layout и способу подключения icon asset в текущем toolchain.

## Inputs
- Текущий action называется `Copy Selection with Path and Lines` и показывается в контекстном меню редактора.
- Owner допускает цветную иконку, но она должна хорошо читаться на сером фоне menu UI.
- По смыслу команда копирует структурированный кодовый контекст: путь, диапазон строк и, при необходимости, diff/context payload.
- В текущем чате есть screenshot контекстного меню с расположением команды; его можно использовать как UX-контекст.

## Steps
1. Прочитать обязательные файлы и понять точный UX-контекст action.
2. Через официальную JetBrains Platform документацию уточнить ограничения и best practices для action icons и SVG assets.
3. Сформулировать краткие критерии хорошей иконки именно для этого action на сером фоне меню.
4. Предложить 2-3 конкретные концепции иконки с плюсами и рисками каждой.
5. Выбрать одну финальную рекомендацию и объяснить, почему она лучше остальных.
6. Подготовить для следующего `CODER` точный implementation handoff: suggested asset path, file naming, format notes, `plugin.xml`/code touchpoints и ограничения по цвету/контрасту.

## Checks
- Official-doc pass по JetBrains Platform icon/action docs -> подтверждает, что рекомендация опирается на актуальные platform requirements.
- Manual check against `plugin.xml` and current action semantics -> подтверждает, что образ иконки соответствует назначению команды, а не абстрактной функции.
- Если понадобятся длинные выписки или заметки, складывать их в `.agents-runtime/RUNLOGS/T0026/`.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/ANALYST/OUT/T0026_20260419-0124_ANALYST_specify-icon-for-copy-selection-action_report.md`
- дополнительные артефакты: only if genuinely needed

## StateImpact
- `LEAD` должен прочитать resulting `ANALYST OUT` и решить, нужен ли следующий `CODER`-тикет на реализацию иконки.

## Acceptance
- [ ] Есть краткое описание технически корректного способа подключить icon asset к текущему action.
- [ ] Есть 2-3 конкретные концепции иконки, а не абстрактные направления.
- [ ] Есть одна финальная рекомендуемая концепция с аргументацией.
- [ ] Есть чёткий handoff для следующего `CODER` с путями, форматом и точками подключения.
- [ ] Рекомендация учитывает читаемость на сером фоне контекстного меню.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: high
- comment: нужен аккуратный аналитический проход на стыке UX-смысла action и реальных требований JetBrains Platform к иконкам
