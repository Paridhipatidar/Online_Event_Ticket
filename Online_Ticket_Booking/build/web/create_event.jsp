<%@ page import="java.sql.*, util.DBConnection" %>

<html><body>
<form action="createEvent" method="post">
    Event Name: <input type="text" name="name"><br>
    Date/Time: <input type="datetime-local" name="datetime"><br>
    Venue: <input type="text" name="venue"><br>
    <input type="submit" value="Create Event">
</form>
</body></html>