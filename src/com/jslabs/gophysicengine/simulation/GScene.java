package com.jslabs.gophysicengine.simulation;

import java.util.*;

import com.jslabs.gophysicengine.physics.*;
import java.io.*;
import com.jslabs.gophysicengine.renderer.GRenderer;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: This class contains all object (rigid bodies)</p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class GScene
        implements Runnable
{
    /**
     * Collection of nodes in the current scene.
     */
    private Vector _nodes = new Vector();
    private long cycleTime = System.currentTimeMillis();
    private GRenderer _renderer;

////////////////////////////////////////////////////////////////////////////////

    /**
     * Add a node to the current collection.
     * @param object GObject
     */
    public void addNode(GObject object)
    {
        _nodes.add(object);
    }

    public void setRenderer(GRenderer renderer)
    {
        _renderer = renderer;
    }

    /**
     * Return the number of nodes in the
     * current collection.
     * @return int Number of nodes.
     */
    public int getNodeCount()
    {
        return _nodes.isEmpty() ? 0 : _nodes.size();
    }

    /**
     * Return the node at current position
     * defined in parameter.
     * @param index int
     * @return GObject
     */
    public GObject getNode(int index)
    {
        return (GObject) _nodes.get(index);
    }

    /**
     * Compile the scene; it initializes all
     * rigid body values.
     * @throws CompilationException
     */
    public void compile()
            throws CompilationException
    {
        // initialize all objects (compute forces, torques, initial values)
        for (int index = 0; index < getNodeCount(); index++)
        {
            try
            {
                getNode(index).initialize();
            }
            catch (InitializationException ex)
            {
                throw new CompilationException("Scene Compilation exception.\n" +
                                               "Object : " +
                                               getNode(index).toString() + "\n" +
                                               ex.getMessage());
            }
        }
    }

	/**
	 * This method is called to wait a little bit between
	 * each calculations.
	 */
    private void synchFramerate()
    {
		// make a cycle time to display
		// 50 FPS
        cycleTime = cycleTime + 20; // 1000 ms / 20 ms = 50 FPS...
        long difference = cycleTime - System.currentTimeMillis();
        try
        {
			// wait a little bit
            Thread.sleep(Math.max(0, difference));
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Method must be implemented in order to treat
     * this class as a thread.
     */
    public void run()
    {
        double counter = 0;
        double timer = 0;
        double dt = 0.2;

        while (true)
        {
            timer = 0.2 * counter;
            for (int index = 0; index < getNodeCount(); index++)
            {
                getNode(index).update(timer, dt);
                System.out.println( timer + ";" + getNode(index).getPosition().getValue(0) );
            }
            // call the renderer to renderer the
            // new object's coordinates.
            _renderer.setObjects(_nodes);
            // wait a little bit.
            synchFramerate();
            ++counter;
        }
    }

}
