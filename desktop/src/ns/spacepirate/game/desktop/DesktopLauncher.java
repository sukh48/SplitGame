package ns.spacepirate.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.tests.CollisionTest;

public class DesktopLauncher
{
	public static void main (String[] arg)
    {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=720/2;
		config.height=1280/2;
		config.foregroundFPS=60;
		config.vSyncEnabled=true;
		new LwjglApplication(new SpacePirate(), config);
	}
}
