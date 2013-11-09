package com.jslabs.simulationsystems;

import com.jslabs.gophysicengine.simulation.*;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: Moteur physique permettant la simulation de la dynamique des
 * corps rigides.</p>
 * <p>Creates new scene with two Mass and Spring systems.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class HarmonicOscillatorSystem
{
	/**
	 * The static method creates a scene with two spring-mass
	 * systems.
	 * @return GScene the scene object containing the 2 springs.
	 * @throws CompilationException If an error has occured
	 * while trying to compile the scene.
	 */
    public static GScene createHarmonicOscillatorScene()
            throws CompilationException, Exception
    {
        // create new scene for adding objects
        GScene scene = new GScene();

		// define the first spring
        G2DSpring spring = (G2DSpring) GObjectBuilder.getInstance().getBuilder(
                GObjectBuilder.OBJECT_SPRING).build(100, 0, 0, 0, 0, 0);
        spring.setMass(20);
        spring.setSpringForce(50);
        spring.setSpringRestLength(50);
        spring.setDimensions(40, 40, 0);
        spring.setInertialReferentialPosition(50, 300, 0);

		// define the parameters of the second spring
        G2DSpring spring2 = (G2DSpring) GObjectBuilder.getInstance().getBuilder(
                GObjectBuilder.OBJECT_SPRING).build(10, 0, 0, 0, 0, 0);
        spring2.setMass(20);
        spring2.setSpringForce(100);
        spring2.setSpringRestLength(200);
        spring2.setDimensions(40, 40, 0);
        spring2.setInertialReferentialPosition(50, 150, 0);

		// add the two springs to the scene
        scene.addNode(spring);
        scene.addNode(spring2);

		// compile the scene : make sure all
		// parameters are initialized correctly.
        scene.compile();

        return scene;
    }
}
