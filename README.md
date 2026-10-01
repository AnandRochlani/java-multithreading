# Java Multithreading (Java 27): course code

Every program from the course, one folder per section. Each program is a small, complete file that you can run straight from source, read in a minute, and change.

## Set up

1. Install any **JDK 27** build, then check it:

   ```
   java -version
   ```

   The first line should say `version "27"`. The course was recorded with `openjdk version "27" 2026-09-15`.

2. Open a terminal in a section folder and run a program from source. No build tool and no IDE needed:

   ```
   cd s01-why-concurrency
   java Ledger.java
   ```

   These are compact source files (final since Java 25): no class declaration and no `public static void main`.

3. Section 11's structured concurrency is a **preview API on Java 27**, so run those programs with the flag (each section README shows the exact command):

   ```
   java --enable-preview WhenOneCardFails.java
   ```

   Scoped values are final since Java 25 and need no flag.

## What is in each folder

Each section folder has a `README.md` that says which lecture uses which file, what it shows and how to run it, and an `OUTPUT.txt` with the output captured on the instructor's machine. Smaller programs used for a single question in a lecture live in a `diagnostics/` subfolder.

| Section | Folder | Lectures |
|---|---|---|
| 1. Processes, Threads and Why Concurrency | [`s01-why-concurrency/`](s01-why-concurrency/) | *Why Your Java Program Sits Idle* · *Processes vs Threads* · *Concurrency vs Parallelism* · *Correct but Wrong* |
| 2. Creating Threads | [`s02-creating-threads/`](s02-creating-threads/) | *Your First Thread* · *Two Ways to Hire a Clerk* · *An Order You Don't Control* · *start vs run — The Bug That Looks Better Than the Fix* |
| 3. The Life of a Thread: sleep, join and interrupt | [`s03-lifecycle/`](s03-lifecycle/) | *The Life of a Thread* · *The Supervisor at the Gate* · *The Wrong-Place Join, and a Tap on the Shoulder* |
| 4. Race Conditions and synchronized | [`s04-race/`](s04-race/) | *The Vanishing Tickets* · *Slow Motion* · *What synchronized Actually Locks* · *Four Ways to Get synchronized Wrong* |
| 5. wait and notify | [`s05-wait-notify/`](s05-wait-notify/) | *Waiting for Something to Happen* · *wait Lets Go of the Key* · *The Hatch* · *while Not if, notifyAll Not notify* |
| 6. Deadlock | [`s06-deadlock/`](s06-deadlock/) | *Two Clerks, Two Keys* · *Found One Java-Level Deadlock* · *Two Orders Are the Bug* · *Give Up Instead of Hanging* |
| 7. Thread Pools (ExecutorService) | [`s07-pools/`](s07-pools/) | *A Pool of Clerks* · *Nobody Told the Clerks* · *Closing Time* · *Which Pool, and What It Costs* · *How Many Clerks?* |
| 8. Callable and Future | [`s08-callable-future/`](s08-callable-future/) | *The Claim Slip* · *A Card That Answers Back* · *Don't Wait Forever* · *Every Answer, or the First* · *The Fine Print on the Slip* |
| 9. Concurrent Collections and Atomics | [`s09-collections-atomics/`](s09-collections-atomics/) | *The Register That Forgets* · *The Reader Who Never Wrote* · *Two Safe Calls Make a Race* · *The Turnstile* · *The Hatch in One Class* · *Every Tool Has a Catch* |
| 10. Virtual Threads | [`s10-virtual-threads/`](s10-virtual-threads/) | *A Million Threads on This Laptop* · *A Clerk Who Is Only Waiting* · *Nobody Waits for a Virtual Thread* · *When a Clerk Keeps the Chair* · *A Million Copies* · *A Million Clerks, Ten Phone Lines* |
| 11. Structured Concurrency and Scoped Values | [`s11-structured-concurrency/`](s11-structured-concurrency/) | *When One Card Fails* · *All of Them, or Any of Them* · *A Deadline for the Whole Booking* · *A Value for One Call* · *One Value, a Million Readers* · *The Fine Print on the Scope* |

## Before you run anything

- **Your numbers will differ.** Timings, thread names and the order of printed lines change from machine to machine and from run to run. The lectures quote ranges over many runs; compare the shape of the result, not the exact milliseconds. Some programs print in a different order on purpose.
- **Some programs hang on purpose.** The deadlock demos and a pool that nobody shuts down never finish. That is the lesson. Press **Ctrl+C** to stop them. The section READMEs mark them.
- **Some programs do not compile on purpose.** The compiler error is the point; the section READMEs mark these too.
- **A few programs start a million threads.** They need a few gigabytes of free memory. The course measured them on a 10-core Mac with 16 GB.
- **Predict before you run.** Before each run, say out loud what you expect it to print. That habit is what the course is built around.
