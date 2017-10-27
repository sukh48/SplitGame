package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

/**
 * Created by slotey on 16-02-12.
 */
public class CTweenEffect extends Component implements Pool.Poolable, TweenCallback
{
    public static final int BEGIN=0;
    public static final int TWEENING=1;
    public static final int END=2;

    public int type;
    public int tweeningState;

    @Override
    public void reset()
    {
        type=0;
        tweeningState=BEGIN;
    }

    @Override
    public void onEvent(int type, BaseTween<?> source)
    {
        tweeningState=END;
    }
}
