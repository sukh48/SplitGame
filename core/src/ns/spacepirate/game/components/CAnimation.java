package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

/**
 * Created by sukhmac on 2016-02-09.
 */
public class CAnimation extends Component implements Pool.Poolable
{
    public static final int ANIM_IDLE=0;
    public static final int ANIM_DESTROYING=1;
    public static final int ANIM_DESTROYED=2;

    public HashMap<Integer, Animation> animations;

    private int state = 0;
    public float time = 0.0f;

    public CAnimation()
    {
        animations = new HashMap<Integer, Animation>();
        reset();
    }

    public int get()
    {
        return state;
    }

    public void set(int newState)
    {
        state = newState;
        time = 0.0f;
    }

    @Override
    public void reset()
    {
        animations.clear();
        state=ANIM_IDLE;
        time=0;
    }
}
