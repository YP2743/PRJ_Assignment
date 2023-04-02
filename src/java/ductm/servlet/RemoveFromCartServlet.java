/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.servlet;

import ductm.cart.CartObj;
import ductm.utils.MyAppConstants;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author truon
 */
public class RemoveFromCartServlet extends HttpServlet {

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
        try {
            //1. Customer goes to cart
            HttpSession session = request.getSession(false);
            if (session == null) {
                return;
            }

            //2.Customer takes his/her cart
            CartObj cart = (CartObj) session.getAttribute("CART");
            if (cart == null) {
                return;
            }

            //3. Customer gets all items
            Map<String, Integer> items = cart.getItems();
            if (items == null) {
                return;
            }

            //4. Remove items from cart
            String[] selectedItems = request.getParameterValues("chkItem");
            if (selectedItems == null) {
                return;
            }

            for (String item : selectedItems) {
                cart.removeItemFromCart(item, 1);
            }// end removing one item
            session.setAttribute("CART", cart);
        } finally {
            //5. Refresh page by calling view cart feature again using url rewriting
            String urlRewrting = MyAppConstants.ShoppingCartFeatures.VIEW_CART_PAGE;
            response.sendRedirect(urlRewrting);
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
