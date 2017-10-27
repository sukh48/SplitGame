package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CPosition;

/**
 * Created by sukhmac on 2016-02-11.
 */
public class ExpireSystem extends IteratingSystem
{
    Engine engine;
    ComponentMapper<CPosition> posMap;

    public ExpireSystem()
    {
        super(Family.all(CPosition.class).get());
        posMap = ComponentMapper.getFor(CPosition.class);
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
        CPosition posComponent = posMap.get(entity);
        if(posComponent.x>SpacePirate.V_WIDTH   || posComponent.x<0 ||
           posComponent.y>SpacePirate.V_HEIGHT  || posComponent.y<0 )
        {
            engine.removeEntity(entity);
        }
    }
}
