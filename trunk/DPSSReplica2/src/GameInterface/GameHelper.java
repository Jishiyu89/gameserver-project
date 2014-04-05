package GameInterface;

/** 
 * Helper class for : Game
 *  
 * @author OpenORB Compiler
 */ 
public class GameHelper
{
    /**
     * Insert Game into an any
     * @param a an any
     * @param t Game value
     */
    public static void insert(org.omg.CORBA.Any a, GameInterface.Game t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract Game from an any
     *
     * @param a an any
     * @return the extracted Game value
     */
    public static GameInterface.Game extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return GameInterface.GameHelper.narrow( a.extract_Object() );
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
     * Return the Game TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "Game" );
        }
        return _tc;
    }

    /**
     * Return the Game IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:GameInterface/Game:1.0";

    /**
     * Read Game from a marshalled stream
     * @param istream the input stream
     * @return the readed Game value
     */
    public static GameInterface.Game read(org.omg.CORBA.portable.InputStream istream)
    {
        return(GameInterface.Game)istream.read_Object(GameInterface._GameStub.class);
    }

    /**
     * Write Game into a marshalled stream
     * @param ostream the output stream
     * @param value Game value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, GameInterface.Game value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to Game
     * @param obj the CORBA Object
     * @return Game Object
     */
    public static Game narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Game)
            return (Game)obj;

        if (obj._is_a(id()))
        {
            _GameStub stub = new _GameStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to Game
     * @param obj the CORBA Object
     * @return Game Object
     */
    public static Game unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Game)
            return (Game)obj;

        _GameStub stub = new _GameStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
