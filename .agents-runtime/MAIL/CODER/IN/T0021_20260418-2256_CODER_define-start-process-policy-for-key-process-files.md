# TASK — formal ticket

Role: CODER
TicketId: T0021
Title: define-start-process-policy-for-key-process-files
CreatedAt: 2026-04-18 22:56
OwnerRequest: Owner wants key process markdown files to open via `Start-Process` instead of
dumping long texts into chat, and wants the phrase "open file" to have an explicit default meaning.
Workspace: `.`

## Scope
- Update `.agents/rules/specs/CONTROL_FLOW.md` so that key process markdown artifacts must be opened via `Start-Process` when they are created or modified.
- Update `.agents/rules/00_COMMON.md` so that phrases like "open file", "open report", and "open ticket" have an explicit default operational meaning.
- Keep the rule narrowly scoped to key process artifacts only.
- Align the wording with current Owner policy that full reports should not be dumped into chat by default.

## OutOfScope
- Do not rewrite historical `MAIL/**`, `RUNLOGS/**`, master packets, or export/view materials.
- Do not modify `.agents/templates/**` unless a direct wording adjustment is strictly necessary to avoid a fresh contradiction.
- Do not create a new plan or new tickets.
- Do not execute verification, review, or LEAD state upkeep beyond your own `OUT`.

## RequiredReads
- `AGENTS.md`
- `.agents/rules/00_COMMON.md`
- `.agents/rules/specs/CONTROL_FLOW.md`
- `.agents/roles/CODER.md`
- `.agents-runtime/MAIL/ANALYST/OUT/T0015_20260417-1758_ANALYST_synthesize-rule-architecture-from-evidence-pack_report.md`
- `.agents-runtime/MAIL/VERIFIER/OUT/T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md`
- `.agents-runtime/MAIL/CODER/OUT/T0018_20260417-1856_CODER_normalize-rule-layer-and-template-contracts_report.md`
- `.agents-runtime/MAIL/VERIFIER/OUT/T0020_20260418-0915_VERIFIER_validate-current-rule-layer-and-pro-pack-readiness_report.md`

## RequiredSkills
- `process-consistency-lint` — чтобы не внести новый конфликт между common/spec/helper layer.
- `process-artifact-workflow` — чтобы корректно оформить process-артефакты и ссылки в `OUT`.

## Inputs
- Current Owner decision in chat:
  - key reports and tickets should open via `Start-Process`;
  - repeated edits of the same key file should also trigger `Start-Process` again;
  - logs, `.txt`, runlogs, and other intermediate artifacts must not be opened this way;
  - semantic meaning of "open file" should be explicit in the rule layer.
- LEAD recommendation:
  - semantic meaning lives in `.agents/rules/00_COMMON.md`;
  - mandatory process behavior lives in `.agents/rules/specs/CONTROL_FLOW.md`.

## Steps
1. Re-read the required rule/spec files and the listed prior reports relevant to the old `Start-Process` conflict family.
2. Update `.agents/rules/00_COMMON.md` with a concise operational-definition section for phrases such as "open file", "open report", and "open ticket".
3. Update `.agents/rules/specs/CONTROL_FLOW.md` with a concise rule that requires `Start-Process` for key process markdown artifacts on every creation or modification.
4. Ensure the scope is limited to:
   - formal `MAIL/<ROLE>/IN/*.md` tickets;
   - formal `MAIL/<ROLE>/OUT/*_report.md` reports.
5. Ensure the rule explicitly excludes:
   - `RUNLOGS/**`;
   - `.txt` and other helper logs;
   - evidence-pack/file-pack and similar intermediate materials;
   - `.agents-runtime/00_STATE.md`;
   - `.agents-runtime/PLANS/**`, unless separately requested by Owner.
6. Sanity-check that the updated wording does not reintroduce helper-layer competition or bootstrap drift.
7. Prepare your `OUT` report with exact file references and a short note on whether any template wording should be adjusted later.

## Checks
- `rg -n "Start-Process|open file|open report|open ticket" AGENTS.md .agents -S` -> confirms the new rule wording exists in the intended source-of-truth files.
- Manual consistency check between `.agents/rules/00_COMMON.md` and `.agents/rules/specs/CONTROL_FLOW.md` -> confirms no fresh contradiction was introduced.
- If extra diagnostic output is needed, store it in `.agents-runtime/RUNLOGS/T0021/`.

## ArtifactsOut
- основной `OUT`: `.agents-runtime/MAIL/CODER/OUT/T0021_20260418-2256_CODER_define-start-process-policy-for-key-process-files_report.md`
- дополнительные артефакты: only if genuinely needed for diagnostics

## StateImpact
- LEAD should move `T0021` from `Active` after reviewing the resulting `CODER OUT`.

## Acceptance
- [ ] `.agents/rules/00_COMMON.md` explicitly defines what "open file" means by default.
- [ ] `.agents/rules/specs/CONTROL_FLOW.md` explicitly requires `Start-Process` for key process markdown tickets/reports on every change.
- [ ] The rule explicitly excludes logs, runlogs, `.txt`, evidence/file packs, `00_STATE.md`, and plans by default.
- [ ] No second active ticket is created and no historical layer is rewritten as if it were source of truth.
- [ ] `CODER OUT` clearly records the exact wording changes and any remaining follow-up.

## FailurePolicy
- При недостижении цели честно завершить шаг статусом `FAILED`.
- Если текущий прогон упёрся во внешний blocker, завершить `OUT` статусом `BLOCKED` и явно описать blocker и что нужно для продолжения.
- Не расширять scope, чтобы искусственно превратить `FAILED` или `BLOCKED` в `DONE`.

## Recommended model / reasoning effort
- model: GPT-5.4
- reasoning effort: medium
- comment: нужен аккуратный rule/process edit без расползания в history и без возврата старых конфликтов
