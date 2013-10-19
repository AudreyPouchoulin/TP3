import java.io.Serializable;

/**
 * Project: MessagerieInstantanee
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

/**
 * @author Audrey, Olfa
 *
 */
public class Utilisateur  {

	private String id;
	private String password;
	private boolean connected;
	private int port_nb;
	
	
	public int getPort_nb() {
		return port_nb;
	}
	public void setPort_nb(int port_nb) {
		this.port_nb = port_nb;
	}
	public Utilisateur(String id, String password, boolean connected,int port_nb){
		this.id=id;
		this.password=password;
		this.connected=connected;
		this.port_nb=port_nb;
	}
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
