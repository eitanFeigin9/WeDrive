<%@ page import="servlet.main.page.event.NewEventServlet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h2>Welcome</h2>
<%
    String uuid = request.getParameter("id");
    String name = NewEventServlet.getNameMap().get(uuid);
    if (name != null) {
%>
<p>Hello <%= name %></p>
<% } else { %>
<p>Invalid URL</p>
<% } %>
</body>
</html>
