package com.mohallekedukandar;

import java.util.Date;

public class Qunatity {

    private  String Product_Id;
    private int Qunatity_P;
    private Date created;
    private Date updated;
    private String objectId;

    public String getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(String product_Id) {
        Product_Id = product_Id;
    }

    public int getQunatity_P() {
        return Qunatity_P;
    }

    public void setQunatity_P(int qunatity_P) {
        Qunatity_P = qunatity_P;
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
