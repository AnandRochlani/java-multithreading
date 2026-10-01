# Section 9: Concurrent Collections and Atomics

Lectures in this section: *The Register That Forgets*, *The Reader Who Never Wrote*, *Two Safe Calls Make a Race*, *The Turnstile*, *The Hatch in One Class*, *Every Tool Has a Catch*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java CasRetries.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `CasRetries.java` | The Turnstile | the turnstile written out: `get`, then `compareAndSet(seen, seen + 1)`, and on a "no" read again and retry. A million sales, and `compareAndSet` said no about five to seven million times | `java CasRetries.java` |  |
| `CopyCost.java` | The Reader Who Never Wrote | what the snapshot costs: 50,000 adds, `ArrayList` 0–3 ms, `CopyOnWriteArrayList` about 180–630 ms (every add copies the whole array) | `java CopyCost.java` |  |
| `CountTheRegister.java` | The Register That Forgets | the register cannot even count itself: `size()` (a plain `++size` inside `putVal`, The Vanishing Tickets' counter) against a walk of the map and a lookup of all 100,000 keys, after every clerk is done. They never agreed; … | `java CountTheRegister.java` | hangs on purpose: press Ctrl+C |
| `Destinations.java` | Two Safe Calls Make a Race | a `ConcurrentHashMap` of four trains, and each booking does `getOrDefault` then `put`: about half the bookings are lost, every run (counted 41,683–60,765 of 100,000 over 40 runs) | `java Destinations.java` |  |
| `DestinationsMerge.java` | Two Safe Calls Make a Race | the same with ONE call, `merge(train, 1, Integer::sum)`: 25,000 per train, 100,000 every run | `java DestinationsMerge.java` |  |
| `FixedRegister.java` | The Register That Forgets | the one-word fix: `new ConcurrentHashMap<>()` (a one-line `diff` from `SharedRegister`): 100,000 every run | `java FixedRegister.java` |  |
| `HatchQueue.java` | The Hatch in One Class | The Hatch (Section 5) with the tray an `ArrayBlockingQueue<>(3)`: `put` and `take`, no `synchronized`, `wait` or `notify`; the printer's put waited about 30–45 ms at a full tray and Meena's take about 50–55 ms at an empty one, same transcript shape every run | `java HatchQueue.java` |  |
| `ReaderCHM.java` | The Reader Who Never Wrote | the same reader on a `ConcurrentHashMap`: `reader finished after N passes`, `fares in the map: 200000`, never an exception (weakly consistent iterators) | `java ReaderCHM.java` |  |
| `ReaderCrash.java` | The Reader Who Never Wrote | Meena writes 200,000 fares into a `HashMap`; Tom only reads, adding them up: `reader threw: java.util.ConcurrentModificationException` every run | `java ReaderCrash.java` |  |
| `ReaderSyncMap.java` | The Reader Who Never Wrote | the same reader on `Collections.synchronizedMap`: still `ConcurrentModificationException` every run (the walk is many calls, and the javadoc says you must hold the map's lock yourself) | `java ReaderSyncMap.java` |  |
| `Register.java` | The Turnstile | A ConcurrentHashMap of bookings plus an AtomicInteger turnstile: 100,000 bookings and 100,000 tickets. | `java Register.java` |  |
| `RegisterCrash.java` | The Register That Forgets | `SharedRegister` plus eight lines (two keep the first slip's exception, six print it in full): `ClassCastException: class java.util.HashMap$Node cannot be cast to class java.util.HashMap$TreeNode`, thrown at `HashMap$TreeNode.moveRootToFront(HashMap.java:2020)` — HashMap's own ca … | `java RegisterCrash.java` | hangs on purpose: press Ctrl+C |
| `SeatChart.java` | Two Safe Calls Make a Race | ten clerks want coach S7's seat chart at once; `get`, and if it is missing build it (100 ms) and `put`: ten builds, ten different charts handed out, every run | `java SeatChart.java` |  |
| `SeatChartOnce.java` | Two Safe Calls Make a Race | the same with `computeIfAbsent`: one build, one chart, every run (the other nine wait for it) | `java SeatChartOnce.java` |  |
| `SharedRegister.java` | The Fine Print on the Slip | Section 8's file, byte for byte: ten clerks, 10,000 different bookings each, into ONE `HashMap`; every slip says 10,000, `size()` says roughly 36,000–87,000 (35,825–86,645 over this lab's 322 runs), and some runs hang (a clerk spinning in `HashMap$TreeNode` code) | `java SharedRegister.java` | hangs on purpose: press Ctrl+C |
| `SnapshotLoop.java` | The Reader Who Never Wrote | one thread adds to a list mid-walk: `ArrayList` throws `ConcurrentModificationException`; `CopyOnWriteArrayList` walks its snapshot: `the walk visited 3, the list now holds 6` | `java SnapshotLoop.java` |  |
| `StallQueue.java` | The Hatch in One Class | Notify Stall's exact shape (tray of one, six slips, two clerks, no naps) on a `BlockingQueue`: `6 of 6 slips taken`, all three TERMINATED, every run (Notify Stall itself is stuck every run) | `java StallQueue.java` |  |
| `SyncRegister.java` | The Register That Forgets | `Collections.synchronizedMap(new HashMap<>())`: also 100,000 every run, with one lock around every call | `java SyncRegister.java` |  |
| `TicketAdder.java` | The Turnstile | the same counter as a `LongAdder` (`increment`, `sum`): 1,000,000 every run | `java TicketAdder.java` |  |
| `TicketAtomic.java` | The Turnstile | Slow Motion's `TicketSync` (from The Vanishing Tickets, fixed) with the key removed and the counter an `AtomicInteger` (`incrementAndGet`): 1,000,000 every run, no `synchronized` anywhere | `java TicketAtomic.java` |  |
| `TrayMethods.java` | The Hatch in One Class | the four ways in and out of a full or empty tray: `add` throws `IllegalStateException: Queue full`, `offer` returns false, `offer` with 100 ms gives up after about 100–105 ms, `put` waits (`WAITING`) until a take; … | `java TrayMethods.java` |  |

## Extra experiments (`diagnostics/`)

Smaller programs used for a single question in a lecture, or to check a claim. Run them from this folder.

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `diagnostics/AdderCells.java` | extra experiment | Looks inside LongAdder with reflection, so run it with: java --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED AdderCells.java A million increments shared by 10, 100 and 1,000 clerks: how many cells does the … | `java --add-opens java.base/java.util.concurrent.atomic=ALL-UNNAMED diagnostics/AdderCells.java` |  |
| `diagnostics/DestinationsLocked.java` | extra experiment | Per-train counts in a ConcurrentHashMap with your own lock around get-then-put: 25,000 each, 100,000 counted. | `java diagnostics/DestinationsLocked.java` |  |
| `diagnostics/LockBeforeTry.java` | Every Tool Has a Catch | lockInterruptibly placed on the line before the try, where it belongs. | `java diagnostics/LockBeforeTry.java` |  |
| `diagnostics/LockInsideTry.java` | Every Tool Has a Catch | lockInterruptibly placed inside the try on an interrupted thread: the finally's unlock throws IllegalMonitorStateException. | `java diagnostics/LockInsideTry.java` |  |
| `diagnostics/NullInCHM.java` | Every Tool Has a Catch | A HashMap accepts a null value; a ConcurrentHashMap throws NullPointerException. | `java diagnostics/NullInCHM.java` |  |
| `diagnostics/OnceStates.java` | extra experiment | Two Safe Calls Make a Race, extra experiment: SeatChartOnce's ten computeIfAbsent cards; 50 ms in, main counts the pool clerks' states. Expect {BLOCKED=9, TIMED_WAITING=1}: nine wait at the reserved pigeonhole's lock (Co… | `java diagnostics/OnceStates.java` |  |
| `diagnostics/ReaderSyncMapLocked.java` | The Reader Who Never Wrote | A reader walks a synchronizedMap while a writer adds 200,000 fares, holding the map's lock for the whole walk. | `java diagnostics/ReaderSyncMapLocked.java` |  |
| `diagnostics/RecursiveUpdate.java` | Two Safe Calls Make a Race | computeIfAbsent called inside computeIfAbsent for the same key: IllegalStateException. | `java diagnostics/RecursiveUpdate.java` |  |
| `diagnostics/SeatChartIfAbsent.java` | extra experiment | Two Safe Calls Make a Race, extra experiment: SeatChart with putIfAbsent. The value is an argument, so every clerk builds first; only the first put sticks and the other nine hand back the chart that was already there. Ex… | `java diagnostics/SeatChartIfAbsent.java` |  |
| `diagnostics/ShuttersUp.java` | extra experiment | A CountDownLatch: the station opens only when all three clerks have arrived. | `java diagnostics/ShuttersUp.java` |  |
| `diagnostics/SoloCME.java` | The Reader Who Never Wrote | One thread, one HashMap: changing the map inside its own loop throws ConcurrentModificationException. | `java diagnostics/SoloCME.java` |  |
| `diagnostics/StateSampler.java` | The Turnstile | Run as: java StateSampler.java sync \| java StateSampler.java atomic | `java diagnostics/StateSampler.java sync` |  |
| `diagnostics/ThreeAtATime.java` | extra experiment | three open windows, ten passengers | `java diagnostics/ThreeAtATime.java` |  |
| `diagnostics/TrainBins.java` | extra experiment | Two Safe Calls Make a Race, extra experiment: which pigeonhole (bin) each train lands in, read from the map itself by reflection. Run: java --add-opens java.base/java.util.concurrent=ALL-UNNAMED TrainBins.java (expect ta… | `java diagnostics/TrainBins.java` |  |
| `diagnostics/TreeCount.java` | The Register That Forgets | Looks inside HashMap with reflection, so run it with: java --add-opens java.base/java.util=ALL-UNNAMED TreeCount.java | `java diagnostics/TreeCount.java` | hangs on purpose: press Ctrl+C |
| `diagnostics/WarmCounters.java` | The Turnstile | One JVM: the three correct counters take turns for 12 rounds, so the later rounds are warm (JIT-compiled). | `java diagnostics/WarmCounters.java` |  |
| `diagnostics/WarmOverlap.java` | extra experiment | WarmCounters' method, plus (a) a FRESH LongAdder every round (WarmCounters reuses one: reset() keeps its cells), and (b) for each counter, how long the ten clerks' counting loops really overlapped. Each cell prints wall/… | `java diagnostics/WarmOverlap.java` |  |
| `diagnostics/WhereIsRavi.java` | The Hatch in One Class | A thread waiting in put() on a full ArrayBlockingQueue, and the stack trace that shows where it waits. | `java diagnostics/WhereIsRavi.java` |  |
