void main() {
    long[] jobs = {2000, 2000, 2000};   // ticket, chai, report

    long sum = 0;
    long longest = 0;
    for (long job : jobs) {
        sum += job;
        longest = Math.max(longest, job);
    }

    IO.println("one clerk    : " + sum + " ms");
    IO.println("three clerks : " + longest + " ms");
}
