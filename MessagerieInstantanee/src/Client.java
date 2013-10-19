/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.net.InetAddress;
import java.rmi.*;
import java.util.Scanner;
/**
 * @author Audrey
 *
 */
public class Client {
	
	int port_num;
	String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPort_num() {
		return port_num;
	}

	public void setPort_num(int port_num) {
		this.port_num = port_num;
	}

	public Client(String id, int port_num) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String args[]) {
		try {
			// Récupération d'un stub sur l'objet serveur.
			String URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ 8090 +"/mon_serveur";
			ServeurInterface serveur = (ServeurInterface) Naming.lookup(URL);
			System.out.println("Client connecté au serveur "+ URL +"\n");
		
			System.out.println("Tu peux interagir avec ta messagerie en utilisant les commandes \n"
					+ "connect (id) pour la connexion\n"
					+ "send (message) pour envoyer un message\n"
					+ "bye pour la déconnexion\n"
					+ "who pour connaitre les autres utilisateurs connectés"
					+ "update pour voir les derniers messages envoyés");
			
			// Appel d'une méthode sur l'objet distant.
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			
			if (str.contains("send")){
				str.replace("send ", "");
//				serveur.send(str);
			} else if (str.contains("connect")){
				str.replace("connect ", "");
				serveur.connect(str);
			} else if (str.contains("bye")){
//				serveur.disconnect();
			} else if (str.contains("who")){
//				serveur.getListUtilisateur();
			} else if (str.contains("update")){
//				serveur.getDernierMessageEnvoye();
			}

			
		} catch (Exception exc) {
			System.out.println("Erreur dans l'appel du serveur "+ exc.toString());
		}
	}

	/* (non-Javadoc)
	 * @see ServeurInterface#seeMessage()
	 */
	@Override
	public void seeMessage(String message) throws RemoteException {
		System.out.println(message);
		
	}

	/* (non-Javadoc)
	 * @see ServeurInterface#connect(java.lang.String)
	 */
	
}
