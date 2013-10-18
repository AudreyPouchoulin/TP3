/**
 * Project: MessagerieInstantanee
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

/**
 * @author Audrey
 *
 */
public class Utilisateur {

	private String id;
	private String password;
	private boolean connected;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the connected
	 */
	public boolean isConnected() {
		return connected;
	}
	/**
	 * @param connected the connected to set
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	
}
