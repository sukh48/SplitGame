package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CPosition;

public class RenderingSystem extends IteratingSystem
{
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CTexture> graphicsMap;

    private SpriteBatch batch;
    private Camera camera;

    private ParticleEffect pe;
    private ParticleEffect splashEffect;
    private ParticleEffect splashGreen;
    private CPosition playerPos;

    public RenderingSystem(CameraSystem cameraSystem, CPosition playerPos)
    {
        super(Family.all(CPosition.class, CTexture.class).get());
        this.batch = new SpriteBatch();
        this.camera = cameraSystem.camera;
        this.playerPos = playerPos;

        posMap = ComponentMapper.getFor(CPosition.class);
        graphicsMap = ComponentMapper.getFor(CTexture.class);

        pe = new ParticleEffect();
        pe.load(Gdx.files.internal("particle/SplashGreen"),Gdx.files.internal("particle"));
        pe.getEmitters().get(0).setContinuous(true);

        splashEffect = new ParticleEffect();
        splashEffect.load(Gdx.files.internal("particle/SplashGreen"),Gdx.files.internal("particle"));
        splashEffect.setPosition(SpacePirate.V_WIDTH, SpacePirate.V_HEIGHT);
        splashEffect.getEmitters().get(0).setContinuous(true);

        splashGreen = new ParticleEffect();
        splashGreen.load(Gdx.files.internal("particle/SplashGreen"),Gdx.files.internal("particle"));
        splashGreen.setPosition(5, SpacePirate.V_HEIGHT);
        splashGreen.getEmitters().get(0).setContinuous(true);
//        pe.getEmitters().get(1).setContinuous(true);
//        pe.getEmitters().get(2).setContinuous(true);
        pe.start();
        splashEffect.start();
        splashGreen.start();

    }

    @Override
    public void update(float deltaTime)
    {
        camera.update();
        pe.update(deltaTime);
        splashEffect.update(deltaTime);
        splashGreen.update(deltaTime);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(Entity entity : getEntities())
        {
            CTexture graphicComponent = graphicsMap.get(entity);
            CPosition positionComponent = posMap.get(entity);

            graphicComponent.sprite.setPosition(positionComponent.x,positionComponent.y);
            graphicComponent.sprite.draw(batch);
        }

        pe.setPosition(playerPos.x+25, playerPos.y);
        pe.draw(batch);
        splashEffect.draw(batch);
        splashGreen.draw(batch);
//        if (pe.isComplete())
//            pe.reset();

        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {

    }
}
