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

import ns.spacepirate.game.Assets;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CBackground;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CPosition;

public class RenderingSystem extends IteratingSystem
{
    private ComponentMapper<CPosition> posMap;
    private ComponentMapper<CTexture> graphicsMap;

    private SpriteBatch batch;
    private Camera camera;

    Camera hudCam;
    int count;
    float timeElap;

    public RenderingSystem(Camera camera)
    {
        super(Family.all(CPosition.class, CTexture.class).exclude(CBackground.class).get());

        this.batch = new SpriteBatch();
        this.camera = camera;

        this.hudCam = new OrthographicCamera(camera.viewportWidth, camera.viewportHeight);
        this.hudCam.position.set(SpacePirate.V_WIDTH/2f, SpacePirate.V_HEIGHT/2f, 0);
        hudCam.update();

        this.posMap = ComponentMapper.getFor(CPosition.class);
        this.graphicsMap = ComponentMapper.getFor(CTexture.class);
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

        batch.setProjectionMatrix(hudCam.combined);
        batch.begin();
        Assets.inst.getBigFont().draw(batch, ""+getEntities().size(), 50, 1230);
        batch.end();

        timeElap += deltaTime;
        if(timeElap > 1) {
            count++;
            timeElap=0;
        }
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
