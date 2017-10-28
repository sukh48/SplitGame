package ns.spacepirate.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ns.spacepirate.game.components.*;
import ns.spacepirate.game.controllers.AsteroidCollisionHandler;
import ns.spacepirate.game.controllers.BubbleCollisionHandler;
import ns.spacepirate.game.controllers.PlayerCollisionHandler;

/**
 * Created by sukhmac on 2016-02-11.
 */
public class Brahma
{
    PooledEngine engine;

    TextureRegion bulletTexture;
    TextureRegion[] bubblePopFrames;

    TextureRegion asteroidTex;
    TextureRegion[] asteroidBlastFrames;

    TextureRegion[] playerFlyFrames;

    public Brahma(PooledEngine engine)
    {
        this.engine = engine;
    }


    public Entity createPlayer()
    {
        TextureRegion playerTex = Assets.inst.getSpriteTexture("Ball");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CCollider colliderComponent = engine.createComponent(CCollider.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);
        CDivideBall divideComponent = engine.createComponent(CDivideBall.class);
        divideComponent.canMove=false;

        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Player";

        posComponent.x = SpacePirate.V_WIDTH/2;
        posComponent.y = SpacePirate.V_HEIGHT/2;

        velComponent.vel.set(0,10);
        graphicsComponent.sprite.setRegion(playerTex);
        graphicsComponent.sprite.setSize(playerTex.getRegionWidth()/2f, playerTex.getRegionHeight()/2f);
        graphicsComponent.sprite.setCenter(playerTex.getRegionWidth()/4f, playerTex.getRegionHeight()/4f);
        graphicsComponent.sprite.setOriginCenter();

        colliderComponent.rect.setSize(playerTex.getRegionWidth()/2f, playerTex.getRegionHeight()/2f);
        colliderComponent.setHandler(PlayerCollisionHandler.getInstance());

        Entity player = engine.createEntity();
        player.add(posComponent);
        player.add(velComponent);
        player.add(engine.createComponent(CPlayerInput.class));
        player.add(engine.createComponent(CShoot.class));
        player.add(colliderComponent);
        player.add(graphicsComponent);
        player.add(divideComponent);

        return player;
    }

    public Entity createBall(int dir, float x, float y, Entity parent)
    {
        TextureRegion playerTex = Assets.inst.getSpriteTexture("Ball");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CCollider colliderComponent = engine.createComponent(CCollider.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);
        CDivideBall divideComponent = engine.createComponent(CDivideBall.class);
        divideComponent.movingDir=dir;
        divideComponent.canMove=true;
        divideComponent.parent=parent;
        divideComponent.attSpeed=0.5f;
        divideComponent.speed=5f;

        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Ball";

        posComponent.x = x;
        posComponent.y = y;

        velComponent.vel.setZero();
        graphicsComponent.sprite.setRegion(playerTex);
        graphicsComponent.sprite.setSize(playerTex.getRegionWidth()/2f, playerTex.getRegionHeight()/2f);
        graphicsComponent.sprite.setCenter(playerTex.getRegionWidth()/4f, playerTex.getRegionHeight()/4f);
        graphicsComponent.sprite.setOriginCenter();

        colliderComponent.rect.setSize(playerTex.getRegionWidth()/2f, playerTex.getRegionHeight()/2f);
        colliderComponent.setHandler(PlayerCollisionHandler.getInstance());

        Entity player = engine.createEntity();
        player.add(posComponent);
        player.add(velComponent);
        player.add(colliderComponent);
        player.add(graphicsComponent);
        player.add(divideComponent);

        return player;
    }

    public Entity createAsteroid()
    {
        TextureRegion asteroidTex;
        if(MathUtils.random(5)<-3)
        {
            asteroidTex = Assets.inst.getSpriteTexture("Spikes");
        }else {
            asteroidTex = Assets.inst.getSpriteTexture("Ball");
        }

        Entity asteroid = engine.createEntity();
        CCollider colliderComponent = engine.createComponent(CCollider.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);
        //velComponent.vel.y=-10;
        CHealth healthComponent = engine.createComponent(CHealth.class);
        CTexture graphicComponent = engine.createComponent(CTexture.class);
        CTag tagComponent = new CTag();

        tagComponent.tag = "Ball";

        graphicComponent.sprite.setRegion(asteroidTex);
        graphicComponent.sprite.setSize(asteroidTex.getRegionWidth(), asteroidTex.getRegionHeight());

        colliderComponent.rect.setSize(asteroidTex.getRegionWidth(), asteroidTex.getRegionHeight());
//        colliderComponent.setHandler(AsteroidCollisionHandler.getInstance());

        asteroid.add(colliderComponent);
        asteroid.add(healthComponent);
        asteroid.add(posComponent);
        asteroid.add(velComponent);
        asteroid.add(graphicComponent);
        asteroid.add(tagComponent);

        return asteroid;
    }

    public Entity createBullet()
    {
        TextureRegion asteroidTex = Assets.inst.getSpriteTexture("Bullet");

        Entity asteroid = engine.createEntity();
        CCollider colliderComponent = engine.createComponent(CCollider.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);
        CTexture graphicComponent = engine.createComponent(CTexture.class);
        CTag tagComponent = new CTag();

        tagComponent.tag = "Bullet";

        graphicComponent.sprite.setRegion(asteroidTex);
        graphicComponent.sprite.setSize(asteroidTex.getRegionWidth(), asteroidTex.getRegionHeight());

//        colliderComponent.rect.setSize(asteroidTex.getRegionWidth(), asteroidTex.getRegionHeight());
//        colliderComponent.setHandler(AsteroidCollisionHandler.getInstance());

        asteroid.add(colliderComponent);
        asteroid.add(posComponent);
        asteroid.add(velComponent);
        asteroid.add(graphicComponent);
        asteroid.add(tagComponent);

        return asteroid;
    }

}
