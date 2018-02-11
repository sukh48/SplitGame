package ns.spacepirate.game.components.Collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import ns.spacepirate.game.components.CPosition;

/**
 * Created by sukhmac on 2018-02-03.
 */

public class CircleCollider implements Collider
{
    Circle circleBound;

    public CircleCollider(float radius)
    {
        circleBound = new Circle();
        circleBound.setRadius(radius);
    }

    @Override
    public void setPosition(float x, float y) {
        circleBound.setPosition(x, y);
    }

    public Circle getBounds() {
        return circleBound;
    }

    @Override
    public boolean collides(Collider otherCollider) {
        boolean collides = false;

        if(otherCollider instanceof BoxCollider) {
            BoxCollider otherBoxCollider = (BoxCollider) otherCollider;
            collides = Intersector.overlaps(circleBound, otherBoxCollider.rectBound);
        }else if(otherCollider instanceof  CircleCollider) {
            CircleCollider otherBoxCollider = (CircleCollider) otherCollider;
            collides = Intersector.overlaps(circleBound, otherBoxCollider.circleBound);
        }

        return collides;
    }
}
