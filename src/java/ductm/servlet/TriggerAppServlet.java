/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.servlet;

import ductm.account.AccountDAO;
import ductm.account.AccountDTO;
import ductm.utils.MyAppConstants;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author truon
 */
public class TriggerAppServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = MyAppConstants.CreateNewAccountFeatures.LOGIN_PAGE;
        try {
            //1.Get cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //2.Get last cookie
                Cookie lastCookie = cookies[cookies.length - 1];
                String username = lastCookie.getName();
                String password = lastCookie.getValue();
                //3.Call model
                AccountDAO dao = new AccountDAO();
                AccountDTO result = dao.checkLogin(username, password);

                if (result != null) {
                    url = MyAppConstants.SearchAccountFeatures.SEARCH_CONTROLLER;
                }
            }
        } catch (SQLException ex) {
            log("TriggerAppServlet _ SQL _ " + ex.getMessage());
        } catch (NamingException ex) {
            log("TriggerAppServlet _ Naming _ " + ex.getMessage());
        } finally {
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
