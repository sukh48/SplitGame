package ns.spacepirate.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;

import ns.spacepirate.game.components.CDestroy;
import ns.spacepirate.game.components.CTag;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.interfaces.WorldEvent;
import ns.spacepirate.game.interfaces.WorldEventListener;
import ns.spacepirate.game.tween.TweenTextureAccessor;

/**
 * Created by sukhmac on 2017-11-16.
 */

public class GameWorld
{
    private int score;
    private int distanceTravelled;

    private Brahma creator;
    private Entity player;

    private Sound waterSound;

    private WorldEventListener eventListener;

    public GameWorld(Brahma creator)
    {
        this.creator = creator;
        this.waterSound = Assets.inst.getSound(Assets.SOUND);

        init();
    }

    public void addListener(WorldEventListener eventListener)
    {
        this.eventListener = eventListener;
    }

    public Brahma getCreator()
    {
        return creator;
    }

    public Entity getPlayer()
    {
        return player;
    }


    private void init()
    {
        score = 0;
        distanceTravelled = 0;

        player = creator.createPlayer();
        creator.addToEngine(player);
    }

    public void pause()
    {
        CVelocity velComp = player.getComponent(CVelocity.class);
        velComp.vel.setZero();
    }

    public void notifyCollision(Entity entity, Entity collidedWith)
    {
        CTag tagComp1 = entity.getComponent(CTag.class);
        CTag tagComp2 = collidedWith.getComponent(CTag.class);

        if (tagComp1==null || tagComp2==null) {
            return;
        }

        if ((tagComp1.tag.equalsIgnoreCase("Player") || tagComp1.tag.equalsIgnoreCase("Ball")) &&
            tagComp2.tag.equalsIgnoreCase("Coin"))
        {
            System.out.println("COLLIDED");
            playerWithCoin(collidedWith);
        }else if((tagComp1.tag.equalsIgnoreCase("Player") || tagComp1.tag.equalsIgnoreCase("Ball")) &&
                tagComp2.tag.equalsIgnoreCase("Block")) {
            if(eventListener!=null) {
                eventListener.onWorldEvent(new WorldEvent(WorldEvent.EVENT_GAMEOVER));
            }
        }
    }

    public void notifyCollisionEnd(Entity entity)
    {

    }

    private void playerWithCoin(Entity coin)
    {
        if(coin.getComponent(CDestroy.class)==null)
        {
            CVelocity velComponent = coin.getComponent(CVelocity.class);
            if(velComponent!=null) {
                velComponent.vel.setZero();
            }

            CDestroy destroyComponent = new CDestroy();
            destroyComponent.addTweenEffect(TweenTextureAccessor.EFFECT_BOUNCE);
            coin.add(destroyComponent);

            waterSound.stop();
            waterSound.play();
        }
    }

}
