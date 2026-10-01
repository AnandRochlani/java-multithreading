# Section 8: Callable and Future

Lectures in this section: *The Claim Slip*, *A Card That Answers Back*, *Don't Wait Forever*, *Every Answer, or the First*, *The Fine Print on the Slip*.

Every program is a compact source file for **Java 27**: no class declaration and no `public static void main` needed. Run it straight from source in this folder, for example `java AnswerHole.java`.
Your timings, thread names and print order will differ from the lectures; the lectures quote ranges over many runs.

`OUTPUT.txt` holds the output captured on the instructor's machine (JDK 27, 10-core Mac, 16 GB).

## Programs

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `AnswerHole.java` | The Claim Slip; A Card That Answers Back | a Runnable that returns a value: `incompatible types: bad return type in lambda expression` / `unexpected return value` (8/8) | `java AnswerHole.java` | does not compile on purpose |
| `CatchWhatYouExpect.java` | The Fine Print on the Slip | *deliberate wrong turn*: `catch (IllegalStateException e)` around `get()`. It compiles only because main says `throws Exception`; the catch never runs, exit 1, one trace with main's stack on top and the clerk's under `Caused by`; cards 3 and 4 are still served first (19/19) | `java CatchWhatYouExpect.java` |  |
| `ClaimSlip.java` | Don't Wait Forever | `isDone` false at once; `get(100, MILLISECONDS)` gives up with `TimeoutException` (printed at 107–117 ms, but the get only starts about 6 ms in: it waited about 104–110 ms); the card keeps running and the plain `get` returns `S7-42` at 305–312 ms (35/35) | `java ClaimSlip.java` |  |
| `DeafSlip.java` | The Fine Print on the Slip | `cancel(true)` on a card that never checks for the tap: true and `CANCELLED` at once, `get` throws, yet the clerk finishes his whole second, `was I tapped? true`, and close waits for him (21/21) | `java DeafSlip.java` |  |
| `FirstAnswer.java` | Every Answer, or the First | `invokeAny` on three mirrors: south (200 ms) answers first and wins, north and east are `told to stop` (interrupted) (35/35; the order of those lines varies) | `java FirstAnswer.java` |  |
| `GetTwice.java` | The Fine Print on the Slip | the same slip presented three times: 302–310 ms, then 0 ms, 0 ms; a torn slip gives a new `ExecutionException` each time, around the same cause (21/21) | `java GetTwice.java` |  |
| `KeptSlips.java` | The Claim Slip | the same cards, each slip kept in a list: slip 2 reads `[Completed exceptionally: java.lang.IllegalStateException: card 2 is torn]`, the others `[Completed normally]` (31/31). The exception was on the slip all along: `FutureTask.run` catches every `Throwable` | `java KeptSlips.java` |  |
| `NoAnswer.java` | Don't Wait Forever | `get()` with no limit on an hour-long card: one line, then hung (14/14). `jstack`: main `WAITING (parking)` in `FutureTask.awaitDone` ← `get` ← `NoAnswer.java:9`, the clerk `TIMED_WAITING (sleeping)`, 0.0 % CPU, no deadlock (6/6 dumps) | `java NoAnswer.java` | hangs on purpose: press Ctrl+C |
| `OneTornInAll.java` | Every Answer, or the First | `invokeAll` with card 2 throwing: all three slips come back; slip 2 holds the exception, 1 and 3 their answers (18/18) | `java OneTornInAll.java` |  |
| `OneWord.java` | A Card That Answers Back | the fix is one word: a `Callable<String>` that sleeps and returns, no try, no catch, no nap helper: `berth S7-42, served by a card that answered` (13/13) | `java OneWord.java` |  |
| `ParkedMain.java` | A Card That Answers Back; Don't Wait Forever | while main waits in `get()`, another clerk sees `main is WAITING` at `FutureTask.awaitDone` under `FutureTask.get` (21/21). The watcher card goes in with `execute`, Section 7's verb | `java ParkedMain.java` |  |
| `PeekSlip.java` | The Fine Print on the Slip | Peeking at slips without waiting: isDone, state() and resultNow on a running, a served and a torn card. | `java PeekSlip.java` |  |
| `PollDone.java` | Don't Wait Forever | *deliberate wrong turn*: a loop on `isDone()` made 838 million to 1.2 billion checks and burned 300–310 ms of CPU in 300–311 ms; `get()` burned 0 ms (21/21) | `java PollDone.java` |  |
| `PresentSlip.java` | The Claim Slip | presenting each slip with `get()`: a Runnable card's slip returns `null`; card 2's throws `java.util.concurrent.ExecutionException: java.lang.IllegalStateException: card 2 is torn`, and `getCause()` is the card's own exception (31/31) | `java PresentSlip.java` |  |
| `SeatQuote.java` | A Card That Answers Back | a 300 ms berth lookup and a 200 ms fare lookup on a desk of two: submit, submit, then get, get. Handed in after 0–1 ms, both answers after **305–311 ms** (30 runs) | `java SeatQuote.java` |  |
| `SeatQuoteSlow.java` | A Card That Answers Back | the same file with ONE line moved (`diff`: `12a13` / `15d15`), a get between the two submits: handed in only after 302–312 ms, finished after **511–523 ms** (30 runs; 201–217 ms slower in each of 27 alternating pairs) | `java SeatQuoteSlow.java` |  |
| `SharedRegister.java` | The Fine Print on the Slip | ten clerks, 10,000 different bookings each, into ONE `HashMap`: every slip says it wrote 10,000, and the register held 38,785–77,396 of 100,000 (179 clean runs of 194); 12 runs hung with 2–3 clerks spinning inside `HashMap$TreeNode` code; … | `java SharedRegister.java` | can hang in a few runs: press Ctrl+C |
| `SilentCard.java` | The Claim Slip | **the silence.** Section 7's file, unchanged: four cards via `submit`, card 2 throws, and the run prints cards 1, 3, 4 on `pool-1-thread-1`, no trace, exit 0 (19/19 here, 54/54 with Section 7's runs) | `java SilentCard.java` |  |
| `SleepHole.java` | A Card That Answers Back | a Runnable that sleeps: `unreported exception InterruptedException; must be caught or declared to be thrown` (8/8) | `java SleepHole.java` | does not compile on purpose |
| `SlipsNotClerks.java` | Every Answer, or the First | eight 100 ms lookups, a desk of two: 424–440 ms, four rounds (23 runs). More slips are not more clerks | `java SlipsNotClerks.java` |  |
| `TallyByCard.java` | Every Answer, or the First | The Vanishing Tickets' ten clerks × 100,000, each counting into its own variable and handing the tally back on a slip; main adds the slips: **1,000,000 in 33/33**, no lock. (`../s04-race/TicketCounter.java`, re-run read-only: 141,571–201,531 today, 10 runs; … | `java TallyByCard.java` |  |
| `TearUp.java` | Don't Wait Forever | a 1 s timed `get`, then `cancel(true)`: the clerk is tapped (interrupted), cancel returns true, `get` throws `CancellationException`, and cancelling a finished slip returns false (35/35) | `java TearUp.java` |  |
| `ThreeWindows.java` | Every Answer, or the First | `invokeAll` on berth 300 ms, fare 250 ms, platform 200 ms: they finish platform, fare, berth, but the list comes back berth, fare, platform (the order handed in), after about the slowest card, 304–314 ms (35/35) | `java ThreeWindows.java` |  |

## Extra experiments (`diagnostics/`)

Smaller programs used for a single question in a lecture, or to check a claim. Run them from this folder.

| File | Lecture | What it shows | Run | Note |
|---|---|---|---|---|
| `diagnostics/CallMeBack.java` | extra experiment | The claim under test: "A Future cannot tell you when it is done ... the class for that is called CompletableFuture." Checked on 27: a callback runs on the clerk's thread, and with no executor it uses the common pool (dae… | `java diagnostics/CallMeBack.java` |  |
| `diagnostics/CancelFalse.java` | The Fine Print on the Slip | cancel(false) on a card that has already started: no tap on the shoulder. | `java diagnostics/CancelFalse.java` |  |
| `diagnostics/CatchNoThrows.java` | The Fine Print on the Slip | Calling get() on a slip without handling ExecutionException. | `java diagnostics/CatchNoThrows.java` | does not compile on purpose |
| `diagnostics/FirstAnswerQuiet.java` | Every Answer, or the First | FirstAnswer prints "invokeAny returned" ~15 ms after "south server: answered". Is that invokeAny, or the first use of the clerk's print line? Same three mirrors, but nobody prints until invokeAny has returned. | `java diagnostics/FirstAnswerQuiet.java` |  |
| `diagnostics/FirstFails.java` | Every Answer, or the First | invokeAny when the fastest server fails, and when every server fails. | `java diagnostics/FirstFails.java` |  |
| `diagnostics/InvokeAllTimeout.java` | extra experiment | invokeAll with a time limit: what happens to the lookups still running when it expires? | `java diagnostics/InvokeAllTimeout.java` |  |
| `diagnostics/OneHoleAtATime.java` | A Card That Answers Back | two holes, but javac reports one | `java diagnostics/OneHoleAtATime.java` | does not compile on purpose |
| `diagnostics/SleepHoleCallable.java` | A Card That Answers Back | A Callable that sleeps but returns nothing (missing return value). | `java diagnostics/SleepHoleCallable.java` | does not compile on purpose |
| `diagnostics/TimedGetOvershoot.java` | Don't Wait Forever | How long does get(100 ms) really wait before it gives up? Twenty timeouts on one slip, warm. | `java diagnostics/TimedGetOvershoot.java` |  |
| `diagnostics/WarmPool.java` | A Card That Answers Back | The claim under test: "The extra fifteen is the pool starting its two clerks, and that happens once." Same two lookups as SeatQuote, five rounds on ONE pool, plus the two sleeps timed alone. | `java diagnostics/WarmPool.java` |  |
