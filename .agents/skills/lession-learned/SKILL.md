---
name: lession-learned
description: Extract and record key issues and concepts learned while implementing an AV-Movie plan into .agents/artifact/lession-learned/<feature-name>/. Use after implementing or testing a planned feature, or when the user asks to summarize issues, lessons learned, or 'lession learned' / 'lesson learned' / 'tổng kết issue'.
---

# AV-Movie Lession Learned

## Approval first

Never add an issue to the lession-learned file on your own. Always filter candidate issues, present them to the user with why each qualifies, and write only after the user explicitly approves which issues to include.

## 1. Gather context

- Identify `<feature-name>` from the user request or the corresponding plan at `.agents/artifact/planning/<feature-name>.md`.
- Read `.agents/artifact/planning/<feature-name>.md` to get the exact requirement IDs (`R1..Rn`).
- Review the chat session(s) related to that feature (the current conversation and relevant session transcripts if needed).
- Check if `.agents/artifact/lession-learned/<feature-name>/<feature-name>.md` already exists so approved issues are appended cleanly without duplicating existing entries.

## 2. Filter candidate issues

Only select issues that match at least one of these criteria:

1. **Conceptually difficult**: concepts that caused the user to ask repeatedly or clarify multiple times.
2. **Uncovered testing bugs**: bugs discovered while testing the feature that were not covered in the plan.
3. **User-specified**: issues the user explicitly asks to include.

Skip routine typos, trivial fixes, or issues smoothly covered by the plan unless the user asks to include them.

## 3. Propose to user

Present the filtered candidates in chat before writing any file:

- Mapped requirement (`R1..Rn`) from the plan (every issue must belong to a requirement; if unclear, ask the user which `R` it maps to before proceeding).
- Which filter criterion it matches.
- Brief problem, implemented solution, and proposed concepts (`Keyword` + `Description`).

Wait for the user's explicit confirmation on which issues to include, adjust, or drop.

## 4. Write artifact

Save approved issues to `.agents/artifact/lession-learned/<feature-name>/<feature-name>.md`.
Concept detail files referenced later belong under `.agents/artifact/lession-learned/<feature-name>/refs/`.

Use this structure for each issue:

```markdown
### Issue <N>: <Short Title>

- **Requirement:** `Rx` — <requirement summary from plan>
- **Problem:** <concise summary of the problem>
- **Solution:** <summary of the implemented solution>

| Keyword | Description | Detail |
|---|---|---|
| `<Concept>` | <brief overview of the concept> | |
```

Rules for the concepts table and issues:

- **Requirement is mandatory**: never record an issue that does not map to a requirement (`Rx`) in the plan.
- **Detail column stays empty initially** so the user can study the concept independently.
- Only populate `Detail` with a reference link (e.g., `[<concept>.md](./refs/<concept>.md)`) **after** the user has researched it and created/provided the detail file in `.agents/artifact/lession-learned/<feature-name>/refs/`.