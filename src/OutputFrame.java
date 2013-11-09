import javax.swing.*;

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

import java.awt.BorderLayout;

public class OutputFrame
        extends JFrame
{
    public OutputFrame()
    {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setResizable(false);
      setTitle("GoPhysicEngine (Simulating...)");
      getContentPane().setLayout( new BorderLayout());
      setSize(640, 480);
    }
}
