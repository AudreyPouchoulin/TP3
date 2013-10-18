/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.net.InetAddress;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
/**
 * @author Audrey
 *
 */
public class Serveur extends UnicastRemoteObject implements ServeurInterface{
	String message;
	// Implémentation du constructeur
	public Serveur(String msg) throws java.rmi.RemoteException {
		message = msg;
	}
	// Implémentation de la méthode distante
	public void seeMessage() throws java.rmi.RemoteException {
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
			Serveur obj = new Serveur("Serveur crée");
		
			// Calcul de l’URL du serveur
			URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ port+"/mon_serveur";
			Naming.rebind(URL, obj);
			System.out.println("Serveur en route: " + URL);
		} catch (Exception exc) {
			System.out.println("Erreur dans la connexion au serveur" + exc.toString()); 
		}
		}
}
