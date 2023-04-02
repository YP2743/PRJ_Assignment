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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author truon
 */
public class DeleteServlet extends HttpServlet {

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

        String username = request.getParameter("pk");
        String lastSearchValue = request.getParameter("lastSearchValue");
        String urlRewritting = MyAppConstants.DeleteAccountFeatures.ERROR_PAGE;
        HttpSession session = request.getSession(false);
        AccountDTO user = (AccountDTO) session.getAttribute("USER");
        try {
            //1.Call model
            AccountDAO dao = new AccountDAO();
            //2.Call Method
            boolean result = dao.deleteAccount(username);
            if (result) {
                //3. If username is equal to username in the session, trigger LogoutServlet
                if (username.equals(user.getUsername())) {
                    urlRewritting = MyAppConstants.LoginFeatures.LOGOUT_CONTROLLER;
                } else {
                    urlRewritting = MyAppConstants.SearchAccountFeatures.SEARCH_CONTROLLER
                            + "?txtSearchValue=" + lastSearchValue;
                }
            }// end result 
        } catch (SQLException ex) {
            log("DeleteServlet _ SQL _ " + ex.getMessage());
        } catch (NamingException ex) {
            log("DeleteServlet _ Naming _ " + ex.getMessage());
        } finally {
            response.sendRedirect(urlRewritting);
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
