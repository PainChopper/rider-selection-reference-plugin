# AGENTS.md

Этот файл является bootstrap-entrypoint для агентной системы репозитория.

## Обязательное чтение

Сначала прочитать:

- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/OPERATING_MODEL.md`
- `.agents/rules/specs/CONTROL_FLOW.md`

## Ролевой роутинг

Если Owner явно задаёт ролевой префикс в начале prompt, дополнительно прочитать соответствующий role-file:

- `@LEAD` -> `.agents/roles/LEAD.md`
- `@ANALYST` -> `.agents/roles/ANALYST.md`
- `@CODER` -> `.agents/roles/CODER.md`
- `@TESTER` -> `.agents/roles/TESTER.md`
- `@VERIFIER` -> `.agents/roles/VERIFIER.md`
- `@REVIEWER` -> `.agents/roles/REVIEWER.md`

Если в одном запросе явно заданы несколько ролей и приоритет неочевиден, запросить уточнение у Owner.
Если ролевой префикс не задан, не входить в роль по догадке.

## Рабочий набор

После обязательного чтения:

1. Прочитать текущий запрос Owner.
2. Если текущий шаг формализован тикетом, прочитать активный `MAIL/**`-тикет.
3. Если текущая роль или тикет зависят от предыдущего шага, прочитать последний релевантный `OUT`.
4. Дочитывать только те артефакты, на которые есть явная ссылка или без которых остаётся реальная неоднозначность.

## Границы слоёв

- `.agents/rules/**` и `.agents/roles/**` образуют нормативный repo-layer.
- `.agents/templates/**` и `.agents/skills/**` являются helper/scaffolding layer.
- `.agents-runtime/**` является mutable process/runtime layer.
- Исторические и advisory-материалы не являются bootstrap-слоем.

Этот файл выполняет только функции bootstrap/router и не заменяет собой rules/specs/role-files.
