package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CBackground;
import ns.spacepirate.game.components.CDivideBall;
import ns.spacepirate.game.components.CPosition;

/**
 * Created by sukhmac on 2016-02-11.
 */
public class ExpireSystem extends IteratingSystem
{
    Engine engine;
    ComponentMapper<CPosition> posMap;
    CameraSystem cameraSystem;

    public ExpireSystem(CameraSystem cameraSystem)
    {
        super(Family.all(CPosition.class).exclude(CBackground.class, CDivideBall.class).get());
        posMap = ComponentMapper.getFor(CPosition.class);
        this.cameraSystem = cameraSystem;
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
        if(posComponent.x>cameraSystem.camera.position.x+SpacePirate.V_WIDTH   || posComponent.x<cameraSystem.camera.position.x-SpacePirate.V_WIDTH ||
           posComponent.y>cameraSystem.camera.position.y+SpacePirate.V_HEIGHT  || posComponent.y<cameraSystem.camera.position.y-SpacePirate.V_HEIGHT )
        {
            engine.removeEntity(entity);
        }
    }
}
