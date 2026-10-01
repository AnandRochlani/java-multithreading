# Section 1: Processes, Threads and Why Concurrency

Lectures in this section: *Why Your Java Program Sits Idle*, *Processes vs Threads*, *Concurrency vs Parallelism*, *Correct but Wrong*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java Busy.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `Busy.java` | Concurrency vs Parallelism | cores vs processes running right now (pair with `ps -M -A` for threads; see `busy_capture.txt`) | `java Busy.java` |  |
| `Cores.java` | Why Your Java Program Sits Idle | `availableProcessors()`; prints this machine's number, not Java's | `java Cores.java` |  |
| `Ideal.java` | Why Your Java Program Sits Idle | the sum (one clerk) versus the longest job (three clerks) | `java Ideal.java` |  |
| `Ledger.java` | Why Your Java Program Sits Idle | five payments; each card row waits 800 ms for "the bank" and holds up every row behind it | `java Ledger.java   # re-captured 28 Sep after Upi -> Phone, dollars and generic bank names: 71 runs, elapsed 1618-1634 ms; this is the 1624 run` |  |
| `Nap.java` | Correct but Wrong | **does not compile on purpose**: `Thread.sleep` with no catch and no throws | `java Nap.java` | does not compile on purpose |
| `NapDeclared.java` | Correct but Wrong | the same file with `throws InterruptedException` on `main`; runs | `java NapDeclared.java` |  |
| `Pid.java` | Processes vs Threads | the process id and the current thread's name; two runs, two pids, both `main` | `java Pid.java` |  |
| `Station.java` | Why Your Java Program Sits Idle; Correct but Wrong | three 2-second waits run one after another: ~6000 ms, the sum | `java Station.java` |  |
| `Station2.java` | Correct but Wrong | Station with the thread name printed in each job: main, main, main | `java Station2.java` |  |
| `Waste.java` | Correct but Wrong | idle percentage with one clerk: 90% on 10 cores | `java Waste.java` |  |
| `Who.java` | Processes vs Threads | every live JVM thread; six on JDK 27, one of them `main` | `java Who.java` |  |
