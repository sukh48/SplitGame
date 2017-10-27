package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class CVelocity extends Component implements Pool.Poolable
{
    public Vector2 vel;

    public CVelocity()
    {
        vel = new Vector2();
    }

    @Override
    public void reset()
    {
        vel.setZero();
    }
}
