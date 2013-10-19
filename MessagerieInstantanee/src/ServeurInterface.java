import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Project: MessagerieInstantan�e
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */


/**
 * @author Audrey,Olfa
 *
 */
public interface ServeurInterface extends Remote {

	/**
	 *  Fonction qui sera appel� par le client pour se connecter au serveur de chat
	 *  ajout du client a la liste des clients connect� au serveur
	 * @param id
	 * @param password
	 * @param num_port
	 * @return �tat de la requ�te (succ�s ou �chec)
	 * @throws RemoteException
	 */
	public String connect(String id,String password,int num_port)throws RemoteException;

	/**
	 * Fonction qui sera appell�e par le client inscrit au service de chat pour envoyer un message au serveur
	 * ajouter un message a la liste des messages
	 * @param message
	 * @param identifiant utilisateur
	 * @return �tat de la requ�te (succ�s ou �chec)
	 * @throws RemoteException
	 */
	public String send(String message, String idUtilisateur) throws RemoteException;
	
	/**
	 * Fonction permettant d'�liminer un client du service du chat
	 * @param id
	 * @return �tat de la requ�te (succ�s ou �chec)
	 * @throws RemoteException
	 */
	public String disconnect(String id)throws RemoteException;

	/**
	 * Fonction qui permet de renvoyer un numero de port dispo
	 * @return num�ro de port disponible
	 * @throws RemoteException
	 */
	public int get_num_port() throws RemoteException;

}
