package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ns.spacepirate.game.components.*;

/**
 * Created by sukhmac on 2016-02-06.
 */
public class CollisionSystem extends IteratingSystem
{
    Engine engine;

    ComponentMapper<CPosition> posMap;
    ComponentMapper<CCollider> boundsMap;
    ComponentMapper<CRange> rangeMap;

    public CollisionSystem()
    {
        super(Family.all(CPosition.class, CCollider.class).get());
        boundsMap = ComponentMapper.getFor(CCollider.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        rangeMap = ComponentMapper.getFor(CRange.class);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CCollider entityCollider = boundsMap.get(entity);
        CPosition entityPos  = posMap.get(entity);
        CRange rangeComponent = rangeMap.get(entity);

        boolean collided=false;

        entityCollider.rect.setPosition(entityPos.x, entityPos.y);
        entityCollider.rect.setSize(50,50);
        for (Entity otherEntity : getEntities())
        {
            if(entity!=otherEntity)
            {
                CPosition otherEntityPos = posMap.get(otherEntity);
                CCollider otherCollider = boundsMap.get(otherEntity);

                otherCollider.rect.setPosition(otherEntityPos.x,
                                               otherEntityPos.y);

                if(entityCollider.rect.overlaps(otherCollider.rect))
                {
                    if(entityCollider.hasCollisionHandler())
                    {
                        /* notify collision */
                        entityCollider.getHandler().notifyCollision(entity, otherEntity);
                        entityCollider.collision=true;
                        collided = true;
                    }
                }

                if(rangeComponent!=null)
                {
                    rangeComponent.rangeBounds.setPosition(entityPos.x, entityPos.y);

                    if(rangeComponent.rangeBounds.contains(otherCollider.rect.getX(), otherCollider.rect.getY()))
                    {
                        if(otherEntity.getComponent(CPlayerInput.class)!=null)
                        {
                            rangeComponent.addEntityInRange(otherEntity);
                        }
                    }
                }
            }
        }

        /* notify collision end */
        if(entityCollider.hasCollisionHandler())
        {
            if(entityCollider.collision && !collided) {
                entityCollider.getHandler().notifyCollisionEnd(entity);
            }
        }
    }
}
