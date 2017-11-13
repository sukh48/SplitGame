package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import ns.spacepirate.game.controllers.InputActionListener;

/**
 * Created by sukhmac on 2017-10-26.
 */
public class CDivideBall extends Component implements Pool.Poolable
{
    public static final int DIR_LEFT=0;
    public static final int DIR_RIGHT=1;

    public static final int SINGLE=0;
    public static final int DIVIDED=1;

    public int state;
    public int movingDir;
    public boolean applyForce;
    public float speed;
    public float attSpeed;
    public Entity parent;

    public CDivideBall()
    {
        reset();
    }

    @Override
    public void reset()
    {
        state=SINGLE;
        movingDir=DIR_LEFT;
        applyForce=false;
        speed=0;
        attSpeed=0;
        parent=null;
    }
}
