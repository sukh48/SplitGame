package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import ns.spacepirate.game.InputListener;
import ns.spacepirate.game.controllers.InputActionListener;

public class PlayerInputSystem extends EntitySystem
{
    boolean initialTouch;
    boolean touching;

    public PlayerInputSystem()
    {
        initialTouch=false;
        touching=false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

//        if(InputListener.touched)
//        {
//            if(!initialTouch && !touching) {
//                initialTouch=true;
//            }
//            touching=true;
//        }else {
//            if(touching) {
//                initialTouch=false;
//            }
//            touching=false;
//        }

        if(InputListener.touched)
        {
            if(!initialTouch) {
                initialTouch = true;
                InputActionListener.getInstance().set(InputActionListener.FIRST_TOUCH);
            }else {
                InputActionListener.getInstance().set(InputActionListener.PRESSED);
            }

        }else {
            InputActionListener.getInstance().set(InputActionListener.NONE);
            initialTouch=false;
        }

//        if(initialTouch && !divideBallComponent.divided) {
//            divideBallComponent.divide=true;
//            initialTouch=false;
//            System.out.println("Touch");
//        }

//        if(initialTouch) {
//            System.out.println("INITIAL Touch");
//            InputActionListener.getInstance().set(InputActionListener.FIRST_TOUCH);
//        }
    }

}
