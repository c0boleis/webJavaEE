package com.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FormulaireServlet
 */
//@WebServlet("/personne")
public class ServletPersonne extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String urlErreurs = null;
	private ArrayList<String> erreursInitialisation = new ArrayList<String>();
	private String[] parametres={"urlFormulaire","urlReponse","urlControleur","lienRetourFormulaire"};
	private Map<String, String> params=new HashMap<String,String>();

	@Override
	public void init() throws ServletException {
		// on récupère les paramètres d'initialisation de la servlet
		ServletConfig config = getServletConfig();
		// on traite les autres paramètres d'initialisation
		String valeur=null;
		for(int i=0;i<parametres.length;i++){
			// valeur du paramètre
			valeur=config.getInitParameter(parametres[i]);
			// paramètre présent ?
			if(valeur==null){
				// on note l'erreur
				erreursInitialisation.add("Le paramètre ["+parametres[i]+"] n'a pas été initialisé");
			}else{
				// on mémorise la valeur du paramètre
				params.put(parametres[i],valeur);
			}
			// l'url de la vue [erreurs] a un traitement particulier
			urlErreurs = config.getInitParameter("urlErreurs");
			if (urlErreurs == null)
				throw new ServletException(
						"Le paramètre [urlErreurs] n'a pas été initialisé");
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletPersonne() {
		super();
	}

	public void doInit(HttpServletRequest request, HttpServletResponse response) throws ServletException,
	IOException{
		// on récupère la session de l'utilisateur
		HttpSession session = request.getSession(true);
		// on envoie le formulaire vide
		session.setAttribute("nom", "Corentin");
		session.setAttribute("age", "24");
		request.setAttribute("urlAction", (String)params.get("urlControleur"));
		getServletContext().getRequestDispatcher((String)params.get("urlFormulaire")).forward(
				request, response);
		return;
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// on vérifie comment s'est passée l'initialisation de la servlet
		if (erreursInitialisation.size() != 0) {
			// on passe la main à la page d'erreurs
			request.setAttribute("erreurs", erreursInitialisation);
			getServletContext().getRequestDispatcher(urlErreurs).forward(
					request, response);
			// fin
			return;
		}

		// on récupère la méthode d'envoi de la requête
		String méthode=request.getMethod().toLowerCase();
		// on récupère l'action à exécuter
		String action=request.getParameter("action");
		// action ?
		if(action==null){
			action="init";
		}
		// exécution action
		if(méthode.equals("get") && action.equals("init")){
			// démarrage application
			doInit(request,response);
			return;
		}
		if(méthode.equals("post") && action.equals("validationFormulaire")){
			// validation du formulaire de saisie
			doValidationFormulaire(request,response);
			return;
		}
		if(méthode.equals("get") && action.equals("retourFormulaire")){
			// retour au formulaire de saisie
			doRetourFormulaire(request,response);
			return;
		}
		// autres cas
		doInit(request,response);
	}

	void doValidationFormulaire(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		// on récupère les paramètres
		String nom = request.getParameter("txtNom");
		String age = request.getParameter("txtAge");
		// qu'on mémorise dans la session
		HttpSession session = request.getSession(true);
		session.setAttribute("nom", nom);
		session.setAttribute("age", age);
		// vérification des paramètres
		ArrayList<String> erreursAppel = new ArrayList<String>();
		// le nom doit être non vide
		nom = nom.trim();

		request.setAttribute("lienRetourFormulaire", (String)params.get("lienRetourFormulaire"));
		if (nom.equals(""))
			erreursAppel.add("Le champ [nom] n'a pas été rempli");
		// l'âge doit être un entier >=0
		if (!age.matches("^\\s*\\d+\\s*$"))
			erreursAppel.add("Le champ [age] est erroné");
		// des erreurs dans les paramètres ?
		if (erreursAppel.size() != 0) {
			// on envoie la page d'erreurs
			request.setAttribute("erreurs", erreursAppel);
			getServletContext().getRequestDispatcher(urlErreurs).forward(request, response);
			return;
		}
		// les paramètres sont corrects - on envoie la page réponse
		request.setAttribute("nom", nom);
		request.setAttribute("age", age);
		getServletContext().getRequestDispatcher((String)params.get("urlReponse")).forward(request,
				response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	// affichage formulaire pré-rempli
	void doRetourFormulaire(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException{
		// on récupère la session de l'utilisateur
		HttpSession session = request.getSession(true);
		// on prépare le modèle du formulaire
		// nom présent dans la session ?
		String nom = (String) session.getAttribute("nom");
		if (nom == null)
			session.setAttribute("nom", "");
		// âge présent dans la session ?
		String age = (String) session.getAttribute("age");
		if (age == null)
			session.setAttribute("age", "");
		// urlAction
		request.setAttribute("urlAction", (String)params.get("urlControleur"));
		// on affiche le formulaire
		getServletContext().getRequestDispatcher((String)params.get("urlFormulaire")).forward(
				request, response);
		return;
	}
}
