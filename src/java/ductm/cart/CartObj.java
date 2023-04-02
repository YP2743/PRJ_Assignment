/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.cart;

import ductm.product.ProductDTO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author truon
 */
public class CartObj implements Serializable {

    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }

//    public String returnName(String id){
//        
//    }
    public boolean addItemToCart(ProductDTO dto, Integer quantity) {
        boolean result = false;
        String id = dto.getName();
        //1. Check data validattion
        if (id == null) {
            return result;
        }
        if (id.trim().isEmpty()) {
            return result;
        }
        if (quantity <= 0) {
            return result;
        }
        //2. Check existed items
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        //3. Check existed item
        if (this.items.containsKey(id)) {
            Integer currentQuantity = this.items.get(id);
            quantity = quantity + currentQuantity;
        }// end item has existed
        //4. Update cart items
        this.items.put(id, quantity);
        result = true;

        return result;
    }

    public boolean removeItemFromCart(String id, int quantity) {
        boolean result = false;

        //1. Check data validattion
        if (id == null) {
            return result;
        }
        if (id.trim().isEmpty()) {
            return result;
        }
        if (quantity <= 0) {
            return result;
        }
        //2. Check existed items
        if (this.items == null) {
            return result;
        }
        //3. Check existed item
        if (!this.items.containsKey(id)) {
            return result;
        }
        //4. Remove items
        int currentQuantity = this.items.get(id);
        if (currentQuantity >= quantity) {
            quantity = currentQuantity - quantity;
        } else {
            return result;
        }
        if (quantity == 0) {
            this.items.remove(id);
            // check isEmpty de tranh loi
            if (this.items.isEmpty()) {
                this.items = null;
            }
        } else {
            this.items.put(id, quantity);
        }
        result = true;

        return result;
    }
}
