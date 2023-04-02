/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.account;

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
public class AccountDAO implements Serializable {

    public AccountDTO checkLogin(String username, String password)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        AccountDTO result = null;
        try {
            //1.Create connection and Check
            con = DBHelper.makeConnection();
            if (con != null) {
                //2.Create SQL String
                String sql = "Select username, lastname, isAdmin "
                        + "From Account "
                        + "Where username = ? And password = ?";
                //3.Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4.Execute statement to get Result
                rs = stm.executeQuery();
                //5.Process Result
                if (rs.next()) {
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");

                    result = new AccountDTO(username, null, fullName, role);
                }
            } //end connection is existed
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

    private List<AccountDTO> accountList;

    public List<AccountDTO> getAccountList() {
        return accountList;
    }

    public void searchLastName(String searchValue)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            //1.Create connection and Check
            con = DBHelper.makeConnection();
            if (con != null) {
                //2.Create SQL String
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Account "
                        + "Where lastname Like ?";
                //3.Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4.Execute statement to get Result
                rs = stm.executeQuery();
                //5.Process Result
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastname = rs.getString("lastname");
                    boolean isAdmin = rs.getBoolean("isAdmin");

                    AccountDTO dto = new AccountDTO(
                            username, password, lastname, isAdmin);

                    if (this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    }

                    this.accountList.add(dto);
                }
            }
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
    }

    public boolean deleteAccount(String username)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1.Create connection and Check
            con = DBHelper.makeConnection();
            if (con != null) {
                //2.Create SQL String
                String sql = "Delete From Account "
                        + "Where username = ?";
                //3.Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4.Execute statement to get Result
                int effectiveRows = stm.executeUpdate();
                //5.Process Result
                if (effectiveRows > 0) {
                    result = true;
                }
            }
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

    public boolean updateAccount(String username, String password, boolean isAdmin)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1.Create connection and Check
            con = DBHelper.makeConnection();
            if (con != null) {
                //2.Create SQL String
                String sql = "Update Account "
                        + "Set password = ?, isAdmin = ? "
                        + "Where username = ?";
                //3.Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setBoolean(2, isAdmin);
                stm.setString(3, username);
                //4.Execute statement to get Result
                int effectiveRows = stm.executeUpdate();
                //5.Process Result
                if (effectiveRows > 0) {
                    result = true;
                }
            }
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

    public boolean createAccount(AccountDTO dto)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1.Create connection and Check
            con = DBHelper.makeConnection();
            if (con != null) {
                //2.Create SQL String
                String sql = "Insert Into Account("
                        + "username, password, lastname, isAdmin"
                        + ") Values("
                        + "?, ?, ?, ?"
                        + ")";
                //3.Create Statement
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getLastname());
                stm.setBoolean(4, dto.isIsAdmin());
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
