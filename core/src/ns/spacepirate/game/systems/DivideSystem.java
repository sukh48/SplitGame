package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import ns.spacepirate.game.Assets;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.InputListener;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CCollider;
import ns.spacepirate.game.components.CDivideBall;
import ns.spacepirate.game.components.CExplode;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CTweenEffect;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.controllers.PlayerCollisionHandler;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class DivideSystem extends IteratingSystem
{
    ComponentMapper<CDivideBall> divideBallMap;
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CVelocity> velMap;

    Engine engine;
    Brahma creator;

    boolean firstTouch;

    int count=-1;
    boolean start=false;

    ArrayList<Entity> entitiesToAdd = new ArrayList<Entity>();
    ArrayList<Entity> entitiesToRemove = new ArrayList<Entity>();

    public DivideSystem(Brahma creator)
    {
        super(Family.all(CDivideBall.class, CPosition.class, CVelocity.class).get());
        this.creator = creator;
        divideBallMap = ComponentMapper.getFor(CDivideBall.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);

        firstTouch=true;
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (start) {
            count += 1;
        }

        for(Entity e : entitiesToAdd) {
            engine.addEntity(e);
        }

        for(Entity e : entitiesToRemove) {
            engine.removeEntity(e);
        }

        entitiesToAdd.clear();
        entitiesToRemove.clear();

        if(InputListener.touched && firstTouch) {
            firstTouch = false;
        }else if(!InputListener.touched) {
            firstTouch = true;
        }
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CDivideBall divideBall = divideBallMap.get(entity);
        CPosition entityPos = posMap.get(entity);

        if(InputListener.touched && firstTouch && divideBall.state==CDivideBall.SINGLE)
        {
            CTexture cTexture = entity.getComponent(CTexture.class);
            Entity ballLeft = creator.createBall(CDivideBall.DIR_LEFT, entityPos.x-20, entityPos.y, cTexture.sprite.getWidth()/1.5f, cTexture.sprite.getHeight()/1.5f, entity);
            Entity ballRight = creator.createBall(CDivideBall.DIR_RIGHT, entityPos.x+20, entityPos.y, cTexture.sprite.getWidth()/1.5f, cTexture.sprite.getHeight()/1.5f, entity);

            entitiesToAdd.add(ballLeft);
            entitiesToAdd.add(ballRight);
//            engine.addEntity(ballLeft);
//            engine.addEntity(ballRight);

            System.out.println("Start: " + (entityPos.x - 20));
            divideBall.state=CDivideBall.DIVIDED;

            entity.remove(CTexture.class);
            entity.remove(CCollider.class);

            start=true;
        }

        boolean destroy=false;
        if(divideBall.parent!=null)
        {
            CPosition parPos = posMap.get(divideBall.parent);
            CDivideBall parent = divideBallMap.get(divideBall.parent);
            if(parent==null) {
                System.out.println("Parent Null");
            }
            entityPos.y = parPos.y;

            if(entityPos.x>=parPos.x-15 && entityPos.x<=parPos.x+15) {
                destroy = true;
                parent.state=CDivideBall.SINGLE;

                TextureRegion playerTex = Assets.inst.getSpriteTexture("Ball3");
                CTexture graphicsComponent = new CTexture();
                graphicsComponent.sprite.setRegion(playerTex);
                graphicsComponent.sprite.setSize(playerTex.getRegionWidth() / 2f, playerTex.getRegionHeight() / 2f);
                graphicsComponent.sprite.setCenter(playerTex.getRegionWidth() / 4f, playerTex.getRegionHeight() / 4f);
                graphicsComponent.sprite.setOriginCenter();
                divideBall.parent.add(graphicsComponent);

                CCollider colliderComponent = new CCollider();
                colliderComponent.rect.setSize(playerTex.getRegionWidth() / 2f, playerTex.getRegionHeight() / 2f);
                colliderComponent.setHandler(PlayerCollisionHandler.getInstance());
                divideBall.parent.add(colliderComponent);
            }
        }


        boolean pressed = InputListener.touched;

        if(divideBall.applyForce && divideBall.state==CDivideBall.SINGLE)
        {
            if (divideBall.movingDir == CDivideBall.DIR_RIGHT) {
                entityPos.x += divideBall.speed;
            } else if (divideBall.movingDir == CDivideBall.DIR_LEFT) {
                entityPos.x -= divideBall.speed;
            }

            divideBall.speed += -divideBall.attSpeed + (pressed ? 1f : 0);
        }

        if(destroy)
        {
            //engine.removeEntity(entity);
            entitiesToRemove.add(entity);
        }
    }
}
