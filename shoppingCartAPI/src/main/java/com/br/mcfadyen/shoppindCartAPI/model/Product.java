package com.br.mcfadyen.shoppindCartAPI.model;

import java.math.BigDecimal;

public class Product{
    private String id;
    private String name;
    private String image;
    private BigDecimal price;

    public Product(String id, String name, String image, BigDecimal price){
        this.id = id;
        this.name  = name;
        this.image = image;
        this.price = price;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    } 
    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}