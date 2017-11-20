
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="java.util.ArrayList"%>
<%
	// -- test : on crée le modèle de la page
	ArrayList<String> erreurs1 = new ArrayList<String>();
	erreurs1.add("erreur1");
	erreurs1.add("erreur2");
	request.setAttribute("erreurs", erreurs1);
%>
<%
	// on récupère les données du modèle
	ArrayList<String> erreurs = (ArrayList<String>) request.getAttribute("erreurs");
%>

<html>
<head>
<title>Personne</title>
</head>
<body>
	<h2>Les erreurs suivantes se sont produites</h2>
	<ul>
		<%
			for (int i = 0; i < erreurs.size(); i++) {
				out.println("<li>" + (String) erreurs.get(i) + "</li>\n");
			} //for
		%>
	</ul>
</body>
</html>
