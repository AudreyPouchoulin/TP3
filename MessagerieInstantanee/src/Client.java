/**
 * Project: MessagerieInstantan�e
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
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
public class Client{

	private static final long serialVersionUID = 1L;
	public static String id = null;
	public static Date dateLastReception = null;
	public static boolean isWriting = false;
	public static Updater updaterAutomatic = null;

	public static void main(String args[]) throws RemoteException {
		Serverable serveur = null;
		try {
			String URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ Serveur.port_num +"/mon_serveur";
			serveur = (Serverable) Naming.lookup(URL);
			System.out.println("Client connecte au serveur "+ URL +"\n");
		} catch (Exception exc) {
			System.out.println("Erreur dans la creation du client connecte au serveur\n"+ exc.toString());
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
					   	+  "*\t\t Commence a utiliser ta messagerie:");
		
		Scanner sc = new Scanner(System.in);
		String str = "init";
		String resultServeur = "";
		MyKeyListener keyListener = new MyKeyListener();
//		sc.addKeyListener(keyListener);
		
		while (!str.equals("quit")){
			str = sc.nextLine();

			// demande d'envoi d'un message (Appel d'une methode sur l'objet distant)
			if (str.contains("send")){
				Date date = new Date();
				String msg=str.replace("send ", "");
				resultServeur = serveur.send(msg, id, date);
				System.out.println(resultServeur);
				
			// demande de connexion a un compte utilisateur (Appel d'une methode sur l'objet distant)
			} else if (str.contains("connect")){
				StringTokenizer token = new StringTokenizer(str, " ");
				String idUtilisateur = null;
				String password = null;
				try {
					token.nextToken();
					idUtilisateur = token.nextToken();
					password = token.nextToken();
				} catch (Exception exc){
					System.out.println("Il manque des elements pour te connecter: connect (espace) identifiant (espace) password\n" +exc.toString());
				}
				int connexionResult = serveur.connect(idUtilisateur, password);
				if (connexionResult == -3){
					System.out.println("Echec de connexion, l'utilisateur "+ id + " est deja connecte.");
				} else if (connexionResult == -2){
					System.out.println("Echec de connexion car mot de passe invalide");
				} else if (connexionResult == -1){
					System.out.println("Echec de connexion, possiblit� d'echec: erreur dans l'identifiant ou identifiant non existant");
				} else if (connexionResult == 1){
					id = idUtilisateur;
					dateLastReception = new Date();
					updaterAutomatic = new Updater(1, serveur);
					System.out.println("Bienvenue "+ id);	
				}
				
			// demande de deconnexion (Appel d'une methode sur l'objet distant)
			} else if (str.contains("bye")){
				int deconnexionResult = serveur.disconnect(id);
				if (deconnexionResult == 1){
					System.out.println("Aurevoir "+ id);
					updaterAutomatic.stop();
				} else if (deconnexionResult == -1){
					System.out.println("Erreur serveur, demande de daconnexion d'un utilisateur deja deconnecte ...");
				} else if (deconnexionResult == -2){
					System.out.println("Erreur serveur, demande de d�connexion d'un utilisateur non existant");
				}
				
			// demande des autres utilisateurs connectes (Appel d'une methode sur l'objet distant)
			} else if (str.contains("who")){
				resultServeur = serveur.getListUtilisateursConnectes(id);
				System.out.println(resultServeur);

			// demande forc�e d'actualisation des derniers messages envoyes (Appel d'une methode sur l'objet distant)
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
