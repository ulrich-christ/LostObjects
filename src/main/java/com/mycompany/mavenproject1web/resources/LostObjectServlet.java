/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1web.resources;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/LostObjectServlet")
@MultipartConfig
public class LostObjectServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("update".equals(action)) {
            updateLostObject(request, response);
        } else if ("delete".equals(action)) {
            deleteLostObject(request, response);
        }
    }

    private void updateLostObject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String contact = request.getParameter("contact");
        String location = request.getParameter("location");
        Part imagePart = request.getPart("image");

        String updateSQL = "UPDATE lostobjects SET name = ?, description = ?, contact = ?, location = ?, image = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se3db", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, contact);
            preparedStatement.setString(4, location);
            
            if (imagePart != null && imagePart.getSize() > 0) {
                InputStream imageInputStream = imagePart.getInputStream();
                preparedStatement.setBlob(5, imageInputStream);
            } else {
                preparedStatement.setNull(5, java.sql.Types.BLOB);
            }

            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
            response.sendRedirect("displayLostObjects");

        } catch (SQLException e) {
            throw new ServletException("Unable to update lost object", e);
        }
    }

    private void deleteLostObject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String deleteSQL = "DELETE FROM lostobjects WHERE id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/se3db", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            response.sendRedirect("displayLostObjects");

        } catch (SQLException e) {
            throw new ServletException("Unable to delete lost object", e);
        }
    }
}
