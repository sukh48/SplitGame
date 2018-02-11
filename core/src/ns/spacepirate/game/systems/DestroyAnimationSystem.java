package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;

import ns.spacepirate.game.components.CAnimation;
import ns.spacepirate.game.components.CDestroy;
import ns.spacepirate.game.components.CTweenEffect;

public class DestroyAnimationSystem extends IteratingSystem
{
    ComponentMapper<CDestroy> destroyMap;
    ComponentMapper<CAnimation> animMap;
    ComponentMapper<CTweenEffect> tweenMap;

    Engine engine;

    public DestroyAnimationSystem()
    {
        super(Family.all(CDestroy.class).get());

        destroyMap = ComponentMapper.getFor(CDestroy.class);
        animMap = ComponentMapper.getFor(CAnimation.class);
        tweenMap = ComponentMapper.getFor(CTweenEffect.class);
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
        CDestroy destroyComponent = destroyMap.get(entity);

        boolean animationEnd = false;
        boolean tweenEnd = false;

        if(animComponent!=null)
        {
            if(animComponent.get()!=CAnimation.ANIM_DESTROYING)
            {
                animComponent.set(CAnimation.ANIM_DESTROYING);
            }

            Animation destroyAnimation = animComponent.animations.get(animComponent.get());
            animationEnd = destroyAnimation == null || destroyAnimation.isAnimationFinished(animComponent.time);
        }else
        {
            animationEnd=true;
        }

        CTweenEffect tweenEffect = tweenMap.get(entity);
        if(!destroyComponent.hasTween()) {
            tweenEnd = true;
        }else if(tweenEffect!=null && tweenEffect.tweeningState==CTweenEffect.END) {
            tweenEnd = true;
            System.out.println("DESTROY");
        }else if(tweenEffect==null) {
            entity.add(new CTweenEffect());
        }

        if(tweenEnd && animationEnd)
        {
            if(animComponent!=null)
            {
                animComponent.set(CAnimation.ANIM_DESTROYED);
            }

            System.out.println("REMOVED");
            engine.removeEntity(entity);
        }
    }
}
