package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by sukhmac on 17-10-28.
 */
public class CBackground extends Component implements Pool.Poolable
{

    public CBackground()
    {
        reset();
    }

    @Override
    public void reset() {

    }
}
