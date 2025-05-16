
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/cancelTicket")
public class CancelTicketServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int ticketId = Integer.parseInt(req.getParameter("ticketId"));

        try (Connection con = DBConnection.getConnection()) {
            // Update ticket status
            PreparedStatement stmt = con.prepareStatement(
                "UPDATE tickets SET status = 'cancelled' WHERE ticket_id = ?"
            );
            stmt.setInt(1, ticketId);
            stmt.executeUpdate();

            // Optionally update payment status
            PreparedStatement pay = con.prepareStatement(
                "UPDATE payments SET status = 'failed' WHERE ticket_id = ?"
            );
            pay.setInt(1, ticketId);
            pay.executeUpdate();

            res.sendRedirect("user_dashboard.jsp?cancelled=1");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
