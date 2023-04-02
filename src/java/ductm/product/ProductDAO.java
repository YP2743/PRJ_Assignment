/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.product;

import ductm.detail.DetailDTO;
import ductm.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author truon
 */
public class ProductDAO implements Serializable {

    public List<ProductDTO> getProductList()
            throws SQLException, NamingException {
        List<ProductDTO> productList = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Select sku, name, description, price, quantity, status "
                        + "From Product "
                        + "Where status = 1 And quantity > 0 ";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                //4. Execute Statement to result
                rs = stm.executeQuery();
                //5. Process Result
                while (rs.next()) {
                    int sku = rs.getInt("sku");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");

                    ProductDTO dto = new ProductDTO(sku, name, description, price, quantity, status);
                    if (productList == null) {
                        productList = new ArrayList<>();
                    }//end account List has not existed
                    productList.add(dto);
                }//end traverse rs to EOF
            }//end connection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return productList;
    }

    public ProductDTO getProductBySku(String sku)
            throws SQLException, NamingException {
        List<ProductDTO> productList = null;
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        ProductDTO result = null;
        try {
            //1. Connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Select sku,name,description,price,quantity,status "
                        + "From Product "
                        + "Where sku = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                int skuID = Integer.parseInt(sku);
                stm.setInt(1, skuID);
                //4. Execute Statement to result
                rs = stm.executeQuery();
                //5. Process Result
                while (rs.next()) {
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    boolean status = rs.getBoolean("status");

                    ProductDTO dto = new ProductDTO(skuID, name, description, price, quantity, status);
                    if (productList == null) {
                        productList = new ArrayList<>();
                    }//end account List has not existed
                    productList.add(dto);
                    result = dto;
                }//end traverse rs to EOF
            }//end connection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public float getPriceByName(String name)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Float result = null;
        try {
            //1. Connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Select price "
                        + "From Product "
                        + "Where name = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                //4. Execute Statement to result
                rs = stm.executeQuery();
                //5. Process Result
                while (rs.next()) {
                    result = rs.getFloat("price");
                }//end account List has not existed
            }//end traverse rs to EOF
        }//end connection is existed
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public int getSkuByName(String name)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Integer result = null;
        try {
            //1. Connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Select sku "
                        + "From Product "
                        + "Where name = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                //4. Execute Statement to result
                rs = stm.executeQuery();
                //5. Process Result
                while (rs.next()) {
                    result = rs.getInt("sku");
                }//end account List has not existed
            }//end traverse rs to EOF
        }//end connection is existed
        finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean updateQuantity(DetailDTO dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1. Connect DB
            con = DBHelper.makeConnection();
            if (con != null) {
                //2. Write SQL command
                String sql = "Update Product "
                        + "Set quantity = quantity - ? "
                        + "Where sku = ?";
                //3. Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setInt(1, dto.getQuantity());
                stm.setInt(2, dto.getSku());
                //4. Execute Statement to result
                int effectiveRows = stm.executeUpdate();
                //5. Process Result
                if (effectiveRows > 0) {
                    result = true;
                }//end account List has not existed
            }//end traverse rs to EOF
        }//end connection is existed
        finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
}
