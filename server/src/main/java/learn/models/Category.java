package learn.models;

public enum Category {
    FLOWERS("Flowers"),
    TREES("Trees"),
    SHRUBS("Shrubs"),
    EDIBLES("Edibles"),
    SUCCULENTS("Succulents"),
    OTHER("Other");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Category fromString(String name) {
        for (Category category : Category.values()) {
            if (category.name.equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }
}
