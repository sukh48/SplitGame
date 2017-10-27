package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class CPerks extends Component implements Pool.Poolable
{
    public int perkType;

    @Override
    public void reset()
    {
        perkType=0;
    }
}
