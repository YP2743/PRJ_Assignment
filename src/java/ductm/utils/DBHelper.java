/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author truon
 */
public class DBHelper implements Serializable {

    public static Connection makeConnection()
            throws NamingException, SQLException {
        //1.Get current context
        Context context = new InitialContext();
        //2.Look up server context
        Context tomcat = (Context) context.lookup("java:comp/env");
        //3.Look up datasource
        DataSource ds = (DataSource) tomcat.lookup("DUC_DB");
        //4.Open connection
        Connection con = ds.getConnection();
        //5.Return connection
        return con;
    }
}
