/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.cart;

/**
 *
 * @author truon
 */
public class CartAddItemError {
    private String invalidQuantity;

    public CartAddItemError() {
    }
    

    /**
     * @return the invalidQuantity
     */
    public String getInvalidQuantity() {
        return invalidQuantity;
    }

    /**
     * @param invalidQuantity the invalidQuantity to set
     */
    public void setInvalidQuantity(String invalidQuantity) {
        this.invalidQuantity = invalidQuantity;
    }
}
