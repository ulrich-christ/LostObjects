/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1web.resources;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author PC
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
@MultipartConfig(maxFileSize = 16177215) // 16MB
public class register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String contact = request.getParameter("contact");
        String location = request.getParameter("location");

        // Get the image part from the request
        Part filePart = request.getPart("image");
        InputStream inputStream = null;
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conl = DriverManager.getConnection("jdbc:mysql://localhost:3306/se3db","root","");
            //PreparedStatement stm = conl.prepareStatement("SELECT * FROM lostobjects WHERE name=?");
          
                PreparedStatement insert = conl.prepareStatement("INSERT INTO lostobjects(id,name,description,contact,location,image) VALUES(0,?,?,?,?,?)");
                insert.setString(1, name);
                insert.setString(2, description);
                insert.setString(3, contact);
                insert.setString(4, location);
                
                if (inputStream != null) {
                    // Set the image data as a BLOB
                    insert.setBlob(5, inputStream);
                }
                
                insert.executeUpdate();
                response.sendRedirect("displayLostObjects");
                System.out.println("Thanks for inserting the object!");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect("register.html");
        }
    }
}

