package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.components.*;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class ExplodeSystem extends IteratingSystem
{
    ComponentMapper<CExplode> explodeMap;
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CVelocity> velMap;

    Engine engine;
    Brahma creator;

    public ExplodeSystem(Brahma creator)
    {
        super(Family.all(CExplode.class, CPosition.class).get());
        this.creator = creator;
        explodeMap = ComponentMapper.getFor(CExplode.class);
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
//        CPosition entityPos = posMap.get(entity);
//
//        // direction the fuel will move when exploded
//        //int randDir = GameAI.getDirectionIndex(dir.scl(-1));
//
//        // choose explode pattern randomly
//        int pattern = 0; //MathUtils.random(1);
//
//        for(int i=0; i<3; i++)
//        {
//            Entity spiro = creator.createSpiro();
//            CPosition position = posMap.get(spiro);
//            CVelocity velocity = velMap.get(spiro);
//
//            position.x = entityPos.x;
//            position.y = entityPos.y;
//
//            // set the appropriate velocity
//            // according to the randomly chose pattern
//            if(pattern==0)
//            {
//                velocity.vel.set(4.5f+(i*2),2.5f+(i*2));
//            }else if(pattern == 1) {
//                velocity.vel.set(4.5f+(i*2),2.5f-(i*2));
//            }
//
//            // always move the fuel to the player's current moving direction
//            velocity.vel.x*=-1;
//            velocity.vel.y*=-1;
//
//            engine.addEntity(spiro);
//        }
//
//        entity.remove(CExplode.class);
//        entity.add(new CDestroy());
    }
}
