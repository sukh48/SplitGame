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
    ComponentMapper<CDivideBall> divideBallMap;
    ComponentMapper<CTexture> texMap;

    boolean initialTouch;
    boolean touching;
    float accel=1.5f;

    public PlayerInputSystem()
    {
        super(Family.all(CPlayerInput.class, CPosition.class, CVelocity.class, CDivideBall.class).get());
        inputMap = ComponentMapper.getFor(CPlayerInput.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);
        divideBallMap = ComponentMapper.getFor(CDivideBall.class);
        texMap = ComponentMapper.getFor(CTexture.class);

        initialTouch=false;
        touching=false;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CPlayerInput playerComponent = inputMap.get(entity);
        CPosition posComponent = posMap.get(entity);
        CTexture texComponent = texMap.get(entity);
        CDivideBall divideBallComponent = divideBallMap.get(entity);
        CVelocity velComponent = velMap.get(entity);

        boolean isShooting=false;
        if(InputListener.touch)
        {
            if(!initialTouch && !touching) {
                initialTouch=true;
            }
            touching=true;
        }else {
            if(touching) {
                initialTouch=false;
            }
            touching=false;
        }

        if(initialTouch && !divideBallComponent.divided) {
            divideBallComponent.divide=true;
            initialTouch=false;
            System.out.println("Touch");
        }

        if(touching) {
            if(divideBallComponent.speed<6);
                divideBallComponent.speed += accel;
        }else {
            divideBallComponent.speed = 0;
        }
    }
}
