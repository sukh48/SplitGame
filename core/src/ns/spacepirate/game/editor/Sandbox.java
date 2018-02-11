package ns.spacepirate.game.editor;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

import ns.spacepirate.game.Assets;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.InputListener;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CTag;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.editor.components.CTrail;
import ns.spacepirate.game.editor.systems.DivideEditorSystem;
import ns.spacepirate.game.editor.systems.InputModeSystem;
import ns.spacepirate.game.editor.systems.TrailSystem;
import ns.spacepirate.game.systems.AnimationSystem;
import ns.spacepirate.game.systems.BackgroundRenderSystem;
import ns.spacepirate.game.systems.BackgroundSystem;
import ns.spacepirate.game.systems.CameraSystem;
import ns.spacepirate.game.systems.CollisionSystem;
import ns.spacepirate.game.systems.DebugRenderingSystem;
import ns.spacepirate.game.systems.DestroyAnimationSystem;
import ns.spacepirate.game.systems.DivideSystem;
import ns.spacepirate.game.systems.ExpireSystem;
import ns.spacepirate.game.systems.MovementSystem;
import ns.spacepirate.game.systems.RenderingSystem;
import ns.spacepirate.game.systems.ShapeRenderingSystem;
import ns.spacepirate.game.systems.SpawnSystem;
import ns.spacepirate.game.systems.TweenSystem;
import ns.spacepirate.game.utils.GameObj;
import ns.spacepirate.game.utils.GameSector;

/**
 * Created by sukhmac on 17-11-11.
 */
public class Sandbox implements ApplicationListener
{
    public static final int V_WIDTH=720;
    public static final int V_HEIGHT=1280;

    public static final int MODE_CREATE=0;
    public static final int MODE_DELETE=1;
    public static final int MODE_EDIT=2;
    public static final int MODE_PLAY=3;

    SpriteBatch batch;
    OrthographicCamera cam;
    StretchViewport viewport;

    PooledEngine engine;
    Brahma creator;
    Entity player;

    int currMode;
    int creatingType;

    public Sandbox()
    {
        engine = new PooledEngine();
        creator = new Brahma(engine);

        setMode(MODE_EDIT);
        //this.controller = controller;
    }

    @Override
    public void create() {

        cam = new OrthographicCamera();
        cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
        cam.position.set(V_WIDTH / 2, V_HEIGHT / 2, 0);

        viewport= new StretchViewport(V_WIDTH, V_HEIGHT, cam);
        viewport.apply();

        //GestureDetector gd = new GestureDetector(new EditorInputListener(cam));
        Gdx.input.setInputProcessor(new InputListener(cam));

        initAssets();

        init();
    }

    public void setCreateType(int type)
    {
        creatingType = type;
    }

    public int getCreatingType()
    {
        return creatingType;
    }

    private void init()
    {
//        player = creator.createPlayer();
//        CVelocity velocityComp = player.getComponent(CVelocity.class);
//        player.add(new CTrail());
//        velocityComp.vel.setZero();
//        engine.addEntity(player);

        CameraSystem cameraSystem = new CameraSystem(cam);
        //cameraSystem.follow(player);

        engine.addSystem(cameraSystem);
        engine.addSystem(new MovementSystem());
        //engine.addSystem(new CollisionSystem());
        engine.addSystem(new InputModeSystem(creator,this));
        engine.addSystem(new DivideEditorSystem(creator));
        engine.addSystem(new TrailSystem(0.2f, creator));
        //engine.addSystem(new BackgroundSystem(player, creator));
        //engine.addSystem(new BackgroundRenderSystem(cameraSystem));
        //engine.addSystem(new ShapeRenderingSystem(cameraSystem));
        engine.addSystem(new RenderingSystem(cameraSystem.getCamera()));
        engine.addSystem(new DebugRenderingSystem(cameraSystem.getCamera()));
        //engine.addSystem(new ExpireSystem(cameraSystem));
    }

    public void setMode(int mode)
    {
        this.currMode = mode;

        if(mode==MODE_PLAY) {
            player = creator.createPlayer();
            CVelocity velocityComp = player.getComponent(CVelocity.class);
            player.add(new CTrail());

            CPosition pos = player.getComponent(CPosition.class);
            pos.y=-100;

            engine.addEntity(player);
        }else {
            if(player!=null) {
                engine.removeEntity(player);
            }
        }
    }

    public int getMode()
    {
        return currMode;
    }

    public ArrayList<GameObj> getEntities()
    {
        ArrayList<GameObj> entities = new ArrayList<GameObj>();
        for (Entity e : engine.getEntities())
        {
            CTag tag = e.getComponent(CTag.class);
            CPosition pos = e.getComponent(CPosition.class);
            if(tag.tag.equalsIgnoreCase("Block"))
            {
                GameObj obj = new GameObj();
                obj.pos.set(pos.x, pos.y);
                obj.type = GameSector.TYPE_OBSTACLE;
                entities.add(obj);
            }else if(tag.tag.equalsIgnoreCase("Coin")) {
                GameObj obj =  new GameObj();
                obj.pos.set(pos.x, pos.y);
                obj.type = GameSector.TYPE_COIN;
                entities.add(obj);
            }
        }

        return entities;
    }

    private void initAssets()
    {
        Assets.inst.init();
        Assets.inst.load();
    }

    public void setupLevel(GameSector sector)
    {
        clearEntities();
        System.out.println("Seting Up Level");
        addEntities(sector.getObjs());
    }

    public void resetPlayer()
    {
        //clearEntities();
        ArrayList<Entity> entitiesToRemove = new ArrayList<Entity>();
        for (Entity entity :  engine.getEntities())
        {
            CTag tagComponent = entity.getComponent(CTag.class);
            if(tagComponent.tag.equalsIgnoreCase("Trail"))
            {
                entitiesToRemove.add(entity);
            }
        }

        for (Entity e : entitiesToRemove)
        {
            engine.removeEntity(e);
        }
    }

    private void addEntities(ArrayList<GameObj> objs) {
        for(GameObj obj : objs) {
            //System.out.println("Entity added");
            Entity e;
            if(obj.type==GameSector.TYPE_OBSTACLE ) {
                e = creator.createObstacle(obj.pos.x, obj.pos.y, 50, 50);
            }else {
                e = creator.createCoin(obj.pos.x, obj.pos.y);
            }

            if(e!=null) {
                engine.addEntity(e);
            }
        }
    }

    private void clearEntities() {
        engine.removeAllEntities();

//        player = creator.createPlayer();
//        CVelocity velocityComp = player.getComponent(CVelocity.class);
//        CPosition positionComp = player.getComponent(CPosition.class);
//
//        player.add(new CTrail());
//        //velocityComp.vel.setZero();
//        positionComp.x=V_WIDTH/2f;
//        positionComp.y=100;
//        engine.addEntity(player);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        cam.position.set(V_WIDTH / 2, V_HEIGHT / 2, 0);
        viewport.apply();
    }

    @Override
    public void render() {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0.5f, 0.5f, 0.5f, 0.0f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        engine.update(Gdx.graphics.getDeltaTime());

        try {
            Thread.sleep((long) (1000 / 60 - Gdx.graphics.getDeltaTime()));
        }catch (Exception e) {

        }
        //System.out.println("Entities: "+engine.getEntities().size());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public Brahma getCreator()
    {
        return creator;
    }
}
