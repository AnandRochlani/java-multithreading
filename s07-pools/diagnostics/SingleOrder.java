void main() {
    List<Integer> order = Collections.synchronizedList(new ArrayList<>());
    try (ExecutorService desk = Executors.newSingleThreadExecutor()) {
        for (int n = 1; n <= 10; n++) {
            int card = n;
            desk.execute(() -> order.add(card));
        }
    }
    IO.println(order);
}
