package ns.spacepirate.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sukhmac on 2016-02-06.
 */
public class InputListener implements InputProcessor
{

    public static boolean touch;
    public static Vector2 touchV = new Vector2();

    OrthographicCamera cam;

    public InputListener(OrthographicCamera cam)
    {
        this.cam = cam;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        touch = true;
        return touch;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        touch=false;
        return touch;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }
}
