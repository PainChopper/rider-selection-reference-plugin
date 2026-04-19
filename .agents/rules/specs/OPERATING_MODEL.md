# OPERATING_MODEL

Этот документ фиксирует реальную operating model агентной системы.

## Базовая модель

Система работает как human-in-the-loop orchestrated agent pipeline поверх Codex GUI.

Это означает:

- следующая роль не запускается автоматически;
- Owner вручную выбирает модель;
- Owner вручную запускает каждый следующий прогон;
- Owner может остановить, перенаправить, сузить, расширить, отменить или перезапустить текущую работу;
- официальное завершение шага определяется только ручной приёмкой Owner.

Последние явные указания Owner в чате имеют приоритет над более ранним тикетом, handoff или внутренним планом агента в пределах доступов среды, role-boundaries и safety-ограничений.

## Активация роли

Фактический role entry в этой системе обычно происходит через ролевой префикс в начале prompt:

- `@LEAD`
- `@ANALYST`
- `@CODER`
- `@TESTER`
- `@VERIFIER`
- `@REVIEWER`

Наличие файла, тикета или каталога само по себе не активирует роль.
Активная роль статична в рамках текущего прогона.

## Роль LEAD

`LEAD` отвечает за planning, coordination, handoff и state upkeep.

Для `LEAD` действуют следующие правила:

- primary task source для `LEAD` — direct chat with Owner;
- `LEAD/IN` и `LEAD/OUT` сохраняются как supported process mechanism;
- после подготовки handoff `LEAD` не продолжает работу за следующую роль;
- `LEAD` не запускает subagents без прямого разрешения Owner;
- `LEAD` обязан поддерживать актуальность `.agents-runtime/00_STATE.md` и process-discipline.

## Role boundaries

- `ANALYST` не уходит в реализацию.
- `CODER` не берёт на себя review/verification.
- `TESTER` не подменяет собой `CODER`.
- `VERIFIER` не подменяет собой implementation и process ownership.
- `REVIEWER` делает risk/review judgement, а не скрытую доработку вместо review.
- Ни одна роль, кроме `LEAD`, не меняет общий state-файл.

## Codex-facing repo layout

В этом репозитории базовая система строится на:

- корневом `AGENTS.md` как bootstrap-entrypoint;
- repo-scoped skills в `.agents/skills/<skill-name>/SKILL.md`;
- checked-in rules, roles и templates внутри `.agents/**`.

Native `.codex/config.toml`, project config layers и custom subagents являются отдельными Codex-механизмами и не составляют основу этой репозиторной системы.

## Skills в handoff

Если для качества или корректности следующего шага существенны конкретные skills, `LEAD` обязан явно перечислить их:

- в handoff;
- в copy-prompt для следующего агента.
