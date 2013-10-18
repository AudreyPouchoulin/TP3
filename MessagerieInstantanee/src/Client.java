/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.net.InetAddress;
import java.rmi.*;
/**
 * @author Audrey
 *
 */
public class Client implements ServeurInterface{
	public static void main(String args[]) {
		try {
			// Récupération d'un stub sur l'objet serveur.
			String URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ 8090 +"/mon_serveur";
			ServeurInterface obj = (ServeurInterface) Naming.lookup(URL);
			System.out.println("Client connecté au serveur "+ URL);
		
			// Appel d'une méthode sur l'objet distant.
			obj.seeMessage();
		} catch (Exception exc) {
			System.out.println("Erreur dans l'appel du serveur "+ exc.toString());
		}
	}

	/* (non-Javadoc)
	 * @see ServeurInterface#seeMessage()
	 */
	@Override
	public void seeMessage() throws RemoteException {
		System.out.println("ca marche !!!!!!!!!!!!!!");
		
	}
}
