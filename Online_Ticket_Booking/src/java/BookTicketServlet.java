

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet("/bookTicket")
public class BookTicketServlet extends HttpServlet {

    /**
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userId = (int) session.getAttribute("userId");
        int eventId = Integer.parseInt(req.getParameter("eventId"));

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO tickets (event_id, user_id, booking_time, status) VALUES (?, ?, NOW(), 'booked')", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, eventId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int ticketId = rs.getInt(1);
                // Proceed to payment
                PreparedStatement pay = con.prepareStatement("INSERT INTO payments (ticket_id, amount, payment_time, status) VALUES (?, ?, NOW(), 'paid')");
                pay.setInt(1, ticketId);
                pay.setDouble(2, 499.99); // Static amount for simplicity
                pay.executeUpdate();
            }
            res.sendRedirect("user_dashboard.jsp?booked=1");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
