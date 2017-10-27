package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class CInflictDamage extends Component implements Pool.Poolable
{
    public int damage;

    @Override
    public void reset()
    {
        damage=0;
    }
}
