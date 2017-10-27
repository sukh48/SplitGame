package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class CPlayerInput extends Component implements Pool.Poolable
{
    public Vector2 startPoint;
    public Vector2 targetPoint;
    public boolean moving;

    public boolean initialTouch;

    public  CPlayerInput()
    {
        startPoint = new Vector2();
        targetPoint = new Vector2();
        moving=false;
        initialTouch=false;
    }

    @Override
    public void reset()
    {
        startPoint.setZero();
        targetPoint.setZero();
        moving=false;
        initialTouch=false;
    }
}
