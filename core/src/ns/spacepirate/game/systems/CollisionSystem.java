package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Shape2D;

import ns.spacepirate.game.GameWorld;
import ns.spacepirate.game.components.*;
import ns.spacepirate.game.components.Collision.BoxCollider;
import ns.spacepirate.game.components.Collision.CCollider;
import ns.spacepirate.game.components.Collision.Collider;

/**
 * Created by sukhmac on 2016-02-06.
 */
public class CollisionSystem extends IteratingSystem
{
    private Engine engine;
    private GameWorld world;

    private ComponentMapper<CPosition> posMap;
    private ComponentMapper<CCollider> boundsMap;
    private ComponentMapper<CRange> rangeMap;

    public CollisionSystem(GameWorld world)
    {
        super(Family.all(CPosition.class, CCollider.class).get());
        boundsMap = ComponentMapper.getFor(CCollider.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        rangeMap = ComponentMapper.getFor(CRange.class);

        this.world = world;
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
        Collider collider = entityCollider.getCollider();
        CPosition pos1 = posMap.get(entity);

        if(collider instanceof BoxCollider) {
            BoxCollider boxCollider = (BoxCollider) collider;
            entityCollider.setPosition(pos1.x-boxCollider.getBounds().width/2, pos1.y-boxCollider.getBounds().height/2);
            //entityCollider.setPosition(pos1.x, pos1.y);
        }else {
            entityCollider.setPosition(pos1.x, pos1.y);
        }

        for (Entity otherEntity : getEntities())
        {
            if(entity!=otherEntity)
            {
                CCollider otherCollider = boundsMap.get(otherEntity);
                Collider collider2 = otherCollider.getCollider();

                CPosition pos2 = posMap.get(otherEntity);
                if(collider2 instanceof BoxCollider) {
                    BoxCollider boxCollider = (BoxCollider) collider2;
                    otherCollider.setPosition(pos2.x-boxCollider.getBounds().width/2, pos2.y-boxCollider.getBounds().height/2);
                    //otherCollider.setPosition(pos2.x, pos2.y);
                }else {
                    otherCollider.setPosition(pos2.x, pos2.y);
                }

                if(!entityCollider.isAlreadyColliding(otherEntity))
                {
                    if(entityCollider.collides(otherCollider)) {
                        //System.out.println("COLLISION");
                        entityCollider.collision = true;
                        entityCollider.addColliding(otherEntity);
                        world.notifyCollision(entity, otherEntity);
                    }
                }else if(!entityCollider.collides(otherCollider)) {
                    entityCollider.removeColliding(otherEntity);
                }
            }
        }

        /* notify collision end */
        //if(entityCollider.hasCollisionHandler())
        //{
//            if(entityCollider.collision && !collided)
//            {
//                //entityCollider.getHandler().notifyCollisionEnd(entity);
//                entityCollider.collision=false;
//                world.notifyCollisionEnd(entity);
//            }
        //}
    }
}
