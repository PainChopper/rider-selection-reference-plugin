# PROCESS_CONSISTENCY_LINT

## Назначение

Повторяемый checklist для проверки консистентности process-слоя.

## Проверки

1. Существуют ли все пути, упомянутые в активном тикете и отчёте.
2. Корректны ли ссылки между `IN`/`OUT` и `00_STATE.md`.
3. Нет ли bootstrap-cycle между `AGENTS.md` и `rules/specs`.
4. Не попали ли helper-правила выше `rules/specs`.
5. Не затронута ли historical `MAIL/**` масса правок вне scope.

## Результат

- Список PASS/FAIL по каждому пункту.
- Явная фиксация недостающих артефактов и follow-up.

## Ограничение

Этот skill не является source of truth и не переопределяет `rules/specs`.
