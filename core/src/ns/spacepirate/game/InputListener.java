package ns.spacepirate.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by sukhmac on 2016-02-06.
 */
public class InputListener implements InputProcessor
{

    public static boolean touched=false;
    public static Vector2 touchedPos = new Vector2();

    public static boolean dragged=false;
    public static Vector2 dragPos = new Vector2();

//    public static
    //public static Vector2 touchV = new Vector2();

    OrthographicCamera cam;

    public InputListener(OrthographicCamera cam)
    {
        this.cam = cam;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        touched = true;
        Vector3 vector3 = new Vector3();
        vector3.set(screenX, screenY,0);
        cam.unproject(vector3);
        touchedPos.set(vector3.x, vector3.y);

        System.out.println("TOUCH DOWN X: "+vector3.x+" y: "+vector3.y);


        return touched;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        touched = false;
        dragged = false;
        System.out.println("TOUCH UP");
        return touched;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        dragged=true;
        Vector3 vector3 = new Vector3();
        vector3.set(screenX, screenY, 0);
        cam.unproject(vector3);
        dragPos.set(vector3.x, vector3.y);

        //System.out.println("DRAGGED: X"+dragPos.x+" Y: "+dragPos.y);

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

//    @Override
//    public boolean touchDown(float x, float y, int pointer, int button) {
//        System.out.println("TOUCHDOWN");
//        touched=true;
//        return false;
//    }
//
//    @Override
//    public boolean tap(float x, float y, int count, int button) {
//        System.out.println("Tap");
//        touched=false;
//        return false;
//    }
//
//    @Override
//    public boolean longPress(float x, float y) {
//        System.out.println("PRESSED");
//        return false;
//    }
//
//    @Override
//    public boolean fling(float velocityX, float velocityY, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean pan(float x, float y, float deltaX, float deltaY) {
//        return false;
//    }
//
//    @Override
//    public boolean panStop(float x, float y, int pointer, int button) {
//        return false;
//    }
//
//    @Override
//    public boolean zoom(float initialDistance, float distance) {
//        return false;
//    }
//
//    @Override
//    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
//        return false;
//    }
}
