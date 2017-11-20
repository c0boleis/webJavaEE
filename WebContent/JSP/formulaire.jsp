<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>formulaire</title>

</head>
<%
	// -- test : on crée le modèle de la page
	request.setAttribute("nom", "tintin");
	request.setAttribute("age", "30");
%>

<%
	String nom = (String) request.getAttribute("nom");
	String age = (String) request.getAttribute("age");
%>
<body>
	<form action="post">
		<table>
			<tr>
				<td>Nom</td>
				<td><input name="txtNom" value=<%=nom%> type="text" size="20"></td>
			</tr>
			<tr>
				<td>Age</td>
				<td><input name="txtAge" value=<%=age%> type="text" size="3"></td>
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
</body>
</html>