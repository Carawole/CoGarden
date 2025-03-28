package learn.models;

public enum OrderStatus {
    PENDING("Pending"),
    PROCESSING("Processing"),
    CANCELLED("Cancelled"),
    COMPLETED("Completed");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static OrderStatus fromString(String name) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getName().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
