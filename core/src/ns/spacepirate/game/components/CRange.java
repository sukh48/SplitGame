package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;

/**
 * Created by sukhmac on 2016-02-13.
 */
public class CRange extends Component implements Pool.Poolable
{

    public Circle rangeBounds;
    Entity entityInRange;

    public CRange()
    {
        rangeBounds = new Circle();
        rangeBounds.setRadius(0);
        entityInRange = null;
    }

    public void setRange(float radius)
    {
        this.rangeBounds.setRadius(radius);
    }

    public void addEntityInRange(Entity entity)
    {
        entityInRange = entity;
    }

    public Entity getEntityInRange()
    {
        return entityInRange;
    }

    public void clearEntitiesInRange()
    {
        entityInRange=null;
    }

    @Override
    public void reset()
    {
        rangeBounds.setRadius(0);
        clearEntitiesInRange();
    }
}
