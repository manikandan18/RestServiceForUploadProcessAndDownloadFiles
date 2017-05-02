<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<html>
<script>
$(':radio').change(function (event) {
    var id = $(this).data('id');
    $('#' + id).addClass('none').siblings().removeClass('none');
});
</script> 
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
 
    <h2>Get Hired</h2>
  
    <form:form action="/rpatransform/hiringcheck" method="POST" enctype="multipart/form-data" modelAttribute="hiringProcessInput">
    <form:input type="hidden" path="id" id="id"/>
        <table>
            <tr>              
                <td><form:checkbox path="hiringCriteria" value="Salary" data-id="div1"/>
                Salary</td>
                <td><form:checkbox path="hiringCriteria" value="Union" data-id="div2"/>
                Union</td>
                <td><form:checkbox path="hiringCriteria" value="Location" data-id="div3"/>
                Location</td>                
            </tr>            
            <tr>
                <td><label for="input_name">Input Transaction file: </label> </td>
                <td><input name="file" id="filename" type="file" /></td>
                <td><form:errors path="input_name" cssClass="error"/></td>
            </tr>         
            <tr>
                <td><label for="output_name">Output Transaction File: </label> </td>
                <td><form:input path="output_name" id="output_name"/></td>
                <td><form:errors path="output_name" cssClass="error"/></td>
            </tr>            
             <tr>
                <td><label for="input_name">Salary: </label> </td>
                <td><form:input path="salary" id="salary"/></td>
                <td><form:errors path="salary" cssClass="error"/></td>
            </tr>    
            <tr>
                <td><label for="input_name">Union: </label> </td>
                <td><form:input path="union" id="union"/></td>
                <td><form:errors path="union" cssClass="error"/></td>
            </tr>  
            <tr>
                <td><label for="input_name">Location: </label> </td>
                <td><form:input path="location" id="location"/></td>
                <td><form:errors path="location" cssClass="error"/></td>
            </tr> 
            <tr>
              <td/>
             </tr> 
            <tr>
                <td colspan="3">
                      <input type="submit" value="Get Candidates"/>
                </td>
            </tr>                         
        </table>            
    </form:form>
</body>
</html>