package ns.spacepirate.game.tween;

import javax.swing.text.html.parser.Entity;

import aurelienribon.tweenengine.TweenAccessor;
import ns.spacepirate.game.components.CTexture;

/**
 * Created by slotey on 16-02-12.
 */
public class TweenTextureAccessor implements TweenAccessor<CTexture>
{
    public static final int EFFECT_BOUNCE=0;
    public static final int EFFECT_FADE=1;

    public TweenTextureAccessor()
    {
    }

    @Override
    public int getValues(CTexture target, int tweenType, float[] returnValues)
    {
        switch (tweenType) {
            case EFFECT_BOUNCE:
                returnValues[0] = target.sprite.getWidth();
                returnValues[1] = target.sprite.getHeight();
                return 2;
            case EFFECT_FADE:
                returnValues[0] = target.sprite.getColor().a;
                return 1;
        }

        return 0;
    }

    @Override
    public void setValues(CTexture target, int tweenType, float[] newValues)
    {
        switch (tweenType) {
            case EFFECT_BOUNCE:
                target.sprite.setSize(newValues[0], newValues[1]);
                break;
            case EFFECT_FADE:
                target.sprite.setAlpha(newValues[0]);
                break;
        }
    }
}
