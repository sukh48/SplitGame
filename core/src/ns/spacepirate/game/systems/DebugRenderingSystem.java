package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.Collision.BoxCollider;
import ns.spacepirate.game.components.Collision.CCollider;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.Collision.CircleCollider;
import ns.spacepirate.game.components.Collision.Collider;

/**
 * Created by sukhmac on 2016-02-08.
 */
public class DebugRenderingSystem extends IteratingSystem
{
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CCollider> boundsMap;

    ShapeRenderer shapeRenderer;
    Camera camera;

    public DebugRenderingSystem(Camera camera)
    {
        super(Family.all(CPosition.class, CCollider.class).get());
        posMap = ComponentMapper.getFor(CPosition.class);
        boundsMap = ComponentMapper.getFor(CCollider.class);

        this.shapeRenderer = new ShapeRenderer();
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime)
    {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Entity entity : getEntities())
        {
            CPosition posComp = posMap.get(entity);
            CCollider boundsComponent = boundsMap.get(entity);
//            boundsComponent.setPosition(posComp.x, posComp.y);

            Collider collider = boundsComponent.getCollider();

            if(boundsComponent.collision) {
                shapeRenderer.setColor(Color.RED);
            }else {
                shapeRenderer.setColor(Color.BLUE);
            }

            if(collider instanceof BoxCollider) {
                BoxCollider rectBoundComp = (BoxCollider) collider;
                shapeRenderer.rect(posComp.x-rectBoundComp.getBounds().width/2, posComp.y-rectBoundComp.getBounds().height/2,
                        rectBoundComp.getBounds().width, rectBoundComp.getBounds().height);
//                shapeRenderer.rect(posComp.x, posComp.y,
//                        rectBoundComp.getBounds().width, rectBoundComp.getBounds().height);

            }else if(collider instanceof CircleCollider) {
                CircleCollider circleBoundComp = (CircleCollider) collider;
//                shapeRenderer.circle(posComp.x-circleBoundComp.getBounds().radius/2, posComp.y-circleBoundComp.getBounds().radius/2, circleBoundComp.getBounds().radius);

                shapeRenderer.circle(posComp.x, posComp.y, circleBoundComp.getBounds().radius);
            }


//            CRange rangeComponent = entity.getComponent(CRange.class);
//            if(rangeComponent!=null)
//            {
//                shapeRenderer.circle(rangeComponent.rangeBounds.x, rangeComponent.rangeBounds.y, rangeComponent.rangeBounds.radius);
//            }
        }

//        shapeRenderer.setColor(Color.GRAY);
//        float offsetX=10;
//        for(int r=0; r < SpacePirate.V_WIDTH/50; r++)
//        {
//            shapeRenderer.line(r*50 + offsetX,0, r*50 + offsetX, SpacePirate.V_HEIGHT);
//        }
//
//        for (int c=0; c < SpacePirate.V_HEIGHT/50; c++) {
//            shapeRenderer.line(0, c*50, SpacePirate.V_WIDTH, c*50);
//        }

        shapeRenderer.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {

    }
}
