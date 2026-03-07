Coding can happen in a 30-minute senior frontend screen, but it’s usually **light**:

* **Most common:** discussion + deep dives (architecture, tradeoffs, performance, debugging) + maybe a tiny snippet review.
* **Sometimes:** a **small practical task** (implement a hook/service, transform data, fix a bug, write a reducer).
* **Less likely:** full algorithm/LeetCode style—unless the company is known for it or the interviewer is explicitly “coding interview”.

If you prep for “**talk through + write 15–25 lines cleanly**”, you’re covered.

Below are **4 ready-to-use Markdown docs** you can copy into separate files.

---

## 1) `01-angular-senior-interview.md`

```md
# Angular Senior Frontend Interview (30 min) — Prep Doc

## 0) 30-min plan (what to do right before)
- 3 min: "Tell me about yourself" tailored to Angular impact (migrations, perf, state, DX).
- 15 min: Deep dives (architecture, perf, RxJS, forms, testing).
- 10 min: Scenario question (bug/perf/design).
- 2 min: Questions for them (team, standards, deployment, ownership).

## 1) Senior talking points (what interviewers want)
- You can **design maintainable UI architecture** (modules/standalone, boundaries, libraries).
- You understand **change detection + perf** in production.
- You can **reason about RxJS** and avoid leaks/over-subscription.
- You handle **forms**, validation, and complex UX with correctness.
- You know **testing strategy** (unit/integration/e2e) and CI reliability.

## 2) Angular architecture & patterns (expected questions)
### Standalone vs NgModules
- Standalone components simplify composition and reduce module boilerplate.
- Still use feature boundaries: `core/`, `shared/`, `features/`, `ui/`.
- Watch for dependency cycles and shared imports that become "god-shared".

**Answer shape:** "We moved to standalone for better locality and tree-shaking, kept feature boundaries, and avoided over-sharing by creating narrow UI libs."

### State management choices
- Component state for local.
- Service + RxJS for shared within feature.
- Store (NgRx/Akita/Signals store) for cross-feature + complex flows.

**Tradeoff framing:** complexity vs predictability; devtools/time travel; testability; team consistency.

## 3) Change detection & performance (high-frequency)
### OnPush
- Use `ChangeDetectionStrategy.OnPush` when inputs are immutable-ish and events drive updates.
- Common pitfalls: mutating arrays/objects; async updates outside Angular zone.

### TrackBy
- Always for large lists.
- Track stable identity (`id`) not index.

### AsyncPipe
- Prefer `async` in templates over manual subscribe.
- Reduces leaks, integrates with CD.

### Zones / NgZone
- Move noisy work outside Angular zone; re-enter for UI update.

### Profiling
- Use Angular DevTools, performance tab, flame charts.
- Identify rerenders, expensive templates/pipes, unnecessary subscriptions.

## 4) RxJS deep dive (what they’ll probe)
### Patterns you should be fluent in
- `switchMap` for latest-only (typeahead, route changes)
- `mergeMap` for concurrency
- `concatMap` for order-preserving sequences
- `exhaustMap` for ignore-new-while-busy (login, save button spam)

### Subscription management
- Prefer `async` pipe.
- Otherwise: `takeUntilDestroyed(inject(DestroyRef))` (Angular 16+) or a `destroy$` subject pattern.

### Sharing & caching
- `shareReplay({ bufferSize: 1, refCount: true })` for memoizing HTTP streams
- Avoid accidental refCount pitfalls; ensure correct lifecycle.

### Error handling
- Handle errors inside streams (`catchError`) and return safe fallback.
- Don’t let one failing stream kill the whole UI unless intentional.

## 5) Forms (they love forms)
### Reactive forms
- Predictable, testable, dynamic forms, async validation, nested groups/arrays.
- Explain approach to:
  - cross-field validation
  - async validators (debounced server checks)
  - form arrays (repeatable sections)

### ControlValueAccessor
- For reusable custom inputs (date pickers, masked inputs).
- Key: propagate changes, touched state, disabled state.

## 6) Routing
- Lazy loading for features.
- Resolvers vs component fetch:
  - Resolver: ensures data before route activation; good for "must-have" data.
  - Component fetch: better for streaming / partial UI / skeleton loading.

- Guards: auth, feature flags.
- Prefer route params as observables; avoid snapshot for dynamic changes.

## 7) Testing strategy (senior lens)
- Unit: components (shallow), services, pure functions.
- Integration: component + template + HTTP mocking.
- E2E: critical journeys only; avoid flaky overcoverage.
- What you say: "I bias towards stable unit/integration tests and a thin e2e layer."

## 8) SSR / Hydration / Performance (if relevant)
- Angular Universal / SSR improves TTFB and SEO.
- Hydration: ensure compatibility, avoid DOM mismatch, careful with browser-only APIs.

## 9) Common “debug me” scenarios (quick answers)
- Memory leak: manual subscriptions not cleaned up; event listeners not removed.
- Rerender storm: default CD + expensive template expressions; fix with OnPush + memoization.
- HTTP called multiple times: cold observable re-subscribed; use shareReplay or move to service cache.
- Cyclic deps: shared module importing feature module; fix boundaries.

## 10) Mini coding prompts they might ask (practice)
- Implement `trackById(index, item) => item.id`.
- Write a debounced search with RxJS (`debounceTime`, `distinctUntilChanged`, `switchMap`).
- Create a reusable form control using CVA.
- Convert imperative subscribe code into template `async` pattern.

## 11) Great questions to ask them (2–3)
- "How do you enforce architecture boundaries and avoid shared-module bloat?"
- "What’s your strategy for performance regressions and monitoring?"
- "How do you handle state—RxJS services, NgRx, Signals—what drove that choice?"
```

---

## 2) `02-react-senior-interview.md`

```md
# React Senior Frontend Interview (30 min) — Prep Doc

## 1) What they assess at senior level
- Component architecture + state boundaries
- Performance: re-renders, memoization, lists
- Data fetching strategy and caching
- Correctness: effects, stale closures, race conditions
- Codebase hygiene: patterns, tests, refactors, incremental adoption

## 2) Hooks: crisp mental models (must be sharp)
### useState
- Functional updates when derived from previous state.

### useEffect
- Sync external systems (subscriptions, timers, manual DOM, analytics).
- Dependency array is a correctness tool, not a performance lever.
- Avoid infinite loops by deriving values with `useMemo` and stable callbacks.

### useMemo / useCallback
- Use to stabilize identity when it prevents expensive recalcs or re-renders.
- Don’t cargo-cult; measure or reason about cost.

### useRef
- Store mutable values that should not trigger renders (timers, latest value, DOM refs).

## 3) Common gotchas (senior-level answers)
- Stale closure: effect captures old state; fix via deps or refs.
- Race conditions in fetch: cancel/ignore prior request, or use a query library.
- Over-rendering: pass stable props, memoize child, split components, use virtualization.

## 4) State management choices
- Local state for UI-only.
- Context for low-frequency global (theme/auth) not high-churn.
- Query state (server state): React Query / SWR.
- App state: Redux Toolkit / Zustand / Jotai depending on complexity.

**Senior framing:** distinguish server state vs client state; avoid storing server data in Redux unless needed.

## 5) Rendering & performance
- React reconciliation: keys must be stable.
- Lists: use `key={id}` not index (unless append-only and safe).
- Virtualize long lists.
- Avoid heavy work in render; precompute, memoize, or move to workers.

## 6) TypeScript in React
- Prefer typed props and discriminated unions for variants.
- Avoid `any`; use generics for reusable hooks/components.
- Narrowing patterns for event handlers and conditional rendering.

## 7) Small coding prompts they might give
- Implement a `useDebouncedValue(value, delay)` hook.
- Build a controlled input component with validation.
- Transform API data to grouped UI sections.
- Fix a bug in an effect dependency array.

## 8) Testing
- Unit: pure functions + hooks where meaningful.
- Component tests with RTL: test behavior, not implementation.
- E2E thin layer for critical flows.

## 9) Questions to ask them
- "What’s your approach to server-state caching and invalidation?"
- "How do you prevent performance regressions (profiling, budgets, monitoring)?"
- "What patterns are standardized (forms, tables, error handling)?"
```

---

## 3) `03-typescript-senior-interview.md`

````md
# TypeScript Senior Interview — Prep Doc

## 1) Topics that signal “senior”
- Type narrowing and correctness (not just annotations)
- API design with generics
- Discriminated unions for UI states
- Utility types and inference
- Avoiding unsafe casts and `any`
- Modeling backend contracts safely

## 2) High-value patterns (say + show)
### Discriminated unions (UI state)
```ts
type LoadState<T> =
  | { status: "idle" }
  | { status: "loading" }
  | { status: "success"; data: T }
  | { status: "error"; error: string };

function render<T>(s: LoadState<T>) {
  switch (s.status) {
    case "success": return s.data;
    case "error": return s.error;
    default: return null;
  }
}
````

### Type narrowing

* `in` operator
* `typeof`
* `instanceof`
* user-defined type guards

```ts
type ApiError = { message: string; code: string };
function isApiError(x: unknown): x is ApiError {
  return typeof x === "object" && x !== null && "message" in x && "code" in x;
}
```

### Generics for reusable functions

```ts
function groupBy<T, K extends string | number>(
  items: readonly T[],
  keyFn: (t: T) => K
): Record<K, T[]> {
  return items.reduce((acc, item) => {
    const k = keyFn(item);
    (acc[k] ??= []).push(item);
    return acc;
  }, {} as Record<K, T[]>);
}
```

## 3) Utility types you should be fluent with

* `Partial<T>`: patch/update payloads (careful with required invariants)
* `Pick<T, K>` / `Omit<T, K>`: DTO shaping
* `Record<K, V>`: maps/dictionaries
* `ReturnType<F>`, `Parameters<F>`
* `Readonly<T>` and `as const`
* `satisfies` (great for config objects without widening)

## 4) “How do you model API responses safely?”

* Prefer runtime validation when data crosses trust boundary:

  * zod / io-ts / valibot style
* TS types alone don’t validate at runtime.

**Good line:** "I use TS for dev-time ergonomics and add runtime parsing for external inputs so we don’t lie to ourselves."

## 5) Common interview mini-questions

* Difference between `interface` and `type`? (merging, unions/intersections)
* Why avoid `any`? Use `unknown` + narrow.
* When to use enums? Prefer string unions unless interop reasons.
* How to type event handlers / promises / async functions.

## 6) “Bug fix” snippets they might ask

* Fix `never` inference by widening generics.
* Fix unsafe cast; replace with guard.
* Fix union narrowing in switch.

````

---

## 4) `04-copilot-ai-interview.md`

```md
# Copilot / AI-Assisted Dev — Interview Prep Doc

## 1) What they’re really evaluating
- Can you use Copilot to go faster without shipping bugs?
- Do you understand privacy/IP constraints?
- Can you review AI output critically and enforce standards?
- Can you use AI to accelerate tests, refactors, migration work?

## 2) How I use Copilot day-to-day (strong senior answer)
- Start with clear intent: function signature + constraints + examples.
- Generate small chunks, not whole systems.
- Review like a PR: correctness, edge cases, naming, complexity, security.
- Add tests immediately; treat Copilot as a "pair" not an authority.

## 3) Best use-cases (bullet answers)
- Boilerplate: DTOs, mappers, form schemas, reducers, typed utilities
- Test generation: edge cases, fixtures, mocks
- Refactors: rename, extract, convert imperative -> functional, simplify types
- Migration help: Angular/React API changes, codemod-like guidance
- Documentation: ADRs, comments for non-obvious decisions

## 4) Risk areas (must mention)
- Hallucinated APIs or outdated library patterns
- Subtle security issues (auth, token handling, injection)
- Performance regressions (extra renders, unnecessary subscriptions)
- Licensing/IP: do not paste proprietary code into external tools if policy forbids
- Sensitive data: never include secrets, customer data, credentials

## 5) “Policy / compliance” answer template
"I follow company policy: I don’t input secrets or proprietary customer data. I keep prompts abstracted, and I verify generated code against our style guides, threat model, and test suite. For anything security-sensitive, I do manual review and add tests."

## 6) Prompting patterns that actually work
### For implementation
- "Implement X with constraints Y. Return TS code only. Include edge cases A/B."
- Provide I/O examples and expected behavior.
- Provide existing types and exact function signature.

### For refactor
- "Refactor to remove nested conditionals; keep behavior identical; add tests."

### For tests
- "Generate tests for these scenarios; use our stack (Jest/RTL/Cypress); avoid flaky timeouts."

## 7) How to answer: “Does Copilot make you faster?”
- Yes, mainly in:
  - reducing context switching
  - generating scaffolding and tests
  - exploring alternatives quickly
- But the critical skill is **review + ownership**.

## 8) Mini practical exercise they may ask
- "Write a helper to transform data + ask Copilot to draft tests; then explain what you reviewed and changed."
````
