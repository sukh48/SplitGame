package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ns.spacepirate.game.Assets;
import ns.spacepirate.game.Brahma;
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

    int count=-1;
    boolean start=false;

    public DivideSystem(Brahma creator)
    {
        super(Family.all(CDivideBall.class, CPosition.class, CVelocity.class).get());
        this.creator = creator;
        divideBallMap = ComponentMapper.getFor(CDivideBall.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);
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
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CDivideBall divideBall = divideBallMap.get(entity);
        CPosition entityPos = posMap.get(entity);

        if(divideBall.divide) {
            Entity ballLeft = creator.createBall(CDivideBall.DIR_LEFT, entityPos.x-20, entityPos.y, entity);
            Entity ballRight = creator.createBall(CDivideBall.DIR_RIGHT, entityPos.x+20, entityPos.y, entity);

            engine.addEntity(ballLeft);
            engine.addEntity(ballRight);

            System.out.println("Start: " + (entityPos.x - 20));
            divideBall.divide=false;
            divideBall.divided=true;

            entity.remove(CTexture.class);
            entity.remove(CCollider.class);

            start=true;
        }

        boolean destroy=false;
        boolean pressed=false;
        if(divideBall.canMove) {
            if(divideBall.parent!=null)
            {
                CPosition parPos = posMap.get(divideBall.parent);
                CDivideBall parent = divideBallMap.get(divideBall.parent);
                entityPos.y = parPos.y;

                pressed = parent.pressed;

                if(entityPos.x>=parPos.x-15 && entityPos.x<=parPos.x+15) {
                    destroy = true;
                    parent.divided=false;

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

            if(divideBall.movingDir==CDivideBall.DIR_RIGHT)
            {
                entityPos.x += divideBall.speed;
            }else if(divideBall.movingDir==CDivideBall.DIR_LEFT)
            {
                entityPos.x -= divideBall.speed;
            }

            divideBall.speed += -divideBall.attSpeed + (pressed? 1f : 0);

//            if(pressed)
//            {
//                if(count<10) {
//                    divideBall.speed += 1f;
//                }
//
//            }

            if(divideBall.movingDir==CDivideBall.DIR_LEFT) {
                //System.out.println("count: "+count);
                //System.out.println(340-entityPos.x);

                if(count>10) {
                    //System.out.println("Speed: "+divideBall.speed);
                }
            }

            if(destroy) {
                engine.removeEntity(entity);
            }

        }

    }
}
