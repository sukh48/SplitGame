package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CRageAttack;
import ns.spacepirate.game.components.CRange;
import ns.spacepirate.game.components.CVelocity;

/**
 * Created by sukhmac on 2016-02-14.
 */
public class RageAttackSystem extends IteratingSystem
{
    ComponentMapper<CRange> rangeMap;
    ComponentMapper<CRageAttack> attackMap;
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CVelocity> velMap;

    public RageAttackSystem()
    {
        super(Family.all(CRageAttack.class, CRange.class, CPosition.class, CVelocity.class).get());
        rangeMap = ComponentMapper.getFor(CRange.class);
        attackMap = ComponentMapper.getFor(CRageAttack.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CRange rangeComponent = rangeMap.get(entity);
        CRageAttack attackComponent = attackMap.get(entity);
        CPosition posComponent = posMap.get(entity);
        CVelocity velComponent = velMap.get(entity);

        Entity entityInRange = rangeComponent.getEntityInRange();

        if(entityInRange!=null)
        {
            if(attackComponent.timeSinceAttack==0)
            {
                attackComponent.originalVel.set(velComponent.vel);
            }

            CPosition entityInRangePos = posMap.get(entityInRange);
            velComponent.vel.set(entityInRangePos.x, entityInRangePos.y);
            velComponent.vel.sub(posComponent.x, posComponent.y);
            velComponent.vel.nor();
            velComponent.vel.scl(3.5f);

            attackComponent.timeSinceAttack+=deltaTime;
            if(attackComponent.timeSinceAttack>3)
            {
                velComponent.vel.set(attackComponent.originalVel);
                rangeComponent.clearEntitiesInRange();
                attackComponent.reset();
            }
        }

    }
}
