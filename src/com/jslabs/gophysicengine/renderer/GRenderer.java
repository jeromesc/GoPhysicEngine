package com.jslabs.gophysicengine.renderer;

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import com.jslabs.gophysicengine.simulation.*;

/**
 * <p>Title: Go! PhysicEngine</p>
 *
 * <p>Description: Moteur physique permettant la simulation de la dynamique des
 * corps rigides.</p>
 * <p>This class displays GObjects on a double buffered canvas. Optimisation have
 * been made in order to increase display performance through the use of VolatileImage
 * class.</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Jerome Schmaltz
 * @version 1.0
 */
public class GRenderer
        extends Canvas
{
	/**
	 * Virtual image.
	 */
    private VolatileImage _vImg;

    /**
     * Collection of objects to render.
     */
    private Vector _nodes;

///////////////////////////////////////////////////////////////////////////////

	/**
	 * Defaut overloaded method for painting the objects
	 * on the canvas.
	 */
    public synchronized void paint(Graphics g)
    {
		// only if the collection is not empty
        if( _nodes != null && !_nodes.isEmpty() )
        {
			// create a back buffer (replace the virtal image)
            createBackBuffer();
            do
            {
				// make sure the virtual image is still available
                if (_vImg.validate(getGraphicsConfiguration()) ==
                    VolatileImage.IMAGE_INCOMPATIBLE)
                {
                    createBackBuffer();
                }
				// create an offscreen image to paint on
                Graphics offScreenG = _vImg.getGraphics();
                for (int index = 0; index < (_nodes.isEmpty() ? 0 : _nodes.size());
                                 index++)
                {
					// all GObject has its own method for painting...
                    ((GObject) _nodes.get(index)).draw(offScreenG);
                }
                // draw the offscreen image to the onscreen image
                // (swap buffers)
                g.drawImage(_vImg, 0, 0, this);
            }
            while (_vImg.contentsLost());
        }
    }

	/**
	 * Create a new compatible volatile image with
	 * the dimension of the current canvas.
	 */
    private void createBackBuffer()
    {
        _vImg = getGraphicsConfiguration().createCompatibleVolatileImage(
                getWidth(), getHeight());
    }

	/**
	 * Update method calls automatically
	 * the paint function.
	 */
    public void update(Graphics g)
    {
        paint(g);
    }

	/**
	 * This method is called by a GScene object to
	 * update the new objet's coordinates on the
	 * canvas.
	 */
    public void setObjects(Vector nodes)
    {
        _nodes = nodes;
        repaint();
    }
}
