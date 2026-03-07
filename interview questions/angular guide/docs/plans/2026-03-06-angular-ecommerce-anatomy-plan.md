# Angular E-Commerce Anatomy Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a single interactive HTML file with 22 scenes covering every Angular concept needed for a senior frontend interview, using an e-commerce dashboard as the example app.

**Architecture:** Single self-contained HTML file following the same 3-column layout (map | code | explanation) as `angular_todo_anatomy_v3.html`. Each scene has its own `<div class="scene">` containing 3 `.col` divs. JavaScript engine handles scene navigation, tab switching, concept highlighting, and keyboard shortcuts. All code is syntax-highlighted `<pre>` blocks with clickable `.hl` spans.

**Tech Stack:** HTML, CSS, vanilla JavaScript, Google Fonts (JetBrains Mono + Sora)

**Reference file:** `/Users/fahimanik/code/dsa-algorithms/interview questions/angular guide/angular_todo_anatomy_v3.html` — copy its exact CSS structure, JS engine pattern, and interaction model.

---

## Task 0: Scaffold — CSS, Topbar, JS Engine

**Files:**
- Create: `angular_ecommerce_anatomy.html`

**Step 1: Create the HTML skeleton**

Copy the CSS from `angular_todo_anatomy_v3.html` verbatim (lines 8-173), but update:
- Title: `Angular E-Commerce — Full Anatomy`
- Brand text: `Angular <span>ShopZone</span> · E-Commerce Anatomy`
- Scene nav buttons: all 22 scenes (0-21) with labels:
  - 0: Overview, 1: Modules, 2: Standalone, 3: Lazy/@defer, 4: Decorators
  - 5: Lifecycle, 6: Signals, 7: Parent-Child, 8: Projection, 9: DI Deep
  - 10: Services, 11: RxJS Basics, 12: RxJS Ops, 13: Routing, 14: Guards
  - 15: HTTP/Intercept, 16: Forms, 17: Directives, 18: Pipes, 19: Zone/CD
  - 20: TypeScript, 21: Performance
- Update `const TOTAL = 22;`

**Step 2: Copy the JavaScript engine**

Copy the JS from `angular_todo_anatomy_v3.html` (lines 1972-2076) — the `goScene`, `nextScene`, `prevScene`, `switchTab`, `pick`, `closeAll`, keyboard handler functions. Update TOTAL to 22. The MAP object will be populated as scenes are built.

**Step 3: Add empty scene divs**

Create 22 empty `<div class="scene" id="scene-N">` blocks, each with the 3-column structure:
```html
<div class="scene" id="scene-N">
  <div class="col"><div class="col-inner"><!-- map --></div></div>
  <div class="col"><div class="col-inner"><!-- code --></div></div>
  <div class="col"><div class="col-inner"><!-- story --></div></div>
</div>
```

**Step 4: Verify in browser**

Open file in browser. Topbar should render with all 22 scene buttons. Arrow keys should navigate. All scenes blank but functional.

**Step 5: Commit**
```bash
git add "interview questions/angular guide/angular_ecommerce_anatomy.html"
git commit -m "feat: scaffold angular e-commerce anatomy with 22 empty scenes"
```

---

## Task 1: Scene 0 — Architecture Overview

**Files:**
- Modify: `angular_ecommerce_anatomy.html` (scene-0 div)

**Step 1: Build the map column**

Create an interactive city-style map showing the full e-commerce architecture:
- Top: AppModule (or app.config.ts) — the root
- Row 2: CoreModule, SharedModule
- Row 3: Feature modules (ProductModule, CartModule, AuthModule, AdminModule) — all with dashed borders indicating lazy loading
- Row 4: Key services (ProductService, CartService, AuthService)
- Row 5: HTTP Interceptors chain
- Row 6: External API
- SVG connection lines between related boxes
- Each box is a `.cbox` with onclick to `pick('concept-key')` or `goScene(N)`

**Step 2: Build the code column**

Show the full project file tree as a `<pre>` block with highlighted clickable file names:
```
src/
  app/
    app.module.ts              (or app.config.ts)
    app.component.ts/html/css
    app-routing.module.ts
    core/
      core.module.ts
      interceptors/
        auth.interceptor.ts
        error.interceptor.ts
        loading.interceptor.ts
      services/
        auth.service.ts
        logger.service.ts
    shared/
      shared.module.ts
      pipes/
        discount.pipe.ts
        truncate.pipe.ts
      directives/
        highlight.directive.ts
        debounce-click.directive.ts
        if-role.directive.ts
      components/
        header/
        search-bar/
    features/
      products/
        product-list/
        product-card/
        product-detail/
        product.service.ts
        product.module.ts
      cart/
        cart.component.ts/html
        cart.service.ts
        cart.module.ts
      checkout/
        checkout.component.ts/html
      auth/
        login.component.ts/html
        auth.module.ts
      admin/
        admin-dashboard.component.ts
        admin.module.ts
    models/
      product.model.ts
      cart-item.model.ts
      user.model.ts
      order.model.ts
      api-response.model.ts
```

Also show the key model interfaces as a second code block:
```typescript
export interface Product {
  id: number;
  name: string;
  price: number;
  description: string;
  imageUrl: string;
  category: string;
  stock: number;
  rating: number;
}

export interface CartItem {
  product: Product;
  quantity: number;
}

export interface User {
  id: number;
  email: string;
  name: string;
  role: 'user' | 'admin';
  token?: string;
}
```

**Step 3: Build the story column**

Add concept cards (`.ccard`) for each major architectural element:
- AppModule / app.config.ts — the entry point
- CoreModule — singleton services, interceptors, import-once guard
- SharedModule — reusable UI, pipes, directives, never services
- Feature Modules — domain ownership, lazy loaded
- Services — business logic layer
- Interceptors — HTTP middleware chain
- Models — TypeScript interfaces as contracts

Each card has: emoji, title, subtitle, "grade 5 explanation" (simple analogy), Angular definition, and a code snippet.

**Step 4: Add MAP entries**

Add all scene-0 concept keys to the JavaScript MAP object.

**Step 5: Verify and commit**
```bash
git add "interview questions/angular guide/angular_ecommerce_anatomy.html"
git commit -m "feat: add scene 0 — architecture overview with full file tree"
```

---

## Task 2: Scene 1 — Modules (App, Core, Shared, Feature)

**Step 1: Map column** — 6 boxes: @NgModule decorator, declarations[], imports[], exports[], providers[], bootstrap[]

**Step 2: Code column** — File tabs for: `app.module.ts`, `core.module.ts`, `shared.module.ts`, `product.module.ts`

Full code for each:

`app.module.ts`:
```typescript
@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,        // singleton services + interceptors
    SharedModule,      // shared UI components
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
```

`core.module.ts`:
```typescript
@NgModule({
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi: true },
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parent: CoreModule) {
    if (parent) throw new Error('CoreModule already loaded — import in AppModule only');
  }
}
```

`shared.module.ts`:
```typescript
@NgModule({
  declarations: [HighlightDirective, DebounceClickDirective, IfRoleDirective, DiscountPipe, TruncatePipe, HeaderComponent, SearchBarComponent],
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  exports: [CommonModule, FormsModule, ReactiveFormsModule, HighlightDirective, DebounceClickDirective, IfRoleDirective, DiscountPipe, TruncatePipe, HeaderComponent, SearchBarComponent]
})
export class SharedModule {}
```

`product.module.ts`:
```typescript
@NgModule({
  declarations: [ProductListComponent, ProductCardComponent, ProductDetailComponent],
  imports: [SharedModule, ProductRoutingModule]
})
export class ProductModule {}
```

**Step 3: Story column** — Cards for: @NgModule decorator, declarations, imports, exports, providers, CoreModule guard pattern, SharedModule rules, Feature module pattern, forRoot vs forChild

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 1 — modules (app, core, shared, feature)"
```

---

## Task 3: Scene 2 — Standalone Components (v14+)

**Step 1: Map** — Boxes: standalone:true, component imports[], app.config.ts, bootstrapApplication, provideRouter, provideHttpClient

**Step 2: Code** — Tabs: `app.config.ts`, `main.ts`, `product-list.component.ts (standalone)`, migration comparison

```typescript
// app.config.ts
export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor, errorInterceptor])),
    provideAnimations(),
  ]
};

// main.ts
bootstrapApplication(AppComponent, appConfig);

// Standalone component
@Component({
  standalone: true,
  selector: 'app-product-list',
  imports: [CommonModule, RouterModule, ProductCardComponent, DiscountPipe],
  templateUrl: './product-list.component.html'
})
export class ProductListComponent { }
```

**Step 3: Story** — Cards: standalone flag, component-level imports, app.config.ts, bootstrapApplication, functional providers, migration path, when to use standalone vs NgModule

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 2 — standalone components"
```

---

## Task 4: Scene 3 — Lazy Loading & @defer

**Step 1: Map** — Boxes: loadChildren, loadComponent, PreloadAllModules, @defer(on viewport), @defer(on idle), @defer(on interaction), @placeholder, @loading, @error

**Step 2: Code** — Tabs: `app-routing.module.ts` (lazy routes), `product-list.component.html` (@defer examples)

```typescript
const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  {
    path: 'products',
    loadChildren: () => import('./features/products/product.module')
      .then(m => m.ProductModule)
  },
  {
    path: 'cart',
    loadComponent: () => import('./features/cart/cart.component')
      .then(c => c.CartComponent)
  },
  {
    path: 'admin',
    loadChildren: () => import('./features/admin/admin.module')
      .then(m => m.AdminModule),
    canActivate: [authGuard],
    canMatch: [adminGuard]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
```

@defer examples:
```html
@defer (on viewport) {
  <app-product-reviews [productId]="product.id" />
} @placeholder {
  <div class="skeleton">Reviews loading on scroll...</div>
} @loading (minimum 300ms) {
  <app-spinner />
} @error {
  <p>Failed to load reviews</p>
}

@defer (on idle) {
  <app-recommended-products />
}

@defer (on interaction) {
  <app-heavy-chart [data]="salesData" />
} @placeholder {
  <button>Click to load chart</button>
}
```

**Step 3: Story** — Cards for each concept with real-world analogy

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 3 — lazy loading and @defer"
```

---

## Task 5: Scene 4 — Components & Decorators

**Step 1: Map** — Boxes for each decorator: @Component, @Input, @Output, @ViewChild, @ViewChildren, @ContentChild, @ContentChildren, @HostListener, @HostBinding

**Step 2: Code** — Tabs: `product-card.component.ts` (all decorators in one component), `product-list.component.ts` (parent using ViewChild), `header.component.ts` (HostListener/HostBinding)

```typescript
@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.Emulated
})
export class ProductCardComponent implements OnInit, OnChanges {
  @Input({ required: true }) product!: Product;
  @Input() showActions = true;

  @Output() addToCart = new EventEmitter<Product>();
  @Output() viewDetail = new EventEmitter<number>();

  @ViewChild('priceEl') priceElement!: ElementRef;
  @ContentChild('customBadge') customBadge!: TemplateRef<any>;

  @HostBinding('class.out-of-stock') get outOfStock() { return this.product.stock === 0; }
  @HostListener('mouseenter') onHover() { this.highlighted = true; }
  @HostListener('mouseleave') onLeave() { this.highlighted = false; }
}
```

**Step 3: Story** — Card for each decorator with analogy and usage

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 4 — components and decorators"
```

---

## Task 6: Scene 5 — Lifecycle Hooks (All 8)

**Step 1: Map** — 8 boxes in vertical order showing the lifecycle flow with arrows: constructor -> ngOnChanges -> ngOnInit -> ngDoCheck -> ngAfterContentInit -> ngAfterContentChecked -> ngAfterViewInit -> ngAfterViewChecked -> ngOnDestroy. Color-code which run once vs every CD cycle.

**Step 2: Code** — Single file `product-detail.component.ts` implementing ALL hooks with console.log and real use cases:

```typescript
export class ProductDetailComponent implements
    OnChanges, OnInit, DoCheck, AfterContentInit,
    AfterContentChecked, AfterViewInit, AfterViewChecked, OnDestroy {

  @Input() productId!: number;
  @ViewChild('reviewSection') reviewSection!: ElementRef;
  @ContentChild('promoSlot') promoSlot!: TemplateRef<any>;

  private destroy$ = new Subject<void>();

  constructor(
    private productService: ProductService,
    private cd: ChangeDetectorRef
  ) {
    console.log('1. constructor — DI happens here, no @Input yet');
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('2. ngOnChanges — @Input changed:', changes);
    if (changes['productId'] && !changes['productId'].firstChange) {
      this.loadProduct(this.productId); // re-fetch on param change
    }
  }

  ngOnInit(): void {
    console.log('3. ngOnInit — component born, @Input available');
    this.loadProduct(this.productId);
  }

  ngDoCheck(): void {
    console.log('4. ngDoCheck — custom change detection (every CD cycle!)');
  }

  ngAfterContentInit(): void {
    console.log('5. ngAfterContentInit — <ng-content> projected, @ContentChild available');
    if (this.promoSlot) { /* use projected template */ }
  }

  ngAfterContentChecked(): void {
    console.log('6. ngAfterContentChecked — projected content re-checked');
  }

  ngAfterViewInit(): void {
    console.log('7. ngAfterViewInit — DOM rendered, @ViewChild available');
    this.reviewSection.nativeElement.scrollIntoView();
  }

  ngAfterViewChecked(): void {
    console.log('8. ngAfterViewChecked — view re-checked (every CD cycle)');
  }

  ngOnDestroy(): void {
    console.log('9. ngOnDestroy — cleanup!');
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

**Step 3: Story** — Cards for each hook with: when it fires, how many times, real use case, common mistakes. Also a card showing parent vs child order: Parent constructor -> Parent ngOnChanges -> Parent ngOnInit -> Child constructor -> Child ngOnInit -> ... -> Child ngAfterViewInit -> Parent ngAfterViewInit.

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 5 — all 8 lifecycle hooks"
```

---

## Task 7: Scene 6 — Signals (v17+)

**Step 1: Map** — Boxes: signal(), computed(), effect(), input(), output(), model(), linkedSignal(), zoneless

**Step 2: Code** — Tabs: `cart.component.ts` using signals, comparison with BehaviorSubject approach

```typescript
@Component({
  selector: 'app-cart',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <h2>Cart ({{ itemCount() }} items)</h2>
    <p>Total: {{ total() | currency }}</p>
    <button (click)="addItem(sampleProduct)">Add Item</button>
  `
})
export class CartComponent {
  // signal — reactive primitive
  items = signal<CartItem[]>([]);

  // computed — derived, auto-tracks dependencies
  itemCount = computed(() => this.items().length);
  total = computed(() =>
    this.items().reduce((sum, i) => sum + i.product.price * i.quantity, 0)
  );

  // effect — side effect when signals change
  constructor() {
    effect(() => {
      console.log('Cart updated:', this.items());
      localStorage.setItem('cart', JSON.stringify(this.items()));
    });
  }

  // input/output — signal-based component API
  discount = input<number>(0);               // replaces @Input()
  checkout = output<CartItem[]>();            // replaces @Output()
  selectedItem = model<CartItem | null>(null); // two-way binding

  addItem(product: Product) {
    this.items.update(items => [...items, { product, quantity: 1 }]);
  }
}
```

**Step 3: Story** — Cards for each signal primitive, comparison table (signal vs BehaviorSubject), zoneless explanation

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 6 — signals (v17+)"
```

---

## Task 8: Scene 7 — Parent-Child Communication

**Step 1: Map** — Show ProductListComponent (parent) and ProductCardComponent (child) with arrows showing 5 communication patterns

**Step 2: Code** — Tabs showing each pattern side by side:

Pattern 1: @Input (parent -> child)
Pattern 2: @Output + EventEmitter (child -> parent)
Pattern 3: @ViewChild (parent accesses child directly)
Pattern 4: @ContentChild (parent accesses projected child)
Pattern 5: Shared service (siblings communicate via CartService BehaviorSubject)

**Step 3: Story** — Cards with when to use each pattern, pros/cons table

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 7 — parent-child communication patterns"
```

---

## Task 9: Scene 8 — Content Projection & Templates

**Step 1: Map** — Boxes: ng-content (default), ng-content select, multi-slot, ng-container, ng-template, ngTemplateOutlet, TemplateRef, ViewContainerRef

**Step 2: Code** — Tabs: `card.component.ts` (reusable card with slots), `product-list.component.html` (using the card), `modal.component.ts` (dynamic template)

```typescript
// card.component.html — multi-slot projection
<div class="card">
  <div class="card-header">
    <ng-content select="[card-header]"></ng-content>
  </div>
  <div class="card-body">
    <ng-content></ng-content>  <!-- default slot -->
  </div>
  <div class="card-footer">
    <ng-content select="[card-footer]"></ng-content>
  </div>
</div>

// Usage:
<app-card>
  <h3 card-header>{{ product.name }}</h3>
  <p>{{ product.description }}</p>
  <button card-footer (click)="buy()">Buy Now</button>
</app-card>

// ng-template + ngTemplateOutlet
<ng-container *ngTemplateOutlet="isGrid ? gridTemplate : listTemplate; context: { $implicit: products }">
</ng-container>

<ng-template #gridTemplate let-items>
  <div class="grid">
    <app-product-card *ngFor="let p of items" [product]="p" />
  </div>
</ng-template>

<ng-template #listTemplate let-items>
  <ul>
    <li *ngFor="let p of items">{{ p.name }} — {{ p.price | currency }}</li>
  </ul>
</ng-template>
```

**Step 3: Story** — Cards explaining each concept with analogies

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 8 — content projection and templates"
```

---

## Task 10: Scene 9 — Dependency Injection Deep Dive

**Step 1: Map** — Injector hierarchy tree (Root -> Module -> Element), provider types, DI decorators

**Step 2: Code** — Tabs: `product.service.ts` (providedIn), `app.module.ts` (providers with recipes), `product-detail.component.ts` (component-level provider), `config.token.ts` (InjectionToken)

```typescript
// InjectionToken
export const API_CONFIG = new InjectionToken<ApiConfig>('API_CONFIG');

// Provider recipes
providers: [
  { provide: ProductService, useClass: ProductService },           // useClass
  { provide: API_CONFIG, useValue: { baseUrl: 'https://api.shop.com', timeout: 5000 } }, // useValue
  { provide: LoggerService, useFactory: (env: Environment) =>      // useFactory
      env.production ? new SilentLogger() : new ConsoleLogger(),
    deps: [Environment]
  },
  { provide: AbstractStorage, useExisting: LocalStorageService },  // useExisting
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }, // multi
]

// DI decorators
constructor(
  private productService: ProductService,              // normal
  @Optional() private analytics: AnalyticsService,     // null if not provided
  @Self() private logger: LoggerService,               // only this injector
  @SkipSelf() private parentLogger: LoggerService,     // skip this, look up
  @Host() private validator: ValidatorService,          // stop at host
  @Inject(API_CONFIG) private config: ApiConfig,       // token injection
) {}

// inject() function (v14+)
const router = inject(Router);
const config = inject(API_CONFIG);
```

**Step 3: Story** — Cards for hierarchy, each provider type, each decorator, inject() vs constructor, forwardRef

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 9 — dependency injection deep dive"
```

---

## Task 11: Scene 10 — Services & Singleton Pattern

**Step 1: Map** — Show CartService as central hub with BehaviorSubject, connected to multiple components

**Step 2: Code** — Tabs: `cart.service.ts` (full BehaviorSubject store), `product.service.ts` (HTTP service), scope comparison

```typescript
@Injectable({ providedIn: 'root' }) // singleton — one instance app-wide
export class CartService {
  private itemsSubject = new BehaviorSubject<CartItem[]>([]);

  // Public read-only observable
  items$ = this.itemsSubject.asObservable();
  itemCount$ = this.items$.pipe(map(items => items.length));
  total$ = this.items$.pipe(
    map(items => items.reduce((sum, i) => sum + i.product.price * i.quantity, 0))
  );

  addItem(product: Product): void {
    const current = this.itemsSubject.getValue();
    const existing = current.find(i => i.product.id === product.id);
    if (existing) {
      this.itemsSubject.next(
        current.map(i => i.product.id === product.id
          ? { ...i, quantity: i.quantity + 1 } : i)
      );
    } else {
      this.itemsSubject.next([...current, { product, quantity: 1 }]);
    }
  }

  removeItem(productId: number): void {
    const updated = this.itemsSubject.getValue().filter(i => i.product.id !== productId);
    this.itemsSubject.next(updated);
  }

  clear(): void {
    this.itemsSubject.next([]);
  }
}
```

Also show singleton vs transient comparison:
```typescript
// Singleton — all components share one instance
@Injectable({ providedIn: 'root' })

// Transient — each component gets its own instance
@Component({
  providers: [FormStateService]  // new instance per component
})
```

**Step 3: Story** — Cards for singleton pattern, BehaviorSubject store, providedIn scopes, when to use service vs @Input/@Output

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 10 — services and singleton pattern"
```

---

## Task 12: Scene 11 — RxJS Observables vs Promises

**Step 1: Map** — Split view: Observable side vs Promise side, Subject types below

**Step 2: Code** — Tabs: comparison table, Subject types, creation operators, unsubscribe patterns

```typescript
// Observable vs Promise comparison
// PROMISE: eager, single value, not cancellable
const promise = fetch('/api/products'); // fires immediately!
promise.then(data => console.log(data));

// OBSERVABLE: lazy, multi-value, cancellable
const obs$ = http.get('/api/products'); // nothing happens yet
const sub = obs$.subscribe(data => console.log(data)); // NOW it fires
sub.unsubscribe(); // cancel the request!

// Subject types
const subject = new Subject<number>();           // no initial value
const behavior = new BehaviorSubject<number>(0); // holds last value
const replay = new ReplaySubject<number>(3);     // replays last 3
const async = new AsyncSubject<number>();         // only last value on complete

// Creation operators
of(1, 2, 3)                    // emit fixed values, complete
from([1, 2, 3])                // array/Promise to Observable
from(fetch('/api'))            // Promise to Observable
interval(1000)                 // emit 0, 1, 2... every second
timer(3000)                    // emit once after 3s
fromEvent(button, 'click')    // DOM events as stream
EMPTY                          // complete immediately

// Unsubscribe patterns
// 1. takeUntil + Subject (class-based)
private destroy$ = new Subject<void>();
ngOnInit() { this.data$.pipe(takeUntil(this.destroy$)).subscribe(); }
ngOnDestroy() { this.destroy$.next(); this.destroy$.complete(); }

// 2. DestroyRef (v16+)
destroyRef = inject(DestroyRef);
ngOnInit() {
  this.data$.pipe(takeUntilDestroyed(this.destroyRef)).subscribe();
}

// 3. async pipe (best — no manual unsub)
// template: {{ data$ | async }}

// 4. take(1) — one-shot
this.config$.pipe(take(1)).subscribe();
```

**Step 3: Story** — Cards for Observable vs Promise (table), hot vs cold (Netflix vs Live TV), each Subject type with use case, each creation operator with real example, each unsubscribe pattern ranked

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 11 — RxJS observables vs promises"
```

---

## Task 13: Scene 12 — RxJS All Operators

**Step 1: Map** — 4 quadrants: Transform, Filter, Combine, Error. Each with operator boxes.

**Step 2: Code** — Tabs: Transform operators, Filter operators, Combine operators, Error operators. Each with a real e-commerce example.

```typescript
// === TRANSFORM ===
// map — convert API response
this.http.get<ApiResponse<Product[]>>('/api/products').pipe(
  map(response => response.data)  // unwrap
);

// switchMap — search-as-you-type (CANCELS previous)
this.searchInput.valueChanges.pipe(
  debounceTime(300),
  distinctUntilChanged(),
  switchMap(term => this.productService.search(term))
  // if user types "sh" then "sho" then "shoe",
  // only the "shoe" request completes
);

// mergeMap — parallel file uploads (ALL run concurrently)
this.files$.pipe(
  mergeMap(file => this.uploadService.upload(file), 3) // max 3 concurrent
);

// concatMap — ordered queue (waits for each to complete)
this.actions$.pipe(
  concatMap(action => this.orderService.process(action))
  // action 1 completes, THEN action 2 starts
);

// exhaustMap — prevent double submit (IGNORES while busy)
this.submitBtn.clicks$.pipe(
  exhaustMap(() => this.orderService.placeOrder(this.cart))
  // second click ignored while first request is in flight
);

// scan — running total (accumulator)
this.cartActions$.pipe(
  scan((cart, action) => {
    switch(action.type) {
      case 'ADD': return [...cart, action.item];
      case 'REMOVE': return cart.filter(i => i.id !== action.id);
      default: return cart;
    }
  }, [] as CartItem[])
);

// === FILTER ===
// debounceTime — wait for user to stop typing
this.searchControl.valueChanges.pipe(
  debounceTime(300),        // wait 300ms of silence
  distinctUntilChanged(),   // skip if same value
  filter(term => term.length >= 2)  // min 2 chars
);

// throttleTime — rate-limit scroll events
fromEvent(window, 'scroll').pipe(
  throttleTime(200)  // max 1 event per 200ms
);

// take / first / takeUntil
this.config$.pipe(take(1));     // get config once, auto-complete
this.config$.pipe(first());    // same but errors if empty
this.data$.pipe(takeUntil(this.destroy$)); // until component destroyed

// === COMBINE ===
// forkJoin — parallel API calls, wait for ALL
forkJoin({
  products: this.productService.getAll(),
  categories: this.categoryService.getAll(),
  user: this.authService.getCurrentUser()
}).subscribe(({ products, categories, user }) => {
  // all three arrived — render page
});

// combineLatest — live filters (re-emit when ANY changes)
combineLatest([
  this.categoryFilter$,
  this.priceRange$,
  this.sortOrder$
]).pipe(
  switchMap(([cat, price, sort]) =>
    this.productService.search({ category: cat, ...price, sort })
  )
);

// withLatestFrom — enrich event with current state
this.addToCartClick$.pipe(
  withLatestFrom(this.currentUser$),
  switchMap(([product, user]) =>
    this.cartService.add(product, user.id)
  )
);

// merge — multiple sources, same action
merge(
  fromEvent(searchBtn, 'click'),
  this.searchInput.valueChanges.pipe(debounceTime(300))
).pipe(
  switchMap(() => this.search())
);

// === ERROR ===
// catchError — fallback
this.productService.getAll().pipe(
  catchError(err => {
    this.errorMsg = 'Failed to load products';
    return of([]);  // return empty array as fallback
  })
);

// retry + exponential backoff
this.http.get('/api/data').pipe(
  retry({ count: 3, delay: (err, retryCount) =>
    timer(Math.pow(2, retryCount) * 1000) // 1s, 2s, 4s
  })
);

// finalize — always runs (like finally)
this.productService.getAll().pipe(
  tap(() => this.loading = true),
  finalize(() => this.loading = false)  // runs on complete OR error
);
```

**Step 3: Story** — Cards for each operator with: analogy, when to use, common mistakes. Special card: "switchMap vs mergeMap vs concatMap vs exhaustMap" decision tree.

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 12 — RxJS all operators with e-commerce examples"
```

---

## Task 14: Scene 13 — Routing Complete System

**Step 1: Map** — URL bar -> Routes config -> Router Outlet, with branches for params, query params, nested routes, named outlets

**Step 2: Code** — Tabs: `app-routing.module.ts`, `product-routing.module.ts` (nested), `app.component.html` (outlets), navigation examples

```typescript
// app-routing.module.ts
const routes: Routes = [
  { path: '', redirectTo: 'products', pathMatch: 'full' },
  { path: 'products', loadChildren: () => import('./features/products/product.module').then(m => m.ProductModule) },
  { path: 'cart', loadComponent: () => import('./features/cart/cart.component').then(c => c.CartComponent) },
  { path: 'login', component: LoginComponent },
  { path: '**', component: NotFoundComponent }
];

// product-routing.module.ts — nested routes
const routes: Routes = [
  { path: '', component: ProductListComponent },
  { path: ':id', component: ProductDetailComponent,
    resolve: { product: productResolver } },
];

// Named outlet
<router-outlet></router-outlet>          <!-- primary -->
<router-outlet name="sidebar"></router-outlet>  <!-- named -->
// Route: { path: 'help', outlet: 'sidebar', component: HelpPanelComponent }
// URL: /products(sidebar:help)

// Navigation
<a routerLink="/products" routerLinkActive="active">Products</a>
<a [routerLink]="['/products', product.id]">View</a>

this.router.navigate(['/products', id]);
this.router.navigate(['/products'], { queryParams: { category: 'shoes', sort: 'price' } });
this.router.navigate([], { relativeTo: this.route, queryParams: { page: 2 }, queryParamsHandling: 'merge' });

// Reading params
this.route.paramMap.subscribe(p => this.id = +p.get('id')!);
this.route.snapshot.paramMap.get('id');  // one-time
this.route.queryParamMap.subscribe(q => this.filter = q.get('category'));
```

**Step 3: Story** — Cards for each routing concept

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 13 — routing complete system"
```

---

## Task 15: Scene 14 — Route Guards & Resolvers

**Step 1: Map** — Route path with guard checkpoints: canMatch -> canLoad -> canActivate -> Resolve -> Component -> canDeactivate

**Step 2: Code** — Tabs: `auth.guard.ts`, `admin.guard.ts`, `unsaved-changes.guard.ts`, `product.resolver.ts`

```typescript
// Functional guard (v15+)
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) return true;
  router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
  return false;
};

// canDeactivate — unsaved changes
export const unsavedChangesGuard: CanDeactivateFn<{ hasUnsavedChanges: () => boolean }> =
  (component) => {
    if (component.hasUnsavedChanges()) {
      return confirm('You have unsaved changes. Leave anyway?');
    }
    return true;
  };

// canLoad — prevent lazy module download
export const adminLoadGuard: CanLoadFn = () => {
  return inject(AuthService).hasRole('admin');
};

// Resolver — pre-fetch data
export const productResolver: ResolveFn<Product> = (route) => {
  const id = Number(route.paramMap.get('id'));
  return inject(ProductService).getById(id).pipe(
    catchError(() => {
      inject(Router).navigate(['/products']);
      return EMPTY;
    })
  );
};

// Route config using all guards
{
  path: 'admin',
  loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
  canLoad: [adminLoadGuard],
  canActivate: [authGuard],
  canMatch: [() => inject(AuthService).hasRole('admin')],
}
{
  path: 'checkout',
  component: CheckoutComponent,
  canActivate: [authGuard],
  canDeactivate: [unsavedChangesGuard],
  resolve: { cart: cartResolver }
}
```

**Step 3: Story** — Cards for each guard type with analogy (bouncer, luggage check, etc.), resolver as pre-fetcher, functional vs class-based

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 14 — route guards and resolvers"
```

---

## Task 16: Scene 15 — HTTP & Interceptors

**Step 1: Map** — Request flow: Component -> Service -> Interceptor Chain (Auth -> Loading -> Cache -> Error) -> HttpClient -> API -> Response flows back through chain

**Step 2: Code** — Tabs: `product.service.ts` (all HTTP methods), `auth.interceptor.ts`, `error.interceptor.ts`, `loading.interceptor.ts`, `cache.interceptor.ts`

```typescript
// HTTP methods
@Injectable({ providedIn: 'root' })
export class ProductService {
  private apiUrl = 'https://api.shop.com/products';

  constructor(private http: HttpClient) {}

  getAll(params?: { category?: string; sort?: string }): Observable<Product[]> {
    let httpParams = new HttpParams();
    if (params?.category) httpParams = httpParams.set('category', params.category);
    if (params?.sort) httpParams = httpParams.set('sort', params.sort);
    return this.http.get<Product[]>(this.apiUrl, { params: httpParams });
  }

  getById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  create(product: Omit<Product, 'id'>): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product);
  }

  update(id: number, changes: Partial<Product>): Observable<Product> {
    return this.http.patch<Product>(`${this.apiUrl}/${id}`, changes);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

// Auth Interceptor (class-based)
@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.auth.getToken();
    if (token) {
      req = req.clone({
        setHeaders: { Authorization: `Bearer ${token}` }
      });
    }
    return next.handle(req);
  }
}

// Functional interceptor (v15+)
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(AuthService).getToken();
  if (token) {
    req = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
  }
  return next(req);
};

// Error interceptor
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        inject(AuthService).logout();
        inject(Router).navigate(['/login']);
      }
      if (error.status === 0) {
        inject(NotificationService).show('Network error — check your connection');
      }
      return throwError(() => error);
    })
  );
};

// Loading interceptor
export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loading = inject(LoadingService);
  loading.show();
  return next(req).pipe(finalize(() => loading.hide()));
};

// Registration (class-based)
providers: [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
]

// Registration (functional, v15+)
provideHttpClient(withInterceptors([authInterceptor, errorInterceptor, loadingInterceptor]))
```

**Step 3: Story** — Cards for HTTP methods, interceptor chain (assembly line analogy), each interceptor's purpose, class vs functional, ordering matters

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 15 — HTTP and interceptors"
```

---

## Task 17: Scene 16 — Forms (Template-Driven vs Reactive)

**Step 1: Map** — Split: Template-Driven side (ngModel, ngForm) vs Reactive side (FormBuilder, FormGroup, FormControl, FormArray)

**Step 2: Code** — Tabs: `login.component.html` (template-driven), `checkout.component.ts` (reactive with FormBuilder), `validators.ts` (custom), `async-validator.ts`

```typescript
// Template-driven
<form #loginForm="ngForm" (ngSubmit)="onLogin(loginForm)">
  <input name="email" ngModel required email #email="ngModel">
  <div *ngIf="email.invalid && email.touched">
    <span *ngIf="email.errors?.['required']">Email is required</span>
    <span *ngIf="email.errors?.['email']">Invalid email format</span>
  </div>
  <input name="password" ngModel required minlength="8" type="password">
  <button [disabled]="loginForm.invalid">Login</button>
</form>

// Reactive — checkout form
export class CheckoutComponent implements OnInit {
  checkoutForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.checkoutForm = this.fb.group({
      personalInfo: this.fb.group({
        name: ['', [Validators.required, Validators.minLength(2)]],
        email: ['', [Validators.required, Validators.email]],
        phone: ['', [Validators.pattern(/^\+?[\d\s-]{10,}$/)]],
      }),
      shippingAddress: this.fb.group({
        street: ['', Validators.required],
        city: ['', Validators.required],
        zip: ['', [Validators.required, Validators.pattern(/^\d{5}$/)]],
      }),
      items: this.fb.array([]),   // dynamic FormArray
      promoCode: ['', [], [this.promoCodeValidator()]],  // async validator
    }, {
      validators: [this.crossFieldValidator]  // group-level
    });
  }

  get items() { return this.checkoutForm.get('items') as FormArray; }

  addItem(product: Product) {
    this.items.push(this.fb.group({
      productId: [product.id],
      productName: [product.name],
      quantity: [1, [Validators.required, Validators.min(1), Validators.max(product.stock)]],
    }));
  }

  removeItem(index: number) { this.items.removeAt(index); }

  // Custom sync validator
  crossFieldValidator(group: FormGroup): ValidationErrors | null {
    const name = group.get('personalInfo.name')?.value;
    const email = group.get('personalInfo.email')?.value;
    if (email && !email.includes('@')) return { invalidEmail: true };
    return null;
  }

  // Custom async validator (check promo code)
  promoCodeValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value) return of(null);
      return this.promoService.validate(control.value).pipe(
        map(valid => valid ? null : { invalidPromo: true }),
        catchError(() => of({ promoCheckFailed: true }))
      );
    };
  }
}
```

**Step 3: Story** — Cards for: template-driven vs reactive comparison table, FormBuilder, validators, async validators, cross-field, FormArray, when to use which

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 16 — forms template-driven vs reactive"
```

---

## Task 18: Scene 17 — Directives (Structural & Attribute)

**Step 1: Map** — Boxes: *ngIf, *ngFor, *ngSwitch, @if, @for, @switch, custom structural (*appIfRole), custom attribute (appHighlight, appDebounceClick), Renderer2

**Step 2: Code** — Tabs: built-in usage, new control flow (v17+), `if-role.directive.ts`, `highlight.directive.ts`, `debounce-click.directive.ts`

```typescript
// What * actually does — microsyntax desugar
*ngIf="isVisible"
// becomes:
<ng-template [ngIf]="isVisible"><div>...</div></ng-template>

// New control flow (v17+)
@if (products.length > 0) {
  @for (product of products; track product.id) {
    <app-product-card [product]="product" />
  } @empty {
    <p>No products found</p>
  }
} @else {
  <p>Loading...</p>
}

@switch (order.status) {
  @case ('pending') { <span class="badge warning">Pending</span> }
  @case ('shipped') { <span class="badge info">Shipped</span> }
  @case ('delivered') { <span class="badge success">Delivered</span> }
  @default { <span class="badge">Unknown</span> }
}

// Custom structural directive — *appIfRole
@Directive({ selector: '[appIfRole]' })
export class IfRoleDirective implements OnInit, OnDestroy {
  @Input('appIfRole') requiredRole!: string;

  private hasView = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private authService: AuthService
  ) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe(user => {
      if (user?.role === this.requiredRole && !this.hasView) {
        this.viewContainer.createEmbeddedView(this.templateRef);
        this.hasView = true;
      } else if (user?.role !== this.requiredRole && this.hasView) {
        this.viewContainer.clear();
        this.hasView = false;
      }
    });
  }
}
// Usage: <button *appIfRole="'admin'">Delete All</button>

// Custom attribute directive — appHighlight
@Directive({ selector: '[appHighlight]' })
export class HighlightDirective {
  @Input('appHighlight') color = 'yellow';

  constructor(private el: ElementRef, private renderer: Renderer2) {}

  @HostListener('mouseenter') onMouseEnter() {
    this.renderer.setStyle(this.el.nativeElement, 'backgroundColor', this.color);
  }
  @HostListener('mouseleave') onMouseLeave() {
    this.renderer.removeStyle(this.el.nativeElement, 'backgroundColor');
  }
}
// Usage: <p appHighlight="lightblue">Hover me</p>

// Custom attribute directive — appDebounceClick
@Directive({ selector: '[appDebounceClick]' })
export class DebounceClickDirective implements OnInit, OnDestroy {
  @Input() debounceTime = 500;
  @Output() debounceClick = new EventEmitter<MouseEvent>();
  private clicks$ = new Subject<MouseEvent>();
  private destroy$ = new Subject<void>();

  ngOnInit() {
    this.clicks$.pipe(
      debounceTime(this.debounceTime),
      takeUntil(this.destroy$)
    ).subscribe(e => this.debounceClick.emit(e));
  }

  @HostListener('click', ['$event'])
  onClick(event: MouseEvent) { this.clicks$.next(event); }

  ngOnDestroy() { this.destroy$.next(); this.destroy$.complete(); }
}
// Usage: <button (debounceClick)="save()" [debounceTime]="300">Save</button>
```

**Step 3: Story** — Cards for: structural vs attribute, microsyntax, new control flow, Renderer2 vs direct DOM, each custom directive explained

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 17 — directives structural and attribute"
```

---

## Task 19: Scene 18 — Pipes (Built-in & Custom)

**Step 1: Map** — Built-in pipes listed, pure vs impure, custom pipes

**Step 2: Code** — Tabs: built-in usage, `discount.pipe.ts`, `truncate.pipe.ts`, `filter.pipe.ts` (impure warning)

```typescript
// Built-in pipes
{{ product.price | currency:'USD':'symbol':'1.2-2' }}    // $29.99
{{ order.date | date:'medium' }}                          // Mar 6, 2026, 2:30:15 PM
{{ order.date | date:'shortDate' }}                       // 3/6/26
{{ 0.85 | percent:'1.0-0' }}                              // 85%
{{ product | json }}                                      // debug output
{{ product.name | titlecase }}                            // Running Shoes
{{ product.name | uppercase }}                            // RUNNING SHOES
{{ products | slice:0:5 }}                                // first 5
{{ product.tags | keyvalue }}                             // object to array
{{ data$ | async }}                                       // auto subscribe/unsub

// Chaining
{{ product.price | discount:0.2 | currency:'USD' }}       // apply 20% off, then format

// Custom PURE pipe — DiscountPipe
@Pipe({ name: 'discount', pure: true })
export class DiscountPipe implements PipeTransform {
  transform(price: number, discountPercent: number): number {
    return price * (1 - discountPercent);
  }
}
// Usage: {{ 100 | discount:0.2 }}  → 80

// Custom PURE pipe — TruncatePipe
@Pipe({ name: 'truncate', pure: true })
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit = 50, trail = '...'): string {
    return value.length > limit ? value.substring(0, limit) + trail : value;
  }
}
// Usage: {{ product.description | truncate:80:'…' }}

// Custom IMPURE pipe — FilterPipe (ANTI-PATTERN!)
@Pipe({ name: 'filter', pure: false })  // ← runs EVERY change detection cycle!
export class FilterPipe implements PipeTransform {
  transform(items: any[], field: string, value: any): any[] {
    return items.filter(item => item[field] === value);
  }
}
// WHY THIS IS BAD: runs on every CD cycle, O(n) each time
// BETTER: filter in the component using a computed/pipe or RxJS
```

**Step 3: Story** — Cards for: each built-in pipe, async pipe (auto-unsub), pure vs impure (with performance impact), custom pipe pattern, why filter pipe is an anti-pattern

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 18 — pipes built-in and custom"
```

---

## Task 20: Scene 19 — Change Detection & Zone.js

**Step 1: Map** — Zone.js monkey-patching diagram -> Angular notified -> CD tree check (top-down). OnPush branch skipped. Signals bypass entirely.

**Step 2: Code** — Tabs: Zone.js explanation, Default vs OnPush, markForCheck/detectChanges, runOutsideAngular, signals zoneless

```typescript
// What Zone.js monkey-patches:
// setTimeout, setInterval, addEventListener, Promise.then,
// XHR.send, fetch, requestAnimationFrame, MutationObserver
// → After any of these complete, Zone.js tells Angular: "something happened!"
// → Angular runs change detection on the ENTIRE component tree (Default strategy)

// Default — checks everything
@Component({
  changeDetection: ChangeDetectionStrategy.Default  // this is the default
})
// Angular checks this component AND all children on every async event

// OnPush — only checks when:
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductCardComponent {
  @Input() product!: Product;  // 1. @Input reference changes
  // 2. Event fires IN this component (click, keydown etc.)
  // 3. async pipe emits new value
  // 4. markForCheck() is called manually
}

// markForCheck vs detectChanges
constructor(private cd: ChangeDetectorRef) {}

// markForCheck — marks component + ancestors for check in NEXT CD cycle
this.cd.markForCheck();  // "check me next time CD runs"

// detectChanges — triggers CD immediately on this component subtree
this.cd.detectChanges();  // "check me RIGHT NOW"

// detach — remove from CD tree entirely (manual control)
this.cd.detach();
// Now you must call detectChanges() manually to update the view

// NgZone.runOutsideAngular — skip CD for performance
constructor(private ngZone: NgZone) {}

ngAfterViewInit() {
  // This interval won't trigger change detection
  this.ngZone.runOutsideAngular(() => {
    setInterval(() => {
      this.animationFrame++;  // no CD triggered
    }, 16);
  });

  // When you DO need to update the view:
  this.ngZone.run(() => {
    this.status = 'complete';  // triggers CD
  });
}

// Signals — zoneless (v18+ experimental)
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush  // combine with signals
})
export class DashboardComponent {
  count = signal(0);
  double = computed(() => this.count() * 2);
  // Angular knows EXACTLY which template bindings depend on which signals
  // No zone.js needed, no full-tree check, just update the specific DOM node
}

// provideExperimentalZonelessChangeDetection() in app.config.ts
```

**Step 3: Story** — Cards for: what Zone.js does (with list of patched APIs), Default strategy (full tree), OnPush (4 triggers), markForCheck vs detectChanges, runOutsideAngular, detach, signals and zoneless future

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 19 — change detection and zone.js"
```

---

## Task 21: Scene 20 — TypeScript Patterns

**Step 1: Map** — Boxes: Interfaces, Types, Generics, Enums, Utility Types, Type Guards, Discriminated Unions, Decorators, Strict Mode

**Step 2: Code** — Tabs: interfaces & types, generics, enums & unions, utility types, advanced types, decorators

```typescript
// Interface vs Type vs Class
interface Product {                    // INTERFACE — shape contract
  id: number;
  name: string;
  price: number;
  description?: string;               // optional
  readonly createdAt: Date;            // immutable
}
interface DigitalProduct extends Product {  // extends
  downloadUrl: string;
}

type ProductStatus = 'active' | 'draft' | 'archived';  // TYPE — union
type ProductMap = Record<string, Product>;              // TYPE — utility
// When to use: Interface for objects/classes, Type for unions/intersections/mapped

// Generics
interface ApiResponse<T> {
  data: T;
  status: number;
  message: string;
  timestamp: Date;
}
function first<T>(arr: T[]): T | undefined { return arr[0]; }
function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
  return obj[key];
}

// Enums
enum OrderStatus {
  Pending = 'PENDING',
  Shipped = 'SHIPPED',
  Delivered = 'DELIVERED',
  Cancelled = 'CANCELLED',
}
// OR — lightweight union alternative (preferred by many):
type OrderStatus = 'pending' | 'shipped' | 'delivered' | 'cancelled';
// const enum — inlined at compile time, no runtime object
const enum Direction { Up, Down, Left, Right }

// Utility Types
type CreateProduct = Omit<Product, 'id' | 'createdAt'>;        // for POST body
type UpdateProduct = Partial<Pick<Product, 'name' | 'price'>>;  // for PATCH body
type ProductSummary = Pick<Product, 'id' | 'name' | 'price'>;  // for list view
type ProductCache = Record<number, Product>;                     // id → product
type NonNullUser = NonNullable<User | null | undefined>;         // strip null

// Type Guards
function isProduct(item: unknown): item is Product {
  return typeof item === 'object' && item !== null && 'id' in item && 'price' in item;
}
// Usage: if (isProduct(data)) { console.log(data.price); }

// Discriminated Unions
type LoadingState =
  | { status: 'idle' }
  | { status: 'loading' }
  | { status: 'success'; data: Product[] }
  | { status: 'error'; error: string };

function render(state: LoadingState) {
  switch (state.status) {
    case 'loading': return 'Loading...';
    case 'success': return state.data;   // TypeScript knows data exists here!
    case 'error':   return state.error;  // TypeScript knows error exists here!
  }
}

// Mapped & Conditional types
type Readonly<T> = { readonly [K in keyof T]: T[K] };
type Nullable<T> = { [K in keyof T]: T[K] | null };
type Unwrap<T> = T extends Promise<infer U> ? U : T;
type EventName = `on${Capitalize<'click' | 'hover' | 'focus'>}`;
// → 'onClick' | 'onHover' | 'onFocus'

// Strict mode (tsconfig.json)
"strict": true  // enables ALL of these:
  "strictNullChecks": true,
  "noImplicitAny": true,
  "strictPropertyInitialization": true,
  "strictBindCallApply": true,
  "noImplicitThis": true,

// Barrel exports
// models/index.ts
export { Product } from './product.model';
export { CartItem } from './cart-item.model';
export { User } from './user.model';
// Usage: import { Product, User } from '../models';
```

**Step 3: Story** — Cards for each TypeScript concept with real Angular use cases

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 20 — TypeScript patterns"
```

---

## Task 22: Scene 21 — Performance & Optimization Cheat Sheet

**Step 1: Map** — Checklist-style boxes grouped: Build-time, Runtime, Template, Network, Advanced

**Step 2: Code** — Single comprehensive cheat sheet with every optimization technique:

```typescript
// === BUILD-TIME ===
// 1. Lazy load feature modules
loadChildren: () => import('./feature/feature.module').then(m => m.FeatureModule)

// 2. Tree shaking — providedIn: 'root' is tree-shakable
@Injectable({ providedIn: 'root' })  // removed from bundle if unused

// 3. Production build
// ng build --configuration=production
// Enables: AOT, tree shaking, minification, dead code elimination

// 4. Bundle analysis
// npx source-map-explorer dist/**/*.js

// === RUNTIME ===
// 5. OnPush + immutable data
changeDetection: ChangeDetectionStrategy.OnPush
this.items = [...this.items, newItem];  // new reference triggers CD

// 6. trackBy in *ngFor / track in @for
<li *ngFor="let p of products; trackBy: trackById">
@for (p of products; track p.id) { }
trackById(index: number, product: Product) { return product.id; }

// 7. Pure pipes over methods in templates
// BAD: {{ getTotal() }}        ← runs every CD cycle
// GOOD: {{ total$ | async }}   ← only emits when value changes
// GOOD: {{ price | discount:0.2 }}  ← pure pipe, cached

// 8. runOutsideAngular for animations
this.ngZone.runOutsideAngular(() => { requestAnimationFrame(...); });

// 9. detach from CD tree for static content
this.cd.detach();

// === TEMPLATE ===
// 10. @defer for below-fold content
@defer (on viewport) { <app-reviews /> }

// 11. Virtual scrolling for large lists
<cdk-virtual-scroll-viewport itemSize="72" class="list">
  <div *cdkVirtualFor="let product of products">
    {{ product.name }}
  </div>
</cdk-virtual-scroll-viewport>

// === NETWORK ===
// 12. shareReplay to cache HTTP
getConfig(): Observable<Config> {
  if (!this.config$) {
    this.config$ = this.http.get<Config>('/api/config').pipe(
      shareReplay(1)
    );
  }
  return this.config$;
}

// 13. NgOptimizedImage
<img [ngSrc]="product.imageUrl" width="400" height="300" priority />

// 14. Preloading strategy
RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })

// === ADVANCED ===
// 15. Web Workers for heavy computation
if (typeof Worker !== 'undefined') {
  const worker = new Worker(new URL('./sort.worker', import.meta.url));
  worker.postMessage(largeDataset);
  worker.onmessage = ({ data }) => { this.sorted = data; };
}

// 16. SSR with Angular Universal
// Faster first paint, better SEO
// ng add @angular/ssr

// 17. Avoid memory leaks
// - takeUntil(destroy$) or takeUntilDestroyed()
// - async pipe (auto-unsub)
// - Remove event listeners in ngOnDestroy
// - Clear intervals/timeouts
```

**Step 3: Story** — Cards grouped by category, each with why it matters, when to use, and impact level (high/medium/low)

**Step 4: Update MAP + verify + commit**
```bash
git commit -m "feat: add scene 21 — performance and optimization cheat sheet"
```

---

## Task 23: Final Polish & Complete MAP

**Step 1:** Ensure ALL concept keys from all 22 scenes are registered in the JavaScript `MAP` object.

**Step 2:** Test all scene navigation (buttons + arrow keys + keyboard Escape).

**Step 3:** Test all clickable highlights open the correct card in every scene.

**Step 4:** Test file tab switching in all scenes.

**Step 5:** Final commit.
```bash
git commit -m "feat: complete angular e-commerce anatomy — all 22 scenes"
```
