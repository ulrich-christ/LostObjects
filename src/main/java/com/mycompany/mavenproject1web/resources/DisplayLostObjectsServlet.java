

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenproject1web.resources;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/displayLostObjects")
public class DisplayLostObjectsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Connect to the database
            Connection conl = DriverManager.getConnection("jdbc:mysql://localhost:3306/se3db", "root", "");

            // Create a SQL statement to retrieve all objects from the lostobjects table
            Statement statement = conl.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lostobjects");

            // Set the content type of the response
            response.setContentType("text/html");

            // Write the retrieved objects to the response
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no'>");
            out.println("<link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css' rel='stylesheet'>");
            out.println("<title>Lost Objects</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<style>");
            out.println("button {  }");
            out.println("th, td { padding: 15px; text-align: left; }");
            out.println(".btn-delete { background-color: #f44336; color: white; border: none; }");
            out.println("body { backgroud-color:green;}");
            out.println(".btn { padding: 10px 20px; margin: 5px; text-align: center; text-decoration: none; display: inline-block; font-size: 14px; cursor: pointer; }");
            out.println(".btn-update { background-color: #4CAF50; color: white; border: none; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("</style>");
            out.println("<h2 class='mt-4'>Lost Objects</h2>");
            out.println("<table class='table table-striped table-bordered'>");
            out.println("<thead class='thead-dark'><tr><th>Name</th><th>Description</th><th>Contact</th><th>Location</th><th>Image</th><th>Action</th></tr></thead>");
            out.println("<tbody>");
            while (resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getString("name") + "</td>");
                out.println("<td>" + resultSet.getString("description") + "</td>");
                out.println("<td>" + resultSet.getString("contact") + "</td>");
                out.println("<td>" + resultSet.getString("location") + "</td>");
                out.println("<td><img src='displayImage?id=" + resultSet.getInt("id") + "' alt='Lost Object Image' style='max-width: 100px;'></td>");
                out.println("<td>");
                out.println("<button type='button' class='btn btn-primary btn-sm' onclick='openModal(" + resultSet.getInt("id") + ", \"" + resultSet.getString("name") + "\", \"" + resultSet.getString("description") + "\", \"" + resultSet.getString("contact") + "\", \"" + resultSet.getString("location") + "\")'>Update</button>");
                out.println("<form action='LostObjectServlet' method='post' style='display:inline;'>");
                out.println("<input type='hidden' name='id' value='" + resultSet.getInt("id") + "'>");
                out.println("<input type='hidden' name='action' value='delete'>");
                out.println("<button type='submit' class='btn btn-danger btn-sm'>Delete</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</tbody>");
            out.println("</table>");
            // Modal for updating objects
            out.println("<div class='modal fade' id='updateModal' tabindex='-1' role='dialog' aria-labelledby='updateModalLabel' aria-hidden='true'>");
            out.println("<div class='modal-dialog' role='document'>");
            out.println("<div class='modal-content'>");
            out.println("<div class='modal-header'>");
            out.println("<h5 class='modal-title' id='updateModalLabel'>Update Lost Object</h5>");
            out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
            out.println("<span aria-hidden='true'>&times;</span>");
            out.println("</button>");
            out.println("</div>");
            out.println("<div class='modal-body'>");
            out.println("<form id='updateForm' action='LostObjectServlet' method='post' enctype='multipart/form-data'>");
            out.println("<div class='form-group'>");
            out.println("<label for='name'>Name</label>");
            out.println("<input type='text' class='form-control' id='updateName' name='name' required>");
            out.println("</div>");
            out.println("<div class='form-group'>");
            out.println("<label for='description'>Description</label>");
            out.println("<input type='text' class='form-control' id='updateDescription' name='description' required>");
            out.println("</div>");
            out.println("<div class='form-group'>");
            out.println("<label for='contact'>Contact</label>");
            out.println("<input type='text' class='form-control' id='updateContact' name='contact' required>");
            out.println("</div>");
            out.println("<div class='form-group'>");
            out.println("<label for='location'>Location</label>");
            out.println("<input type='text' class='form-control' id='updateLocation' name='location' required>");
            out.println("</div>");
            out.println("<div class='form-group'>");
            out.println("<label for='image'>Image</label>");
            out.println("<input type='file' class='form-control' id='updateImage' name='image'>");
            out.println("</div>");
            out.println("<input type='hidden' id='updateId' name='id'>");
            out.println("<input type='hidden' name='action' value='update'>");
            out.println("<button type='submit' class='btn btn-primary'>Save changes</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

            out.println("<script src='https://code.jquery.com/jquery-3.2.1.slim.min.js'></script>");
            out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js'></script>");
            out.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js'></script>");
            out.println("<script>");
            out.println("function openModal(id, name, description, contact, location) {");
            out.println("document.getElementById('updateId').value = id;");
            out.println("document.getElementById('updateName').value = name;");
            out.println("document.getElementById('updateDescription').value = description;");
            out.println("document.getElementById('updateContact').value = contact;");
            out.println("document.getElementById('updateLocation').value = location;");
            out.println("$('#updateModal').modal('show');");
            out.println("}");
            out.println("</script>");
            out.println("</body></html>");

            // Close the connection
            resultSet.close();
            statement.close();
            conl.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

