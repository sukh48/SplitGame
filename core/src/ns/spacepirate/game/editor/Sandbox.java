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

    SpriteBatch batch;
    OrthographicCamera cam;
    StretchViewport viewport;

    PooledEngine engine;
    Brahma creator;
    Entity player;

    int currMode;

    public Sandbox()
    {
        engine = new PooledEngine();
        creator = new Brahma(engine);

        this.currMode = MODE_EDIT;
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

    private void init()
    {
        player = creator.createPlayer();
        CVelocity velocityComp = player.getComponent(CVelocity.class);
        player.add(new CTrail());
        velocityComp.vel.setZero();
        engine.addEntity(player);

        CameraSystem cameraSystem = new CameraSystem(cam);
        //cameraSystem.follow(player);

        engine.addSystem(cameraSystem);
        engine.addSystem(new MovementSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new InputModeSystem(creator,this));
        engine.addSystem(new DivideEditorSystem(creator));
        engine.addSystem(new TrailSystem(0.2f, creator));
        //engine.addSystem(new BackgroundSystem(player, creator));
        //engine.addSystem(new BackgroundRenderSystem(cameraSystem));
        //engine.addSystem(new ShapeRenderingSystem(cameraSystem));
        engine.addSystem(new RenderingSystem(cameraSystem));
        engine.addSystem(new DebugRenderingSystem(cameraSystem));
        //engine.addSystem(new ExpireSystem(cameraSystem));
    }

    public void setMode(int mode)
    {
        this.currMode = mode;
    }

    public int getMode()
    {
        return currMode;
    }

    public ArrayList<Vector2> getEntities()
    {
        ArrayList<Vector2> entities = new ArrayList<Vector2>();
        for (Entity e : engine.getEntities())
        {
            CTag tag = e.getComponent(CTag.class);
            CPosition pos = e.getComponent(CPosition.class);
            if(tag.tag.equalsIgnoreCase("Block"))
            {
                entities.add(new Vector2(pos.x, pos.y));
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

        CPosition pos = player.getComponent(CPosition.class);
        pos.y=-100;
    }

    private void addEntities(ArrayList<Vector2> objs) {
        for(Vector2 obj : objs) {
            //System.out.println("Entity added");
            Entity e = creator.createObstacle(obj.x, obj.y, 50, 50);
            engine.addEntity(e);
        }
    }

    private void clearEntities() {
        engine.removeAllEntities();

        player = creator.createPlayer();
        CVelocity velocityComp = player.getComponent(CVelocity.class);
        CPosition positionComp = player.getComponent(CPosition.class);

        player.add(new CTrail());
        //velocityComp.vel.setZero();
        positionComp.x=V_WIDTH/2f;
        positionComp.y=100;
        engine.addEntity(player);
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
