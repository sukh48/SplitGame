package ns.spacepirate.game.tween;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by sukhmac on 2017-11-19.
 */

public class TweenActorAccessor implements TweenAccessor<Actor>
{
    public static final int EFFECT_1 = 0;
    public static final int EFFECT_2 = 1;

    public TweenActorAccessor()
    {

    }

    @Override
    public int getValues(Actor target, int tweenType, float[] returnValues)
    {
        switch (tweenType) {
            case EFFECT_1:
                returnValues[0] = target.getWidth();
                returnValues[1] = target.getHeight();
                return 2;
            case EFFECT_2:
                returnValues[0] = target.getColor().a;
                return 1;
        }

        return 0;
    }

    @Override
    public void setValues(Actor target, int tweenType, float[] newValues)
    {
        switch (tweenType) {
            case EFFECT_1:
                target.setSize(newValues[0], newValues[1]);
                break;
            case EFFECT_2:
                target.getColor().a = newValues[0];
                break;
        }
    }
}
