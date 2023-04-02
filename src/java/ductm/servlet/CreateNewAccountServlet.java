/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.servlet;

import ductm.account.AccountCreateError;
import ductm.account.AccountDAO;
import ductm.account.AccountDTO;
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
public class CreateNewAccountServlet extends HttpServlet {

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
        String password = request.getParameter("txtPassword");
        String fullname = request.getParameter("txtFullname");
        String confirm = request.getParameter("txtConfirm");
        AccountCreateError error = new AccountCreateError();
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        String url = siteMaps.getProperty(MyAppConstants.CreateNewAccountFeatures.CREATE_NEW_ACCOUNT_PAGE_DYNAMIC);
        boolean result = false;
        boolean foundError = false;

        try {
            //1. Check user errors
            if ((username.trim().length() < 6) || username.trim().length() > 20) {
                foundError = true;
                error.setUsernameLengthError("Username is required from 6 to 20 characters");
            }// end username check
            if ((password.trim().length() < 6)
                    || password.trim().length() > 30) {
                foundError = true;
                error.setPasswordLengthError("Password is required from 6 to 30 characters");
            }// end password check
            else if (!confirm.trim().equals(password.trim())) {
                foundError = true;
                error.setConfirmNotMatched("Confirm is not matched");
            } //end confirm check
            if ((fullname.trim().length() < 2)
                    || fullname.trim().length() > 50) {
                foundError = true;
                error.setFullnameLengthError("Fullname is required from 2 to 50 characters");
            }// end fullname check

            //2.
            //2.1: if user violated, system throws error
            //2.2: otherwise, call model
            if (foundError) {
                request.setAttribute("CREATE_ERROR", error);
            } else {
                //3. Call model
                AccountDAO dao = new AccountDAO();
                AccountDTO dto = new AccountDTO(username, password, fullname, false);
                result = dao.createAccount(dto);

                if (result) {
                    url = MyAppConstants.CreateNewAccountFeatures.LOGIN_PAGE;
                }//end user has been created
            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            log("CreateNewAccountServlet _ SQL _ " + ex.getMessage());
            if (msg.contains("duplicate")) {
                error.setUsernameIsExisted(username + " is existed");
                request.setAttribute("CREATE_ERROR", error);
            }//duplicate username
        } catch (NamingException ex) {
            log("CreateNewAccountServlet _ Naming _ " + ex.getMessage());
        } finally {
            if (result) {
                response.sendRedirect(url);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
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
