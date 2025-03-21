package learn.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private int productId;
    private String productName;
    private Category category;
    private String description;
    private String cycle;
    private String watering;
    private String sunlight;
    private int hardinessZone;
    private BigDecimal price;

    public Product() {
    }

    public Product( int productId, String productName, Category category,  String description) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.description = description;
    }


    public Product(int productId, String productName, Category category, String description, String cycle, String watering, String sunlight,  int hardinessZone, BigDecimal price) {
        this.category = category;
        this.cycle = cycle;
        this.description = description;
        this.hardinessZone = hardinessZone;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.sunlight = sunlight;
        this.watering = watering;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHardinessZone() {
        return hardinessZone;
    }

    public void setHardinessZone(int hardinessZone) {
        this.hardinessZone = hardinessZone;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSunlight() {
        return sunlight;
    }

    public void setSunlight(String sunlight) {
        this.sunlight = sunlight;
    }

    public String getWatering() {
        return watering;
    }

    public void setWatering(String watering) {
        this.watering = watering;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && Objects.equals(productName, product.productName) && category == product.category && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, category, price);
    }
}
