import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */


/**
 * @author Audrey,Olfa
 *
 */
public interface ServeurInterface extends Remote {
	
	
	/**
	 *  Fonction qui sera appelé par le client pour se connecter au serveur de chat
	 *  ajout du client a la liste des clients connecté au serveur
	 * @param id
	 * @param password
	 * @param num_port
	 * @return
	 * @throws RemoteException
	 */
	  
	  boolean connect(String id,String password,int num_port)throws RemoteException;
	  
	  /**
	   * Fonction qui sera appellée par le client inscrit au service de chat pour envoyer un message au serveur
	   * ajouter un message a la liste des messages
	   * @param message
	   * @throws RemoteException
	   */
	 void sendMessage(String message) throws RemoteException;
	 /**
	  * Fonction permettant d'éliminer un client du service du chat
	  * @param nom
	  * @throws RemoteException
	  */
	 void disconnect(String nom)throws RemoteException;

	 //int getnbrcon() throws RemoteException;
	 //String []list_con()throws RemoteException;
	 /**
	  * Fonction qui permet de renvoyer un numero de port dispo
	  * @return
	  * @throws RemoteException
	  */
	 int get_num_port() throws RemoteException;
	
}
