package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.components.CDivideBall;
import ns.spacepirate.game.components.CExplode;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CVelocity;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class DivideSystem extends IteratingSystem
{
    ComponentMapper<CDivideBall> divideBallMap;
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CVelocity> velMap;

    Engine engine;
    Brahma creator;

    public DivideSystem(Brahma creator)
    {
        super(Family.all(CDivideBall.class, CPosition.class, CVelocity.class).get());
        this.creator = creator;
        divideBallMap = ComponentMapper.getFor(CDivideBall.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);
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
        CDivideBall divideBall = divideBallMap.get(entity);
        CPosition entityPos = posMap.get(entity);

        if(divideBall.divide) {
            Entity ballLeft = creator.createBall(CDivideBall.DIR_LEFT, entityPos.x-30, entityPos.y, entity);
            Entity ballRight = creator.createBall(CDivideBall.DIR_RIGHT, entityPos.x+30, entityPos.y, entity);
            engine.addEntity(ballLeft);
            engine.addEntity(ballRight);

            divideBall.divide=false;
            divideBall.divided=true;
        }

        if(divideBall.canMove) {
            if(divideBall.parent!=null)
            {
                CPosition parPos = posMap.get(divideBall.parent);
                CDivideBall parent = divideBallMap.get(divideBall.parent);
                entityPos.y = parPos.y;

                divideBall.speed = parent.speed;
            }

            if(divideBall.movingDir==CDivideBall.DIR_RIGHT)
            {
                entityPos.x += divideBall.speed;
                entityPos.x -= divideBall.attSpeed;
            }else if(divideBall.movingDir==CDivideBall.DIR_LEFT)
            {
                entityPos.x -= divideBall.speed;
                entityPos.x += divideBall.attSpeed;
            }

            if(divideBall.attSpeed<5)
            {
                divideBall.attSpeed += 1.5f;
            }

        }

    }
}
