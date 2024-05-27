/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1web.resources;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author PC
 */
//@WebServlet(name = "register", urlPatterns = {"/register"})
public class register123 extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("username");
        String pwd = request.getParameter("password");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conl = DriverManager.getConnection("jdbc:mysql://localhost/se3db","root","");
            PreparedStatement stm = conl.prepareStatement("SELECT * FROM students WHERE name='"+name+"' ");
            ResultSet Rs = stm.executeQuery();
            System.out.println("Student already exists !");
            if (Rs.next()) {
                System.out.println("Student already exists !");
                response.sendRedirect("register_error.html");
            }
            else {
                PreparedStatement insert = conl.prepareStatement("INSERT INTO students(name, password) VALUES(?,?)");
                insert.setString(1, name);
                insert.setString(2, pwd);
                insert.executeUpdate();
                    response.sendRedirect("index.html");
                    System.out.println("Success Inserting new student !");
            }
        }
        catch (Exception e) {
             
             System.out.println(e.getMessage());
            response.sendRedirect("register.html");
        }
    }

}
