---
name: planning
description: Plan an AV-Movie change before writing code - find what exists, recommend one approach with files, steps and checks, get one approval. Use before any non-trivial feature, screen, refactor, flow change or new tooling, and when asked how to build something in AV-Movie. Vietnamese requests count, e.g. 'triển khai', 'thêm tính năng', 'sửa luồng', 'làm theo spec', 'refactor', 'làm sao để'. Not for obvious few-line fixes, pure code reviews, or simple bug debugging.
---

# AV-Movie Change Planning

## Approval first

No implementation code, new source/test files, or dependencies until the user approves the approach. Reading, searching, and saving the plan artifact under `.agents/artifact/planning/` are fine (ask before overwriting an existing plan file). Skip this skill for a trivial fix or when the user said to proceed without a plan.

## 1. Understand

- Ticket / spec / design: if the request references a ticket, spec, or Figma link, read or inspect it with available tools. If none is provided, work from the request and state what was assumed.
- Read `AGENTS.md` (especially Clean Architecture layer boundaries `domain` / `data` / `core` / `presentation` / `di`, MVVM/UDF, `ResultData`, Compose rules, and guardrails).
- List requirements `R1..Rn` with their source. Answer what the code or spec can answer; ask the user only the rest, together, each with a recommended default.

## 2. Find what exists

Search before designing; reuse or fix what exists instead of adding a parallel one.

- A symbol: `sh .agents/skills/planning/scripts/symbol.sh X` (declarations, subtypes, DI wiring, XML references, users).
- A similar feature or existing components: `find app/src/main -iname '*<Keyword>*'`; inspect `domain/`, `data/`, `core/`, `di/`, and `presentation/components/` before introducing new abstractions.
- Blast radius: trace callers and usages across `:app` layers and existing unit tests (`app/src/test/`).
- How the flow behaves on `develop/phucnguyen/main` today: `git show develop/phucnguyen/main:<path>`.

## 3. Propose

One proposal in chat and saved as a plan file, proportional to the change:

- Goal, approach, and the files to create, modify, and test.
- Behavior vs `develop/phucnguyen/main`: what is added, changed, removed, and what must stay — each tied to an `R`.
- Steps, each with a check. Verification per requirement: a unit test (`app/src/test/...`) that fails first (no UI / instrumented test required).
- Commits planned as the `commit` skill (`.agents/skills/commit/SKILL.md`) will record them.
- Final checks: `./gradlew testDebugUnitTest` (unit tests pass), build/compile check (`./gradlew compileDebugKotlin` or `./gradlew assembleDebug`), and compliance with `AGENTS.md` (layer boundaries, no unrequested dependency edits in `gradle/libs.versions.toml` or `build.gradle`).

### Save plan artifact & Version Changes

- **Destination**: `.agents/artifact/planning/<feature-name>.md` (inside the project root).
- **Feature name**: Use the feature name explicitly specified by the user; otherwise derive a concise kebab-case `<feature-name>` from the planning request.
- **Duplicate check**: Before writing, check if `.agents/artifact/planning/<feature-name>.md` already exists.
  - If it exists, notify the user and ask them to choose either a **new feature name** or **override** the existing plan file before writing.
- **Version Changes**: Every plan file must include a `## Version Changes` section tracking each plan proposal (`v1`, `v2`, ...) with the date and a brief note of what changed compared to the previous version:
  - New file: start at `v1` (initial proposal).
  - Override: read the existing plan first, keep all prior entries in `## Version Changes`, and append the next version (`v2`, `v3`, ...) summarizing concisely what changed vs the most recent version.

## 4. Execute

After one approval, implement without asking again. Ask again only when new information changes the scope. Commit changes using the `commit` skill (`.agents/skills/commit/SKILL.md`).