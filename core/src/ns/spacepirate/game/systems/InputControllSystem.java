package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import ns.spacepirate.game.components.CPosition;

/**
 * Created by sukhmac on 2018-02-04.
 */

public class InputControllSystem extends EntitySystem
{
    private ComponentMapper<CPosition> posMap;

    Entity player;

    public InputControllSystem(Entity player)
    {
        this.player = player;
        this.posMap = ComponentMapper.getFor(CPosition.class);
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        CPosition pos = posMap.get(player);
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            pos.y+=4;
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            pos.y-=4;
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pos.x-=4;
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pos.x+=4;
        }

    }
}
