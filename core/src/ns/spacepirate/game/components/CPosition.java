package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class CPosition extends Component implements Pool.Poolable
{
    public float x;
    public float y;

    @Override
    public void reset()
    {
        x = 0;
        y = 0;
    }
}
