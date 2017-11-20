<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>formulaire</title>

</head> -->

<%
	String nom = (String) session.getAttribute("nom");
	String age = (String) session.getAttribute("age");
	String urlAction=(String)request.getAttribute("urlAction");
	System.out.println("recupération du nom("+nom+") et de l'age("+age+")");
%>
<!-- <body> -->
<form action="<%=urlAction%>" method="post">
	<table>
		<tr>
			<td>Nom</td>
			<td><input name="txtNom" value=<%= nom %> type="text" size="20"></td>
		</tr>
		<tr>
			<td>Age</td>
			<td><input name="txtAge" value=<%= age %> type="text" size="3"></td>
		</tr>
	</table>
	<table>
		<tr>
			<td><input type="submit" value="Envoyer"></td>
			<td><input type="reset" value="Retablir"></td>
			<td><input type="button" value="Effacer"></td>
		</tr>
	</table>
	<input type="hidden" name="action" value="validationFormulaire">
</form>
<!-- </body> -->
<!-- </html> -->