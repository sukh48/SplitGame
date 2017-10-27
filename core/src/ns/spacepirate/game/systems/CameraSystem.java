package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CVelocity;

/**
 * Created by sukhmac on 2016-02-25.
 */
public class CameraSystem extends EntitySystem
{
    Camera camera;
    CVelocity camMovement;
    Entity entityToFollow;

    public CameraSystem(Camera camera)
    {
        this.camera = camera;
        this.camera.position.set(0,0,0);
        camMovement = new CVelocity();
    }

    public void follow(Entity entity)
    {
        this.entityToFollow = entity;
    }

    public Camera getCamera()
    {
        return camera;
    }

    @Override
    public void update(float deltaTime)
    {
        CPosition entityPos = entityToFollow.getComponent(CPosition.class);
        camera.position.set(SpacePirate.V_WIDTH/2, entityPos.y+200, 0);
    }
}
