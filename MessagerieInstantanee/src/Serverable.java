import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Project: MessagerieInstantan�e
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */


/**
 * @author Audrey,Olfa
 *
 */
public interface Serverable extends Remote {
	

	/**
	 *  Fonction qui sera appel� par le client pour se connecter au chat
	 *  ajout du client a la liste des clients connect� au serveur
	 * @param id
	 * @param password
	 * @return �tat de la requ�te (succ�s ou �chec)
	 * @throws RemoteException
	 */
	public String connect(String id,String password)throws RemoteException;
	
	/**
	 * Fonction permettant de d�connecter un client du service de chat
	 * @param id
	 * @return �tat de la requ�te (succ�s ou �chec)
	 * @throws RemoteException
	 */
	public String disconnect(String id)throws RemoteException;
	
	/**
	 * Fonction qui sera appell�e par un client du chat pour envoyer un message aux autres utilisateurs connect�s au chat
	 * ajouter un message a la liste des messages
	 * @param message
	 * @param identifiant utilisateur
	 * @return �tat de la requ�te (succ�s ou �chec)
	 * @throws RemoteException
	 */
	public String send(String message, String idUtilisateur, Date date) throws RemoteException;
	
	/**
	 * Fonction qui sera appell�e par un client du chat pour savoir quels sont les autres utilisateurs connect�s
	 * @param identifiant de l'utilisateur d�j� connect�
	 * @return �tat de la requ�te (succ�s ou �chec)
	 * @throws RemoteException
	 */
	//who
	public String getListUtilisateursConnectes(String id) throws RemoteException;
	
	/**
	 * Fonction qui sera appell�e par un client du chat � intervalle r�gulier pour voir les derniers messages envoy�s
	 * @return �tat de la requ�te (succ�s ou �chec)
	 * @throws RemoteException
	 */
	public String updateMessage(String idUtilisateur, Date dateLastReception) throws RemoteException;

}
