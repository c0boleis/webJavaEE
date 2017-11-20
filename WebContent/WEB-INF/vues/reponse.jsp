<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	// on récupère les données du modèle
	String nom = (String) request.getAttribute("nom");
	String age = (String) request.getAttribute("age");
	String lienRetourFormulaire=(String)request.getAttribute("lienRetourFormulaire");
%>

<html>
<head>
<title>Personne</title>
</head>
<body>
	<h2>Personne - réponse</h2>
	<hr>
	<table>
		<tr>
			<td>Nom</td>
			<td><%=nom%>
		</tr>
		<tr>
			<td>Age</td>
			<td><%=age%>
		</tr>
	</table>
	<br>
	<a href="?action=retourFormulaire"><%= lienRetourFormulaire %></a>
</body>