package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.*;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class AsteroidSystem extends EntitySystem
{
    CameraSystem cameraSystem;
    PooledEngine engine;
    Brahma creator;

    float timeSinceSpawn;

    public AsteroidSystem(CameraSystem cameraSystem, Brahma creator)
    {
        this.cameraSystem = cameraSystem;
        this.creator = creator;
        this.timeSinceSpawn=0;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        super.addedToEngine(engine);
        this.engine = (PooledEngine)engine;
    }

    private void spawnAsteroid(CPosition position)
    {
        int xPos = (int)(Math.random()*SpacePirate.V_WIDTH);
        int yPos = (SpacePirate.V_HEIGHT/2)+(int)(cameraSystem.camera.position.y+100);

        position.x = xPos;
        position.y = yPos;
    }

    @Override
    public void update(float deltaTime)
    {
        timeSinceSpawn+=deltaTime;
        if(timeSinceSpawn>0.5f)
        {
            for (int i=0;i<1;i++)
            {
                Entity asteroid = creator.createAsteroid();
                CPosition asteroidPos = asteroid.getComponent(CPosition.class);
                CVelocity asteroidVel = asteroid.getComponent(CVelocity.class);

                spawnAsteroid(asteroidPos);

                engine.addEntity(asteroid);
            }

            timeSinceSpawn=0;
        }
    }

}
