package dpss.model;

/**  Player object (Server side) 
 * 	 Represents each player on the server hash table
 *   @class Player
 *   
 * */
public class Player {
	
	private	String firstName;
	private	String lastName;
	private	String userName;
	private	int age;
	private	String pwd;
	private	String iPAdress;
	private	boolean online;
	
	public Player(String firstNameParam,String lastNameParam, String userNameParam, int ageParam, String pwdParam, String iPAdressParam){
		this.firstName=firstNameParam;
		this.lastName=lastNameParam;
		this.age=ageParam;
		this.userName=userNameParam;
		this.pwd=pwdParam;
		this.iPAdress=iPAdressParam;
	}

	public Player(String userNameParam){
		this.firstName="";
		this.lastName="";
		this.age=-1;
		this.userName=userNameParam;
		this.pwd="";
		this.iPAdress="";
	}
	
	@Override 
	public boolean equals(Object obj){
		final Player p=(Player) obj;
		if(this.userName.equals(p.userName))
			return true;
		else return false;
	}
	
	public void setName(String firstNameParam,String lastNameParam){
		this.firstName=firstNameParam;
		this.lastName=lastNameParam;
		}
	
	public void setAge(int age){
			this.age=age;
		}
	
	public boolean validatePWD(String pwdParam){
		if(this.pwd.equals(pwdParam)){
			return true;
		}
		else return false;
	}
	
	public void setStatus(boolean status){
	this.online=status;
	}
	
	public boolean getStatus(){
		return this.online;
	}
	
	public void setIPAdress(String iPAdressParam){
		this.iPAdress=iPAdressParam;
	}	
	
	public String getCSV(){
		return this.firstName + ";" +
		this.lastName + ";" +
		this.userName + ";" +
		this.pwd + ";" +				
		this.age + ";" +
		this.iPAdress;		
	}
	
}
