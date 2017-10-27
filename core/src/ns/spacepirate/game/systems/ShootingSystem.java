package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.*;
import ns.spacepirate.game.controllers.BubbleCollisionHandler;

import java.util.ArrayList;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class ShootingSystem extends IntervalIteratingSystem
{
    ComponentMapper<CShoot> shootMap;
    ComponentMapper<CPosition> posMap;
    ComponentMapper<CVelocity> velMap;

    PooledEngine engine;
    Brahma creator;

    public ShootingSystem(Brahma creator)
    {
        super(Family.all(CShoot.class, CPosition.class, CVelocity.class).get(), 0.15f);
        this.creator = creator;

        shootMap = ComponentMapper.getFor(CShoot.class);
        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        super.addedToEngine(engine);
        this.engine = (PooledEngine)engine;
    }

    @Override
    protected void processEntity(Entity entity)
    {
        CShoot shootComponent = shootMap.get(entity);
        CPosition entityPosition = posMap.get(entity);
        CVelocity entityVelocity = velMap.get(entity);
        CTexture entityTexture = entity.getComponent(CTexture.class);

        if(shootComponent.isShooting)
        {
            Entity shootArrow = creator.createBullet();
            CPosition posComponent = posMap.get(shootArrow);
            CVelocity velComponent = velMap.get(shootArrow);

            posComponent.x = entityPosition.x+entityTexture.sprite.getWidth()/2-10;
            posComponent.y = entityPosition.y+entityTexture.sprite.getHeight();

            velComponent.vel.set(entityVelocity.vel);
            velComponent.vel.scl(5);

            engine.addEntity(shootArrow);
        }

    }

}
