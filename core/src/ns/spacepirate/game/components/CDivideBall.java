package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by sukhmac on 2017-10-26.
 */
public class CDivideBall extends Component implements Pool.Poolable
{
    public static final int DIR_LEFT=0;
    public static final int DIR_RIGHT=1;

    public int movingDir;
    public boolean divide;
    public boolean canMove;
    public float speed;
    public float attSpeed;
    public boolean divided;
    public Entity parent;

    public CDivideBall()
    {
        movingDir = DIR_LEFT;
        divide=false;
        canMove=false;
        speed=0;
        attSpeed=0;
        parent=null;
        divided=false;
    }

    @Override
    public void reset()
    {
        movingDir=DIR_LEFT;
        divide=false;
        canMove=false;
        speed=0;
        attSpeed=0;
        parent=null;
        divided=false;
    }
}
