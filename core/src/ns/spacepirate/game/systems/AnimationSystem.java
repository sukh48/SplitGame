package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import ns.spacepirate.game.components.CAnimation;
import ns.spacepirate.game.components.CTexture;

/**
 * Created by sukhmac on 2016-02-10.
 */
public class AnimationSystem extends IteratingSystem
{
    ComponentMapper<CAnimation> animMap;
    ComponentMapper<CTexture> texMap;

    Engine engine;

    public AnimationSystem()
    {
        super(Family.all(CAnimation.class, CTexture.class).get());
        animMap = ComponentMapper.getFor(CAnimation.class);
        texMap = ComponentMapper.getFor(CTexture.class);
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CAnimation animComponent = animMap.get(entity);
        CTexture texComponent = texMap.get(entity);

        Animation animation = animComponent.animations.get(animComponent.get());

        if(animation!=null)
        {
            texComponent.sprite.setRegion(animation.getKeyFrame(animComponent.time));
        }

        animComponent.time+=deltaTime;
    }
}
