package dpss.service.corba;

/** 
 * Helper class for : GameServer
 *  
 * @author OpenORB Compiler
 */ 
public class GameServerHelper
{
    /**
     * Insert GameServer into an any
     * @param a an any
     * @param t GameServer value
     */
    public static void insert(org.omg.CORBA.Any a, dpss.service.corba.GameServer t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract GameServer from an any
     *
     * @param a an any
     * @return the extracted GameServer value
     */
    public static dpss.service.corba.GameServer extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return dpss.service.corba.GameServerHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the GameServer TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "GameServer" );
        }
        return _tc;
    }

    /**
     * Return the GameServer IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:GameInterface/GameServer:1.0";

    /**
     * Read GameServer from a marshalled stream
     * @param istream the input stream
     * @return the readed GameServer value
     */
    public static dpss.service.corba.GameServer read(org.omg.CORBA.portable.InputStream istream)
    {
        return(dpss.service.corba.GameServer)istream.read_Object(dpss.service.corba._GameServerStub.class);
    }

    /**
     * Write GameServer into a marshalled stream
     * @param ostream the output stream
     * @param value GameServer value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, dpss.service.corba.GameServer value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to GameServer
     * @param obj the CORBA Object
     * @return GameServer Object
     */
    public static GameServer narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof GameServer)
            return (GameServer)obj;

        if (obj._is_a(id()))
        {
            _GameServerStub stub = new _GameServerStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to GameServer
     * @param obj the CORBA Object
     * @return GameServer Object
     */
    public static GameServer unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof GameServer)
            return (GameServer)obj;

        _GameServerStub stub = new _GameServerStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
