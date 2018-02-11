package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Sine;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CTweenEffect;
import ns.spacepirate.game.tween.TweenTextureAccessor;

/**
 * Created by slotey on 16-02-12.
 */
public class TweenSystem extends IteratingSystem
{
    TweenManager manager;

    ComponentMapper<CTexture> texMap;
    ComponentMapper<CTweenEffect> tweenMap;

    public TweenSystem(TweenManager manager)
    {
        super(Family.all(CTweenEffect.class).get());
        texMap = ComponentMapper.getFor(CTexture.class);
        tweenMap = ComponentMapper.getFor(CTweenEffect.class);
        this.manager = manager;

        Tween.registerAccessor(CTexture.class, new TweenTextureAccessor());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime)
    {
        CTexture texture = texMap.get(entity);
        CTweenEffect tweenEffect = tweenMap.get(entity);

        if(tweenEffect.tweeningState==CTweenEffect.BEGIN)
        {
            aurelienribon.tweenengine.Timeline.createSequence()
                    .push(Tween.to(texture, TweenTextureAccessor.EFFECT_BOUNCE, 0.1f)
                                    .target(texture.sprite.getWidth()*1.5f, texture.sprite.getHeight()*1.5f)
                                    .ease(Back.OUT))
                    .beginParallel()
                    .push(Tween.to(texture, TweenTextureAccessor.EFFECT_FADE, 0.15f)
                               .target(0)
                               .ease(Sine.OUT))
                    .push(Tween.to(texture, TweenTextureAccessor.EFFECT_BOUNCE, 0.15f)
                            .target(texture.sprite.getWidth()/1.5f, texture.sprite.getHeight()/1.5f)
                            .ease(Back.OUT))
                    .setCallback(tweenEffect)
                    .start(manager);

            tweenEffect.tweeningState=CTweenEffect.TWEENING;
        }

//        if(tweenEffect.tweeningState==CTweenEffect.END)
//        {
//            entity.remove(CTweenEffect.class);
//        }
    }

}
