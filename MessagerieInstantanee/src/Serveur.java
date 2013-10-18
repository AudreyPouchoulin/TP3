/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
/**
 * @author Audrey
 *
 */
@SuppressWarnings("serial")
public class Serveur extends UnicastRemoteObject implements ServeurInterface{
	/**
	 * @throws RemoteException
	 */
	protected Serveur() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	static ArrayList<Utilisateur> listUser;
	static ArrayList<Message> listMessage;
	
	// Implémentation de la méthode distante
	public void seeMessage(String message) throws java.rmi.RemoteException {
		System.out.println(message);
	}
	
	public static void main(String args[]) {
		int port;
		String URL;
		try { 
			// transformation d ’une chaîne de caractères en entier
			Integer I = new Integer(args[0]); 
			port = I.intValue();
		} catch (Exception ex) {
			System.out.println(" Please enter: Server <port>"); 
			return;
		}
		try {	
			// Création du serveur de nom - rmiregistry
			Registry registry = LocateRegistry.createRegistry(port);
			
			// Création d ’une instance de l’objet serveur
			Serveur obj = new Serveur();
		
			// Calcul de l’URL du serveur
			URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ port+"/mon_serveur";
			Naming.rebind(URL, obj);
			System.out.println("Serveur en route: " + URL);
			createListOfUser();
		} catch (Exception exc) {
			System.out.println("Erreur dans la connexion au serveur" + exc.toString()); 
		}
	}
	
	public static void createListOfUser(){
		Utilisateur utilisateur1 = new Utilisateur("olfa", "koubaa", false);
		listUser = new ArrayList<Utilisateur>();
		listUser.add(utilisateur1);
	}
	
	// Implémentation de la méthode distante
	public void connect(String id) throws java.rmi.RemoteException{
		for (int i=0;i<listUser.size();i++){
			if (listUser.get(i).getId().equals(id)){
				listUser.get(i).getPassword();
				ServeurInterface client = null;
				try {
					client = (ServeurInterface) Naming.lookup("//"+InetAddress.getLocalHost().getHostName()+":8090"+"/mon_serveur");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (client!=null){
					client.connect("Entre ton mot de passe");
				} else {
					System.out.println("Erreur dans l'envoi d'un message sur le client");
				}
				
			}
		}
	}
}
