/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */


/**
 * @author Audrey
 *
 */
public interface ServeurInterface extends java.rmi.Remote{
		public void seeMessage(String message) throws java.rmi.RemoteException;

		/**
		 * @param str
		 */
		public void connect(String str) throws java.rmi.RemoteException;
}
