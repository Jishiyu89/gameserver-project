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
     * Operation getPlayerStatus
     */
    public String getPlayerStatus(String adminUsernameParam, String adminPasswordParam, String iPAdressParam);

}
