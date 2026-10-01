void main() throws Exception {
    List<Callable<String>> enquiries = List.of(
            () -> "berth S7-42",
            () -> { throw new IllegalStateException("card 2 is torn"); },
            () -> "platform 3");
    try (ExecutorService desk = Executors.newFixedThreadPool(3)) {
        List<Future<String>> slips = desk.invokeAll(enquiries);   // does one torn card stop the others?
        IO.println("invokeAll returned " + slips.size() + " slips");
        for (int n = 1; n <= slips.size(); n++) {
            try {
                IO.println("slip " + n + ": " + slips.get(n - 1).get());
            } catch (ExecutionException e) {
                IO.println("slip " + n + ": " + e.getCause());
            }
        }
    }
}
