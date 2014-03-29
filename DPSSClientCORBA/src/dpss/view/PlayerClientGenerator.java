package dpss.view;


/** Specific class designed to emulate multiple players trying to create an account and sign=in (Client side)
 *  Using multi-threading it instantiates several PlayerClient objects parallelly. 
 *  @class PlayerClientGenerator
 *   
 * */
public class PlayerClientGenerator extends Thread {

	public void run(){

		PlayerClient client = new PlayerClient("a","a","c","a","18");
		client.createPlayer();
		client.signInPlayer();		
	}

	public static void main(String[] args) {

		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();
		(new PlayerClientGenerator()).start();

	}
}
