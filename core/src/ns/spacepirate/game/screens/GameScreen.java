package ns.spacepirate.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;

import ns.spacepirate.game.Assets;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.*;
import ns.spacepirate.game.systems.*;

public class GameScreen extends ScreenAdapter
{
    SpacePirate game;
    //SpriteBatch batch;
    PooledEngine engine;
    Brahma creator;

    FPSLogger fpsLogger = new FPSLogger();

    public GameScreen(SpacePirate game)
    {
        this.game = game;
        //this.batch= game.batcher;

        engine = new PooledEngine();
        creator = new Brahma(engine);

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    @Override
    public void show()
    {
//        batch = new SpriteBatch();
//        batch.setProjectionMatrix(game.cam.combined);

        Entity player = creator.createPlayer();
        engine.addEntity(player);

        CameraSystem cameraSystem = new CameraSystem(game.cam);
        cameraSystem.follow(player);

        engine.addSystem(cameraSystem);
        engine.addSystem(new SpawnSystem(player, creator));
        engine.addSystem(new PlayerInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new DivideSystem(creator));
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new TweenSystem());
        engine.addSystem(new DestroyAnimationSystem());
        engine.addSystem(new BackgroundSystem(player, creator));
        engine.addSystem(new BackgroundRenderSystem(cameraSystem));
        engine.addSystem(new ShapeRenderingSystem(cameraSystem));
        engine.addSystem(new RenderingSystem(cameraSystem));
//        engine.addSystem(new DebugRenderingSystem(cameraSystem));
        engine.addSystem(new ExpireSystem(cameraSystem));
    }

    private void update(float delta)
    {
        engine.update(delta);
    }

    @Override
    public void render(float delta)
    {
        game.cam.update();
        update(delta);
        fpsLogger.log();
    }

    @Override
    public void dispose()
    {
        super.dispose();

        engine.clearPools();
        Assets.inst.dispose();
        //batch.dispose();
    }
}
