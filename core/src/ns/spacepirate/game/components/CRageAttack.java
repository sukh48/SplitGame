package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by sukhmac on 2016-02-14.
 */
public class CRageAttack extends Component implements Pool.Poolable
{

    public float timeSinceAttack;
    public Vector2 originalVel;

    public CRageAttack()
    {
        originalVel = new Vector2();
        reset();
    }

    @Override
    public void reset()
    {
        timeSinceAttack=0;
        originalVel.setZero();
    }
}
