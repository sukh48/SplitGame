package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class CSpiro extends Component implements Pool.Poolable
{
    public static final int TYPE_LEMON=0;
    public static final int TYPE_WHITE=1;
    public static final int TYPE_RED = 2;

    int amount;

    @Override
    public void reset()
    {
        amount=0;
    }
}
