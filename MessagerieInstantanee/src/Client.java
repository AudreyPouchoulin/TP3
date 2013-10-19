/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * @author Audrey
 *
 */
public class Client {

	private static String id;


	public static void main(String args[]) throws RemoteException {
		ServeurInterface serveur = null;
		try {
			// Récupération d'un stub sur l'objet serveur.
			String URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ Serveur.port_num +"/mon_serveur";
			serveur = (ServeurInterface) Naming.lookup(URL);
			System.out.println("Client connecté au serveur "+ URL +"\n");
		} catch (Exception exc) {
			System.out.println("Erreur dans la création du client connecté au serveur\n"+ exc.toString());
			return;
		}
		System.out.println("Uttilisation de ta messagerie:\n"
				+ "connect id password\n"
				+ "send message\n"
				+ "bye (déconnexion)\n"
				+ "who (voir utilisateurs connectés)\n"
				+ "update (voir derniers messages envoyés)\n\n"
				+ "Commence à utiliser ta messagerie:");
		
		// Appel d'une méthode sur l'objet distant
		Scanner sc = new Scanner(System.in);
		String str = "init";
		String resultServeur = null;
		while (!str.equals("quit")){
			str = sc.nextLine();
			
			// demande d'envoi d'un message
			if (str.contains("send")){
				str.replace("send ", "");
				resultServeur = serveur.send(str, id);
				System.out.println(resultServeur);
				
			// demande de connexion à un compte utilisateur
			} else if (str.contains("connect")){
				StringTokenizer token = new StringTokenizer(str, " ");
				String idUtilisateur = null;
				String password = null;
				try {
					token.nextToken();
					idUtilisateur = token.nextToken();
					password = token.nextToken();
				} catch (Exception exc){
					System.out.println("Il manque des élements pour te connecter: connect (espace) identifiant (espace) password\n" +exc.toString());
				}
				resultServeur = serveur.connect(idUtilisateur, password);
				System.out.println(resultServeur);
				id = idUtilisateur;
				
			// demande de déconnexion
			} else if (str.contains("bye")){
				resultServeur = serveur.disconnect(id);
				System.out.println(resultServeur);
				
			// demande des autres utilisateurs connectés
			} else if (str.contains("who")){
				resultServeur = serveur.getListUtilisateursConnectés(id);
				System.out.println(resultServeur);

			// demande forcée d'actualisation des derniers messages envoyés
			} else if (str.contains("update")){
//				serveur.getDernierMessageEnvoye();
			}
			
			sc = new Scanner(System.in);
		}
		System.exit(0);
	}
	
}
