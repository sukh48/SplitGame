package ns.spacepirate.game.controllers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import ns.spacepirate.game.components.*;
import ns.spacepirate.game.interfaces.CollisionHandler;
import ns.spacepirate.game.tween.TweenEffectAccessor;

public class AsteroidCollisionHandler implements CollisionHandler
{
    private final static AsteroidCollisionHandler inst = new AsteroidCollisionHandler();

    private AsteroidCollisionHandler()
    {
        // singleton
    }

    public static AsteroidCollisionHandler getInstance()
    {
        return  inst;
    }

    @Override
    public void notifyCollision(Entity entity, Entity collidedWith)
    {
        CHealth healthComponent = entity.getComponent(CHealth.class);
        CTexture textureComponent = entity.getComponent(CTexture.class);

        CTag collidedTag = collidedWith.getComponent(CTag.class);
        if(collidedTag!=null && collidedTag.tag.equalsIgnoreCase("Bubble"))
        {
            textureComponent.sprite.setColor(Color.RED);
            healthComponent.health -= 3;
            if(healthComponent.health < 0)
            {
                if (MathUtils.random(10) >= 5)
                {
                    CDestroy destroyComponent = new CDestroy();
                    destroyComponent.addTweenEffect(TweenEffectAccessor.EFFECT_BOUNCE);
                    entity.remove(CCollider.class);
                    entity.add(destroyComponent);
                } else
                {
                    entity.remove(CCollider.class);
                    entity.add(new CExplode());
                }
            }
        }

    }

    @Override
    public void notifyCollisionEnd(Entity entity)
    {
        CTexture textureComponent = entity.getComponent(CTexture.class);
        textureComponent.sprite.setColor(Color.WHITE);
    }
}
