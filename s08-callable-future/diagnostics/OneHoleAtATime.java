void main() {                                                     // two holes, but javac reports one
    Runnable seatFinder = () -> {
        return "S7-42";
    };
    Runnable fareQuote = () -> {
        Thread.sleep(200);
    };
}
