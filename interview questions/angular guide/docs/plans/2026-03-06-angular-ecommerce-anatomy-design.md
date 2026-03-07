# Angular E-Commerce Anatomy — Design Document

## Overview

Interactive single-HTML-file learning tool for Angular senior interview preparation. Uses an e-commerce dashboard as the example app to demonstrate every major Angular concept. Same 3-column layout as `angular_todo_anatomy_v3.html` (map | code | explanation) but with 22 scenes covering the full Angular ecosystem.

## Format

- Single self-contained HTML file (no external dependencies except Google Fonts)
- 3-column layout: interactive map (left), highlighted code with file tabs (center), explanation cards (right)
- Scene navigation via top bar buttons + arrow keys
- Click any highlighted code span or map box to open its explanation card
- Dark theme matching existing files

## E-Commerce Domain

The example app is a **ShopZone** e-commerce dashboard with:
- **Modules:** AppModule, CoreModule, SharedModule, ProductModule (lazy), CartModule (lazy), AuthModule (lazy), AdminModule (lazy)
- **Components:** AppComponent, ProductListComponent, ProductCardComponent, ProductDetailComponent, CartComponent, CheckoutComponent, LoginComponent, AdminDashboardComponent, HeaderComponent, SearchBarComponent
- **Services:** ProductService, CartService (BehaviorSubject store), AuthService, NotificationService, LoggerService
- **Guards:** AuthGuard, AdminGuard, UnsavedChangesGuard
- **Interceptors:** AuthInterceptor, ErrorInterceptor, LoadingInterceptor, CacheInterceptor
- **Pipes:** DiscountPipe, TruncatePipe, FilterPipe (impure, with warning)
- **Directives:** HighlightDirective, DebounceClickDirective, IfRoleDirective (structural)
- **Models/Interfaces:** Product, CartItem, User, Order, ApiResponse<T>

## Scene Breakdown (22 Scenes)

### Scene 0: Architecture Overview
Full map of the e-commerce app showing all modules, components, services, and their relationships. File tree of the entire project. High-level explanation of how everything connects.

### Scene 1: Modules — App, Core, Shared, Feature
- NgModule anatomy (declarations, imports, exports, providers)
- CoreModule with `@SkipSelf()` double-import guard
- SharedModule exporting CommonModule, FormsModule, shared components/pipes/directives
- Feature modules owning a domain (ProductModule, CartModule)
- `RouterModule.forRoot()` vs `.forChild()`

### Scene 2: Standalone Components (v14+)
- `standalone: true` in @Component
- Component-level `imports: []`
- `app.config.ts` replacing AppModule
- `bootstrapApplication(AppComponent, appConfig)`
- `provideRouter()`, `provideHttpClient()`
- Migration path from NgModule to standalone

### Scene 3: Lazy Loading & @defer
- `loadChildren` for feature modules
- `loadComponent` for standalone components
- Route-level code splitting (separate JS chunks)
- `PreloadAllModules` and custom preloading strategies
- `@defer (on viewport)` / `(on idle)` / `(on interaction)`
- `@placeholder`, `@loading`, `@error` blocks

### Scene 4: Components & Decorators
- @Component decorator (selector, template, styles, encapsulation, changeDetection)
- @Input() and @Input({ required: true })
- @Output() with EventEmitter
- @ViewChild / @ViewChildren
- @ContentChild / @ContentChildren
- @HostListener / @HostBinding

### Scene 5: Lifecycle Hooks — All 8
- ngOnChanges (SimpleChanges, runs before ngOnInit, every @Input change)
- ngOnInit (fetch data, setup subscriptions)
- ngDoCheck (custom change detection, runs every CD cycle)
- ngAfterContentInit (projected content ready, @ContentChild available)
- ngAfterContentChecked (after every projected content check)
- ngAfterViewInit (@ViewChild available, DOM ready)
- ngAfterViewChecked (after every view check)
- ngOnDestroy (unsubscribe, cancel timers, cleanup)
- Parent vs child execution order

### Scene 6: Signals (v17+)
- `signal()` — reactive primitive
- `computed()` — derived value, auto-tracks dependencies
- `effect()` — side effects on signal changes
- `input()` replacing @Input()
- `output()` replacing @Output()
- `model()` for two-way binding
- Zoneless change detection with signals

### Scene 7: Parent-Child Communication
- @Input (parent to child data flow)
- @Output + EventEmitter (child to parent events)
- @ViewChild (parent accesses child directly)
- @ContentChild (parent accesses projected child)
- Service-based communication (shared BehaviorSubject)
- When to use each pattern

### Scene 8: Content Projection & Templates
- `<ng-content>` default slot
- `<ng-content select="[header]">` multi-slot projection
- `<ng-container>` — grouping without DOM element
- `<ng-template>` — lazy template definition
- `*ngTemplateOutlet` — render template dynamically
- `TemplateRef` and `ViewContainerRef` for dynamic rendering

### Scene 9: Dependency Injection — Deep Dive
- Injector hierarchy (Root > Module > Element)
- Resolution order: Element -> Module -> Root -> NullInjector (error)
- Provider types: useClass, useValue, useFactory, useExisting
- `InjectionToken<T>` for non-class values
- @Optional(), @Self(), @SkipSelf(), @Host() decorators
- `inject()` function (v14+) — functional DI
- `forwardRef()` for circular dependencies
- `multi: true` for interceptors/validators

### Scene 10: Services & Singleton Pattern
- @Injectable and providedIn scopes ('root', 'any', 'platform', component-level)
- Singleton vs transient instances
- CartService as BehaviorSubject store (scan operator for accumulator)
- Service-to-service injection
- When to use a service vs @Input/@Output

### Scene 11: RxJS — Observables vs Promises
- Observable: lazy, cancellable, multi-emit, operators
- Promise: eager, non-cancellable, single value
- Hot vs Cold observables (Netflix vs Live TV analogy)
- Subject, BehaviorSubject, ReplaySubject, AsyncSubject — when to use each
- Creation operators: of, from, interval, timer, fromEvent, EMPTY
- Unsubscribe patterns: takeUntil(destroy$), async pipe, take(1), DestroyRef

### Scene 12: RxJS — All Operators
- **Transform:** map, switchMap, mergeMap, concatMap, exhaustMap, scan
  - When to use which flattening operator (search=switchMap, upload=mergeMap, queue=concatMap, submit=exhaustMap)
- **Filter:** filter, take, first, takeUntil, debounceTime, throttleTime, distinctUntilChanged
- **Combine:** forkJoin, combineLatest, withLatestFrom, merge, concat, zip
- **Error:** catchError, retry, retryWhen (exponential backoff), finalize
- **Multicast:** shareReplay, share
- Real e-commerce examples for every operator

### Scene 13: Routing — Complete System
- Routes config (path, component, redirectTo, pathMatch, children)
- Route params (`:id`) vs query params (`?search=x`)
- `ActivatedRoute` (snapshot vs observable paramMap)
- Nested routes with `children: []`
- Named router outlets (`<router-outlet name="sidebar">`)
- `routerLink`, `routerLinkActive`, `router.navigate()`
- Wildcard routes (`**`)
- Route animations

### Scene 14: Route Guards & Resolvers
- `canActivate` — auth check before entering route
- `canDeactivate` — unsaved changes warning on leave
- `canLoad` — prevent downloading lazy module entirely
- `canMatch` — conditionally match route (v14+)
- `Resolve` — pre-fetch data before route activates
- Functional guards (v15+) with `inject()`
- Class-based vs functional comparison

### Scene 15: HTTP & Interceptors
- HttpClient methods (get, post, put, patch, delete)
- Typed responses with generics: `http.get<Product[]>(url)`
- HttpParams and HttpHeaders
- Interceptor chain architecture:
  - AuthInterceptor (attach JWT token)
  - ErrorInterceptor (global error handling, retry)
  - LoadingInterceptor (show/hide spinner)
  - CacheInterceptor (cache GET requests)
- Interceptor ordering and `multi: true`
- Functional interceptors (v15+) with `withInterceptors()`

### Scene 16: Forms — Template-Driven vs Reactive
- Template-driven: ngModel, ngForm, ngModelGroup, #ref="ngModel"
- Reactive: FormControl, FormGroup, FormArray, FormBuilder
- Built-in validators: required, minLength, email, pattern
- Custom sync validator function
- Custom async validator (e.g., check username availability)
- Cross-field validation at FormGroup level
- Dynamic FormArray (add/remove items in cart)
- `form.getRawValue()` — includes disabled controls
- When to use template-driven vs reactive

### Scene 17: Directives — Structural & Attribute
- Structural directives: *ngIf, *ngFor, *ngSwitch
- New control flow (v17+): @if, @for (with track), @switch, @case
- The `*` microsyntax — what it desugars to (ng-template)
- Custom structural directive: `*appIfRole="'admin'"` using TemplateRef + ViewContainerRef
- Custom attribute directive: `appHighlight`, `appDebounceClick`
- @HostListener and @HostBinding in directives
- Renderer2 for safe DOM manipulation (vs direct DOM access)

### Scene 18: Pipes — Built-in & Custom
- Built-in: date, currency, percent, json, slice, async, keyvalue, titlecase, uppercase, lowercase
- async pipe — auto subscribe/unsubscribe (Observable AND Promise)
- Pure pipe: only recalculates when input reference changes (performant)
- Impure pipe: recalculates every CD cycle (expensive, avoid)
- Custom pure pipe: DiscountPipe (calculate sale price)
- Custom impure pipe: FilterPipe (and why it's an anti-pattern)
- Chaining pipes: `{{ price | discount:0.2 | currency:'USD' }}`

### Scene 19: Change Detection & Zone.js
- What Zone.js does (monkey-patches setTimeout, addEventListener, Promise, XHR)
- How Angular triggers change detection (zone notifies Angular of async events)
- Default strategy: checks entire component tree top-down
- OnPush strategy: only checks when @Input ref changes, event fires in component, async pipe emits, or markForCheck() called
- `markForCheck()` vs `detectChanges()` — when to use each
- `NgZone.runOutsideAngular()` — for animations, intervals, WebSocket without triggering CD
- `ChangeDetectorRef.detach()` — fully remove from CD tree
- Signals and zoneless Angular (v18+ preview)

### Scene 20: TypeScript Patterns
- Interfaces vs Types vs Classes — when to use each
- Generics: `ApiResponse<T>`, service methods, component generics
- String enums vs const enums vs union literal types
- Utility types: Partial<T>, Omit<T,K>, Pick<T,K>, Record<K,V>, ReturnType<T>, NonNullable<T>
- Type guards: `is` keyword, `in` operator, typeof, instanceof
- Discriminated unions: `type State = { status: 'loading' } | { status: 'done'; data: T }`
- Mapped types, conditional types, template literal types
- Decorators: class, property, method — evaluation order
- Strict mode flags: strictNullChecks, noImplicitAny, strictPropertyInitialization
- Barrel exports (index.ts pattern)

### Scene 21: Performance & Optimization Cheat Sheet
- Lazy loading modules and components
- OnPush + immutable data + async pipe
- trackBy in *ngFor / track in @for
- Pure pipes over methods in templates
- Preloading strategies
- Virtual scrolling (cdk-virtual-scroll-viewport)
- Bundle analysis with source-map-explorer
- Tree shaking (providedIn: 'root' is tree-shakable)
- @defer for below-fold content
- NgOptimizedImage for LCP optimization
- Web Workers for CPU-heavy tasks
- SSR with Angular Universal / hydration
- Avoiding memory leaks (unsubscribe patterns)

## Technical Approach

- Single HTML file, same structure as `angular_todo_anatomy_v3.html`
- CSS variables for theming, same dark color scheme
- JavaScript engine: scene management, tab switching, pick/highlight system, keyboard navigation
- All Angular code shown as syntax-highlighted `<pre>` blocks with clickable `<span class="hl">` elements
- Interactive map per scene using absolutely-positioned boxes with SVG connection lines
- Explanation cards in right column that open/close on click
- Estimated file size: ~8000-10000 lines
