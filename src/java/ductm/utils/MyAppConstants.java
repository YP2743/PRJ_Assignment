/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.utils;

/**
 *
 * @author truon
 */
public class MyAppConstants {
    public class LoginFeatures {
        public static final String INVALID_PAGE = "invalidPage";
        public static final String SEARCH_PAGE = "searchPage";
        public static final String LOGOUT_CONTROLLER="logout";
    }

    public class DeleteAccountFeatures{
        public static final String ERROR_PAGE = "errorPage";
    }
    
    public class SearchAccountFeatures{
        public static final String SEARCH_CONTROLLER = "search";
    }

    public class CreateNewAccountFeatures{
        public static final String CREATE_NEW_ACCOUNT_PAGE_STATIC="createNewAccountPageStatic";
        public static final String CREATE_NEW_ACCOUNT_PAGE_DYNAMIC="createNewAccountPageDynamic";
        public static final String LOGIN_PAGE="loginPage";
    }
    
    public class ShoppingCartFeatures{
        public static final String SHOPPING_CONTROLLER = "shopping";
        public static final String SHOPPING_PAGE = "shoppingPage";
        public static final String VIEW_CART_PAGE="viewCartPage"; 
    }
    
    public class CheckOutFeatures{
        public static final String CHECKOUT_PAGE="checkOutPage";
    }
}
