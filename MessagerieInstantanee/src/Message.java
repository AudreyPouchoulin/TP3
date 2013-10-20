
/**
 * Project: MessagerieInstantanee
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

/**
 * @author Audrey
 *
 */
public class Message {

	private int id;
	private String idClient;
	private String date;
	private String message;
	
	Message(String idClient , String message){
		
		this.idClient=idClient;
		this.message=message;
	}
	
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the idClient
	 */
	public String getIdClient() {
		return idClient;
	}
	/**
	 * @param idClient the idClient to set
	 */
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * affichage du message envoyé
	 */
	public String toString(){
		return idClient+">>"+message+"\n";
	}
	
}
