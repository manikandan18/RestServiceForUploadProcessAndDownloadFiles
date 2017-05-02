<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<html>
 
<head>
   <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Transform Excel sheet</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
 
<style>
 
    .error {
        color: #ff0000;
    }
    .table{
    	width: 100%;
    }
    .input_tr{
    	width: 40%;
    }
</style>
<h2>Click the below links to Download</h2>
<form:form method="GET" enctype="multipart/form-data" modelAttribute="linkFileAndURIArr">
	
	        <table class="table">
            <tr>              
                <td>Processed Files: <c:out value="${noOfFiles}" /></td>
            </tr> 
            <c:forEach items="${linkFileAndURIArr}" var="fileLinks">	        
            <tr>              
                <td><a href=<c:out value="${fileLinks.detail.href}" />><c:out value="${fileLinks.fileName}" /></a></td>
            </tr>
            </c:forEach>                      
        </table>	
</form:form>