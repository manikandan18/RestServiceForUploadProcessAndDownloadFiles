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
</style>
 
</head>
 
<body>
 
    <h2>Transform ExcelSheet</h2>
  
    <form:form method="POST" modelAttribute="transactionFile">
    <form:input type="hidden" path="id" id="id"/>
        <table>
            <tr>              
                <td><form:radiobutton path="transformType" value="Transform to Excel"/>
                Transform to Excel</td>
                <td><form:radiobutton path="transformType" value="Transform to CSV"/>
                Transform to CSV</td>
            </tr>            
            <tr>
                <td><label for="input_name">Input Transaction file: </label> </td>
                <td><form:input path="input_name" id="input_name"/></td>
                <td><form:errors path="input_name" cssClass="error"/></td>
            </tr>
         
            <tr>
                <td><label for="output_name">Output Transaction File: </label> </td>
                <td><form:input path="output_name" id="output_name"/></td>
                <td><form:errors path="output_name" cssClass="error"/></td>
            </tr>
     
            <tr>
                <td colspan="3">
                      <input type="submit" value="Transform"/>
                </td>
            </tr>
        </table>
    </form:form>
</body>
</html>