package com.jslabs.gophysicengine.renderer;

import com.jslabs.gophysicengine.maths.linear.*;
import com.jslabs.gophysicengine.simulation.*;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: Moteur physique permettant la simulation de la dynamique des
 * corps rigides.</p>
 *
 * <p>This class converts coordinates of a GObject from the object's referential
 * to the inertial referential. This provides a way to determine the first position
 * of a rigid body in the referential.</p>
 *
 * @todo Implement the logic of the class.
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class GGeometricConverter
{
    /**
     * Convert the position of a rigid body from its body referential
     * coordinates to a inertial referential by specifying its initial
     * position.
     * @param object G2DObject
     * @param initialPosition The initial position of the object relative
     * to the inertial referential.
     * @return GVector The object with its new positioning.
     */
    public static GVector convertReferential(GObject object, GVector initialPosition)
    {
        GVector result = new GVector(object.getPosition().getSize());
        result.setValue(0, object.getPosition().getValue(0) +
                        initialPosition.getValue(0));
        result.setValue(1, object.getPosition().getValue(1) +
                        initialPosition.getValue(1));
        if( object.getPosition().getSize() > 2 )
        {
            result.setValue(2, object.getPosition().getValue(2) +
                            initialPosition.getValue(2));
        }
        return result;
    }

}
