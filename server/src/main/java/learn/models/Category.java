package learn.models;

public enum Category {
    FLOWERS("Flowers"),
    TREES("Trees"),
    SHRUBS("Shrubs"),
    EDIBLES("Edibles"),
    OTHER("Other");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
