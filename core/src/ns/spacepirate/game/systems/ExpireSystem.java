package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;

import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CBackground;
import ns.spacepirate.game.components.CDivideBall;
import ns.spacepirate.game.components.CPosition;

/**
 * Created by sukhmac on 2016-02-11.
 */
public class ExpireSystem extends IteratingSystem
{
    private Engine engine;
    private ComponentMapper<CPosition> posMap;
    private Camera camera;

    public ExpireSystem(Camera camera)
    {
        super(Family.all(CPosition.class).exclude(CBackground.class, CDivideBall.class).get());
        this.posMap = ComponentMapper.getFor(CPosition.class);
        this.camera = camera;
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
        if(posComponent.x>camera.position.x+SpacePirate.V_WIDTH   || posComponent.x<camera.position.x-SpacePirate.V_WIDTH ||
           posComponent.y>camera.position.y+SpacePirate.V_HEIGHT*3  || posComponent.y<camera.position.y-SpacePirate.V_HEIGHT )
        {
            engine.removeEntity(entity);
        }
    }
}
