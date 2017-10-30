package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ns.spacepirate.game.components.CBackground;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CTexture;

/**
 * Created by sukhmac on 17-10-28.
 */
public class BackgroundRenderSystem extends IteratingSystem
{
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CTexture> graphicsMap;

    private SpriteBatch batch;
    private Camera camera;


    public BackgroundRenderSystem(CameraSystem cameraSystem)
    {
        super(Family.all(CPosition.class, CTexture.class, CBackground.class).get());
        this.batch = new SpriteBatch();
        this.camera = cameraSystem.camera;

        posMap = ComponentMapper.getFor(CPosition.class);
        graphicsMap = ComponentMapper.getFor(CTexture.class);
    }

    @Override
    public void update(float deltaTime)
    {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(Entity entity : getEntities())
        {
            CTexture graphicComponent = graphicsMap.get(entity);
            CPosition positionComponent = posMap.get(entity);

            graphicComponent.sprite.setPosition(positionComponent.x,positionComponent.y);
            graphicComponent.sprite.draw(batch);
        }

        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {

    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        batch.dispose();
    }
}
