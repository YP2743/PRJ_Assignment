/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.servlet;

import ductm.account.AccountDAO;
import ductm.account.AccountUpdateError;
import ductm.utils.MyAppConstants;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author truon
 */
public class UpdateServlet extends HttpServlet {

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

        String username = request.getParameter("txtUsername");
        String lastSearchValue = request.getParameter("lastSearchValue");
        String password = request.getParameter("txtPassword");
        String checkAdmin = request.getParameter("checkRole");
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        AccountUpdateError error = new AccountUpdateError();
        boolean foundError = false;
        boolean isAdmin = false;

        if (checkAdmin != null) {
            isAdmin = true;
        }
        String urlRewritting = MyAppConstants.DeleteAccountFeatures.ERROR_PAGE;

        try {
            if (password.trim().length() < 6 || password.trim().length() > 32) {
                foundError = true;
                error.setPasswordUpdateError("Password is required from 6 to 30 characters");
            }
            if (foundError) {
                urlRewritting = siteMaps.getProperty(MyAppConstants.SearchAccountFeatures.SEARCH_CONTROLLER)
                        + "?txtSearchValue=" + lastSearchValue;
                request.setAttribute("UPDATE_ERROR", error);
            } else {
                //1.Call model
                AccountDAO dao = new AccountDAO();
                //2.Call Method
                boolean result = dao.updateAccount(username, password, isAdmin);
                if (result) {
                    urlRewritting = MyAppConstants.SearchAccountFeatures.SEARCH_CONTROLLER
                            + "?txtSearchValue=" + lastSearchValue;
                }
            }
        } catch (SQLException ex) {
            log("UpdateServlet _ SQL _ " + ex.getMessage());
        } catch (NamingException ex) {
            log("UpdateServlet _ Naming _ " + ex.getMessage());
        } finally {
            if (foundError) {
                RequestDispatcher rd = request.getRequestDispatcher(urlRewritting);
                rd.forward(request, response);
            } else {
                response.sendRedirect(urlRewritting);
            }
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
