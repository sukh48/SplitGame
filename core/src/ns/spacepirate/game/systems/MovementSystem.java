package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CPlayerInput;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CVelocity;

/**
 * Created by sukhmac on 2016-02-04.
 */
public class MovementSystem extends IteratingSystem
{
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CVelocity> velMap;

    public MovementSystem()
    {
        super(Family.all(CPosition.class, CVelocity.class).get());

        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CPosition positionComponent = posMap.get(entity);
        CVelocity velocityComponent = velMap.get(entity);


        CPlayerInput player = entity.getComponent(CPlayerInput.class);
        if(player!=null)
        {
            if(positionComponent.x> SpacePirate.V_WIDTH+50) {
                positionComponent.x=0;
            }else if(positionComponent.x<=-50) {
                positionComponent.x=SpacePirate.V_WIDTH;
            }
        }

        positionComponent.x+=velocityComponent.vel.x;
        positionComponent.y+=velocityComponent.vel.y;
    }
}
