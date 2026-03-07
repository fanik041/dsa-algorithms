# React E-Commerce Anatomy — Design Document

## Overview

Interactive single-HTML-file learning tool for React senior interview preparation. Uses the same ShopZone e-commerce dashboard domain as the Angular anatomy to enable direct comparison. Same 3-column layout (map | code | explanation) with 22 scenes covering the full React ecosystem including Redux Toolkit and React Query.

## Format

- Single self-contained HTML file (no external dependencies except Google Fonts)
- 3-column layout: interactive map (left), highlighted code with file tabs (center), explanation cards (right)
- Scene navigation via top bar buttons + arrow keys
- Click any highlighted code span or map box to open its explanation card
- Dark theme matching existing anatomy files

## E-Commerce Domain

The example app is a **ShopZone** e-commerce dashboard (same domain as Angular version) with:
- **Components:** App, ProductList, ProductCard, ProductDetail, Cart, Checkout, Login, AdminDashboard, Header, SearchBar
- **Hooks:** useCart, useDebounce, useFetch, useLocalStorage, useMediaQuery, useOnClickOutside
- **Redux Store:** productSlice, cartSlice, authSlice (Redux Toolkit)
- **React Query:** product queries, mutation for cart/orders, optimistic updates
- **Router:** React Router v6 with nested routes, protected routes, loaders
- **Context:** ThemeContext, NotificationContext
- **Models/Types:** Product, CartItem, User, Order, ApiResponse<T>

## Scene Breakdown (22 Scenes)

### Scene 0: Architecture Overview
Full map of the React ShopZone app. Component tree, Redux store shape, React Query setup, React Router layout. File tree showing `src/` structure with features/, hooks/, store/, api/, components/.

### Scene 1: JSX & Rendering
- JSX compilation to `React.createElement`
- Expression embedding `{}`
- Conditional rendering (ternary, `&&`, early return)
- List rendering with `.map()` and `key` prop (why keys matter)
- Fragments `<>...</>`
- JSX vs HTML differences (className, htmlFor, camelCase)

### Scene 2: Components & Props
- Functional components
- Props destructuring, `children` prop
- Default values, prop spreading
- Composition over inheritance
- TypeScript props interfaces
- When to split components

### Scene 3: State — useState & useReducer
- `useState` with primitives and objects
- Updater function `setState(prev => ...)`
- Batching (React 18 auto-batches all)
- `useReducer` for complex state (cart reducer)
- Immer pattern for immutable updates
- Lifting state up

### Scene 4: Lifecycle — useEffect
- Mount/update/cleanup phases
- Dependency array (empty = mount, deps = update, none = every render)
- Cleanup return function
- Common pitfalls (missing deps, infinite loops, race conditions)
- Strict mode double-invoke
- Comparison to class lifecycle methods

### Scene 5: Hooks Deep Dive
- `useRef` (DOM refs + mutable containers)
- `useMemo` (expensive computations)
- `useCallback` (stable function references)
- `useId` (SSR-safe IDs)
- `useImperativeHandle` (expose child methods)
- Rules of Hooks (top-level only, React functions only)

### Scene 6: Custom Hooks
- Extracting reusable logic
- `useCart()`, `useDebounce(value, delay)`, `useFetch(url)`
- `useLocalStorage(key, initial)`, `useMediaQuery(query)`
- `useOnClickOutside(ref, handler)`
- Naming convention, returning tuples/objects, composing hooks

### Scene 7: Context API
- `createContext`, `Provider` with value
- `useContext` consumer
- Context + useReducer = mini Redux
- Avoiding re-renders (split contexts, memo)
- When to use context vs Redux vs prop drilling

### Scene 8: Redux Toolkit — Store & Slices
- `configureStore`
- `createSlice` (name, initialState, reducers)
- Immer under the hood
- `Provider` wrapping app
- `useSelector` (with selector functions), `useDispatch`
- Action creators auto-generated
- Slice file pattern

### Scene 9: Redux Toolkit — Async & Advanced
- `createAsyncThunk` for API calls
- Extra reducers (pending/fulfilled/rejected)
- RTK Query overview (`createApi`, endpoints, auto-caching)
- Middleware concept
- `createSelector` (memoized)
- Entity adapter for normalized state

### Scene 10: React Query (TanStack Query)
- `QueryClientProvider`
- `useQuery` (queryKey, queryFn, staleTime, cacheTime)
- `useMutation` (mutationFn, onSuccess, onError)
- `queryClient.invalidateQueries`
- Optimistic updates
- Infinite queries
- Query status states (loading, error, success, idle)

### Scene 11: React Router
- `<BrowserRouter>`, `<Routes>`, `<Route>`
- `<Link>`, `<NavLink>`, `useNavigate`
- `useParams`, `useSearchParams`
- Nested routes with `<Outlet>`
- Layout routes, protected route pattern
- `loader` functions (v6.4+)
- `Navigate` redirect, wildcard `*`

### Scene 12: Forms
- Controlled inputs (value + onChange)
- Uncontrolled (useRef)
- Form submission, validation patterns
- React Hook Form pattern (register, handleSubmit, errors)
- File upload, debounced search input

### Scene 13: Component Patterns
- Composition (children, slots via props)
- Compound components (Select + Option)
- Render props pattern
- HOCs (withAuth wrapper)
- Headless components (useToggle)
- Container/presentational split
- When to use each

### Scene 14: Error Handling
- Error boundaries (class component with getDerivedStateFromError + componentDidCatch)
- Fallback UI, error boundaries per-route
- try/catch in useEffect
- React Query error/retry
- Toast notifications, global error handler

### Scene 15: Refs & DOM
- `useRef` for DOM access
- `forwardRef` to pass refs to children
- `useImperativeHandle` to expose API
- Callback refs
- `createPortal` for modals/tooltips
- Measuring DOM elements, focus management

### Scene 16: Performance & Memoization
- `React.memo` (shallow compare)
- `useMemo` (derived data)
- `useCallback` (stable callbacks for child props)
- `React.lazy` + `Suspense` (code splitting)
- Virtualization (`react-window`)
- React DevTools Profiler
- When NOT to memoize

### Scene 17: Concurrent React (v18+)
- `useTransition` (mark updates as non-urgent)
- `useDeferredValue` (defer expensive re-renders)
- `startTransition`
- `<Suspense>` for async
- Automatic batching
- Concurrent features mental model (urgent vs transition updates)

### Scene 18: Server Components & SSR
- RSC mental model (server vs client components)
- `'use client'` / `'use server'` directives
- Hydration (what it is, hydration mismatch)
- Streaming SSR
- Server Actions for mutations
- When to use server vs client

### Scene 19: TypeScript with React
- `FC<Props>` typing, props interfaces
- Generic components `<T,>`
- Event handler types (`ChangeEvent<HTMLInputElement>`)
- Hook return types
- Discriminated unions for state
- `as const` assertions
- `ComponentPropsWithoutRef<'button'>`

### Scene 20: Testing
- React Testing Library philosophy (test behavior not implementation)
- `render`, `screen.getByRole/getByText`
- `fireEvent` vs `userEvent`
- Async testing (`waitFor`, `findBy`)
- Mocking (modules, API with MSW)
- Testing hooks, testing Redux

### Scene 21: Performance Cheat Sheet
- All optimizations grouped: Build, Runtime, Network, Advanced
- Impact ratings (HIGH/MEDIUM/LOW)
- Lazy loading, memo, code splitting, virtualization
- Image optimization, bundle analysis
- Avoiding common pitfalls

## Technical Approach

- Single HTML file, same structure as `angular_ecommerce_anatomy.html`
- CSS variables for theming, same dark color scheme
- JavaScript engine: scene management, tab switching, pick/highlight system, keyboard navigation
- All React code shown as syntax-highlighted `<pre>` blocks with clickable `<span class="hl">` elements
- Interactive map per scene using styled boxes
- Explanation cards in right column that open/close on click
- Estimated file size: ~6000-8000 lines
