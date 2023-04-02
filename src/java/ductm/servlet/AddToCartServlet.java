/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.servlet;

import ductm.cart.CartAddItemError;
import ductm.cart.CartObj;
import ductm.product.ProductDAO;
import ductm.product.ProductDTO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author truon
 */
public class AddToCartServlet extends HttpServlet {

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
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        String url = siteMaps.getProperty(MyAppConstants.ShoppingCartFeatures.SHOPPING_CONTROLLER);
        CartAddItemError error = new CartAddItemError();
        boolean foundError = false;
        try {
            //1. Customer goes to cart place
            HttpSession session = request.getSession();

            //2. Customer takes his/her cart
            CartObj cart = (CartObj) session.getAttribute("CART");
            String item = request.getParameter("ddlBook");
            ProductDAO dao = new ProductDAO();
            ProductDTO dto = dao.getProductBySku(item);
            int amountToAdd = Integer.parseInt(request.getParameter("txtQuantity"));

            //3. Check if cart is null
            if (cart == null) {
                cart = new CartObj();
                cart.addItemToCart(dto, amountToAdd);
                session.setAttribute("CART", cart);
                return;
            }

            //4. Add items to cart
            if (cart.getItems() == null) {
                cart = new CartObj();
                cart.addItemToCart(dto, amountToAdd);
                session.setAttribute("CART", cart);
                return;
            }

            if (cart.getItems().get(dto.getName()) == null) {
                cart.addItemToCart(dto, amountToAdd);
                return;
            }

            //5. Check product quantity in cart + amountToAdd and product quantity in database
            int cartQuantity = cart.getItems().get(dto.getName());
            int productQuantity = dto.getQuantity();
            if (cartQuantity + amountToAdd > productQuantity) {
                foundError = true;
                error.setInvalidQuantity("Product " + dto.getName() + " has only "
                        + (productQuantity - cartQuantity) + " left! You already have "
                        + cartQuantity + " in your cart");
            }
            if (foundError) {
                request.setAttribute("ADD_TO_CART_ERROR", error);
            } else {
                cart.addItemToCart(dto, amountToAdd);
                session.setAttribute("CART", cart);
            }
        } catch (NamingException ex) {
            log("AddToCartServlet _ Naming _ " + ex.getMessage());
        } catch (SQLException ex) {
            log("AddToCartServlet _ SQL _ " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
