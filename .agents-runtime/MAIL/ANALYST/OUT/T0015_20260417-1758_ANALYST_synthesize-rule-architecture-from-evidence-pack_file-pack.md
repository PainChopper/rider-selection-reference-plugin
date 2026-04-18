# ANALYST FILE PACK

Role: ANALYST
TicketId: T0015
SourceTicket: `.agents-runtime/MAIL/ANALYST/IN/T0015_20260417-1758_ANALYST_synthesize-rule-architecture-from-evidence-pack.md`
CreatedAt: 2026-04-17
Workspace: `D:\.DEV\JetBrainsPlagins`
Purpose: upload-ready architecture brief for ChatGPT 5.4 Pro

## Final architecture

Recommended system model:
- `AGENTS.md` is the only bootstrap entrypoint.
- Source of truth is limited to active runtime/task, `@ROLE`, `specs/*` and `00_COMMON.md`.
- `TEMPLATES/*` and `skills/*` are helper layers only.
- Historical `MAIL/**` is evidence only.

Final architecture scheme:
1. `AGENTS.md` routes bootstrap.
2. `00_COMMON.md` fixes common invariants.
3. `specs/*` fixes process and boundary rules.
4. `@ROLE` fixes role duties.
5. Current user request and active ticket fix task-local contract.
6. Referenced artifacts provide only targeted context.
7. `TEMPLATES/*` may help with file shape, `skills/*` may help with repeatable execution, and historical `MAIL/**` remains evidence only.

## Final bootstrap-flow

1. Read `AGENTS.md`.
2. Read `.agents/rules/00_COMMON.md`.
3. Read `.agents/rules/specs/CONTROL_FLOW.md`.
5. If present, read the explicit `@ROLE` file.
6. Read the current user request and active `MAIL/**` ticket.
7. Read only explicitly referenced artifacts or files needed to remove a real ambiguity.
8. Use `TEMPLATES/*` only as scaffolding for new process-files.
9. Use `skills/*` only as opt-in recipes.
10. Treat historical `MAIL/**` only as evidence.

Non-negotiable rule:
- no reverse jump from `00_COMMON.md` back to `AGENTS.md`;
- no automatic bootstrap through templates, skills or history.

## Final layer boundary

`rules`
- bootstrap
- layer order
- repo boundary
- process-contract
- conflict resolution
- ownership
- role duties

`skills`
- reusable execution recipes
- lint/check workflows
- platform-specific operating playbooks

`templates`
- shape scaffolds for `MAIL/**`
- no independent authority over process rules

`history`
- evidence for audits and synthesis
- no mass retro-rewrite

## Final decisions

- Canonical workspace for current rules/process artifacts: `D:\.DEV\JetBrainsPlagins`.
- `AGENTS.md` stays a router, not a duplicate rulebook.
- `TEMPLATES/*` must be aligned to actual process practice, but they remain subordinate to rules/specs and the active ticket.
- Conflicting runtime/process norms must be solved once in rules/specs or the active task contract, not hidden inside templates.
- Live user clarifications in the thread are part of the runtime contract for the current task.

## Factual cleanup targets

1. Remove the bootstrap cycle between `AGENTS.md` and `00_COMMON.md`.
2. Normalize workspace-path usage in rules/specs/templates.
3. Record one canonical layer stack in repo rules.
4. Separate source-of-truth layers from helper layers.
5. Align `TASK.md`, `REPORT.md`, `VERIFY.md` with current `MAIL` practice.
6. Remove process-rule drift from `LEAD_PROMPT.md` and keep file-opening/runtime actions centralized outside templates.
7. Introduce a physical `.agents/skills` layer.
8. Clarify ownership/update-flow for `.agents-runtime/00_STATE.md`.

## Change-plan for next CODER

1. Fix bootstrap and priority wording in `AGENTS.md`, `00_COMMON.md`, and the relevant specs.
2. Normalize the canonical workspace path to `D:\.DEV\JetBrainsPlagins`.
3. Update `TEMPLATES/INDEX.md`, `TASK.md`, `REPORT.md`, `VERIFY.md`, `LEAD_PROMPT.md` so templates match current practice but remain non-authoritative.
4. Create the physical `.agents/skills` layer and move only reusable recipes there.
5. Document ownership for `00_STATE.md` and process-artifact staging.
6. Run a targeted consistency check; do not rewrite historical `MAIL/**`.

## Upload pack

Upload exactly these 4 files to ChatGPT 5.4 Pro:

1. `.agents-runtime/MAIL/ANALYST/OUT/T0015_20260417-1758_ANALYST_synthesize-rule-architecture-from-evidence-pack_file-pack.md`
2. `.agents-runtime/MAIL/ANALYST/OUT/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction_report.md`
3. `.agents-runtime/MAIL/ANALYST/OUT/T0014_20260417-0220_ANALYST_audit-agent-rules-and-design-skill-extraction_evidence-pack.md`
4. `.agents-runtime/MAIL/VERIFIER/OUT/T0016_20260417-1803_VERIFIER_validate-templates-against-rules-and-process_report.md`

Why this pack works:
- file 1 gives the final recommendation;
- files 2 and 3 provide compact evidence;
- file 4 independently confirms template-side conflicts and limitations.
