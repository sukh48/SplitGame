package ns.spacepirate.game.screens;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Sine;
import ns.spacepirate.game.Assets;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.GameWorld;
import ns.spacepirate.game.InputListener;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.interfaces.WorldEvent;
import ns.spacepirate.game.interfaces.WorldEventListener;
import ns.spacepirate.game.systems.*;
import ns.spacepirate.game.tween.TweenActorAccessor;
import ns.spacepirate.game.tween.TweenTextureAccessor;

public class GameScreen extends ScreenAdapter implements WorldEventListener
{
    private static final int STATE_RUNNING = 0;
    private static final int STATE_PAUSE_MENU_IN = 1;
    private static final int STATE_PAUSE_MENU_OUT = 2;
    private static final int STATE_PAUSED = 3;
    private static final int STATE_GAMEOVER = 4;

    private SpacePirate game;
    private PooledEngine engine;
    private Brahma creator;
    private GameWorld world;

    private Stage stage;
    private Skin skin;
    private TextButton button;
    private TextButton button2;
    private TextButton button3;

    private VerticalGroup vg;
    private TweenManager tweenManager;

    private int state;

    private FPSLogger fpsLogger = new FPSLogger();

    public GameScreen(SpacePirate game)
    {
        this.game = game;
        this.engine = new PooledEngine();
        this.creator = new Brahma(engine);
        this.world = creator.createWorld();
        this.stage = new Stage(game.viewport);

        this.world.addListener(this);
        this.tweenManager = new TweenManager();

        initStage();

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    private void initStage()
    {
        Tween.registerAccessor(Actor.class, new TweenActorAccessor());

        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        // Create a table that fills the screen. Everything else will go inside this table.
//        Table table = new Table();
//        table.setFillParent(true);
//        stage.addActor(table);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        button = new TextButton("Resume", skin);
        button.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                System.out.println("Clicked Resume");
                setState(STATE_PAUSE_MENU_OUT);
            }
        });

        button2 = new TextButton("Scores", skin);
        button3 = new TextButton("Quit", skin);

//        table.add(button);
////        table.bottom();
//        table.row();
//        table.add(button2);
//        table.row();
//        table.add(button3);

        //vg = new VerticalGroup();
        //vg.setFillParent(true);
        stage.addActor(button);
        stage.addActor(button2);
        stage.addActor(button3);

        button.setX(SpacePirate.V_WIDTH/2f - button.getWidth()/2f);
        button2.setX(SpacePirate.V_WIDTH/2f - button2.getWidth()/2f);
        button3.setX(SpacePirate.V_WIDTH/2f - button3.getWidth()/2f);

        //stage.addActor(vg);
        //table.pack();
    }

    @Override
    public void show()
    {
        CameraSystem cameraSystem = new CameraSystem(game.cam);
        cameraSystem.follow(world.getPlayer());

        engine.addSystem(cameraSystem);
        engine.addSystem(new SpawnSystem(world));
        //engine.addSystem(new InputControllSystem(world.getPlayer()));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new DivideSystem(creator));
        engine.addSystem(new CollisionSystem(world));
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new TweenSystem(tweenManager));
        engine.addSystem(new DestroyAnimationSystem());
        engine.addSystem(new BackgroundSystem(world));
        engine.addSystem(new BackgroundRenderSystem(game.cam));
        engine.addSystem(new ShapeRenderingSystem(game.cam));
        engine.addSystem(new RenderingSystem(game.cam));
        //engine.addSystem(new DebugRenderingSystem(game.cam));
        engine.addSystem(new ExpireSystem(game.cam));

        setState(STATE_RUNNING);
    }

    @Override
    public void render(float delta)
    {
        tweenManager.update(delta);
        game.cam.update();
        engine.update(delta);

        switch (state) {
            case STATE_RUNNING :
                updateRunning(delta);
                break;
            case STATE_PAUSED :
                updatePaused(delta);
                break;
            case STATE_PAUSE_MENU_IN :
                updatePaused(delta);
                break;
            case STATE_PAUSE_MENU_OUT :
                updatePaused(delta);
                break;
            case STATE_GAMEOVER :
                updateGameOver(delta);
                break;
        }

        fpsLogger.log();
    }

    private void updateRunning(float delta)
    {

    }

    private void updatePaused(float delta)
    {
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    private void updateGameOver(float delta)
    {

    }

    private void setState(int state)
    {
        this.state = state;

        if(state == STATE_PAUSE_MENU_IN)
        {
            System.out.println("STATE SET TO MENU_IN");
            //CameraSystem cameraSystem = engine.getSystem(CameraSystem.class);
            //cameraSystem.setProcessing(false);

            world.pause();

            //game.cam.position.set(SpacePirate.V_WIDTH/2,SpacePirate.V_HEIGHT/2,0);
            Gdx.input.setInputProcessor(stage);

            button.setPosition(button.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f-100);
            button2.setPosition(button2.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f-100);
            button3.setPosition(button3.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f-100);

            SequenceAction sequence = new SequenceAction();
            ParallelAction parallel = new ParallelAction();

            MoveToAction moveToAction1 = new MoveToAction();
            moveToAction1.setPosition(button.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f+1280-200);
            moveToAction1.setDuration(1.5f+1);
            moveToAction1.setTarget(button);
            moveToAction1.setInterpolation(Interpolation.elasticOut);

            MoveToAction moveToAction2 = new MoveToAction();
            moveToAction2.setPosition(button.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f+1280-400);
            moveToAction2.setDuration(1.7f+1);
            moveToAction2.setTarget(button2);
            moveToAction2.setInterpolation(Interpolation.elasticOut);

            MoveToAction moveToAction3 = new MoveToAction();
            moveToAction3.setPosition(button.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f+1280-600);
            moveToAction3.setTarget(button3);
            moveToAction3.setDuration(2f+1);
            moveToAction3.setInterpolation(Interpolation.elasticOut);

            parallel.addAction(moveToAction1);
            parallel.addAction(moveToAction2);
            parallel.addAction(moveToAction3);

            sequence.addAction(parallel);
            sequence.addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    setState(STATE_PAUSED);
                    return true;
                }
            });

            button.setTransform(true);
            button.setOrigin(button.getX()+button.getWidth()/2f, button.getY()+button.getHeight()/2f);
            button.addAction(sequence);

        }else if(state == STATE_PAUSE_MENU_OUT)
        {
            System.out.println("STATE SET TO MENU OUT");
            SequenceAction sequence = new SequenceAction();
            ParallelAction parallel = new ParallelAction();

            MoveToAction moveToAction1 = new MoveToAction();
            moveToAction1.setPosition(button.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f-100);
            moveToAction1.setDuration(0.5f);
            moveToAction1.setTarget(button);
            moveToAction1.setInterpolation(Interpolation.swingIn);

            MoveToAction moveToAction2 = new MoveToAction();
            moveToAction2.setPosition(button2.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f-100);
            moveToAction2.setDuration(0.25f);
            moveToAction2.setTarget(button2);
            moveToAction2.setInterpolation(Interpolation.swingIn);

            MoveToAction moveToAction3 = new MoveToAction();
            moveToAction3.setPosition(button2.getX(), game.cam.position.y-SpacePirate.V_HEIGHT/2f-100);
            moveToAction3.setTarget(button3);
            moveToAction3.setDuration(0.1f);
            moveToAction3.setInterpolation(Interpolation.swingIn);

            parallel.addAction(moveToAction1);
            parallel.addAction(moveToAction2);
            parallel.addAction(moveToAction3);

            sequence.addAction(parallel);
            sequence.addAction(new Action() {
                @Override
                public boolean act(float delta) {
                    setState(STATE_RUNNING);
                    return true;
                }
            });

            button.clearActions();
            button.setTransform(true);
            button.setOrigin(button.getX()+button.getWidth()/2f, button.getY()+button.getHeight()/2f);
            button.addAction(sequence);

        }else if(state == STATE_RUNNING)
        {
            System.out.println("STATE SET TO RUNNING");
            Gdx.input.setInputProcessor(new InputListener(game.cam));

            CameraSystem cameraSystem = engine.getSystem(CameraSystem.class);
            cameraSystem.setProcessing(true);

            // TODO player speed
            CVelocity velComp = world.getPlayer().getComponent(CVelocity.class);
            velComp.vel.set(0,4.5f);
        }else if(state == STATE_GAMEOVER) {
            System.out.println("STATE SET TO GAMEOVER");
        }else if(state == STATE_PAUSED) {
            System.out.println("STATE SET TO PAUSED");
        }
    }

    @Override
    public void onWorldEvent(WorldEvent event)
    {
        if(event.get()==WorldEvent.EVENT_GAMEOVER && state!=STATE_PAUSE_MENU_IN)
        {
            setState(STATE_PAUSE_MENU_IN);
        }
    }

    @Override
    public void resize (int width, int height)
    {
        super.resize(width, height);

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose()
    {
        super.dispose();

        engine.clearPools();
        Assets.inst.dispose();

        stage.dispose();
    }
}
