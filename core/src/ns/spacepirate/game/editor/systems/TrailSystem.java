package ns.spacepirate.game.editor.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IntervalIteratingSystem;

import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.components.CDivideBall;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.editor.components.CTrail;

/**
 * Created by sukhmac on 17-11-12.
 */
public class TrailSystem extends IntervalIteratingSystem
{

    ComponentMapper<CPosition> posMap;
    Brahma creator;
    PooledEngine engine;

    public TrailSystem(float interval, Brahma creator)
    {
        super(Family.all(CPosition.class, CVelocity.class, CTrail.class).get(), interval);

        posMap = ComponentMapper.getFor(CPosition.class);

        this.creator = creator;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = (PooledEngine)engine;
    }

    @Override
    protected void processEntity(Entity entity)
    {
        CPosition pos = posMap.get(entity);
        Entity trail = creator.createTrail(pos.x, pos.y);
        engine.addEntity(trail);
    }
}
