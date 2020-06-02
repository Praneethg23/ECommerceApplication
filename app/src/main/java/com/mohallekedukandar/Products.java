package com.mohallekedukandar;

import java.util.Date;

public class Products {

    private String Product_Id;
    private String Name;
    private String Category;
    private String Package_Quantity;
    private int Price;
    private Date created;
    private Date updated;
    private String objectId;

    public String getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(String product_Id) {
        Product_Id = product_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getPackage_Quantity() {
        return Package_Quantity;
    }

    public void setPackage_Quantity(String package_Quantity) {
        Package_Quantity = package_Quantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }



    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
