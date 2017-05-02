<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hiring Decision Error Page</title>
</head>
<body>
    message : ${error}
    <br/>
    <br/>
    Go back to <a href="<c:url value='/rpatransform/hiringcheck' />">Hiring Check Main Page</a>
</body>
 
</html>