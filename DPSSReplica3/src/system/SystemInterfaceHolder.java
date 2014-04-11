package system;

/**
 * Holder class for : SystemInterface
 * 
 * @author OpenORB Compiler
 */
final public class SystemInterfaceHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal SystemInterface value
     */
    public system.SystemInterface value;

    /**
     * Default constructor
     */
    public SystemInterfaceHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public SystemInterfaceHolder(system.SystemInterface initial)
    {
        value = initial;
    }

    /**
     * Read SystemInterface from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = SystemInterfaceHelper.read(istream);
    }

    /**
     * Write SystemInterface into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        SystemInterfaceHelper.write(ostream,value);
    }

    /**
     * Return the SystemInterface TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return SystemInterfaceHelper.type();
    }

}
