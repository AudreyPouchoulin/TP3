/**
 * Project: MessagerieInstantanée
 * Creation date: 18 oct. 2013
 * Author: Audrey
 */

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
/**
 * @author Audrey,Olfa
 *
 */
@SuppressWarnings("serial")

public class Serveur extends UnicastRemoteObject implements ServeurInterface{
	/**
	 * @throws RemoteException
	 */
	
	//Nom du fichier utilisé pour sauvegarder les clients inscrits dans le service de chat
	  private final String NOM_FICHIER="clients.dat";
	//numero de port
	  public static int port_num=8090;
	//liste des clients
	  private ArrayList <Utilisateur> utilisateurs=new ArrayList<Utilisateur>();;
	//liste des clients connectés 
	  ArrayList <Client>clients_conx=new ArrayList<Client>();
	  
	  /**
	   * Constructeur
	   * @throws RemoteException
	   */
	  
	  protected Serveur() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	  }

		/**
		 * Chargement des clients apartir du fichier
		 */
	
	  ObjectInputStream fe;
   
	  public void readData(){
    
		  boolean trouve=true,pasEncore=true;
		  try {
			  fe = new ObjectInputStream(new FileInputStream(NOM_FICHIER));
		  	}
		  catch(FileNotFoundException fnf){
			  System.out.println(fnf.toString());
			  trouve = false;
		  }
		  catch (IOException eof) {
			  System.out.println(eof.toString());
		  }
		  //si le fichier existe
		  if(trouve){
			  while (pasEncore) {
				  try {
					  Utilisateur u = (Utilisateur) fe.readObject();
					  utilisateurs.add(u);
					  System.out.println("lecture des contacts terminé");
				  }
				  catch (EOFException e) {
					  pasEncore = false;
					  System.out.println("fin de fichier");
				  }
				  catch (ClassNotFoundException ex2) {}
				  catch (IOException ex2) {}
			  }
			  try {
				  fe.close();
			  }
			  catch (IOException ex) {
			  }
		  }
	  }
	
	  /**
	   * Arret du serveur 
	   */
	
	
	// Implémentation de la méthode distante
	public void sendMessage(String message) throws java.rmi.RemoteException {
		
		System.out.println(message);
		
	}
	
	@Override
	public int get_num_port() throws RemoteException {
		// TODO Auto-generated method stub
		return port_num++;
	}
	
	/**
	 * Demarrage du serveur
	 */
	public static void _start(){
		
		String URL;		
		try {	
			// Création du serveur de nom - rmiregistry
			Registry registry = LocateRegistry.createRegistry(1099);
			
			// Création d ’une instance de l’objet serveur
			Serveur obj = new Serveur();
		
			// Calcul de l’URL du serveur
			URL = "//"+InetAddress.getLocalHost().getHostName()+":"+ port_num+"/mon_serveur";
			Naming.rebind(URL, obj);
			
			System.out.println("Serveur en route: " + URL);
			
			
		} catch (Exception exc) {
			System.out.println("Erreur dans la connexion au serveur" + exc.toString()); 
		}
		
		
	}
	
	public static void main(String args[]) {

		_start();
		
	}
	
	
	// Implémentation de la méthode distante
/*	public void connect(String id) throws java.rmi.RemoteException{
		
		for (int i=0;i<listUser.size();i++){
			if (listUser.get(i).getId().equals(id)){
				listUser.get(i).getPassword();
				ServeurInterface client = null;
				try {
					client = (ServeurInterface) Naming.lookup("//"+InetAddress.getLocalHost().getHostName()+":8090"+"/mon_serveur");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (client!=null){
					client.connect("Entre ton mot de passe");
				} else {
					System.out.println("Erreur dans l'envoi d'un message sur le client");
				}
				
			}
		}
	}
*/
	@Override
	public void disconnect(String id) throws RemoteException {
		// TODO Auto-generated method stub
		     int i=0;
		     	//recherche ds la liste des clients connectés
			     while(i<clients_conx.size()&&(clients_conx.get(i)).getId().compareTo(id)!=0)
			       i++;
			     
			      if(i<clients_conx.size())
			       {
			         clients_conx.remove(i);
			        if(clients_conx.size()>=1){
			          for (i = 0; i < clients_conx.size(); i++) {
			            ServeurInterface S = null;
			                   try {
			                	   S = (ServeurInterface) Naming.lookup("//"+InetAddress.getLocalHost().getHostName()+clients_conx.get(i).getPort_num()+"/mon_serveur");
			                     //S.clidisconnect(id);
			                   }
			                   catch (Exception e) {
			                	   System.out.println(e.toString());
			                   }   
			     }
			   }
			   //Changement de l'état de l'utilisateur dans le serveur
			         i=0;
			         while(i<utilisateurs.size()&& utilisateurs.get(i).getId().compareTo(id)!=0)
			           i++;
			           utilisateurs.get(i).setConnected(false);

			       }
			    }
		


	@Override
	public boolean connect(String id, String password, int port_num)
			throws RemoteException {
		// TODO Auto-generated method stub
		boolean result=false;
		
		//recherche dans la liste des clients 
		int i=0;
		while (i<utilisateurs.size()&& 
				utilisateurs.get(i).getId().compareTo(id)!=0 ){
			i++;	
		}
		
		if (i<utilisateurs.size()){
			result=true;
			//changement de l'état dans le serveur
			utilisateurs.get(i).setConnected(true);
			Client c= new Client(id,port_num);
			//ajouter a la liste des clients connecté
			clients_conx.add(c);
			
			if (clients_conx.size()>1){
				for (i=0;i<clients_conx.size();i++){
					ServeurInterface S=null;
					try{
						S = (ServeurInterface) Naming.lookup("//"+InetAddress.getLocalHost().getHostName()+clients_conx.get(i).getPort_num()+"/mon_serveur");
						//S.newcliConnexion(id);
					}
					catch (Exception e){
						System.out.println("Impossible de se connecter"+e.toString() );
					}
					
				}
			}
		
		}
		return result;
	}

	
	
	

}
