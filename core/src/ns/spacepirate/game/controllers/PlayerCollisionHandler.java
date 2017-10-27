package ns.spacepirate.game.controllers;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.ashley.core.Entity;
import ns.spacepirate.game.components.CDestroy;
import ns.spacepirate.game.components.CTag;
import ns.spacepirate.game.components.CTweenEffect;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.interfaces.CollisionHandler;
import ns.spacepirate.game.tween.TweenEffectAccessor;

/**
 * Created by sukhmac on 2016-02-12.
 */
public class PlayerCollisionHandler implements CollisionHandler
{
    private final static PlayerCollisionHandler inst = new PlayerCollisionHandler();

    private PlayerCollisionHandler()
    {
        // singleton
    }

    public static PlayerCollisionHandler getInstance()
    {
        return  inst;
    }

    @Override
    public void notifyCollision(Entity entity, Entity collidedWith)
    {
        CTag tagComponent = collidedWith.getComponent(CTag.class);
        if(tagComponent!=null && tagComponent.tag.equalsIgnoreCase("Ball")) {
            if(collidedWith.getComponent(CDestroy.class)==null)
            {
                CVelocity velComponent = collidedWith.getComponent(CVelocity.class);
                if(velComponent!=null) {
                    velComponent.vel.setZero();
                }

                CDestroy destroyComponent = new CDestroy();
                destroyComponent.addTweenEffect(TweenEffectAccessor.EFFECT_BOUNCE);
                collidedWith.add(destroyComponent);
            }
        }
    }

    @Override
    public void notifyCollisionEnd(Entity entity)
    {

    }
}
