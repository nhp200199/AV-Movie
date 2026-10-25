---
name: commit
description: Commit work following AV-Movie conventions. Use whenever the user asks to commit, save, or checkpoint work - even if they don't mention conventions.
---

# AV-Movie Commit Workflow

## Step 1 - Review what changed

Run `git status` and `git diff --stat`, then `git diff` on relevant files. Understand what the changes actually do before writing anything.

## Step 2 - Stage deliberately

- Stage files explicitly by path (`git add <file>`). Never run `git add -A` or `git add .` blindly from the repo root, and never use `git add -f`.
- If the working tree contains more than one unrelated logical change (e.g. a feature + an unrelated architecture refactor), propose splitting them into separate commits.
- **Never stage:**
  - Secret/local config files (`local.properties`, `*.jks`, `*.keystore`)
  - Build outputs (`build/`, `.gradle/`)
  - Unrequested dependency changes in `gradle/libs.versions.toml` or `build.gradle` (dependency updates must be their own intentional commit).

## Step 3 - Verify the staged diff

Run `git diff --cached`. If nothing is staged, or only whitespace/trivial changes are staged, stop and confirm with the user before proceeding.

## Step 4 - Write the commit message

### Core Rules
1. **Golden Rule (Staged-only):** The commit message must describe **ONLY** what is currently in `git diff --cached`. Ignore earlier commits in the session and ignore unstaged modifications.
2. **Primary Driver Rule:** Identify the single primary reason for the change to pick one `[type]` and write the subject. Small supporting edits (imports, minor cleanup) are implicit—do not list all of them in the subject.
3. **Concise & Direct:** Default to a **subject-only** message. Only add bullet points in the body when you need to explain a non-obvious *why* (rationale, architectural trade-off), not *what* changed.

### Format

```text
[type] summary

- optional bullet explaining why (omit for straightforward changes)
```

- **`[type]`** (required):
  | Type | When to use |
  |---|---|
  | `[feat]` | New feature, screen, or capability |
  | `[fix]` | Bug fix |
  | `[ui]` | Compose UI / layout / styling adjustments with no logic change |
  | `[refactor]` | Code restructuring (Clean Architecture / MVVM adjustments) without behavior change |
  | `[perf]` | Performance optimization |
  | `[build]` | Gradle scripts, `libs.versions.toml`, ProGuard, SDK configuration |
  | `[chore]` | Housekeeping, rules (`AGENTS.md`), skill configs, formatting, cleanup |
  | `[test]` | Adding or updating unit/instrumented tests |
  | `[docs]` | Documentation only (`README.md`, code comments) |
  | `[revert]` | Revert a previous commit |

- **Length & Hygiene Caps:**
  - Subject line (`[type] summary`): **<= 72 characters**, no trailing period.
  - Body (if any): Exactly one blank line after subject, maximum **3 bullets** (`- ...`), each **<= 100 characters**, no trailing period.
  - Language: English (preferred) or Vietnamese (matching the user's request or context).

### Examples
- `[feat] add movie cast horizontal list on detail screen`
- `[refactor] create 'core' layer`
- `[refactor] Chuyển project về single module`
- `[fix] prevent duplicate paging requests on configuration change`
- `[chore] add commit skill workflow`

## Step 5 - Present and Commit

Before running the commit command, always display the summary in chat using this exact layout:

1. **STAGED FILES** table (fresh from `git diff --cached --name-status`, with status spelled out: `M` -> `modified`, `A` -> `new file`, `D` -> `deleted`, `R` -> `renamed`):

   **STAGED FILES**

   | Status | File |
   |:---:|:---|
   | modified | app/src/main/java/com/av/movie/... |
   | new file | app/src/main/java/com/av/movie/... |

2. **PROPOSED COMMIT MESSAGE** as a blockquote (`>`) with the subject line bolded:

   **PROPOSED COMMIT MESSAGE**

   > **[type] summary**
   >
   > - optional why bullet

   Directly under the blockquote, state: `✅ Commit message follows the commit convention`

3. Execute the commit using a clean heredoc (or single-line `-m` when there is no body):

```bash
git commit --cleanup=strip -m "$(cat <<'EOF'
[type] summary
EOF
)"
```

*(Note: If `git commit` requires confirmation via tool approval dialog, run it in the same turn so the dialog serves as the single approval step. If running in auto-approve/bypass mode where `git commit` is not gated, present the table and message first, then wait for user approval before running `git commit`.)*

## Step 6 - After the commit

Confirm the commit succeeded (and any pre-commit hook passed). If a hook failed, fix the cause and return to Step 3—never bypass with `--no-verify`.

## Forbidden - No Exceptions

- **No AI signatures:** Never include `Co-Authored-By`, `Generated with...`, or any AI attribution lines.
- **No `--no-verify`:** Never bypass git hooks.
- **No accidental dependency ride-alongs:** Never include unrelated `gradle/libs.versions.toml` changes in a feature/refactor commit.
