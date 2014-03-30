package dpss.service.corba;

/**
 * Holder class for : GameServer
 * 
 * @author OpenORB Compiler
 */
final public class GameServerHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal GameServer value
     */
    public dpss.service.corba.GameServer value;

    /**
     * Default constructor
     */
    public GameServerHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public GameServerHolder(dpss.service.corba.GameServer initial)
    {
        value = initial;
    }

    /**
     * Read GameServer from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = GameServerHelper.read(istream);
    }

    /**
     * Write GameServer into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        GameServerHelper.write(ostream,value);
    }

    /**
     * Return the GameServer TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return GameServerHelper.type();
    }

}
