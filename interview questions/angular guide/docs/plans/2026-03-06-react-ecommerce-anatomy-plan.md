# React E-Commerce Anatomy Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Build a single interactive HTML file with 22 scenes covering every React concept needed for a senior frontend interview, using the ShopZone e-commerce dashboard as the example app.

**Architecture:** Single self-contained HTML file following the same 3-column layout (map | code | explanation) as `angular_ecommerce_anatomy.html`. Each scene has its own `<div class="scene">` containing 3 `.col` divs. JavaScript engine handles scene navigation, tab switching, concept highlighting, and keyboard shortcuts. All code is syntax-highlighted `<pre>` blocks with clickable `.hl` spans.

**Tech Stack:** HTML, CSS, vanilla JavaScript, Google Fonts (JetBrains Mono + Sora)

**Reference file:** `/Users/fahimanik/code/dsa-algorithms/interview questions/angular guide/angular_ecommerce_anatomy.html` — copy its exact CSS structure, JS engine pattern, and interaction model.

---

## Task 0: Scaffold — CSS, Topbar, JS Engine

**Files:**
- Create: `react_ecommerce_anatomy.html`

**Step 1: Create the HTML skeleton**

Copy the CSS from `angular_ecommerce_anatomy.html` (the `<style>` block) verbatim, but update:
- Title: `React E-Commerce — Full Anatomy`
- Brand text: `React <span>ShopZone</span> · E-Commerce Anatomy`
- Scene nav buttons: all 22 scenes (0-21) with labels:
  - 0: Overview, 1: JSX, 2: Components, 3: State, 4: useEffect
  - 5: Hooks, 6: Custom Hooks, 7: Context, 8: Redux, 9: Redux Async
  - 10: React Query, 11: Router, 12: Forms, 13: Patterns, 14: Errors
  - 15: Refs/DOM, 16: Performance, 17: Concurrent, 18: SSR/RSC
  - 19: TypeScript, 20: Testing, 21: Perf Cheat
- Update `const TOTAL = 22;`
- Add CSS color variables for React concepts:
  - `--c-hook:#61dafb` (React blue for hooks)
  - `--c-redux:#764abc` (Redux purple)
  - `--c-query:#ff4154` (React Query red)
  - `--c-router:#f44250` (React Router red)
  - `--c-ts:#3178c6` (TypeScript blue)
  - `--c-perf:#ff6b35` (Performance orange)
  - `--c-rsc:#58c4dc` (Server components cyan)
  - `--c-test:#15c213` (Testing green)

**Step 2: Copy the JavaScript engine**

Copy the JS from `angular_ecommerce_anatomy.html` — the `goScene`, `nextScene`, `prevScene`, `switchTab`, `pick`, `closeAll`, keyboard handler functions. Update TOTAL to 22. The MAP object will be populated as scenes are built.

**Step 3: Add empty scene divs**

Create 22 empty `<div class="scene" id="scene-N">` blocks, each with the 3-column structure:
```html
<div class="scene" id="scene-N">
  <div class="col"><div class="col-inner">
    <div class="col-hdr" style="color:var(--c-comp)"><div class="col-hdr-dot"></div>Map</div>
    <!-- map content will go here -->
  </div></div>
  <div class="col"><div class="col-inner">
    <div class="col-hdr" style="color:var(--c-svc)"><div class="col-hdr-dot"></div>Code</div>
    <!-- code content will go here -->
  </div></div>
  <div class="col"><div class="col-inner">
    <div class="col-hdr" style="color:var(--c-tmpl)"><div class="col-hdr-dot"></div>The Story</div>
    <div class="story-hint">Click any building on the map or highlighted word in the code to see the explanation.</div>
  </div></div>
</div>
```

Scene 0 gets `class="scene active"`.

**Step 4: Verify in browser**

Open file in browser. Topbar should render with all 22 scene buttons. Arrow keys should navigate. All scenes blank but functional.

---

## Task 1: Scene 0 — Architecture Overview

**Step 1: Map** — Full ShopZone React app architecture: Component tree (App → Header, Routes → ProductList/Cart/Checkout/Login/Admin), Redux Store (3 slices), React Query (QueryClient), React Router layout. Show src/ file tree.

**Step 2: Code** — Tabs: `App.tsx`, `store/index.ts`, `main.tsx`

```typescript
// App.tsx
function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Provider store={store}>
        <BrowserRouter>
          <Header />
          <Routes>
            <Route path="/" element={<Layout />}>
              <Route index element={<ProductList />} />
              <Route path="products/:id" element={<ProductDetail />} />
              <Route path="cart" element={<Cart />} />
              <Route path="checkout" element={<ProtectedRoute><Checkout /></ProtectedRoute>} />
              <Route path="login" element={<Login />} />
              <Route path="admin" element={<ProtectedRoute role="admin"><AdminDashboard /></ProtectedRoute>} />
              <Route path="*" element={<NotFound />} />
            </Route>
          </Routes>
          <ToastContainer />
        </BrowserRouter>
      </Provider>
    </QueryClientProvider>
  );
}

// store/index.ts
export const store = configureStore({
  reducer: {
    products: productReducer,
    cart: cartReducer,
    auth: authReducer,
  },
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

// main.tsx
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
createRoot(document.getElementById('root')!).render(
  <StrictMode><App /></StrictMode>
);
```

**Step 3: Story** — Cards for: component tree, Redux store shape, React Query role, file structure, how data flows

**Step 4: Update MAP + verify**

---

## Task 2: Scene 1 — JSX & Rendering

**Step 1: Map** — JSX → React.createElement, expressions {}, conditionals (ternary, &&), .map() + keys, fragments, JSX vs HTML

**Step 2: Code** — Tabs: `jsx-basics.tsx`, `conditional.tsx`, `lists.tsx`

```tsx
// jsx-basics.tsx
// JSX compiles to React.createElement
<div className="card">Hello</div>
// becomes:
React.createElement('div', { className: 'card' }, 'Hello')

// Expressions in JSX
<h1>{product.name}</h1>
<p>Price: ${product.price.toFixed(2)}</p>
<span className={isActive ? 'active' : 'inactive'}>
  {user.name}
</span>

// JSX vs HTML differences
className    // not class
htmlFor      // not for
onClick      // camelCase events
style={{ color: 'red', fontSize: 16 }}  // object, not string
tabIndex     // not tabindex

// Fragments — no extra DOM node
<>
  <h1>Title</h1>
  <p>Content</p>
</>
// or <React.Fragment key={id}>...</React.Fragment>

// conditional.tsx
// Ternary
{isLoggedIn ? <Dashboard /> : <Login />}

// && short-circuit (careful with 0!)
{items.length > 0 && <ItemList items={items} />}
// BAD: {count && <span>{count}</span>}  // renders "0"!
// GOOD: {count > 0 && <span>{count}</span>}

// Early return
function ProductCard({ product }: Props) {
  if (!product) return null;
  return <div>{product.name}</div>;
}

// lists.tsx
// List rendering with keys
{products.map(product => (
  <ProductCard key={product.id} product={product} />
))}

// WHY keys matter:
// - React uses keys to match old/new elements
// - Without keys: entire list re-renders
// - With keys: only changed items update
// - NEVER use index as key if list can reorder!
// key={index} → BAD for dynamic lists
// key={item.id} → GOOD always
```

**Step 3: Story** — Cards for: JSX compilation, expressions, conditional patterns, keys explained, fragments, JSX vs HTML

---

## Task 3: Scene 2 — Components & Props

**Step 1: Map** — Component tree: parent → child, props flow down, children prop, composition

**Step 2: Code** — Tabs: `ProductCard.tsx`, `props-patterns.tsx`, `composition.tsx`

```tsx
// ProductCard.tsx
interface ProductCardProps {
  product: Product;
  onAddToCart: (id: number) => void;
  featured?: boolean;  // optional prop
}

function ProductCard({ product, onAddToCart, featured = false }: ProductCardProps) {
  return (
    <div className={`card ${featured ? 'featured' : ''}`}>
      <img src={product.imageUrl} alt={product.name} />
      <h3>{product.name}</h3>
      <p>${product.price.toFixed(2)}</p>
      <button onClick={() => onAddToCart(product.id)}>
        Add to Cart
      </button>
    </div>
  );
}

// props-patterns.tsx
// Children prop
function Card({ children, title }: { children: ReactNode; title: string }) {
  return (
    <div className="card">
      <h2>{title}</h2>
      <div className="card-body">{children}</div>
    </div>
  );
}
// Usage: <Card title="Featured"><ProductCard .../></Card>

// Prop spreading (use carefully)
function Button({ variant, ...rest }: ButtonProps & ComponentPropsWithoutRef<'button'>) {
  return <button className={`btn btn-${variant}`} {...rest} />;
}

// composition.tsx
// Composition over inheritance (ALWAYS in React)
// BAD: class SpecialButton extends Button { ... }
// GOOD:
function IconButton({ icon, children, ...props }: IconButtonProps) {
  return (
    <Button {...props}>
      <Icon name={icon} />
      {children}
    </Button>
  );
}
```

**Step 3: Story** — Cards for: functional components, props interface, children, defaults, spreading, composition

---

## Task 4: Scene 3 — State (useState & useReducer)

**Step 1: Map** — State flow: useState (simple) vs useReducer (complex), batching, lifting state, immutable updates

**Step 2: Code** — Tabs: `useState-basics.tsx`, `useReducer-cart.tsx`, `immutable.tsx`

```tsx
// useState-basics.tsx
function ProductFilter() {
  const [search, setSearch] = useState('');
  const [category, setCategory] = useState<string | null>(null);
  const [priceRange, setPriceRange] = useState({ min: 0, max: 1000 });

  // Updater function — always use for derived state
  const [count, setCount] = useState(0);
  setCount(prev => prev + 1);  // CORRECT
  setCount(count + 1);          // BUG if called multiple times

  // Object state — must spread
  setPriceRange(prev => ({ ...prev, max: 500 }));
  // NOT: setPriceRange({ max: 500 })  // loses min!

  // React 18: automatic batching
  function handleClick() {
    setSearch('shoes');      // no re-render yet
    setCategory('footwear'); // no re-render yet
    setPriceRange({ min: 0, max: 200 }); // re-renders ONCE
  }
}

// useReducer-cart.tsx
type CartAction =
  | { type: 'ADD_ITEM'; payload: Product }
  | { type: 'REMOVE_ITEM'; payload: number }
  | { type: 'UPDATE_QTY'; payload: { id: number; qty: number } }
  | { type: 'CLEAR' };

function cartReducer(state: CartItem[], action: CartAction): CartItem[] {
  switch (action.type) {
    case 'ADD_ITEM': {
      const exists = state.find(i => i.id === action.payload.id);
      if (exists) return state.map(i =>
        i.id === action.payload.id ? { ...i, qty: i.qty + 1 } : i
      );
      return [...state, { ...action.payload, qty: 1 }];
    }
    case 'REMOVE_ITEM':
      return state.filter(i => i.id !== action.payload);
    case 'UPDATE_QTY':
      return state.map(i =>
        i.id === action.payload.id ? { ...i, qty: action.payload.qty } : i
      );
    case 'CLEAR': return [];
  }
}

function Cart() {
  const [items, dispatch] = useReducer(cartReducer, []);
  return <button onClick={() => dispatch({ type: 'ADD_ITEM', payload: product })}>Add</button>;
}

// immutable.tsx — state update patterns
// Array: add
setItems(prev => [...prev, newItem]);
// Array: remove
setItems(prev => prev.filter(i => i.id !== id));
// Array: update
setItems(prev => prev.map(i => i.id === id ? { ...i, qty: 5 } : i));
// Object: update nested
setUser(prev => ({ ...prev, address: { ...prev.address, city: 'NYC' } }));
```

**Step 3: Story** — Cards for: useState, updater function, batching, useReducer, when to use which, immutable patterns

---

## Task 5: Scene 4 — Lifecycle (useEffect)

**Step 1: Map** — Effect lifecycle: mount → update → cleanup → unmount. Dependency array controls when.

**Step 2: Code** — Tabs: `useEffect-basics.tsx`, `cleanup.tsx`, `pitfalls.tsx`

```tsx
// useEffect-basics.tsx
function ProductDetail({ productId }: { productId: number }) {
  const [product, setProduct] = useState<Product | null>(null);

  // Runs on MOUNT only (empty deps)
  useEffect(() => {
    console.log('Component mounted');
  }, []);

  // Runs when productId CHANGES
  useEffect(() => {
    let cancelled = false;
    async function fetchProduct() {
      const res = await fetch(`/api/products/${productId}`);
      const data = await res.json();
      if (!cancelled) setProduct(data);
    }
    fetchProduct();
    return () => { cancelled = true; }; // cleanup: cancel stale request
  }, [productId]);

  // Runs EVERY render (no deps array) — rarely needed
  useEffect(() => {
    document.title = `${product?.name ?? 'Shop'} | ShopZone`;
  });
}

// cleanup.tsx
function useWebSocket(url: string) {
  useEffect(() => {
    const ws = new WebSocket(url);
    ws.onmessage = (e) => handleMessage(e.data);
    return () => ws.close();  // cleanup on unmount or url change
  }, [url]);
}

function useEventListener(event: string, handler: (e: Event) => void) {
  useEffect(() => {
    window.addEventListener(event, handler);
    return () => window.removeEventListener(event, handler);
  }, [event, handler]);
}

// pitfalls.tsx
// PITFALL 1: Missing dependency
useEffect(() => {
  fetchData(userId);  // userId not in deps = stale closure
}, []);  // ESLint will warn!

// PITFALL 2: Infinite loop
useEffect(() => {
  setCount(count + 1);  // triggers re-render → effect runs again
}, [count]);  // INFINITE LOOP!

// PITFALL 3: Object/array dependency
useEffect(() => {
  // This runs EVERY render because {} !== {}
}, [{ id: 1 }]);  // object created each render

// React 18 Strict Mode: effects run TWICE in dev
// Mount → cleanup → mount (tests cleanup works)
```

**Step 3: Story** — Cards for: effect lifecycle, dependency array, cleanup, race conditions, pitfalls, strict mode

---

## Task 6: Scene 5 — Hooks Deep Dive

**Step 1: Map** — All built-in hooks: useRef, useMemo, useCallback, useId, useImperativeHandle, Rules of Hooks

**Step 2: Code** — Tabs: `useRef.tsx`, `useMemo-useCallback.tsx`, `other-hooks.tsx`, `rules.tsx`

```tsx
// useRef.tsx
function SearchBar() {
  const inputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    inputRef.current?.focus();  // focus on mount
  }, []);

  return <input ref={inputRef} placeholder="Search..." />;
}

// useRef as mutable container (no re-render!)
function Timer() {
  const intervalRef = useRef<number | null>(null);
  const [seconds, setSeconds] = useState(0);

  const start = () => {
    intervalRef.current = setInterval(() =>
      setSeconds(s => s + 1), 1000);
  };
  const stop = () => {
    if (intervalRef.current) clearInterval(intervalRef.current);
  };
  return <button onClick={start}>Start</button>;
}

// useMemo-useCallback.tsx
function ProductList({ products, filter }: Props) {
  // useMemo — cache expensive computation
  const filtered = useMemo(() =>
    products.filter(p => p.category === filter)
      .sort((a, b) => a.price - b.price),
    [products, filter]  // only recompute when these change
  );

  // useCallback — stable function reference
  const handleAddToCart = useCallback((id: number) => {
    dispatch(addItem(id));
  }, [dispatch]);  // same function ref between renders

  return filtered.map(p =>
    <ProductCard
      key={p.id}
      product={p}
      onAdd={handleAddToCart}  // stable ref = child won't re-render
    />
  );
}

// other-hooks.tsx
// useId — SSR-safe unique IDs
function FormField({ label }: { label: string }) {
  const id = useId();  // ":r1:", ":r2:", etc.
  return (
    <>
      <label htmlFor={id}>{label}</label>
      <input id={id} />
    </>
  );
}

// useImperativeHandle — expose methods to parent
const FancyInput = forwardRef<{ focus: () => void; clear: () => void }, Props>(
  (props, ref) => {
    const inputRef = useRef<HTMLInputElement>(null);
    useImperativeHandle(ref, () => ({
      focus: () => inputRef.current?.focus(),
      clear: () => { if (inputRef.current) inputRef.current.value = ''; },
    }));
    return <input ref={inputRef} {...props} />;
  }
);

// rules.tsx
// Rules of Hooks:
// 1. Only call at TOP LEVEL (not in if/for/nested functions)
// 2. Only call in React FUNCTIONS (components or custom hooks)

// BAD:
if (isLoggedIn) {
  useEffect(() => { ... });  // BREAKS HOOK ORDER!
}

// GOOD:
useEffect(() => {
  if (isLoggedIn) { ... }  // condition INSIDE the hook
}, [isLoggedIn]);
```

**Step 3: Story** — Cards for: useRef (DOM + mutable), useMemo, useCallback, useId, useImperativeHandle, Rules of Hooks

---

## Task 7: Scene 6 — Custom Hooks

**Step 1: Map** — Custom hooks: useCart, useDebounce, useFetch, useLocalStorage, useMediaQuery, useOnClickOutside

**Step 2: Code** — Tabs: `useDebounce.ts`, `useLocalStorage.ts`, `useFetch.ts`, `useOnClickOutside.ts`

```tsx
// useDebounce.ts
function useDebounce<T>(value: T, delay: number): T {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    const timer = setTimeout(() => setDebouncedValue(value), delay);
    return () => clearTimeout(timer);
  }, [value, delay]);

  return debouncedValue;
}
// Usage:
const [search, setSearch] = useState('');
const debouncedSearch = useDebounce(search, 300);
// Use debouncedSearch in API call

// useLocalStorage.ts
function useLocalStorage<T>(key: string, initialValue: T): [T, (value: T) => void] {
  const [stored, setStored] = useState<T>(() => {
    try {
      const item = localStorage.getItem(key);
      return item ? JSON.parse(item) : initialValue;
    } catch { return initialValue; }
  });

  const setValue = (value: T) => {
    setStored(value);
    localStorage.setItem(key, JSON.stringify(value));
  };

  return [stored, setValue];
}
// Usage: const [theme, setTheme] = useLocalStorage('theme', 'dark');

// useFetch.ts
function useFetch<T>(url: string) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    fetch(url)
      .then(res => res.json())
      .then(data => { if (!cancelled) { setData(data); setLoading(false); } })
      .catch(err => { if (!cancelled) { setError(err); setLoading(false); } });
    return () => { cancelled = true; };
  }, [url]);

  return { data, loading, error };
}

// useOnClickOutside.ts
function useOnClickOutside(ref: RefObject<HTMLElement>, handler: () => void) {
  useEffect(() => {
    const listener = (event: MouseEvent | TouchEvent) => {
      if (!ref.current || ref.current.contains(event.target as Node)) return;
      handler();
    };
    document.addEventListener('mousedown', listener);
    document.addEventListener('touchstart', listener);
    return () => {
      document.removeEventListener('mousedown', listener);
      document.removeEventListener('touchstart', listener);
    };
  }, [ref, handler]);
}
// Usage: useOnClickOutside(dropdownRef, () => setOpen(false));

// useMediaQuery
function useMediaQuery(query: string): boolean {
  const [matches, setMatches] = useState(() =>
    window.matchMedia(query).matches
  );
  useEffect(() => {
    const mql = window.matchMedia(query);
    const handler = (e: MediaQueryListEvent) => setMatches(e.matches);
    mql.addEventListener('change', handler);
    return () => mql.removeEventListener('change', handler);
  }, [query]);
  return matches;
}
```

**Step 3: Story** — Cards for: custom hook pattern, useDebounce, useLocalStorage, useFetch, useOnClickOutside, composing hooks, naming conventions

---

## Task 8: Scene 7 — Context API

**Step 1: Map** — Context flow: createContext → Provider → useContext consumer. Show context splitting to avoid re-renders.

**Step 2: Code** — Tabs: `ThemeContext.tsx`, `NotificationContext.tsx`, `context-reducer.tsx`, `performance.tsx`

```tsx
// ThemeContext.tsx
type Theme = 'light' | 'dark';
interface ThemeContextType {
  theme: Theme;
  toggleTheme: () => void;
}

const ThemeContext = createContext<ThemeContextType | null>(null);

function ThemeProvider({ children }: { children: ReactNode }) {
  const [theme, setTheme] = useState<Theme>('dark');
  const toggleTheme = useCallback(() =>
    setTheme(prev => prev === 'dark' ? 'light' : 'dark'), []);

  const value = useMemo(() => ({ theme, toggleTheme }), [theme, toggleTheme]);
  return <ThemeContext.Provider value={value}>{children}</ThemeContext.Provider>;
}

function useTheme() {
  const ctx = useContext(ThemeContext);
  if (!ctx) throw new Error('useTheme must be inside ThemeProvider');
  return ctx;
}

// NotificationContext.tsx — toast notifications
const NotificationContext = createContext<{
  notify: (msg: string, type: 'success' | 'error') => void;
} | null>(null);

function NotificationProvider({ children }: { children: ReactNode }) {
  const [toasts, setToasts] = useState<Toast[]>([]);
  const notify = useCallback((message: string, type: 'success' | 'error') => {
    const id = Date.now();
    setToasts(prev => [...prev, { id, message, type }]);
    setTimeout(() => setToasts(prev => prev.filter(t => t.id !== id)), 3000);
  }, []);
  return (
    <NotificationContext.Provider value={{ notify }}>
      {children}
      <div className="toast-container">
        {toasts.map(t => <Toast key={t.id} {...t} />)}
      </div>
    </NotificationContext.Provider>
  );
}

// context-reducer.tsx — Context + useReducer = mini Redux
const CartContext = createContext<{
  state: CartState;
  dispatch: Dispatch<CartAction>;
} | null>(null);

function CartProvider({ children }: { children: ReactNode }) {
  const [state, dispatch] = useReducer(cartReducer, { items: [], total: 0 });
  return (
    <CartContext.Provider value={{ state, dispatch }}>
      {children}
    </CartContext.Provider>
  );
}

// performance.tsx — avoiding re-renders
// PROBLEM: every consumer re-renders when ANY value changes
// SOLUTION: split contexts
const ThemeValueContext = createContext<Theme>('dark');
const ThemeToggleContext = createContext<() => void>(() => {});
// Components using only toggleTheme won't re-render on theme change
```

**Step 3: Story** — Cards for: createContext, Provider, useContext, custom hook wrapper, context + reducer, performance pitfalls, when to use context vs Redux

---

## Task 9: Scene 8 — Redux Toolkit (Store & Slices)

**Step 1: Map** — Redux flow: Component → dispatch(action) → Reducer → Store → useSelector → Component. Show slices as store divisions.

**Step 2: Code** — Tabs: `store/index.ts`, `store/cartSlice.ts`, `store/authSlice.ts`, `components/Cart.tsx`

```tsx
// store/index.ts
import { configureStore } from '@reduxjs/toolkit';
export const store = configureStore({
  reducer: {
    cart: cartReducer,
    auth: authReducer,
    products: productReducer,
  },
});
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

// Typed hooks (create once, use everywhere)
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
export const useAppDispatch: () => AppDispatch = useDispatch;

// store/cartSlice.ts
interface CartState {
  items: CartItem[];
  total: number;
}
const initialState: CartState = { items: [], total: 0 };

const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    addItem(state, action: PayloadAction<Product>) {
      // Immer lets you "mutate" — it's actually immutable!
      const existing = state.items.find(i => i.id === action.payload.id);
      if (existing) {
        existing.qty += 1;
      } else {
        state.items.push({ ...action.payload, qty: 1 });
      }
      state.total = state.items.reduce((sum, i) => sum + i.price * i.qty, 0);
    },
    removeItem(state, action: PayloadAction<number>) {
      state.items = state.items.filter(i => i.id !== action.payload);
      state.total = state.items.reduce((sum, i) => sum + i.price * i.qty, 0);
    },
    clearCart(state) {
      state.items = [];
      state.total = 0;
    },
  },
});
export const { addItem, removeItem, clearCart } = cartSlice.actions;
export default cartSlice.reducer;

// store/authSlice.ts
const authSlice = createSlice({
  name: 'auth',
  initialState: { user: null as User | null, token: null as string | null },
  reducers: {
    setCredentials(state, action: PayloadAction<{ user: User; token: string }>) {
      state.user = action.payload.user;
      state.token = action.payload.token;
    },
    logout(state) {
      state.user = null;
      state.token = null;
    },
  },
});

// components/Cart.tsx
function Cart() {
  const items = useAppSelector(state => state.cart.items);
  const total = useAppSelector(state => state.cart.total);
  const dispatch = useAppDispatch();

  return (
    <div>
      {items.map(item => (
        <div key={item.id}>
          {item.name} x{item.qty}
          <button onClick={() => dispatch(removeItem(item.id))}>Remove</button>
        </div>
      ))}
      <p>Total: ${total.toFixed(2)}</p>
      <button onClick={() => dispatch(clearCart())}>Clear Cart</button>
    </div>
  );
}
```

**Step 3: Story** — Cards for: configureStore, createSlice, Immer mutations, Provider, useSelector, useDispatch, typed hooks, slice pattern

---

## Task 10: Scene 9 — Redux Toolkit (Async & Advanced)

**Step 1: Map** — Async flow: dispatch(thunk) → pending → API call → fulfilled/rejected. RTK Query as alternative. Selectors and middleware.

**Step 2: Code** — Tabs: `productThunks.ts`, `productSlice.ts`, `rtk-query.ts`, `selectors.ts`

```tsx
// productThunks.ts
export const fetchProducts = createAsyncThunk(
  'products/fetchAll',
  async (params: { category?: string }, { rejectWithValue }) => {
    try {
      const res = await fetch(`/api/products?category=${params.category ?? ''}`);
      if (!res.ok) throw new Error('Failed to fetch');
      return (await res.json()) as Product[];
    } catch (err) {
      return rejectWithValue((err as Error).message);
    }
  }
);

// productSlice.ts — extraReducers for async
const productSlice = createSlice({
  name: 'products',
  initialState: {
    items: [] as Product[],
    status: 'idle' as 'idle' | 'loading' | 'succeeded' | 'failed',
    error: null as string | null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.items = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.payload as string;
      });
  },
});

// rtk-query.ts — RTK Query alternative
const apiSlice = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({ baseUrl: '/api' }),
  tagTypes: ['Product', 'Cart'],
  endpoints: (builder) => ({
    getProducts: builder.query<Product[], void>({
      query: () => '/products',
      providesTags: ['Product'],
    }),
    getProductById: builder.query<Product, number>({
      query: (id) => `/products/${id}`,
      providesTags: (result, error, id) => [{ type: 'Product', id }],
    }),
    addProduct: builder.mutation<Product, Omit<Product, 'id'>>({
      query: (body) => ({ url: '/products', method: 'POST', body }),
      invalidatesTags: ['Product'],
    }),
  }),
});
export const { useGetProductsQuery, useGetProductByIdQuery, useAddProductMutation } = apiSlice;

// selectors.ts — memoized selectors
import { createSelector } from '@reduxjs/toolkit';

const selectCartItems = (state: RootState) => state.cart.items;

export const selectCartCount = createSelector(
  selectCartItems,
  (items) => items.reduce((sum, i) => sum + i.qty, 0)
);

export const selectCartByCategory = createSelector(
  [selectCartItems, (state: RootState, category: string) => category],
  (items, category) => items.filter(i => i.category === category)
);
```

**Step 3: Story** — Cards for: createAsyncThunk, extraReducers (3 states), RTK Query (createApi, endpoints, tags, invalidation), createSelector, middleware, normalized state

---

## Task 11: Scene 10 — React Query (TanStack Query)

**Step 1: Map** — Query lifecycle: fresh → stale → fetching → fresh. Cache layers. Mutations and invalidation.

**Step 2: Code** — Tabs: `setup.tsx`, `useQuery-examples.tsx`, `mutations.tsx`, `advanced.tsx`

```tsx
// setup.tsx
const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 5 * 60 * 1000,    // 5 min before stale
      gcTime: 10 * 60 * 1000,       // 10 min garbage collect
      retry: 3,                       // retry failed 3 times
      refetchOnWindowFocus: true,     // refetch on tab switch
    },
  },
});

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Router />
      <ReactQueryDevtools />
    </QueryClientProvider>
  );
}

// useQuery-examples.tsx
function ProductList({ category }: { category: string }) {
  const { data: products, isLoading, isError, error } = useQuery({
    queryKey: ['products', category],
    queryFn: () => fetchProducts(category),
    staleTime: 30_000,
  });

  if (isLoading) return <Skeleton count={6} />;
  if (isError) return <ErrorMessage error={error} />;

  return products.map(p => <ProductCard key={p.id} product={p} />);
}

// Dependent queries
function ProductDetail({ productId }: { productId: number }) {
  const { data: product } = useQuery({
    queryKey: ['product', productId],
    queryFn: () => fetchProduct(productId),
  });

  const { data: reviews } = useQuery({
    queryKey: ['reviews', productId],
    queryFn: () => fetchReviews(productId),
    enabled: !!product,  // only fetch when product loaded
  });
}

// mutations.tsx
function AddToCartButton({ product }: { product: Product }) {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: (item: CartItem) => api.addToCart(item),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['cart'] });
    },
    onError: (error) => {
      toast.error('Failed to add to cart');
    },
  });

  return (
    <button
      onClick={() => mutation.mutate({ ...product, qty: 1 })}
      disabled={mutation.isPending}
    >
      {mutation.isPending ? 'Adding...' : 'Add to Cart'}
    </button>
  );
}

// advanced.tsx — optimistic updates
const mutation = useMutation({
  mutationFn: updateCart,
  onMutate: async (newItem) => {
    await queryClient.cancelQueries({ queryKey: ['cart'] });
    const previous = queryClient.getQueryData(['cart']);
    queryClient.setQueryData(['cart'], (old: Cart) => ({
      ...old, items: [...old.items, newItem]
    }));
    return { previous };
  },
  onError: (err, newItem, context) => {
    queryClient.setQueryData(['cart'], context?.previous);
  },
  onSettled: () => {
    queryClient.invalidateQueries({ queryKey: ['cart'] });
  },
});

// Infinite scroll
const { data, fetchNextPage, hasNextPage } = useInfiniteQuery({
  queryKey: ['products'],
  queryFn: ({ pageParam = 0 }) => fetchProducts({ offset: pageParam }),
  getNextPageParam: (lastPage) => lastPage.nextCursor,
});
```

**Step 3: Story** — Cards for: useQuery, queryKey, staleTime/gcTime, useMutation, invalidation, optimistic updates, infinite queries, React Query vs Redux

---

## Task 12: Scene 11 — React Router

**Step 1: Map** — Route tree: BrowserRouter → Routes → Route (with nested children and Outlet)

**Step 2: Code** — Tabs: `routes.tsx`, `navigation.tsx`, `protected.tsx`, `params.tsx`

```tsx
// routes.tsx
function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<ProductList />} />
          <Route path="products">
            <Route index element={<ProductList />} />
            <Route path=":id" element={<ProductDetail />} />
          </Route>
          <Route path="cart" element={<Cart />} />
          <Route path="checkout" element={
            <ProtectedRoute><Checkout /></ProtectedRoute>
          } />
          <Route path="admin" element={
            <ProtectedRoute role="admin"><AdminDashboard /></ProtectedRoute>
          } />
          <Route path="login" element={<Login />} />
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

function Layout() {
  return (
    <>
      <Header />
      <main><Outlet /></main>
      <Footer />
    </>
  );
}

// navigation.tsx
function Header() {
  const navigate = useNavigate();
  return (
    <nav>
      <Link to="/">Home</Link>
      <NavLink to="/products"
        className={({ isActive }) => isActive ? 'active' : ''}>
        Products
      </NavLink>
      <button onClick={() => navigate('/cart')}>Cart</button>
      <button onClick={() => navigate(-1)}>Back</button>
    </nav>
  );
}

// protected.tsx
function ProtectedRoute({ children, role }: {
  children: ReactNode;
  role?: string;
}) {
  const { user } = useAppSelector(state => state.auth);
  const location = useLocation();

  if (!user) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }
  if (role && user.role !== role) {
    return <Navigate to="/" replace />;
  }
  return <>{children}</>;
}

// After login, redirect back:
const location = useLocation();
const from = (location.state as any)?.from?.pathname || '/';
navigate(from, { replace: true });

// params.tsx
function ProductDetail() {
  const { id } = useParams<{ id: string }>();
  const [searchParams, setSearchParams] = useSearchParams();
  const tab = searchParams.get('tab') ?? 'details';

  return (
    <div>
      <p>Product ID: {id}</p>
      <button onClick={() => setSearchParams({ tab: 'reviews' })}>
        Reviews
      </button>
    </div>
  );
}
```

**Step 3: Story** — Cards for: BrowserRouter, Route/Routes, Outlet/nested, Link/NavLink, useNavigate, useParams, useSearchParams, protected routes, Navigate redirect

---

## Task 13: Scene 12 — Forms

**Step 1: Map** — Controlled (value+onChange) vs Uncontrolled (ref). Validation. React Hook Form pattern.

**Step 2: Code** — Tabs: `controlled.tsx`, `uncontrolled.tsx`, `react-hook-form.tsx`, `validation.tsx`

```tsx
// controlled.tsx
function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState<Record<string, string>>({});

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    const newErrors: Record<string, string> = {};
    if (!email) newErrors.email = 'Email required';
    if (!email.includes('@')) newErrors.email = 'Invalid email';
    if (password.length < 8) newErrors.password = 'Min 8 characters';

    if (Object.keys(newErrors).length) {
      setErrors(newErrors);
      return;
    }
    login({ email, password });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input value={email} onChange={e => setEmail(e.target.value)} />
      {errors.email && <span className="error">{errors.email}</span>}
      <input type="password" value={password}
        onChange={e => setPassword(e.target.value)} />
      {errors.password && <span className="error">{errors.password}</span>}
      <button type="submit">Login</button>
    </form>
  );
}

// uncontrolled.tsx
function SearchBar() {
  const inputRef = useRef<HTMLInputElement>(null);
  const handleSubmit = (e: FormEvent) => {
    e.preventDefault();
    const query = inputRef.current?.value ?? '';
    search(query);
  };
  return (
    <form onSubmit={handleSubmit}>
      <input ref={inputRef} defaultValue="" />
      <button type="submit">Search</button>
    </form>
  );
}

// react-hook-form.tsx
import { useForm } from 'react-hook-form';

interface CheckoutForm {
  name: string;
  email: string;
  address: string;
  zip: string;
}

function Checkout() {
  const { register, handleSubmit, formState: { errors, isSubmitting } } =
    useForm<CheckoutForm>();

  const onSubmit = async (data: CheckoutForm) => {
    await submitOrder(data);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register('name', { required: 'Name required', minLength: 2 })} />
      {errors.name && <span>{errors.name.message}</span>}
      <input {...register('email', {
        required: 'Email required',
        pattern: { value: /^\S+@\S+$/, message: 'Invalid email' }
      })} />
      <input {...register('zip', {
        required: 'ZIP required',
        pattern: { value: /^\d{5}$/, message: '5-digit ZIP' }
      })} />
      <button disabled={isSubmitting}>
        {isSubmitting ? 'Placing Order...' : 'Place Order'}
      </button>
    </form>
  );
}

// Debounced search
function SearchInput() {
  const [query, setQuery] = useState('');
  const debouncedQuery = useDebounce(query, 300);

  const { data } = useQuery({
    queryKey: ['search', debouncedQuery],
    queryFn: () => searchProducts(debouncedQuery),
    enabled: debouncedQuery.length > 2,
  });

  return <input value={query} onChange={e => setQuery(e.target.value)} />;
}
```

**Step 3: Story** — Cards for: controlled vs uncontrolled, React Hook Form, validation, debounced input, form submission, when to use each

---

## Task 14: Scene 13 — Component Patterns

**Step 1: Map** — Pattern tree: Composition, Compound Components, Render Props, HOCs, Headless Components

**Step 2: Code** — Tabs: `composition.tsx`, `compound.tsx`, `render-props.tsx`, `hoc.tsx`

```tsx
// composition.tsx — slots via props
function Modal({ header, footer, children }: {
  header: ReactNode;
  footer?: ReactNode;
  children: ReactNode;
}) {
  return createPortal(
    <div className="modal-overlay">
      <div className="modal">
        <div className="modal-header">{header}</div>
        <div className="modal-body">{children}</div>
        {footer && <div className="modal-footer">{footer}</div>}
      </div>
    </div>,
    document.body
  );
}
// Usage:
<Modal
  header={<h2>Confirm Order</h2>}
  footer={<Button onClick={confirm}>Confirm</Button>}
>
  <p>Are you sure?</p>
</Modal>

// compound.tsx — compound components
const Select = ({ children, value, onChange }: SelectProps) => {
  return (
    <SelectContext.Provider value={{ value, onChange }}>
      <div className="select">{children}</div>
    </SelectContext.Provider>
  );
};
const Option = ({ value, children }: OptionProps) => {
  const ctx = useContext(SelectContext);
  return (
    <div
      className={ctx.value === value ? 'selected' : ''}
      onClick={() => ctx.onChange(value)}
    >{children}</div>
  );
};
Select.Option = Option;
// Usage: <Select value={v} onChange={setV}>
//          <Select.Option value="a">A</Select.Option>
//        </Select>

// render-props.tsx
function MouseTracker({ render }: {
  render: (pos: { x: number; y: number }) => ReactNode;
}) {
  const [pos, setPos] = useState({ x: 0, y: 0 });
  useEffect(() => {
    const handler = (e: MouseEvent) => setPos({ x: e.clientX, y: e.clientY });
    window.addEventListener('mousemove', handler);
    return () => window.removeEventListener('mousemove', handler);
  }, []);
  return <>{render(pos)}</>;
}
// Custom hook is the MODERN alternative:
function useMousePosition() { /* same logic, return pos */ }

// hoc.tsx — Higher-Order Components (legacy pattern)
function withAuth<P extends object>(Component: ComponentType<P>) {
  return function AuthenticatedComponent(props: P) {
    const { user } = useAppSelector(state => state.auth);
    if (!user) return <Navigate to="/login" />;
    return <Component {...props} />;
  };
}
const ProtectedCheckout = withAuth(Checkout);
// Modern alternative: custom hook or wrapper component
```

**Step 3: Story** — Cards for: composition (slots), compound components, render props, HOCs, headless components, when to use each, modern alternatives

---

## Task 15: Scene 14 — Error Handling

**Step 1: Map** — Error boundary wrapping routes, try/catch in effects, React Query error states, toast notifications

**Step 2: Code** — Tabs: `ErrorBoundary.tsx`, `error-patterns.tsx`, `query-errors.tsx`

```tsx
// ErrorBoundary.tsx
class ErrorBoundary extends Component<
  { fallback: ReactNode; children: ReactNode },
  { hasError: boolean; error: Error | null }
> {
  state = { hasError: false, error: null };

  static getDerivedStateFromError(error: Error) {
    return { hasError: true, error };
  }

  componentDidCatch(error: Error, info: ErrorInfo) {
    console.error('Error caught:', error, info);
    reportToSentry(error, info);
  }

  render() {
    if (this.state.hasError) {
      return this.props.fallback;
    }
    return this.props.children;
  }
}

// Usage — per route
<Route path="products" element={
  <ErrorBoundary fallback={<ProductsError />}>
    <ProductList />
  </ErrorBoundary>
} />

// error-patterns.tsx
// try/catch in useEffect
useEffect(() => {
  async function loadData() {
    try {
      const data = await fetchProducts();
      setProducts(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Unknown error');
      notify('Failed to load products', 'error');
    } finally {
      setLoading(false);
    }
  }
  loadData();
}, []);

// Global error handler for unhandled rejections
useEffect(() => {
  const handler = (event: PromiseRejectionEvent) => {
    event.preventDefault();
    reportError(event.reason);
    notify('Something went wrong', 'error');
  };
  window.addEventListener('unhandledrejection', handler);
  return () => window.removeEventListener('unhandledrejection', handler);
}, []);

// query-errors.tsx
// React Query automatic retry + error UI
const { data, error, isError } = useQuery({
  queryKey: ['products'],
  queryFn: fetchProducts,
  retry: 3,
  retryDelay: (attempt) => Math.min(1000 * 2 ** attempt, 30000),
});

if (isError) return (
  <div className="error-card">
    <p>{error.message}</p>
    <button onClick={() => refetch()}>Try Again</button>
  </div>
);

// useMutation error handling
const mutation = useMutation({
  mutationFn: placeOrder,
  onError: (error) => {
    if (error.status === 401) navigate('/login');
    else notify(error.message, 'error');
  },
});
```

**Step 3: Story** — Cards for: error boundaries (class component only!), getDerivedStateFromError, componentDidCatch, per-route boundaries, try/catch in effects, React Query error/retry, global error handler

---

## Task 16: Scene 15 — Refs & DOM

**Step 1: Map** — useRef (DOM + mutable), forwardRef, useImperativeHandle, callback refs, createPortal

**Step 2: Code** — Tabs: `dom-refs.tsx`, `forwardRef.tsx`, `portals.tsx`, `measurements.tsx`

```tsx
// dom-refs.tsx
function AutoFocusInput() {
  const inputRef = useRef<HTMLInputElement>(null);
  useEffect(() => { inputRef.current?.focus(); }, []);
  return <input ref={inputRef} />;
}

// Callback ref — fires when element mounts/unmounts
function MeasuredBox() {
  const [height, setHeight] = useState(0);
  const callbackRef = useCallback((node: HTMLDivElement | null) => {
    if (node) setHeight(node.getBoundingClientRect().height);
  }, []);
  return <div ref={callbackRef}>Content: {height}px tall</div>;
}

// forwardRef.tsx
const FancyButton = forwardRef<HTMLButtonElement, ButtonProps>(
  ({ children, ...props }, ref) => (
    <button ref={ref} className="fancy-btn" {...props}>
      {children}
    </button>
  )
);
// Parent: const btnRef = useRef<HTMLButtonElement>(null);
// <FancyButton ref={btnRef}>Click</FancyButton>

// Expose custom API
const VideoPlayer = forwardRef<{ play: () => void; pause: () => void }, Props>(
  (props, ref) => {
    const videoRef = useRef<HTMLVideoElement>(null);
    useImperativeHandle(ref, () => ({
      play: () => videoRef.current?.play(),
      pause: () => videoRef.current?.pause(),
    }));
    return <video ref={videoRef} src={props.src} />;
  }
);

// portals.tsx — render outside parent DOM
function Modal({ children, onClose }: ModalProps) {
  return createPortal(
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={e => e.stopPropagation()}>
        {children}
      </div>
    </div>,
    document.body  // renders here, not in parent
  );
}

function Tooltip({ children, target }: TooltipProps) {
  const [pos, setPos] = useState({ top: 0, left: 0 });
  useEffect(() => {
    const rect = target.current?.getBoundingClientRect();
    if (rect) setPos({ top: rect.bottom + 8, left: rect.left });
  }, [target]);
  return createPortal(
    <div className="tooltip" style={pos}>{children}</div>,
    document.body
  );
}

// measurements.tsx — ResizeObserver
function useElementSize(ref: RefObject<HTMLElement>) {
  const [size, setSize] = useState({ width: 0, height: 0 });
  useEffect(() => {
    if (!ref.current) return;
    const observer = new ResizeObserver(([entry]) => {
      setSize({
        width: entry.contentRect.width,
        height: entry.contentRect.height,
      });
    });
    observer.observe(ref.current);
    return () => observer.disconnect();
  }, [ref]);
  return size;
}
```

**Step 3: Story** — Cards for: useRef DOM, useRef mutable, forwardRef, useImperativeHandle, callback refs, createPortal, measuring DOM

---

## Task 17: Scene 16 — Performance & Memoization

**Step 1: Map** — React.memo, useMemo, useCallback, lazy/Suspense, virtualization, Profiler. When NOT to memoize.

**Step 2: Code** — Tabs: `memo.tsx`, `code-splitting.tsx`, `virtualization.tsx`, `anti-patterns.tsx`

```tsx
// memo.tsx
// React.memo — skip re-render if props unchanged
const ProductCard = memo(function ProductCard({ product, onAdd }: Props) {
  return (
    <div className="card">
      <h3>{product.name}</h3>
      <button onClick={() => onAdd(product.id)}>Add</button>
    </div>
  );
});
// Only re-renders if product or onAdd reference changes

// Custom comparison
const ProductCard = memo(
  function ProductCard({ product }: Props) { ... },
  (prev, next) => prev.product.id === next.product.id
    && prev.product.price === next.product.price
);

// useMemo + useCallback together
function ProductList({ products }: Props) {
  const sorted = useMemo(() =>
    [...products].sort((a, b) => a.price - b.price),
    [products]
  );

  const handleAdd = useCallback((id: number) => {
    dispatch(addItem(id));
  }, [dispatch]);

  return sorted.map(p =>
    <ProductCard key={p.id} product={p} onAdd={handleAdd} />
  );
}

// code-splitting.tsx
const AdminDashboard = lazy(() => import('./pages/AdminDashboard'));
const Checkout = lazy(() => import('./pages/Checkout'));

function App() {
  return (
    <Suspense fallback={<LoadingSpinner />}>
      <Routes>
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/checkout" element={<Checkout />} />
      </Routes>
    </Suspense>
  );
}

// Route-level code splitting
const ProductDetail = lazy(() =>
  import('./pages/ProductDetail')
);

// virtualization.tsx
import { FixedSizeList } from 'react-window';

function ProductVirtualList({ products }: { products: Product[] }) {
  const Row = ({ index, style }: { index: number; style: CSSProperties }) => (
    <div style={style}>
      <ProductCard product={products[index]} />
    </div>
  );
  return (
    <FixedSizeList
      height={600} width="100%"
      itemCount={products.length} itemSize={80}
    >
      {Row}
    </FixedSizeList>
  );
}
// 10,000 items → only ~10 DOM nodes at once!

// anti-patterns.tsx
// DON'T memoize everything!
// useMemo/useCallback have a cost (memory + comparison)

// DON'T: memoize cheap operations
const name = useMemo(() => `${first} ${last}`, [first, last]);
// Just compute it: const name = `${first} ${last}`;

// DON'T: useCallback for handlers not passed to memo'd children
const handleClick = useCallback(() => { ... }, []);
// Unless child is wrapped in React.memo, this does nothing

// DO memoize when:
// 1. Expensive computation (sorting, filtering large arrays)
// 2. Referential equality matters (effect deps, memo'd child props)
// 3. React DevTools Profiler shows re-render issues
```

**Step 3: Story** — Cards for: React.memo, useMemo, useCallback, lazy/Suspense, virtualization, Profiler, when NOT to memoize

---

## Task 18: Scene 17 — Concurrent React (v18+)

**Step 1: Map** — Urgent vs transition updates. useTransition marks non-urgent. useDeferredValue defers value. Suspense for async boundaries.

**Step 2: Code** — Tabs: `useTransition.tsx`, `useDeferredValue.tsx`, `suspense.tsx`, `batching.tsx`

```tsx
// useTransition.tsx
function ProductSearch() {
  const [query, setQuery] = useState('');
  const [isPending, startTransition] = useTransition();

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    // Urgent: update input immediately
    setQuery(e.target.value);

    // Non-urgent: filter results can wait
    startTransition(() => {
      setFilteredProducts(filterProducts(e.target.value));
    });
  };

  return (
    <>
      <input value={query} onChange={handleChange} />
      {isPending && <Spinner />}
      <ProductGrid products={filteredProducts} />
    </>
  );
}

// Tab switching — keep old content visible
function ProductTabs() {
  const [tab, setTab] = useState('details');
  const [isPending, startTransition] = useTransition();

  const switchTab = (newTab: string) => {
    startTransition(() => setTab(newTab));
    // Old tab stays visible until new tab is ready!
  };

  return (
    <div style={{ opacity: isPending ? 0.7 : 1 }}>
      <TabContent tab={tab} />
    </div>
  );
}

// useDeferredValue.tsx
function SearchResults({ query }: { query: string }) {
  const deferredQuery = useDeferredValue(query);
  const isStale = query !== deferredQuery;

  // This expensive computation uses the DEFERRED value
  const results = useMemo(() =>
    products.filter(p =>
      p.name.toLowerCase().includes(deferredQuery.toLowerCase())
    ),
    [deferredQuery]
  );

  return (
    <div style={{ opacity: isStale ? 0.5 : 1 }}>
      {results.map(p => <ProductCard key={p.id} product={p} />)}
    </div>
  );
}

// suspense.tsx
// Suspense for code splitting
<Suspense fallback={<Skeleton />}>
  <LazyProductDetail />
</Suspense>

// Suspense for data fetching (with React Query)
<Suspense fallback={<ProductSkeleton />}>
  <ProductDetail id={id} />
</Suspense>

function ProductDetail({ id }: { id: number }) {
  // useSuspenseQuery throws promise → Suspense catches
  const { data } = useSuspenseQuery({
    queryKey: ['product', id],
    queryFn: () => fetchProduct(id),
  });
  return <div>{data.name}</div>;
}

// batching.tsx
// React 18: ALL updates are batched automatically
// (previously only event handlers were batched)

function handleClick() {
  setCount(c => c + 1);    // batched
  setFlag(f => !f);         // batched
  // Only ONE re-render!
}

// Even in setTimeout, promises, native events:
setTimeout(() => {
  setCount(c => c + 1);    // batched in React 18!
  setFlag(f => !f);         // batched in React 18!
}, 1000);

// To opt OUT of batching (rare):
import { flushSync } from 'react-dom';
flushSync(() => setCount(c => c + 1));  // re-renders NOW
flushSync(() => setFlag(f => !f));       // re-renders NOW
```

**Step 3: Story** — Cards for: useTransition (urgent vs non-urgent), useDeferredValue, Suspense, automatic batching, flushSync, concurrent mental model

---

## Task 19: Scene 18 — Server Components & SSR

**Step 1: Map** — Server vs Client component boundary. 'use client'/'use server' directives. Hydration flow. Streaming SSR.

**Step 2: Code** — Tabs: `server-component.tsx`, `client-component.tsx`, `server-actions.tsx`, `hydration.tsx`

```tsx
// server-component.tsx
// Server Components (default in Next.js App Router)
// - Run on the server ONLY
// - Can directly access DB, files, env vars
// - Zero JS shipped to client
// - Cannot use hooks, state, or browser APIs

async function ProductList() {
  // Direct DB access — no API needed!
  const products = await db.product.findMany({
    orderBy: { createdAt: 'desc' },
  });

  return (
    <div className="grid">
      {products.map(p => (
        <ProductCard key={p.id} product={p} />
      ))}
    </div>
  );
}
// This component's code NEVER reaches the browser

// client-component.tsx
'use client';  // ← this directive makes it a Client Component

import { useState } from 'react';

function AddToCartButton({ productId }: { productId: number }) {
  const [isAdding, setIsAdding] = useState(false);

  // Client Components:
  // - Run in the browser
  // - CAN use hooks, state, effects, browser APIs
  // - CAN handle user interactions
  // - JS IS shipped to client

  return (
    <button
      onClick={async () => {
        setIsAdding(true);
        await addToCart(productId);
        setIsAdding(false);
      }}
      disabled={isAdding}
    >
      {isAdding ? 'Adding...' : 'Add to Cart'}
    </button>
  );
}

// server-actions.tsx
'use server';  // ← runs on server, callable from client

async function addToCart(productId: number) {
  const session = await getSession();
  if (!session) throw new Error('Not authenticated');

  await db.cart.create({
    data: { userId: session.userId, productId, quantity: 1 },
  });
  revalidatePath('/cart');
}

// Called from Client Component:
<form action={addToCart}>
  <input type="hidden" name="productId" value={id} />
  <button type="submit">Add to Cart</button>
</form>

// hydration.tsx
// SSR → HTML sent to browser → JS loads → Hydration
// Hydration = attaching event handlers to server-rendered HTML

// Hydration mismatch (COMMON BUG):
function Timestamp() {
  // Server renders: "March 6, 2026 10:00:00"
  // Client hydrates: "March 6, 2026 10:00:01"
  // MISMATCH! React warns.
  return <p>{new Date().toLocaleString()}</p>;
}

// Fix: useEffect for client-only values
function Timestamp() {
  const [time, setTime] = useState<string>('');
  useEffect(() => {
    setTime(new Date().toLocaleString());
  }, []);
  return <p>{time || 'Loading...'}</p>;
}

// Streaming SSR — send HTML in chunks
// <Suspense fallback={<Skeleton/>}> marks streaming boundaries
// Server sends shell first, then fills in Suspense boundaries
```

**Step 3: Story** — Cards for: Server Components (what/why), Client Components ('use client'), Server Actions ('use server'), hydration (what/mismatch), streaming SSR, when to use server vs client

---

## Task 20: Scene 19 — TypeScript with React

**Step 1: Map** — Props interfaces, generic components, event types, hook types, discriminated unions, utility types

**Step 2: Code** — Tabs: `props-types.tsx`, `generics.tsx`, `events-hooks.tsx`, `advanced.tsx`

```tsx
// props-types.tsx
// Props interface
interface ProductCardProps {
  product: Product;
  onAdd: (id: number) => void;
  featured?: boolean;        // optional
  children?: ReactNode;      // any renderable
  renderBadge?: () => ReactElement;  // must be JSX
}

// Extending HTML element props
interface ButtonProps extends ComponentPropsWithoutRef<'button'> {
  variant: 'primary' | 'secondary' | 'danger';
  isLoading?: boolean;
}

function Button({ variant, isLoading, children, ...rest }: ButtonProps) {
  return (
    <button className={`btn-${variant}`} disabled={isLoading} {...rest}>
      {isLoading ? <Spinner /> : children}
    </button>
  );
}

// Discriminated union props
type ModalProps =
  | { type: 'alert'; message: string; onOk: () => void }
  | { type: 'confirm'; message: string; onOk: () => void; onCancel: () => void }
  | { type: 'prompt'; message: string; onSubmit: (value: string) => void };

function Modal(props: ModalProps) {
  switch (props.type) {
    case 'alert': return <AlertModal {...props} />;
    case 'confirm': return <ConfirmModal {...props} />;
    case 'prompt': return <PromptModal {...props} />;
  }
}

// generics.tsx
// Generic list component
interface ListProps<T> {
  items: T[];
  renderItem: (item: T) => ReactNode;
  keyExtractor: (item: T) => string | number;
}

function List<T>({ items, renderItem, keyExtractor }: ListProps<T>) {
  return <ul>{items.map(item =>
    <li key={keyExtractor(item)}>{renderItem(item)}</li>
  )}</ul>;
}
// <List items={products} renderItem={p => <ProductCard product={p} />} keyExtractor={p => p.id} />

// Generic select component
function Select<T extends string>({ options, value, onChange }: {
  options: T[];
  value: T;
  onChange: (val: T) => void;
}) {
  return (
    <select value={value} onChange={e => onChange(e.target.value as T)}>
      {options.map(opt => <option key={opt} value={opt}>{opt}</option>)}
    </select>
  );
}

// events-hooks.tsx
// Event types
const handleChange = (e: ChangeEvent<HTMLInputElement>) => { ... };
const handleSubmit = (e: FormEvent<HTMLFormElement>) => { ... };
const handleClick = (e: MouseEvent<HTMLButtonElement>) => { ... };
const handleKeyDown = (e: KeyboardEvent<HTMLInputElement>) => { ... };

// Typing hooks
const [user, setUser] = useState<User | null>(null);
const ref = useRef<HTMLDivElement>(null);
const ctx = useContext<ThemeContextType>(ThemeContext);

// Custom hook return types
function useToggle(initial = false): [boolean, () => void] {
  const [state, setState] = useState(initial);
  const toggle = useCallback(() => setState(s => !s), []);
  return [state, toggle];  // tuple
}

// advanced.tsx
// as const for literal types
const ROUTES = {
  home: '/',
  products: '/products',
  cart: '/cart',
} as const;
type Route = typeof ROUTES[keyof typeof ROUTES];  // '/' | '/products' | '/cart'

// Loading state with discriminated unions
type AsyncState<T> =
  | { status: 'idle' }
  | { status: 'loading' }
  | { status: 'success'; data: T }
  | { status: 'error'; error: Error };

function useAsync<T>(): [AsyncState<T>, (promise: Promise<T>) => void] {
  const [state, setState] = useState<AsyncState<T>>({ status: 'idle' });
  const run = useCallback((promise: Promise<T>) => {
    setState({ status: 'loading' });
    promise
      .then(data => setState({ status: 'success', data }))
      .catch(error => setState({ status: 'error', error }));
  }, []);
  return [state, run];
}
```

**Step 3: Story** — Cards for: props interfaces, extending HTML props, generic components, event types, hook types, discriminated unions, as const, utility types in React

---

## Task 21: Scene 20 — Testing

**Step 1: Map** — Testing Library philosophy → render → query → assert. Mocking (MSW). Testing hooks and Redux.

**Step 2: Code** — Tabs: `component.test.tsx`, `async.test.tsx`, `hooks.test.tsx`, `msw-mock.ts`

```tsx
// component.test.tsx
import { render, screen, within } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

test('adds product to cart when button clicked', async () => {
  const user = userEvent.setup();
  const mockAdd = vi.fn();

  render(<ProductCard product={mockProduct} onAdd={mockAdd} />);

  // Query by role (accessible, recommended)
  const button = screen.getByRole('button', { name: /add to cart/i });
  expect(button).toBeInTheDocument();

  // Click
  await user.click(button);
  expect(mockAdd).toHaveBeenCalledWith(mockProduct.id);

  // Text content
  expect(screen.getByText('Running Shoes')).toBeInTheDocument();
  expect(screen.getByText('$99.99')).toBeInTheDocument();
});

// Query priority (most to least preferred):
// getByRole → getByLabelText → getByPlaceholderText →
// getByText → getByDisplayValue → getByAltText →
// getByTitle → getByTestId

// async.test.tsx
test('loads and displays products', async () => {
  render(<ProductList />);

  // Loading state
  expect(screen.getByText(/loading/i)).toBeInTheDocument();

  // Wait for data
  const products = await screen.findAllByRole('listitem');
  expect(products).toHaveLength(3);

  // Or with waitFor:
  await waitFor(() => {
    expect(screen.queryByText(/loading/i)).not.toBeInTheDocument();
  });
});

test('shows error when fetch fails', async () => {
  server.use(
    http.get('/api/products', () => HttpResponse.error())
  );

  render(<ProductList />);
  expect(await screen.findByText(/error/i)).toBeInTheDocument();
  expect(screen.getByRole('button', { name: /retry/i })).toBeInTheDocument();
});

// hooks.test.tsx
import { renderHook, act } from '@testing-library/react';

test('useDebounce returns debounced value', async () => {
  const { result, rerender } = renderHook(
    ({ value, delay }) => useDebounce(value, delay),
    { initialProps: { value: 'hello', delay: 300 } }
  );

  expect(result.current).toBe('hello');
  rerender({ value: 'world', delay: 300 });
  expect(result.current).toBe('hello');  // not yet

  await act(async () => {
    await new Promise(r => setTimeout(r, 300));
  });
  expect(result.current).toBe('world');  // now!
});

// Testing with Redux
test('cart shows items from store', () => {
  const store = configureStore({
    reducer: { cart: cartReducer },
    preloadedState: { cart: { items: [mockItem], total: 99.99 } },
  });

  render(
    <Provider store={store}>
      <Cart />
    </Provider>
  );

  expect(screen.getByText('Running Shoes')).toBeInTheDocument();
  expect(screen.getByText('$99.99')).toBeInTheDocument();
});

// msw-mock.ts — Mock Service Worker
import { http, HttpResponse } from 'msw';
import { setupServer } from 'msw/node';

const handlers = [
  http.get('/api/products', () => {
    return HttpResponse.json([
      { id: 1, name: 'Running Shoes', price: 99.99 },
      { id: 2, name: 'Backpack', price: 49.99 },
    ]);
  }),
  http.post('/api/cart', async ({ request }) => {
    const body = await request.json();
    return HttpResponse.json({ ...body, id: Date.now() });
  }),
];

export const server = setupServer(...handlers);
// In setupTests.ts:
// beforeAll(() => server.listen());
// afterEach(() => server.resetHandlers());
// afterAll(() => server.close());
```

**Step 3: Story** — Cards for: RTL philosophy, render/screen/queries, userEvent vs fireEvent, async testing (findBy, waitFor), mocking with MSW, testing hooks (renderHook), testing Redux

---

## Task 22: Scene 21 — Performance Cheat Sheet

**Step 1: Map** — Checklist grouped: Build-time, Runtime, Network, Advanced. Impact ratings.

**Step 2: Code** — Single cheat sheet with all optimization techniques:

```tsx
// === BUILD-TIME ===
// 1. Code splitting with lazy()
const Page = lazy(() => import('./Page'));
// Separate JS chunk, loaded on demand

// 2. Tree shaking — ES modules required
import { addItem } from './cartSlice';  // GOOD: named import
// import * as cart from './cartSlice';  // BAD: imports everything

// 3. Bundle analysis
// npx source-map-explorer build/static/js/*.js

// === RUNTIME ===
// 4. React.memo for pure components
const Card = memo(function Card(props: Props) { ... });

// 5. useMemo for expensive derivations
const sorted = useMemo(() => expensiveSort(items), [items]);

// 6. useCallback for stable references
const onClick = useCallback((id) => dispatch(add(id)), [dispatch]);

// 7. Key prop — stable, unique IDs
{items.map(i => <Card key={i.id} />)}  // NOT key={index}

// 8. Avoid inline objects/arrays in JSX
// BAD: <Card style={{ color: 'red' }} />  ← new object every render
const style = useMemo(() => ({ color: 'red' }), []);

// === NETWORK ===
// 9. React Query staleTime
useQuery({ queryKey: ['config'], queryFn: ..., staleTime: Infinity });

// 10. Preload on hover
const prefetch = () => queryClient.prefetchQuery({
  queryKey: ['product', id], queryFn: () => fetchProduct(id)
});
<Link onMouseEnter={prefetch}>View</Link>

// 11. Image optimization
<img loading="lazy" decoding="async" src={url} />

// === ADVANCED ===
// 12. Virtualization for large lists
<FixedSizeList height={600} itemCount={10000} itemSize={80}>
  {Row}
</FixedSizeList>

// 13. useTransition for non-urgent updates
const [isPending, startTransition] = useTransition();
startTransition(() => setFilter(query));

// 14. Web Workers for heavy computation
const worker = new Worker(new URL('./sort.worker.ts', import.meta.url));

// 15. Avoid memory leaks
// - Cleanup in useEffect return
// - AbortController for fetch
// - Remove event listeners
// - Clear timeouts/intervals

// 16. React DevTools Profiler
// Identify unnecessary re-renders
// Highlight Updates → visual re-render feedback
```

**Step 3: Story** — Cards grouped by category, each with impact level (HIGH/MEDIUM/LOW), when to use, and common mistakes

---

## Task 23: Final Polish & Complete MAP

**Step 1:** Ensure ALL concept keys from all 22 scenes are registered in the JavaScript `MAP` object.

**Step 2:** Test all scene navigation (buttons + arrow keys + keyboard Escape).

**Step 3:** Test all clickable highlights open the correct card in every scene.

**Step 4:** Test file tab switching in all scenes.

**Step 5:** Final commit.
```bash
git commit -m "feat: complete react e-commerce anatomy — all 22 scenes"
```
