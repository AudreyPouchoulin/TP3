/**
 * Project: MessagerieInstantan�e
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
	// Impl�mentation du constructeur
	public Serveur(String msg) throws java.rmi.RemoteException {
		message = msg;
	}
	// Impl�mentation de la m�thode distante
	public void seeMessage() throws java.rmi.RemoteException {
		System.out.println(message);
	}
	
	public static void main(String args[]) {
		int port;
		String URL;
		try { 
			// transformation d �une cha�ne de caract�res en entier
			Integer I = new Integer(args[0]); 
			port = I.intValue();
		} catch (Exception ex) {
			System.out.println(" Please enter: Server <port>"); 
			return;
		}
		try {
			// Cr�ation du serveur de nom - rmiregistry
			Registry registry = LocateRegistry.createRegistry(port);
		
			// Cr�ation d �une instance de l�objet serveur
			Serveur obj = new Serveur("Serveur cr�e");
		
			// Calcul de l�URL du serveur
			URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ port+"/mon_serveur";
			Naming.rebind(URL, obj);
			System.out.println("Serveur en route: " + URL);
		} catch (Exception exc) {
			System.out.println("Erreur dans la connexion au serveur" + exc.toString()); 
		}
		}
}
