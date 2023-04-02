/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.detail;

import ductm.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author truon
 */
public class DetailDAO implements Serializable{
    public boolean createDetail(DetailDTO dto)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1.Create connection and Check
            con = DBHelper.makeConnection();
            if (con != null) {
                //2.Create SQL String
                String sql = "Insert Into [tbl.Detail]("
                        + "order_id, sku, quantity, price, total"
                        + ") Values("
                        + "?, ?, ?, ?, ?"
                        + ")";
                //3.Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getOrder_id());
                stm.setInt(2, dto.getSku());
                stm.setInt(3, dto.getQuantity());
                stm.setFloat(4, dto.getPrice());
                stm.setFloat(5, dto.getTotal());
                //4.Execute statement to get Result
                int effectiveRows = stm.executeUpdate();
                //5.Process Result
                if (effectiveRows > 0) {
                    result = true;
                }
            } //end connection is existed
        } finally {
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
