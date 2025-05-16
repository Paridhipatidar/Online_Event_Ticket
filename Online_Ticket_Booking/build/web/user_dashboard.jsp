<%@ page import="java.sql.*, util.DBConnection" %>
<%
    Connection con = null;
    try {
        con = DBConnection.getConnection();
        int userId = (int) session.getAttribute("userId");
        PreparedStatement bookedPs = con.prepareStatement(
            "SELECT t.ticket_id, e.event_name, e.date_time, e.venue, t.status " +
            "FROM tickets t JOIN events e ON t.event_id = e.event_id " +
            "WHERE t.user_id = ?"
        );
        bookedPs.setInt(1, userId);
        ResultSet bookedRs = bookedPs.executeQuery();
%>
<h2>Your Booked Tickets</h2>
<table border="1">
<tr><th>Event</th><th>Date</th><th>Venue</th><th>Status</th></tr>
<%
    while(bookedRs.next()) {
%>
<tr>
    <td><%= bookedRs.getString("event_name") %></td>
    <td><%= bookedRs.getString("date_time") %></td>
    <td><%= bookedRs.getString("venue") %></td>
    <td><%= bookedRs.getString("status") %></td>
</tr>
<%
    }
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    } finally {
        if (con != null) con.close();
    }
%>
</table>
