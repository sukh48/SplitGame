package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CBackground;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CPosition;

public class RenderingSystem extends IteratingSystem
{
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CTexture> graphicsMap;

    private SpriteBatch batch;
    private Camera camera;

    public RenderingSystem(CameraSystem cameraSystem)
    {
        super(Family.all(CPosition.class, CTexture.class).exclude(CBackground.class).get());
        this.batch = new SpriteBatch();
        this.camera = cameraSystem.camera;

        posMap = ComponentMapper.getFor(CPosition.class);
        graphicsMap = ComponentMapper.getFor(CTexture.class);
    }

    @Override
    public void update(float deltaTime)
    {
        //camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();
        batch.begin();

        for(Entity entity : getEntities())
        {
            CTexture graphicComponent = graphicsMap.get(entity);
            float width = graphicComponent.sprite.getWidth();
            float height = graphicComponent.sprite.getHeight();
            CPosition positionComponent = posMap.get(entity);

            graphicComponent.sprite.setPosition(positionComponent.x-width/2f, positionComponent.y-height/2f);
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
