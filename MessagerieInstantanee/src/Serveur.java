/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Audrey,Olfa
 *
 */

public class Serveur extends UnicastRemoteObject implements Serverable{
	
	private static final long serialVersionUID = 1L;
	public static int port_num = 8090; 					//numero de port pour le serveur
	private static ArrayList <Utilisateur> utilisateurs = new ArrayList<Utilisateur>();	//liste des utilisateurs
	private static ArrayList <Utilisateur> utilisateursConnectés = new ArrayList<Utilisateur>();	//liste des utilisateurs connectés
	private static ArrayList<Message> messages = new ArrayList<Message>();
	private static int nbrMessages = 0;
	
	/**
	 * Constructeur du serveur
	 * @throws RemoteException
	 */
	protected Serveur() throws RemoteException {
		super();
	}
	
	/**
	 * Main qui démarre le serveur, charge la liste d'utilisateurs du chat
	 * @param args
	 */
	public static void main(String args[]) {
		_start();
		_createUser();
	}
	
	/**
	 * Demarrage du serveur
	 */
	public static void _start(){
		String URL;		
		try {	
			LocateRegistry.createRegistry(port_num);  // Création du serveur de nom - rmiregistry
			Serveur obj = new Serveur();							// Création d’une instance de l’objet serveur
			URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ port_num+"/mon_serveur";  // Calcul de l’URL du serveur
			Naming.rebind(URL, obj);
			System.out.println("Serveur en route: " + URL);
		} catch (Exception exc) {
			System.out.println("Erreur dans la connexion au serveur" + exc.toString()); 
		}
	}
	
	public static void _createUser(){
		Utilisateur utilisateur1 = new Utilisateur("olfa", "koubaa");
		Utilisateur utilisateur2 = new Utilisateur("audrey", "123");
		Utilisateur utilisateur3 = new Utilisateur("admin", "admin");
		Utilisateur utilisateur4 = new Utilisateur("ACCENTS", "international");
		Utilisateur utilisateur5 = new Utilisateur("Centrale", "ecn");
		utilisateurs.add(utilisateur1);
		utilisateurs.add(utilisateur2);
		utilisateurs.add(utilisateur3);
		utilisateurs.add(utilisateur4);
		utilisateurs.add(utilisateur5);
	}

	
	/* (non-Javadoc)
	 * @see ServeurInterface#connect(java.lang.String, java.lang.String, int)
	 */
	@Override
	public String connect(String id, String password) throws RemoteException {
		 System.out.println("Demande de connexion de l'utilisateur " + id + " avec le mot de passe " + password);
         for (int i=0;i<utilisateurs.size();i++){
                 if (utilisateurs.get(i).getId().equals(id)){
                         if (utilisateurs.get(i).getPassword().equals(password)){
                                 if (utilisateurs.get(i).isConnected()){
                                         System.out.println("Echec connexion");
                                         return "Echec de connexion, l'utilisateur "+ id + " est déjà connecté.";
                                 } else {
                                         utilisateurs.get(i).setConnected(true);
                                         utilisateursConnectés.add(utilisateurs.get(i));
                                         System.out.println("Connexion réussie");
                                         return "Bienvenue "+ id;
                                 }
                         } else {
                                 System.out.println("Echec connexion");
                                 return "Echec de connexion car mot de passe invalide";
                         }
                 }
         }
         System.out.println("Echec connexion");
         return "Echec de connexion, possiblité d'échec: erreur dans l'identifiant ou identifiant non existant"; 
	}

	/* (non-Javadoc)
	 * @see ServeurInterface#disconnect(java.lang.String)
	 */
	@Override
	public String disconnect(String nom) throws RemoteException {
		System.out.println("Demande de déconnexion de l'utilisateur " + nom);
		for (int i=0;i<utilisateurs.size();i++){
			if (utilisateurs.get(i).getId().equals(nom)){
				if (!utilisateurs.get(i).isConnected()){
					System.out.println("Erreur serveur, demande de deconnexion d\'un utilisateur deja deconnecte ...");
					return "Erreur serveur, demande de daconnexion d'un utilisateur deja deconnecte ...";
				} else {
					utilisateurs.get(i).setConnected(false);
					utilisateursConnectés.remove(utilisateurs.get(i));
					System.out.println("Déconnexion réussie");
					return "Aurevoir "+ nom;
				}
			}
		}
		System.out.println("Erreur serveur, demande de déconnexion d'un utilisateur non existant");
		return "Erreur serveur, demande de déconnexion d'un utilisateur non existant";	
	}

	/* (non-Javadoc)
	 * @see ServeurInterface#getListUtilisateurs(java.lang.String)
	 */
	@Override
	public String getListUtilisateursConnectés(String id) throws RemoteException {
		System.out.println("Demande de liste d'utilisateurs connectés par " + id);
		boolean utilisateurConnecte = utilisateurIsConnected(id);
		String result = "pas d'autre utilisateur connecté";
		if(utilisateurConnecte){
			System.out.println("Demande acceptée");
			if (utilisateursConnectés.size()>1){
				result = "Liste des utilisateurs en ligne:";
				for (int i=0; i<utilisateursConnectés.size();i++){
					if (!utilisateursConnectés.get(i).getId().equals(id)){
						result = result + "\n"+ utilisateursConnectés.get(i).getId();
					}
				}
			}
		} else {
			System.out.println("Demande refusée, utilisateur non connecté");
			result = "Veuillez vous connecter";
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see ServeurInterface#send(java.lang.String)
	 */
	@Override
	public String send(String message, String id, Date date) throws RemoteException {
		System.out.println("Demande d'envoi de message par " + id);
		boolean utilisateurConnecte = utilisateurIsConnected(id);
		if(utilisateurConnecte){
			Message m=new Message(nbrMessages++, id,message, date);
			messages.add(m);
			System.out.println("Message envoyé");
			return "Message envoyé";
		} 
		else {
			System.out.println("Envoi de message refusé, utilisateur non connecté");
			return "Veuillez vous connecter";
		}
	}
	

	/* (non-Javadoc)
	 * @see ServeurInterface#updateMessage()
	 */
	@Override
	public String updateMessage(String id, Date dateLastReception) throws RemoteException {
		System.out.println("Demande d'actualisation des messages envoyés par " + id);
		boolean utilisateurConnecte = utilisateurIsConnected(id);
		if(utilisateurConnecte){
			if (messages.size()==0){
				System.out.println("Actualisation des derniers messages réalisée");
				return "Aucun nouveau message";
			} else {
				boolean hasNewMessage = false;
				String result = null;
				for(int i=0; i<messages.size(); i++){
					result = "Nouveaux messages : ";
					if (messages.get(i).getDate().after(dateLastReception)){
						hasNewMessage = true;
						result = result + "\n" + messages.get(i).toString();
					}
				}
				if (!hasNewMessage){
					result = "Aucun nouveau message";
				}
				System.out.println("Actualisation des derniers messages réalisée");
				return result;
			}
		}
		else{
			System.out.println("Actualisation des messages refusée, utilisateur non connecté");
			return "Veuillez vous connecter";
		}
	}
	
	/**
	 * Test si un utilisateur est connecté (utile avant d'uatoriser l'envoie d'un message, la demande des autres personnes connectées, ...)
	 * @param id
	 * @return true if connected, false if not connecte
	 */
	private boolean  utilisateurIsConnected(String id){
		int i=0;
		boolean find=false;
		while (i<utilisateursConnectés.size()&&!find){
			find =utilisateursConnectés.get(i).getId().equals(id);
			i++;
		}
		return find;
	}
	
}
