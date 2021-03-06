/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ftp.FTPService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin123
 */
public class loginServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet loginServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        try {
            String username = request.getParameter("username");
        String password = request.getParameter("password");
        String host = request.getParameter("host");
         int choice = Integer.valueOf(request.getParameter("choice"));
        
        int port = Integer.valueOf(request.getParameter("port"));
        
         FTPService fTPService = new FTPService(host, port, username, password);
        
           int check = fTPService.getConnectionServer(choice);
           ArrayList<String> reply = new ArrayList<String>();
          
           
           SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
           reply.add("220 (vsFTPd 3.0.3) at " + formatter.format(date));
         //  ftpClient.getLocalPort()+"/"+ftpClient.getLocalAddress()
           reply.add("Data Port : " +fTPService.getFtpClient().getLocalPort());
           reply.add("IP Client : " +fTPService.getFtpClient().getLocalAddress());
           
           fTPService.showServerReply2(fTPService.getFtpClient(),reply);
            for (String string : reply) {
                System.out.println("Reply in Controller :" + string);
            }
           request.getSession().setAttribute("replyServer", reply);
           if(check == 0 || check == -1){
               request.setAttribute("messageLogin","FTP server not respond!");
               request.getRequestDispatcher("login.jsp").forward(request, response);
           }
           else if (check == 1){
               request.setAttribute("messageLogin","Username or Password incorrect");
               request.getRequestDispatcher("login.jsp").forward(request, response);
           }
           else if(check == 2){
                if(choice == 1)
               reply.add("Data connection - Active Mode ");
                else if(choice == 2)
                reply.add("Data connection - Passive Mode ");
           
           
             //  request.getRequestDispatcher("listFileServlet").forward(request, response);
                response.sendRedirect("listFileServlet");
           }
        
        } catch (NumberFormatException e) {
            request.setAttribute("messageLogin","Port must be a integer");
               request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        

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
