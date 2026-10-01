# Section 10: Virtual Threads

Lectures in this section: *A Million Threads on This Laptop*, *A Clerk Who Is Only Waiting*, *Nobody Waits for a Virtual Thread*, *When a Clerk Keeps the Chair*, *A Million Copies*, *A Million Clerks, Ten Phone Lines*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java AlwaysDaemon.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `AlwaysDaemon.java` | Nobody Waits for a Virtual Thread | `isVirtual true, isDaemon true`; `setDaemon(false)` -> `IllegalArgumentException: 'false' not legal for virtual threads` | `java AlwaysDaemon.java` |  |
| `Carriers.java` | A Clerk Who Is Only Waiting | 10,000 virtual threads nap twice: they ran on 10 carrier threads (one per core), and about nine in ten woke up on a DIFFERENT carrier than they fell asleep on | `java Carriers.java` |  |
| `CpuVirtual.java` | When a Clerk Keeps the Chair | Section 7's computing cards: a pool of ten and a virtual thread per card take the same time (about 0.8 s each; neither is faster: whichever half runs first in a round tends to win it); … | `java CpuVirtual.java` |  |
| `NobodyWaits.java` | Nobody Waits for a Virtual Thread | a virtual thread naps 100 ms then prints; main returns without join: only `main is finished`, exit 0 | `java NobodyWaits.java` |  |
| `Pinned.java` | When a Clerk Keeps the Chair | The classic pinning test: 1,000 virtual threads nap 100 ms INSIDE `synchronized`, each on its own key: about 0.11 s on 27 | `java Pinned.java` |  |
| `PlatformMillion.java` | A Million Threads on This Laptop | the same million with `new Thread(...).start()`: `Thread-4064`, `-4065` or `-4066` fails (`pthread_create failed (EAGAIN) ... stacksize: 2048k`), `hired 4064`-`4066 platform threads in` 0.2-0.6 s, `OutOfMemoryError: unable to create native thread` | `java PlatformMillion.java` |  |
| `PooledVirtual.java` | Nobody Waits for a Virtual Thread | Roster200 on `newFixedThreadPool(4, Thread.ofVirtual().factory())`: about 5.2 s again — a pool of virtual threads rebuilds the ceiling | `java PooledVirtual.java` |  |
| `Roster200Virtual.java` | A Million Threads on This Laptop | Section 7's `Roster200` with the factory changed to `newVirtualThreadPerTaskExecutor()` (and the label): about 110 ms instead of about 5.2 s; `card 50 served by ` prints NO name (a virtual thread has none unless you give it one) | `java Roster200Virtual.java` |  |
| `RushHour.java` | A Million Threads on This Laptop | a million cards, a new virtual thread for each (`newVirtualThreadPerTaskExecutor`), each naps one second and bumps an `AtomicInteger`: `handed in 1000000 cards after ~0.3-0.5 s`, `1000000 passengers served after ~5.0-5.7 s`, peak resident memory about 1 GB (`/usr/bin/time -l`) | `java RushHour.java` |  |
| `ThreadCost.java` | A Million Threads on This Laptop | what a SLEEPING thread costs: `java ThreadCost.java virtual 1000000` vs `platform 4000` (and `virtual 0`, the empty JVM): resident memory before and after (ps), heap in use after a GC, OS threads in the process (`ps -M`) | `java ThreadCost.java virtual 1000000` |  |
| `ThreadLocalCost.java` | A Million Copies; A Million Clerks, Ten Phone Lines | a `ThreadLocal` 1 KB buffer, the pool-era cache: `pool of 10: 1000000 cards, buffers made: 10`; `virtual threads: 1000000 cards, buffers made: 1000000`, plus the heap while they sleep | `java ThreadLocalCost.java pool` |  |
| `ThreeWays.java` | A Clerk Who Is Only Waiting | `Thread.startVirtualThread`, `Thread.ofVirtual().name("clerk-", 1).start`, `newVirtualThreadPerTaskExecutor`: the same `Thread` class, `join` as always, `isVirtual true`, name `""` unless given (#33 and #35 every run; the executor's ID varies, #37-#39) | `java ThreeWays.java` |  |
| `VirtualNoClose.java` | Nobody Waits for a Virtual Thread | byte-identical copy of `../s07-pools/diagnostics/VirtualNoClose.java`: a virtual executor, one card, no close: exit 0 every run, the card's line printed in only SOME runs (a race with the JVM's exit) | `java VirtualNoClose.java` |  |
| `WhoServed.java` | A Clerk Who Is Only Waiting | Roster200Virtual printing the whole thread: `card 50 served by VirtualThread[#84]/runnable@ForkJoinPool-1-worker-2` — the virtual thread, then the carrier it sits on (#84 in 10 of 11 runs, #85 once; the carrier varies) | `java WhoServed.java` |  |

## Extra experiments (`diagnostics/`)

Smaller programs used for a single question in a lecture, or to check a claim. Run them from this folder.

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `diagnostics/DumpVirtual.java` | A Million Copies | 10,000 NAMED virtual threads asleep: `jstack` lists 39-41 threads and none of them; `jcmd <pid> Thread.dump_to_file` lists all 10,000 (`"passenger-1" virtual TIMED_WAITING`) | `java diagnostics/DumpVirtual.java` |  |
| `diagnostics/NoPermits.java` | A Million Clerks, Ten Phone Lines | TenAtATime with its two Semaphore lines (11 and 17) switched off: nothing limits how many reach the server | `java diagnostics/NoPermits.java` |  |
| `diagnostics/PinnedClassic.java` | When a Clerk Keeps the Chair | the same test in classic syntax: about 10.4 s on JDK 21 (pinned), about 0.11 s on JDK 25 and 27; with `-Djdk.tracePinnedThreads=short` JDK 21 prints `reason:MONITOR`, 27 prints nothing; JFR `jdk.VirtualThreadPinned` events: 1,000 on 21, 0 on 27 | `java diagnostics/PinnedClassic.java` |  |
| `diagnostics/PlatformWaits.java` | Nobody Waits for a Virtual Thread | the same with `Thread.ofPlatform()`: the JVM waits, the card is served | `java diagnostics/PlatformWaits.java` |  |
| `diagnostics/RushHourTenThousand.java` | A Clerk Who Is Only Waiting | RushHour with one number changed (10,000): served after about 1.03 s | `java diagnostics/RushHourTenThousand.java` |  |
| `diagnostics/RushHourTimeline.java` | A Million Threads on This Laptop | RushHour with three clocks: every card was asleep by about half a second, the first woke on time (about 1.008 s), the LAST woke at about 5.3-5.7 s: the million's extra seconds are the waking, not the creating (optional arg: the number of cards) | `java diagnostics/RushHourTimeline.java` |  |
| `diagnostics/SleepOneSecond.java` | A Million Threads on This Laptop | how late `Thread.sleep(1000)` wakes, on main and on a virtual thread | `java diagnostics/SleepOneSecond.java` |  |
| `diagnostics/SlipsVirtual.java` | Nobody Waits for a Virtual Thread | Section 8's `SlipsNotClerks` on virtual threads: 8 lookups of 100 ms in about 100 ms (the fixed pool of two: 424-440 ms) | `java diagnostics/SlipsVirtual.java` |  |
| `diagnostics/StillPinned.java` | When a Clerk Keeps the Chair | what still pins on 27: one card naps inside a class initializer while a thousand ordinary 100 ms naps are timed; ten carriers: about 0.11 s; … | `java diagnostics/StillPinned.java` |  |
| `diagnostics/TenAtATime.java` | A Million Clerks, Ten Phone Lines | limit the scarce thing, not the threads: a `Semaphore(10)` around the "server", 200 virtual threads, at most 10 at the server at once, about 2 s | `java diagnostics/TenAtATime.java` |  |
| `diagnostics/ThreadLocalNone.java` | A Million Copies; A Million Clerks, Ten Phone Lines | the same million sleeping virtual cards with no ThreadLocal: the heap baseline (about 0.7 GB, against about 1.85 GB with the buffers) | `java diagnostics/ThreadLocalNone.java` |  |
| `diagnostics/TraceLocals.java` | One Value, a Million Readers | run with `-Djdk.traceVirtualThreadLocals=true`: the JDK prints the stack of a virtual thread that sets a ThreadLocal | `java -Djdk.traceVirtualThreadLocals=true diagnostics/TraceLocals.java` |  |
| `diagnostics/WakeUps.java` | A Million Threads on This Laptop | the million's wake-ups per half second (about 110,000 a half second, roughly level for four and a half seconds; the first half second of waking wakes more, and across 8 runs a full half second ranged 70,000-134,000) and the CPU of the carriers vs the pool's delay-scheduler thread | `java diagnostics/WakeUps.java` |  |
