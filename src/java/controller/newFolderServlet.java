/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ftp.FTPService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author macbookpro
 */
public class newFolderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet newFolderServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet newFolderServlet at " + request.getContextPath() + "</h1>");
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

//        System.out.println(folderName);
//       long numOfSplash = folderName.chars().filter(ch -> ch == '/').count();
//       if(numOfSplash < 1){
//           request.getRequestDispatcher("listFileServlet").forward(request, response);
//       }
//       else {
//           request.setAttribute("folderName", folderName);
//           request.getRequestDispatcher("listFolderServlet").forward(request, response);
//           
//       }
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
        String folderName = request.getParameter("folderName");
        System.out.println(folderName);
        String newFolderName = request.getParameter("newFolderName");
        System.out.println(newFolderName);
         ArrayList<String> replyServer = (ArrayList<String>) request.getSession().getAttribute("replyServer");
        if (folderName == null) {
               FTPService.makeNewFolder(newFolderName);
             
               FTPService.showServerReply2(FTPService.getFtpClientGlobal(), replyServer);
            request.getRequestDispatcher("listFileServlet").forward(request, response);
        } else {
              FTPService.makeNewFolder(folderName + "/" + newFolderName);
               FTPService.showServerReply2(FTPService.getFtpClientGlobal(), replyServer);
            request.setAttribute("folderName", folderName);
            request.getRequestDispatcher("listFolderServlet").forward(request, response);
        }
        request.getSession().setAttribute("replyServer", replyServer);
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
