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

    public static boolean drag;
    public static Vector2 dragV = new Vector2();

    public static boolean leftPressed;
    public static boolean rightPressed;
    public static boolean shootPressed;

    int shootPointer;
    int leftPointer;
    int rightPointer;

    OrthographicCamera cam;

    public InputListener(OrthographicCamera cam)
    {
        this.cam = cam;
    }

    @Override
    public boolean keyDown(int keycode)
    {
        if(keycode==Input.Keys.LEFT){
            leftPressed=true;
        }
        if(keycode==Input.Keys.RIGHT){
            rightPressed=true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if(keycode==Input.Keys.LEFT){
            leftPressed=false;
        }
        if(keycode==Input.Keys.RIGHT){
            rightPressed=false;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        final Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        cam.unproject(worldCoordinates);

        touchV.set(worldCoordinates.x, worldCoordinates.y);
        touch=true;

        if(touchV.x>0 && touchV.x<SpacePirate.V_WIDTH/4)
        {
            leftPressed=true;
            leftPointer=pointer;
        }

        if(touchV.x>=SpacePirate.V_WIDTH/4 && touchV.x<((SpacePirate.V_WIDTH/4)+300))
        {
            shootPressed=true;
            shootPointer=pointer;
        }

        if(touchV.x>=((SpacePirate.V_WIDTH/4)+300))
        {
            rightPressed=true;
            rightPointer=pointer;
        }

        return touch;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        touch=false;
        drag=false;

        if (pointer==shootPointer) {
            shootPressed=false;
        }
        if (pointer==leftPointer) {
            leftPressed=false;
        }
        if (pointer==rightPointer) {
            rightPressed=false;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        final Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        cam.unproject(worldCoordinates);

        drag=true;
        dragV.set(worldCoordinates.x, worldCoordinates.y);

        return drag;
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
}
