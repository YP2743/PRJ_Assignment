/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ductm.servlet;

import ductm.cart.CartObj;
import ductm.detail.DetailDAO;
import ductm.detail.DetailDTO;
import ductm.order.OrderDAO;
import ductm.order.OrderDTO;
import ductm.order.OrderInputError;
import ductm.product.ProductDAO;
import ductm.utils.MyAppConstants;
import ductm.utils.MyUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class CheckOutServlet extends HttpServlet {

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
        String name = request.getParameter("txtCheckOutName");
        String address = request.getParameter("txtAddress");
        OrderInputError error = new OrderInputError();
        boolean foundError = false;
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAP");
        String url = siteMaps.getProperty(MyAppConstants.ShoppingCartFeatures.VIEW_CART_PAGE);
        
        //1. New Http Session
        HttpSession session = request.getSession();
        if (session != null) {
            
            //2. Create cart
            CartObj cart = (CartObj) session.getAttribute("CART");
            if (cart != null) {
                
                try {

                    //3. Check name and address length
                    if (name.trim().length() > 50) {
                        foundError = true;
                        error.setNameLengthError("Name is allowded atmost 50 characters");
                    }
                    if (address.trim().length() > 250) {
                        foundError = true;
                        error.setAddressLengthError("Address is allowded atmost 250 characters");
                    }

                    // If error, system throws error. Else continue
                    if (foundError) {
                        request.setAttribute("ORDER_ERROR", error);
                    } else {
                        //4. Call DAO models
                        ProductDAO product_dao = new ProductDAO();
                        OrderDAO order_dao = new OrderDAO();
                        DetailDAO detail_dao = new DetailDAO();

                        //5. Create OrderDTO to be inserted into the Order Table
                        OrderDTO order_dto = new OrderDTO(name, address, Timestamp.valueOf(LocalDateTime.now()), MyUtils.calculateTotal(cart));
                        order_dao.createOrder(order_dto);

                        //6. Initialize a Detail List for checkout.jsp
                        List<DetailDTO> detail_list = new ArrayList<>();
                        String order_id = order_dto.getId();

                        //7. Iterate over the cart to create DetailDTOs to be inserted into the Detail Table and added into the Detail List
                        for (String key : cart.getItems().keySet()) {
                            int sku = product_dao.getSkuByName(key);
                            int quantity = cart.getItems().get(key);
                            float price = product_dao.getPriceByName(key);
                            float total = quantity * price;
                            DetailDTO detail_dto = new DetailDTO(order_id, key, sku, quantity, price, total);
                            detail_dao.createDetail(detail_dto);
                            detail_list.add(detail_dto);

                            //8. Update the remaining quantity in Product Table
                            product_dao.updateQuantity(detail_dto);
                        }

                        //9. Set the required attributes for checkout.jsp
                        request.setAttribute("ORDER", order_dto);
                        session.setAttribute("DETAILS", detail_list);

                        //10. End cart session
                        session.setAttribute("CART", null);

                        url = siteMaps.getProperty(MyAppConstants.CheckOutFeatures.CHECKOUT_PAGE);
                    }
                } catch (SQLException ex) {
                    log("CheckOutServlet _ SQL _ " + ex.getMessage());
                } catch (NamingException ex) {
                    log("CheckOutServlet _ Naming _ " + ex.getMessage());
                } finally {
                    RequestDispatcher rd = request.getRequestDispatcher(url);
                    rd.forward(request, response);
                }
            }//end cart not null            
        }//end session not null
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
