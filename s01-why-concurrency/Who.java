void main() {
    Thread.getAllStackTraces().keySet().stream()
            .map(Thread::getName)
            .sorted()
            .forEach(IO::println);
}
