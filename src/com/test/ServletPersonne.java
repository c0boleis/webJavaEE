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
		// on r�cup�re les param�tres d'initialisation de la servlet
		ServletConfig config = getServletConfig();
		// on traite les autres param�tres d'initialisation
		String valeur=null;
		for(int i=0;i<parametres.length;i++){
			// valeur du param�tre
			valeur=config.getInitParameter(parametres[i]);
			// param�tre pr�sent ?
			if(valeur==null){
				// on note l'erreur
				erreursInitialisation.add("Le param�tre ["+parametres[i]+"] n'a pas �t� initialis�");
			}else{
				// on m�morise la valeur du param�tre
				params.put(parametres[i],valeur);
			}
			// l'url de la vue [erreurs] a un traitement particulier
			urlErreurs = config.getInitParameter("urlErreurs");
			if (urlErreurs == null)
				throw new ServletException(
						"Le param�tre [urlErreurs] n'a pas �t� initialis�");
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
		// on r�cup�re la session de l'utilisateur
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
		// on v�rifie comment s'est pass�e l'initialisation de la servlet
		if (erreursInitialisation.size() != 0) {
			// on passe la main � la page d'erreurs
			request.setAttribute("erreurs", erreursInitialisation);
			getServletContext().getRequestDispatcher(urlErreurs).forward(
					request, response);
			// fin
			return;
		}

		// on r�cup�re la m�thode d'envoi de la requ�te
		String m�thode=request.getMethod().toLowerCase();
		// on r�cup�re l'action � ex�cuter
		String action=request.getParameter("action");
		// action ?
		if(action==null){
			action="init";
		}
		// ex�cution action
		if(m�thode.equals("get") && action.equals("init")){
			// d�marrage application
			doInit(request,response);
			return;
		}
		if(m�thode.equals("post") && action.equals("validationFormulaire")){
			// validation du formulaire de saisie
			doValidationFormulaire(request,response);
			return;
		}
		if(m�thode.equals("get") && action.equals("retourFormulaire")){
			// retour au formulaire de saisie
			doRetourFormulaire(request,response);
			return;
		}
		// autres cas
		doInit(request,response);
	}

	void doValidationFormulaire(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		// on r�cup�re les param�tres
		String nom = request.getParameter("txtNom");
		String age = request.getParameter("txtAge");
		// qu'on m�morise dans la session
		HttpSession session = request.getSession(true);
		session.setAttribute("nom", nom);
		session.setAttribute("age", age);
		// v�rification des param�tres
		ArrayList<String> erreursAppel = new ArrayList<String>();
		// le nom doit �tre non vide
		nom = nom.trim();

		request.setAttribute("lienRetourFormulaire", (String)params.get("lienRetourFormulaire"));
		if (nom.equals(""))
			erreursAppel.add("Le champ [nom] n'a pas �t� rempli");
		// l'�ge doit �tre un entier >=0
		if (!age.matches("^\\s*\\d+\\s*$"))
			erreursAppel.add("Le champ [age] est erron�");
		// des erreurs dans les param�tres ?
		if (erreursAppel.size() != 0) {
			// on envoie la page d'erreurs
			request.setAttribute("erreurs", erreursAppel);
			getServletContext().getRequestDispatcher(urlErreurs).forward(request, response);
			return;
		}
		// les param�tres sont corrects - on envoie la page r�ponse
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
	// affichage formulaire pr�-rempli
	void doRetourFormulaire(HttpServletRequest request, HttpServletResponse response) throws
	ServletException, IOException{
		// on r�cup�re la session de l'utilisateur
		HttpSession session = request.getSession(true);
		// on pr�pare le mod�le du formulaire
		// nom pr�sent dans la session ?
		String nom = (String) session.getAttribute("nom");
		if (nom == null)
			session.setAttribute("nom", "");
		// �ge pr�sent dans la session ?
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
