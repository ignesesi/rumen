import java.util.ArrayList;

public class Board {
    private ArrayList<BoardItem> items;

    public Board() {
        items = new ArrayList<>();
    }

    public void addItem(BoardItem item) {
        if (items.contains(item)) {
            throw new IllegalArgumentException("Item already in the list.");
        }
        items.add(item);
    }

    public int totalItems() {
        return items.size();
    }
}
