package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CVelocity;

/**
 * Created by sukhmac on 2016-02-25.
 */
public class CameraSystem extends EntitySystem
{
    OrthographicCamera camera;
    CVelocity camMovement;
    Entity entityToFollow;

    public CameraSystem(Camera camera)
    {
        this.camera = (OrthographicCamera)camera;
        this.camera.position.set(0,0,0);
        //this.camera.zoom+=5.5f;
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
        if(entityToFollow!=null) {
            CPosition entityPos = entityToFollow.getComponent(CPosition.class);
            camera.position.set(SpacePirate.V_WIDTH / 2, entityPos.y + 200, 0);
        }
    }
}
