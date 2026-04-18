# TASK — formal ticket

Role: CODER
TicketId: T0022
Title: define-staging-rules-for-new-project-and-process-files
CreatedAt: 2026-04-18 23:21
OwnerRequest: Owner wants two narrow staging rules added to the live rule-layer:
one common rule for new key process files, and role-local rules for new project files created by CODER and TESTER.
Workspace: `.`

## Scope
- Update `.agents/rules/00_COMMON.md` with a common rule for newly created key process files that should become part of the live process-layer.
- Update `.agents/roles/CODER.md` with a role-local rule for newly created project files within approved implementation scope.
- Update `.agents/roles/TESTER.md` with a role-local rule for newly created project files within approved testing scope.
- Keep the new rules narrow and explicit about exclusions for junk, logs, and transient artifacts.

## OutOfScope
- Do not change `AGENTS.md`.
- Do not change `.agents/rules/specs/CONTROL_FLOW.md` unless a direct contradiction appears and a minimal wording fix is strictly necessary.
- Do not rewrite historical `MAIL/**`, `RUNLOGS/**`, export/view materials, or old reports.
- Do not update `.agents-runtime/00_STATE.md`.
- Do not create additional tickets or plans.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/CODER.md`
- `.agents/roles/TESTER.md`
- `.agents-runtime/MAIL/CODER/OUT/T0021_20260418-2256_CODER_define-start-process-policy-for-key-process-files_report.md`

## RequiredSkills
- `process-consistency-lint` — чтобы не внести новый конфликт между common/spec/role layer.
- `process-artifact-workflow` — чтобы корректно оформить собственный `OUT` и не расползтись в process-мусор.

## Inputs
- Current Owner decision in chat:
  - новые project files по смыслу обычно создают `CODER` и `TESTER`, значит это правило должно жить в их role-files;
  - новые key process files вроде formal tickets/reports требуют общего правила для всех, значит это должно жить в общем rule-layer;
  - мусор, runlogs, `.txt` и прочие вспомогательные артефакты не должны попадать под это требование по умолчанию.
- LEAD decision for this ticket:
  - common rule goes into `.agents/rules/00_COMMON.md`;
  - project-file rules go into `.agents/roles/CODER.md` and `.agents/roles/TESTER.md`.

## Steps
1. Re-read the required live rule/spec/role files and the latest `T0021` CODER `OUT`.
2. Update `.agents/rules/00_COMMON.md` with a concise common rule:
   - if an agent creates a new key process file that should become part of the live process-layer, that file must be added to the repository;
   - by default this applies to formal `MAIL/<ROLE>/IN/*.md` and formal `MAIL/<ROLE>/OUT/*_report.md`;
   - by default this does not apply to `RUNLOGS/**`, `.txt`, evidence/file packs, and other auxiliary or intermediate artifacts unless Owner explicitly requests otherwise.
3. Update `.agents/roles/CODER.md` with a concise role rule:
   - if `CODER` creates a new project file within approved scope, that file must be added to the repository;
   - do not add junk, temporary files, caches, build outputs, `RUNLOGS/**`, or other auxiliary artifacts unless Owner explicitly requests otherwise.
4. Update `.agents/roles/TESTER.md` with the analogous role rule for new project files created by `TESTER`.
5. Keep the wording short, even, and aligned with the current Russian-heavy style of the live rule-layer.
6. Run a targeted consistency pass to ensure the new common rule and the role-local rules do not conflict with `CONTROL_FLOW.md` or with each other.
7. Prepare your `OUT` report with exact file references, checks, and any narrowly scoped follow-up if one remains.

## Checks
- `rg -n "добав.*репозитор|add.*repository|git index|git add|нов.*project|process-file|process file" .agents -S` -> confirms the new rule wording exists where intended.
- Manual consistency check across `.agents/rules/00_COMMON.md`, `.agents/roles/CODER.md`, and `.agents/roles/TESTER.md` -> confirms no fresh contradiction with `.agents/rules/specs/CONTROL_FLOW.md`.
- If diagnostics are needed, store them in `.agents-runtime/RUNLOGS/T0022/`.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/CODER/OUT/T0022_20260418-2321_CODER_define-staging-rules-for-new-project-and-process-files_report.md`
- дополнительные артефакты: only if genuinely needed for diagnostics

## StateImpact
- LEAD should review the resulting `CODER OUT`, then move `T0022` out of `Active` in `.agents-runtime/00_STATE.md`.

## Acceptance
- [ ] `.agents/rules/00_COMMON.md` contains a clear common rule for newly created key process files that should be added to the repository.
- [ ] `.agents/roles/CODER.md` contains a clear rule for newly created project files within CODER scope.
- [ ] `.agents/roles/TESTER.md` contains a clear rule for newly created project files within TESTER scope.
- [ ] The exclusions for junk, temporary files, runlogs, `.txt`, and other auxiliary artifacts are kept explicit.
- [ ] No unrelated source-of-truth files or historical artifacts are rewritten.
- [ ] `CODER OUT` records the exact wording changes and the checks run.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: high
- comment: нужен аккуратный rule/role edit без расползания в history и без новых process-конфликтов
