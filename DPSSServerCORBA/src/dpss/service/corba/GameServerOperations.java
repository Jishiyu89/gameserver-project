package dpss.service.corba;

/**
 * Interface definition: GameServer.
 * 
 * @author OpenORB Compiler
 */
public interface GameServerOperations
{
    /**
     * Operation createPlayerAccount
     */
    public String createPlayerAccount(String firstNameParam, String lastNameParam, String usernameParam, String passwordParam, int ageParam, String iPAdressParam);

    /**
     * Operation playerSignIn
     */
    public String playerSignIn(String usernameParam, String passwordParam, String iPAdressParam);

    /**
     * Operation playerSignOut
     */
    public String playerSignOut(String usernameParam, String iPAdressParam);

    /**
     * Operation transferAccount
     */
    public String transferAccount(String usernameParam, String passwordParam, String oldIPAddressParam, String newIPAddressParam);

    /**
     * Operation getPlayerStatus
     */
    public String getPlayerStatus(String adminUsernameParam, String adminPasswordParam, String iPAdressParam);

    /**
     * Operation suspendAccount
     */
    public String suspendAccount(String adminUsernameParam, String adminPasswordParam, String iPAdressParam, String usernameToSuspendParam);

}
