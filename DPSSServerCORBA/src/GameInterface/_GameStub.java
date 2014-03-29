package GameInterface;

/**
 * Interface definition: Game.
 * 
 * @author OpenORB Compiler
 */
public class _GameStub extends org.omg.CORBA.portable.ObjectImpl
        implements Game
{
    static final String[] _ids_list =
    {
        "IDL:GameInterface/Game:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = GameInterface.GameOperations.class;

    /**
     * Operation createPlayerAccount
     */
    public String createPlayerAccount(String firstNameParam, String lastNameParam, String usernameParam, String passwordParam, int ageParam, String iPAdressParam)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("createPlayerAccount",true);
                    _output.write_string(firstNameParam);
                    _output.write_string(lastNameParam);
                    _output.write_string(usernameParam);
                    _output.write_string(passwordParam);
                    _output.write_long(ageParam);
                    _output.write_string(iPAdressParam);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("createPlayerAccount",_opsClass);
                if (_so == null)
                   continue;
                GameInterface.GameOperations _self = (GameInterface.GameOperations) _so.servant;
                try
                {
                    return _self.createPlayerAccount( firstNameParam,  lastNameParam,  usernameParam,  passwordParam,  ageParam,  iPAdressParam);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation playerSignIn
     */
    public String playerSignIn(String usernameParam, String passwordParam, String iPAdressParam)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("playerSignIn",true);
                    _output.write_string(usernameParam);
                    _output.write_string(passwordParam);
                    _output.write_string(iPAdressParam);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("playerSignIn",_opsClass);
                if (_so == null)
                   continue;
                GameInterface.GameOperations _self = (GameInterface.GameOperations) _so.servant;
                try
                {
                    return _self.playerSignIn( usernameParam,  passwordParam,  iPAdressParam);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation playerSignOut
     */
    public String playerSignOut(String usernameParam, String iPAdressParam)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("playerSignOut",true);
                    _output.write_string(usernameParam);
                    _output.write_string(iPAdressParam);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("playerSignOut",_opsClass);
                if (_so == null)
                   continue;
                GameInterface.GameOperations _self = (GameInterface.GameOperations) _so.servant;
                try
                {
                    return _self.playerSignOut( usernameParam,  iPAdressParam);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation getPlayerStatus
     */
    public String getPlayerStatus(String adminUsernameParam, String adminPasswordParam, String iPAdressParam)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("getPlayerStatus",true);
                    _output.write_string(adminUsernameParam);
                    _output.write_string(adminPasswordParam);
                    _output.write_string(iPAdressParam);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("getPlayerStatus",_opsClass);
                if (_so == null)
                   continue;
                GameInterface.GameOperations _self = (GameInterface.GameOperations) _so.servant;
                try
                {
                    return _self.getPlayerStatus( adminUsernameParam,  adminPasswordParam,  iPAdressParam);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
