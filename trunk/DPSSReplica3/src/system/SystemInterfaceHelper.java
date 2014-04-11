package system;

/** 
 * Helper class for : SystemInterface
 *  
 * @author OpenORB Compiler
 */ 
public class SystemInterfaceHelper
{
    /**
     * Insert SystemInterface into an any
     * @param a an any
     * @param t SystemInterface value
     */
    public static void insert(org.omg.CORBA.Any a, system.SystemInterface t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract SystemInterface from an any
     *
     * @param a an any
     * @return the extracted SystemInterface value
     */
    public static system.SystemInterface extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return system.SystemInterfaceHelper.narrow( a.extract_Object() );
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
     * Return the SystemInterface TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "SystemInterface" );
        }
        return _tc;
    }

    /**
     * Return the SystemInterface IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:system/SystemInterface:1.0";

    /**
     * Read SystemInterface from a marshalled stream
     * @param istream the input stream
     * @return the readed SystemInterface value
     */
    public static system.SystemInterface read(org.omg.CORBA.portable.InputStream istream)
    {
        return(system.SystemInterface)istream.read_Object(system._SystemInterfaceStub.class);
    }

    /**
     * Write SystemInterface into a marshalled stream
     * @param ostream the output stream
     * @param value SystemInterface value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, system.SystemInterface value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to SystemInterface
     * @param obj the CORBA Object
     * @return SystemInterface Object
     */
    public static SystemInterface narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof SystemInterface)
            return (SystemInterface)obj;

        if (obj._is_a(id()))
        {
            _SystemInterfaceStub stub = new _SystemInterfaceStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to SystemInterface
     * @param obj the CORBA Object
     * @return SystemInterface Object
     */
    public static SystemInterface unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof SystemInterface)
            return (SystemInterface)obj;

        _SystemInterfaceStub stub = new _SystemInterfaceStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
