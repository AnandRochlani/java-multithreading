# Section 2: Creating Threads

Lectures in this section: *Your First Thread*, *Two Ways to Hire a Clerk*, *An Order You Don't Control*, *start vs run — The Bug That Looks Better Than the Fix*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java Counter.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `Counter.java` | An Order You Don't Control | record `BookingClerk` card, three named clerks (Meena, Tom, Kavya), summary prints 0 first | `java Counter.java` |  |
| `CounterRun.java` | start vs run — The Bug That Looks Better Than the Fix | **the bug**: `.run()` instead of `.start()` — all `[main]`, ~6.37 s, summary says 9 | `java CounterRun.java` |  |
| `DaemonOff.java` | An Order You Don't Control | main ends first; the daemon clerk is abandoned | `java DaemonOff.java` |  |
| `DaemonOn.java` | An Order You Don't Control | main ends first; the daemon clerk is abandoned | `java DaemonOn.java` |  |
| `DefaultNames.java` | Two Ways to Hire a Clerk | unnamed threads: Thread-0/1/2, scrambled | `java DefaultNames.java` |  |
| `ExtendsThread.java` | Two Ways to Hire a Clerk | the `extends Thread` way | `java ExtendsThread.java` |  |
| `HelloRunnable.java` | Your First Thread | a Runnable, a Thread, `start()`; `main carries on` usually prints first | `java HelloRunnable.java` |  |
| `LambdaClerks.java` | Two Ways to Hire a Clerk | a method that returns a Runnable (`clerkAt`) | `java LambdaClerks.java` |  |
| `LambdaReassign.java` | Two Ways to Hire a Clerk | **does not compile on purpose**: lambda captures a reassigned local | `java LambdaReassign.java` | does not compile on purpose |
| `NamedThreads.java` | Two Ways to Hire a Clerk | constructor name + `Thread.ofPlatform().name("window-", 2)` builder (Java 21+) | `java NamedThreads.java` |  |
| `SleepyJob.java` | Your First Thread | **does not compile on purpose**: `Thread.sleep` inside a lambda job | `java SleepyJob.java` | does not compile on purpose |
| `StartTwice.java` | start vs run — The Bug That Looks Better Than the Fix | `IllegalThreadStateException` | `java StartTwice.java` |  |
| `StationThreads.java` | Your First Thread | Station's three jobs on three threads: all finish at ~2010 ms, order varies | `java StationThreads.java` |  |
| `ThreadIsRunnable.java` | Two Ways to Hire a Clerk | a Thread handed to another Thread as a card: window-9 never starts | `java ThreadIsRunnable.java` |  |
