package ns.spacepirate.game.desktop.editor;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ns.spacepirate.game.editor.Sandbox;

/**
 * Created by sukhmac on 17-11-10.
 */
public class EditorLauncher extends JFrame
{
    LwjglAWTCanvas canvas;

    public EditorLauncher()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container container = getContentPane();


        Sandbox sandbox = new Sandbox();

        Controller controller = new Controller();
        EditorPanel editorPanel = new EditorPanel(controller);
        controller.init(editorPanel, sandbox);

        canvas = new LwjglAWTCanvas(sandbox);
        //canvas.getCanvas().setSize(720,1280);

        container.add(editorPanel, BorderLayout.LINE_START);
        container.add(canvas.getCanvas(), BorderLayout.CENTER);

        pack();

        setVisible(true);
        setSize(800,600);
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EditorLauncher();
            }
        });
    }

    @Override
    public void dispose() {
        canvas.stop();
        super.dispose();
    }

}
