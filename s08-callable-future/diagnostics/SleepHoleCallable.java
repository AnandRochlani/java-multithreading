void main() {
    Callable<String> seatFinder = () -> {                         // one word changed, but nothing is returned
        Thread.sleep(300);                                        // the seat system takes a while
    };
}
