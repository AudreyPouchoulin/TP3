import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */


/**
 * @author Audrey,Olfa
 *
 */
public interface Serverable extends Remote {
	

	/**
	 *  Fonction qui sera appelé par le client pour se connecter au chat
	 *  ajout du client a la liste des clients connecté au serveur
	 * @param id
	 * @param password
	 * @return état de la requête (succès ou échec)
	 * @throws RemoteException
	 */
	public String connect(String id,String password)throws RemoteException;
	
	/**
	 * Fonction permettant de déconnecter un client du service de chat
	 * @param id
	 * @return état de la requête (succès ou échec)
	 * @throws RemoteException
	 */
	public String disconnect(String id)throws RemoteException;
	
	/**
	 * Fonction qui sera appellée par un client du chat pour envoyer un message aux autres utilisateurs connectés au chat
	 * ajouter un message a la liste des messages
	 * @param message
	 * @param identifiant utilisateur
	 * @return état de la requête (succès ou échec)
	 * @throws RemoteException
	 */
	public String send(String message, String idUtilisateur, Date date) throws RemoteException;
	
	/**
	 * Fonction qui sera appellée par un client du chat pour savoir quels sont les autres utilisateurs connectés
	 * @param identifiant de l'utilisateur déjà connecté
	 * @return état de la requête (succès ou échec)
	 * @throws RemoteException
	 */
	//who
	public String getListUtilisateursConnectés(String id) throws RemoteException;
	
	/**
	 * Fonction qui sera appellée par un client du chat à intervalle régulier pour voir les derniers messages envoyés
	 * @return état de la requête (succès ou échec)
	 * @throws RemoteException
	 */
	public String updateMessage(String idUtilisateur, Date dateLastReception) throws RemoteException;

}
