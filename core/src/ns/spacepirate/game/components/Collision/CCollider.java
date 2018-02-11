package ns.spacepirate.game.components.Collision;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool;

import java.util.HashMap;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class CCollider extends Component implements Pool.Poolable
{
    public boolean collision;
    private HashMap<Long, Entity> collidingEntities;
    private Collider collider;

    public CCollider()
    {
        collidingEntities = new HashMap<Long, Entity>();
    }

    public void setCollider(Collider collider)
    {
        this.collider = collider;
    }

    public Collider getCollider()
    {
        return collider;
    }

    public void setPosition(float x, float y)
    {
        this.collider.setPosition(x, y);
    }

    @Override
    public void reset()
    {
        collision=false;
        collidingEntities.clear();
    }

    public boolean isAlreadyColliding(Entity entity)
    {
        boolean colliding = false;
        if(collidingEntities.containsKey(entity.getId())) {
            colliding=true;
        }

        return colliding;
    }

    public void addColliding(Entity entity)
    {
        collidingEntities.put(entity.getId(), entity);
    }

    public void removeColliding(Entity entity)
    {
        collidingEntities.remove(entity);
    }

    public boolean collides(CCollider otherCollider)
    {
        return collider.collides(otherCollider.collider);
    }

}
