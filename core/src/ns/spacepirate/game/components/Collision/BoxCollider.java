package ns.spacepirate.game.components.Collision;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import ns.spacepirate.game.components.CPosition;

/**
 * Created by sukhmac on 2018-02-03.
 */

public class BoxCollider implements Collider
{
    Rectangle rectBound;

    public BoxCollider(float width, float height)
    {
        rectBound = new Rectangle();
        rectBound.setSize(width, height);
    }

    @Override
    public void setPosition(float x, float y) {
        rectBound.setPosition(x, y);
    }

    public Rectangle getBounds() {
        return rectBound;
    }

    @Override
    public boolean collides(Collider otherCollider) {
        boolean collides = false;

        if(otherCollider instanceof BoxCollider) {
            BoxCollider otherBoxCollider = (BoxCollider) otherCollider;
            rectBound.overlaps(otherBoxCollider.rectBound);
            collides = true;
        }else if(otherCollider instanceof  CircleCollider) {
            CircleCollider otherBoxCollider = (CircleCollider) otherCollider;
            collides = Intersector.overlaps(otherBoxCollider.circleBound, rectBound);
        }

        return collides;
    }
}
