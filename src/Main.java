import com.jslabs.simulationsystems.HarmonicOscillatorSystem;
import com.jslabs.gophysicengine.simulation.*;
import com.jslabs.gophysicengine.renderer.*;

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
public class Main
{
    public Main()
    {
        try
        {
            GScene scene = HarmonicOscillatorSystem.
                           createHarmonicOscillatorScene();
            Thread tScene = new Thread(scene);
            OutputFrame frame = new OutputFrame();
            GRenderer renderer = new GRenderer();
            scene.setRenderer(renderer);
            frame.getContentPane().add(renderer);
            tScene.start();
            frame.setVisible(true);
        }
        catch (Exception ex)
        {
            System.out.println("GoPhysicEngine Error : " + ex.getMessage() );
        }
    }

    public static void main(String[] args)
    {
        Main main = new Main();
    }
}
