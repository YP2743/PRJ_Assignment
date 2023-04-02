/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.detail;

import java.io.Serializable;

/**
 *
 * @author truon
 */
public class DetailDTO implements Serializable{
    private String order_id;
    private String item_name;
    private int sku;
    private int quantity;
    private float price;
    private float total;

    public DetailDTO() {
    }

    public DetailDTO(String order_id, String item_name, int sku, int quantity, float price, float total) {
        this.order_id = order_id;
        this.item_name = item_name;
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    /**
     * @return the order_id
     */
    public String getOrder_id() {
        return order_id;
    }

    /**
     * @param order_id the order_id to set
     */
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    /**
     * @return the sku
     */
    public int getSku() {
        return sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(int sku) {
        this.sku = sku;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * @return the item_name
     */
    public String getItem_name() {
        return item_name;
    }

    /**
     * @param item_name the item_name to set
     */
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
