package ns.spacepirate.game.interfaces;

import com.badlogic.ashley.core.Entity;

/**
 * Created by sukhmac on 2016-02-10.
 */
public interface CollisionHandler
{
    void notifyCollision(Entity entity, Entity collidedWith);

    void notifyCollisionEnd(Entity entity);
}
