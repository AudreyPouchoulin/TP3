/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * @author Audrey,Olfa
 *
 */

public class Serveur extends UnicastRemoteObject implements ServeurInterface{
	private static final long serialVersionUID = 1L;
	private final String NOM_FICHIER = "clients.dat"; 	//Nom du fichier de sauvegarde des clients inscrits au service de chat
	public static int port_num = 8090; 					//numero de port pour le serveur
	private static ArrayList <Utilisateur> utilisateurs = new ArrayList<Utilisateur>();	//liste des utilisateurs
	private static ArrayList <Utilisateur> utilisateursConnectés = new ArrayList<Utilisateur>();	//liste des utilisateurs connectés

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
		utilisateurs.add(utilisateur1);
		utilisateurs.add(utilisateur2);
		utilisateurs.add(utilisateur3);
	}

	/**
	 * Chargement des clients à partir du fichier
	 */
	public void readData(){
		ObjectInputStream fe = null;
		boolean trouve=true;
		boolean pasEncore=true;
		try {
			fe = new ObjectInputStream(new FileInputStream(NOM_FICHIER));
		}
		catch(FileNotFoundException fnf){
			System.out.println(fnf.toString());
			trouve = false;
		}
		catch (IOException eof) {
			System.out.println(eof.toString());
		}
		//si le fichier existe
		if(trouve){
			while (pasEncore) {
				try {
					Utilisateur u = (Utilisateur) fe.readObject();
					utilisateurs.add(u);  
				}
				catch (EOFException e) {
					pasEncore = false;
					System.out.println("lecture des contacts terminée");
				}
				catch (ClassNotFoundException ex2) {}
				catch (IOException ex2) {}
			}
			try {
				fe.close();
			}
			catch (IOException ex) {
				System.out.println(ex.toString());
			}
		}
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
						return "Connexion réussie \n Bienvenue "+ id;
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
					System.out.println("Erreur serveur, demande de déconnexion d'un utilisateur déjà déconnecté ...");
					return "Erreur serveur, demande de déconnexion d'un utilisateur déjà déconnecté ...";
				} else {
					utilisateurs.get(i).setConnected(false);
					utilisateursConnectés.remove(utilisateurs.get(i));
					System.out.println("Déconnexion réussie");
					return "Déconnexion réussie \n Aurevoir "+ nom;
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
		String result = "pas d'autre utilisateur connecté";
		if (utilisateursConnectés.size()>1){
			result = "Liste des utilisateurs en ligne:";
			for (int i=0; i<utilisateursConnectés.size();i++){
				if (!utilisateursConnectés.get(i).getId().equals(id)){
					result = result + "\n"+ utilisateursConnectés.get(i).getId();
				}
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see ServeurInterface#send(java.lang.String)
	 */
	@Override
	public String send(String message, String id) throws RemoteException {
		System.out.println("Demande d'envoi de message par l'utilisateur" + id);
		return "Message envoyé";	
	}

	/* (non-Javadoc)
	 * @see ServeurInterface#updateMessage()
	 */
	@Override
	public String updateMessage() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
