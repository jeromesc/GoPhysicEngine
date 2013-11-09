package com.jslabs.gophysicengine.simulation;

import java.util.*;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: Moteur physique permettant la simulation de la dynamique des
 * corps rigides.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class GObjectBuilder
{
    /**
     * Object Constants
     */
    public final static Integer OBJECT_SPRING = new Integer(1);

////////////////////////////////////////////////////////////////////////////////

    // collection of builders for objects
    // 2D
    private HashMap _object2DBuilders = new HashMap();
    // 3D
    private HashMap _object3DBuilders = new HashMap();
    // implements the singleton pattern
    private static GObjectBuilder _instance = new GObjectBuilder();

////////////////////////////////////////////////////////////////////////////////

    /**
     * Register a builder for a specific object type.
     * @param objectType Integer Object type
     * @param builder IG2DObjectBuilder The class builder
     */
    public void register2DBuilder(Integer objectType, Class builder)
    {
        _object2DBuilders.put(objectType, builder);
    }

    /**
     * Return a builder for a specific object type.
     * @param objectType Integer
     * @return IG2DObjectBuilder
     */
    public IG2DObjectBuilder getBuilder(Integer objectType) throws Exception
    {
        return (IG2DObjectBuilder) ((Class)_object2DBuilders.
                                    get(objectType)).newInstance();
    }

    /**
     * Return the current instance.
     * @return GObjectBuilder
     */
    public static GObjectBuilder getInstance()
    {
        return _instance;
    }

    /**
     * Private : for implementation of the
     * singleton pattern.
     */
    private GObjectBuilder()
    {
        register2DBuilder(OBJECT_SPRING, G2DSpringBuilder.class);
    }
}
