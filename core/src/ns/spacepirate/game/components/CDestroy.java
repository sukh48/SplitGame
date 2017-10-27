package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by sukhmac on 2016-02-11.
 */
public class CDestroy extends Component implements Pool.Poolable
{
    private int tween=-1;

    @Override
    public void reset()
    {
        tween = -1;
    }

    public void addTweenEffect(int tween)
    {
        this.tween = tween;
    }

    public boolean hasTween()
    {
        return tween!=-1;
    }
}
