package GameServer;

public class Player {
public
	String firstName;
	String lastName;
	String userName;
	int age;
	String psw;
	String IP;
	boolean status;
	
	Player(String firstName,String lastName, String userName, int age, String psw, String IP){
		this.firstName=firstName;
		this.lastName=lastName;
		this.age=age;
		this.userName=userName;
		this.psw=psw;
		this.IP=IP;
		this.status=false;
	}
	Player(String userName){
		firstName="";
		lastName="";
		age=-1;
		this.userName=userName;
		psw="";
		IP="";
	}
	@Override 
	public boolean equals(Object obj){
		final Player p=(Player) obj;
		if(this.userName.equals(p.userName))
			return true;
		else return false;
	}
	public void setName(String firstName,String lastName){
		this.firstName=firstName;
		this.lastName=lastName;
		}
	public void setAge(int age){
			this.age=age;
		}
	public String getPSW(){
		return this.psw;
	}
	public boolean setPSW(String oldone,String newone){
		if(this.psw==oldone){
			this.psw=newone;
			return true;
		}
		else return false;
	}
	public void setStatus(boolean status){
		this.status=status;
	}
	public boolean getStatus(){
		return this.status;
	}
	
}
