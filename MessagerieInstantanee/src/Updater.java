import java.rmi.RemoteException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Project: MessagerieInstantanee
 * Creation date: 20 oct. 2013
 * Author: Audrey
 */

/**
 * @author Audrey
 *
 */
public class Updater{
	Timer timer ;
	Serverable serveur;
	
	public Updater(int minutes, Serverable serveur) {
		this.serveur = serveur;
		this.timer = new Timer();
		long t = Client.dateLastReception.getTime();
		Date dateFirstTime = new Date(t + (minutes*60*1000));
		this.timer.scheduleAtFixedRate(new UpdateTask (), dateFirstTime, minutes*1000*60);

	}
	
	class UpdateTask extends TimerTask {
		public void run () {
			String resultServeur ="";
			try {
				resultServeur = serveur.updateMessage(Client.id, Client.dateLastReception);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(resultServeur);
			Client.dateLastReception = new Date();
		}
	}
	
	public void stop(){
		timer. cancel ();
	}
}
