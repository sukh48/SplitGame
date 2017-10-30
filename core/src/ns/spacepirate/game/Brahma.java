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

        float width = playerTex.getRegionWidth()/2f;
        float height = playerTex.getRegionHeight()/2f;

        posComponent.x = SpacePirate.V_WIDTH/2;//-width/2;
        posComponent.y = SpacePirate.V_HEIGHT/2;//-height/2;

        velComponent.vel.set(0,7f);
        graphicsComponent.sprite.setRegion(playerTex);
        graphicsComponent.sprite.setSize(width, height);
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
        player.add(tagComponent);

        return player;
    }

    public Entity createBall(int dir, float x, float y, Entity parent)
    {
        TextureRegion ballTex = Assets.inst.getSpriteTexture("Ball");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CCollider colliderComponent = engine.createComponent(CCollider.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);
        CDivideBall divideComponent = engine.createComponent(CDivideBall.class);
        divideComponent.movingDir=dir;
        divideComponent.canMove=true;
        divideComponent.parent=parent;
        divideComponent.attSpeed=0.5f/3;
        divideComponent.speed=5f/4  ;

        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Ball";

        posComponent.x = x;
        posComponent.y = y;

        velComponent.vel.setZero();
        graphicsComponent.sprite.setRegion(ballTex);
        graphicsComponent.sprite.setSize(ballTex.getRegionWidth() / 2f, ballTex.getRegionHeight() / 2f);
        graphicsComponent.sprite.setCenter(ballTex.getRegionWidth() / 4f, ballTex.getRegionHeight() / 4f);
        graphicsComponent.sprite.setOriginCenter();

        colliderComponent.rect.setSize(ballTex.getRegionWidth() / 2f, ballTex.getRegionHeight() / 2f);
        colliderComponent.setHandler(PlayerCollisionHandler.getInstance());

        Entity ball = engine.createEntity();
        ball.add(tagComponent);
        ball.add(posComponent);
        ball.add(velComponent);
        ball.add(colliderComponent);
        ball.add(graphicsComponent);
        ball.add(divideComponent);

        return ball;
    }

    public Entity createCoin(float x, float y) {
        TextureRegion coinTex = Assets.inst.getSpriteTexture("Coin3");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CCollider colliderComponent = engine.createComponent(CCollider.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);


        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Coin";

        posComponent.x = x;
        posComponent.y = y;

        velComponent.vel.setZero();
        graphicsComponent.sprite.setRegion(coinTex);
        graphicsComponent.sprite.setSize(coinTex.getRegionWidth() / 2f, coinTex.getRegionHeight() / 2f);
        graphicsComponent.sprite.setCenter(coinTex.getRegionWidth() / 4f, coinTex.getRegionHeight() / 4f);
        graphicsComponent.sprite.setOriginCenter();

        colliderComponent.rect.setSize(coinTex.getRegionWidth() / 2f, coinTex.getRegionHeight() / 2f);

        Entity coin = engine.createEntity();
        coin.add(tagComponent);
        coin.add(posComponent);
        coin.add(velComponent);
        coin.add(colliderComponent);
        coin.add(graphicsComponent);

        return coin;
    }

    public Entity createObstacle(float x, float y, float width, float height) {
        TextureRegion coinTex = Assets.inst.getSpriteTexture("Block1");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CCollider colliderComponent = engine.createComponent(CCollider.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);


        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Block";

        posComponent.x = x;
        posComponent.y = y;

        velComponent.vel.setZero();
        graphicsComponent.sprite.setRegion(coinTex);
        graphicsComponent.sprite.setSize(width,height);
//        graphicsComponent.sprite.setCenter(width/2, height/2);
//        graphicsComponent.sprite.setOriginCenter();

        colliderComponent.rect.setSize(width,height);

        Entity coin = engine.createEntity();
        coin.add(tagComponent);
        coin.add(posComponent);
        coin.add(velComponent);
        coin.add(colliderComponent);
        coin.add(graphicsComponent);

        return coin;
    }

}
