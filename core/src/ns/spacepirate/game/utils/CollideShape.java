package ns.spacepirate.game.utils;

import com.badlogic.gdx.math.Shape2D;

/**
 * Created by sukhmac on 2017-11-23.
 */

public class CollideShape
{
    Shape2D collideShape;

    public CollideShape(Shape2D shape)
    {
        this.collideShape = shape;
    }

    public boolean isColliding(Shape2D otherShape)
    {
        return false;
    }
}
