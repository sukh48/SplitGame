package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class CHealth extends Component implements Pool.Poolable
{
    public int health=25;

    @Override
    public void reset()
    {
        health=25;
    }
}
