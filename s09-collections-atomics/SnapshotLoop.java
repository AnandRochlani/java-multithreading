void main() {
    List<String> boards = new ArrayList<>(List.of("board-1", "board-2", "board-3"));
    try {
        for (String board : boards) {                             // walk the list...
            if (board.equals("board-2")) {
                boards.add("board-4");                            // ...and add to it mid-walk
            }
        }
    } catch (ConcurrentModificationException e) {
        IO.println("ArrayList           : " + e);
    }

    List<String> copying = new CopyOnWriteArrayList<>(List.of("board-1", "board-2", "board-3"));
    int visited = 0;
    for (String board : copying) {                                // walk the list...
        visited++;
        copying.add(board + "-new");                              // ...and add to it on EVERY step
    }
    IO.println("CopyOnWriteArrayList: the walk visited " + visited + ", the list now holds " + copying.size());
    IO.println("                      " + copying);
}
