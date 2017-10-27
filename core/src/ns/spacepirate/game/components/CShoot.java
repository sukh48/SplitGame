package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class CShoot extends Component implements Pool.Poolable
{
    public boolean isShooting;
    public int speed;

    @Override
    public void reset()
    {
        isShooting=false;
        speed=0;
    }
}
