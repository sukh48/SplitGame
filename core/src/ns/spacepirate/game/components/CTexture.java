package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;

public class CTexture extends Component implements Pool.Poolable
{
    public Sprite sprite;

    public CTexture()
    {
        sprite = new Sprite();
    }

    @Override
    public void reset()
    {
        sprite.setTexture(null);
        sprite.setColor(Color.WHITE);
        sprite.setAlpha(1);
    }
}
