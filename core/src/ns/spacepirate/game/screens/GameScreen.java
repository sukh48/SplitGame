package ns.spacepirate.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ns.spacepirate.game.Assets;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.*;
import ns.spacepirate.game.systems.*;

public class GameScreen extends ScreenAdapter
{
    SpacePirate game;
    SpriteBatch batch;
    PooledEngine engine;
    Brahma creator;

    public GameScreen(SpacePirate game)
    {
        this.game = game;
        this.batch= game.batcher;

        engine = new PooledEngine();
        creator = new Brahma(engine);
    }

    @Override
    public void show()
    {
        batch = new SpriteBatch();
        batch.setProjectionMatrix(game.cam.combined);

        CameraSystem cameraSystem = new CameraSystem(game.cam);

        engine.addSystem(cameraSystem);
        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new TweenSystem());
        engine.addSystem(new DivideSystem(creator));
        engine.addSystem(new DestroyAnimationSystem());

        Entity player = creator.createPlayer();

        for(int i=0; i<25; i++)
        {
            Entity background = createBackground();
            CPosition position = background.getComponent(CPosition.class);
            position.x=0;
            position.y=i*SpacePirate.V_HEIGHT;

            engine.addEntity(background);
        }
        engine.addEntity(player);

        cameraSystem.follow(player);

        engine.addSystem(new RenderingSystem(cameraSystem, player.getComponent(CPosition.class)));

    }

    private Entity createBackground()
    {
        Texture backTexture = Assets.inst.getTexture(Assets.T_BACKGROUND);
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        graphicsComponent.sprite.setRegion(backTexture);
        graphicsComponent.sprite.setSize(SpacePirate.V_WIDTH, SpacePirate.V_HEIGHT);

        // set position to center
        CPosition positionComponent = new CPosition();
        positionComponent.x = 0;
        positionComponent.y = graphicsComponent.sprite.getHeight()/2;

        Entity background = engine.createEntity();
        background.add(positionComponent);
        background.add(graphicsComponent);

        return background;
    }

    private void update(float delta)
    {
        engine.update(delta);
    }

    @Override
    public void render(float delta)
    {
        update(delta);
    }

    @Override
    public void dispose()
    {
        super.dispose();

        engine.clearPools();
        Assets.inst.dispose();
        batch.dispose();
    }
}
