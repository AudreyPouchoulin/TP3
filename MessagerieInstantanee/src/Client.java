/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * @author Audrey
 *
 */
public class Client {

	private static String id;
	private static Date dateLastReception;

	public static void main(String args[]) throws RemoteException {
		Serverable serveur = null;
		try {
			String URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ Serveur.port_num +"/mon_serveur";
			serveur = (Serverable) Naming.lookup(URL);
			System.out.println("Client connecté au serveur "+ URL +"\n");
		} catch (Exception exc) {
			System.out.println("Erreur dans la création du client connecté au serveur\n"+ exc.toString());
			return;
		}
		
		System.out.println("*****************************************************************\n"
					   	+  "*\t\t Uttilisation de ta messagerie \t\t\t*\n"	
					   	+  "*****************************************************************\n"
					   	+  "*\t\t connect id password\t\t\t\t*\n"
					   	+  "*\t\t send message\t\t\t\t\t*\n"
					   	+  "*\t\t bye (deconnexion)\t\t\t\t*\n"
					   	+  "*\t\t who (voir utilisateurs connectes)\t\t*\n"
					   	+  "*\t\t update (voir derniers messages envoyes)\t*\n"
					   	+  "*****************************************************************\n"
					   	+  "*\t\t Commence à utiliser ta messagerie:");
		
		Scanner sc = new Scanner(System.in);
		String str = "init";
		String resultServeur = null;
		while (!str.equals("quit")){
			str = sc.nextLine();
			
			// demande d'envoi d'un message (Appel d'une méthode sur l'objet distant)
			if (str.contains("send")){
				Date date = new Date();
				String msg=str.replace("send ", "");
				resultServeur = serveur.send(msg, id, date);
				System.out.println(resultServeur);
				
			// demande de connexion à un compte utilisateur (Appel d'une méthode sur l'objet distant)
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
				dateLastReception = new Date();
				
			// demande de déconnexion (Appel d'une méthode sur l'objet distant)
			} else if (str.contains("bye")){
				resultServeur = serveur.disconnect(id);
				System.out.println(resultServeur);
				
			// demande des autres utilisateurs connectés (Appel d'une méthode sur l'objet distant)
			} else if (str.contains("who")){
				resultServeur = serveur.getListUtilisateursConnectés(id);
				System.out.println(resultServeur);

			// demande forcée d'actualisation des derniers messages envoyés (Appel d'une méthode sur l'objet distant)
			} else if (str.contains("update")){
				resultServeur = serveur.updateMessage(id, dateLastReception);
				System.out.println(resultServeur);
				dateLastReception = new Date();
			}
			
			sc = new Scanner(System.in);
		}
		System.exit(0);
	}

	
}
