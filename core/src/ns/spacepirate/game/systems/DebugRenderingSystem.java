package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import ns.spacepirate.game.components.CCollider;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CRange;

/**
 * Created by sukhmac on 2016-02-08.
 */
public class DebugRenderingSystem extends IteratingSystem
{
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CCollider> boundsMap;

    ShapeRenderer shapeRenderer;
    CameraSystem cameraSystem;

    public DebugRenderingSystem(CameraSystem cameraSystem)
    {
        super(Family.all(CPosition.class, CCollider.class).get());
        posMap = ComponentMapper.getFor(CPosition.class);
        boundsMap = ComponentMapper.getFor(CCollider.class);

        this.shapeRenderer = new ShapeRenderer();
        this.cameraSystem = cameraSystem;
    }

    @Override
    public void update(float deltaTime)
    {
        shapeRenderer.setProjectionMatrix(cameraSystem.camera.combined);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Entity entity : getEntities())
        {
            CCollider boundsComponent = boundsMap.get(entity);

            shapeRenderer.rect(boundsComponent.rect.x-boundsComponent.rect.width/2, boundsComponent.rect.y-boundsComponent.rect.height/2,
                               boundsComponent.rect.width, boundsComponent.rect.height);

            CRange rangeComponent = entity.getComponent(CRange.class);
            if(rangeComponent!=null)
            {
                shapeRenderer.circle(rangeComponent.rangeBounds.x, rangeComponent.rangeBounds.y, rangeComponent.rangeBounds.radius);
            }
        }
        shapeRenderer.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {

    }
}
