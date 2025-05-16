<%@ page import="java.sql.*, util.DBConnection" %>

<%@ page import="java.sql.*, java.util.*" %>
<%
    int adminId = (int) session.getAttribute("userId");
    Connection con = DBConnection.getConnection();
    PreparedStatement ps = con.prepareStatement("SELECT * FROM events WHERE created_by = ?");
    ps.setInt(1, adminId);
    ResultSet rs = ps.executeQuery();
%>
<html><body>
<h2>Your Events</h2>
<table border="1">
<tr><th>Name</th><th>Date</th><th>Venue</th></tr>
<% while(rs.next()) { %>
<tr>
    <td><%= rs.getString("event_name") %></td>
    <td><%= rs.getString("date_time") %></td>
    <td><%= rs.getString("venue") %></td>
</tr>
<% } %>
</table>
<a href="create_event.jsp">Create New Event</a>
</body></html>
