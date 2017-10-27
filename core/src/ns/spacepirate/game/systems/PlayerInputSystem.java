package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ns.spacepirate.game.InputListener;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.*;

public class PlayerInputSystem extends IteratingSystem
{
    Vector2 offsetPos = new Vector2();

    ComponentMapper<CPlayerInput> inputMap;
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CVelocity> velMap;
    ComponentMapper<CShoot> shootMap;
    ComponentMapper<CTexture> texMap;

    public PlayerInputSystem()
    {
        super(Family.all(CPlayerInput.class, CPosition.class, CVelocity.class).get());
        inputMap = ComponentMapper.getFor(CPlayerInput.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);
        shootMap = ComponentMapper.getFor(CShoot.class);
        texMap = ComponentMapper.getFor(CTexture.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CPlayerInput playerComponent = inputMap.get(entity);
        CPosition posComponent = posMap.get(entity);
        CTexture texComponent = texMap.get(entity);
        CShoot shootComponent = shootMap.get(entity);
        CVelocity velComponent = velMap.get(entity);

        boolean isShooting=false;
        if(InputListener.leftPressed)
        {
            texComponent.sprite.rotate(3.5f);
            velComponent.vel.rotate(3.5f);
        }

        if(InputListener.shootPressed)
        {
            isShooting=true;
        }

        if(InputListener.rightPressed)
        {
            texComponent.sprite.rotate(-3.5f);
            velComponent.vel.rotate(-3.5f);
        }

        if(InputListener.leftPressed && InputListener.rightPressed) {
            isShooting=true;
        }

        /* apply shooting if attached to player */
        if (shootComponent != null)
        {
            shootComponent.isShooting = isShooting;
        }

    }
}
