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
<h2>Transform To ExcelSheet</h2>
<form:form action="/rpatransform" method="post" enctype="multipart/form-data" modelAttribute="transactionFile">
	
	        <table class="table">
            <tr>              
                <td><form:radiobutton path="transformType" value="Transform from Excel to Excel"/>
                Transform from Excel to Excel</td>
                <td><form:radiobutton path="transformType" value="Transform from Excel to CSV"/>
                Transform from Excel to CSV</td>

            </tr>            
            <tr>
                <td><label for="input_name">Input Transaction file: </label> </td>
                <td><input name="file" id="filename" type="file" /></td>
                <td><form:errors path="input_name" cssClass="error"/></td>
            </tr>
         
            <tr>
                <td><br><label for="output_name">Output Transaction File: </label> </td>
                <td><br><form:input class="input_tr" path="output_name" id="output_name"/><br></td>
                <td><form:errors path="output_name" cssClass="error"/></td>
                <br>
            </tr>
     
        </table>
	<button name="submit" type="submit">Transform</button>
</form:form>