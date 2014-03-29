package GameInterface;

/**
 * Holder class for : Game
 * 
 * @author OpenORB Compiler
 */
final public class GameHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal Game value
     */
    public GameInterface.Game value;

    /**
     * Default constructor
     */
    public GameHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public GameHolder(GameInterface.Game initial)
    {
        value = initial;
    }

    /**
     * Read Game from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = GameHelper.read(istream);
    }

    /**
     * Write Game into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        GameHelper.write(ostream,value);
    }

    /**
     * Return the Game TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return GameHelper.type();
    }

}
