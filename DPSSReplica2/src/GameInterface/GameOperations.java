package GameInterface;

/**
 * Interface definition: Game.
 * 
 * @author OpenORB Compiler
 */
public interface GameOperations
{
    /**
     * Operation createPlayerAccount
     */
    public String createPlayerAccount(String firstName, String lastName, String username, String password, short age, String iPAdress);

    /**
     * Operation playerSignIn
     */
    public String playerSignIn(String username, String password, String iPAdress);

    /**
     * Operation playerSignOut
     */
    public String playerSignOut(String username, String iPAdress);

    /**
     * Operation transferAccount
     */
    public String transferAccount(String Username, String Password, String OldIP, String NewIP);

    /**
     * Operation getPlayerStatus
     */
    public String getPlayerStatus(String adminUsername, String adminPassword, String IP);

    /**
     * Operation suspendAccount
     */
    public String suspendAccount(String adminUsername, String adminPassword, String IP, String userName);

}
