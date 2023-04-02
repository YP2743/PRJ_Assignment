/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.utils;

import ductm.cart.CartObj;
import ductm.product.ProductDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.naming.NamingException;

/**
 *
 * @author truon
 */
public class MyUtils {

    public static String randomID() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static Float calculateTotal(CartObj cart) throws NamingException, SQLException {
        Float result = new Float(0);
        ProductDAO dao = new ProductDAO();
        for (String key : cart.getItems().keySet()) {
            result += cart.getItems().get(key) * dao.getPriceByName(key);
        }
        return result;
    }
}
