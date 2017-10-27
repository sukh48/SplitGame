package ns.spacepirate.game.controllers;

import com.badlogic.ashley.core.Entity;
import ns.spacepirate.game.components.CCollider;
import ns.spacepirate.game.components.CDestroy;
import ns.spacepirate.game.components.CTag;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.interfaces.CollisionHandler;

public class BubbleCollisionHandler implements CollisionHandler
{
    private final static BubbleCollisionHandler inst = new BubbleCollisionHandler();

    private BubbleCollisionHandler()
    {
        // singleton
    }

    public static BubbleCollisionHandler getInstance()
    {
        return  inst;
    }

    @Override
    public void notifyCollision(Entity entity, Entity collidedWith)
    {
        CVelocity velComponent = entity.getComponent(CVelocity.class);
        CTag tagComponent = collidedWith.getComponent(CTag.class);

        if(tagComponent!=null && tagComponent.tag.equalsIgnoreCase("Asteroid"))
        {
            velComponent.vel.setZero();
            entity.remove(CCollider.class);
            entity.add(new CDestroy());
        }
    }

    @Override
    public void notifyCollisionEnd(Entity entity)
    {

    }
}
