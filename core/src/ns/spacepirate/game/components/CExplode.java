package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class CExplode extends Component implements Pool.Poolable
{
    public boolean explode;

    @Override
    public void reset()
    {
        explode=false;
    }
}
