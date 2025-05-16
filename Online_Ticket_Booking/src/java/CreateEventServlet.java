
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */



/**
 *
 * @author parid
 */
@WebServlet("/createEvent")
public class CreateEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int adminId = (int) session.getAttribute("userId");

        String name = req.getParameter("name");
        String datetime = req.getParameter("datetime");
        String venue = req.getParameter("venue");

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO events (event_name, date_time, venue, created_by) VALUES (?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, datetime);
            stmt.setString(3, venue);
            stmt.setInt(4, adminId);
            stmt.executeUpdate();
            res.sendRedirect("admin_dashboard.jsp?success=1");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}