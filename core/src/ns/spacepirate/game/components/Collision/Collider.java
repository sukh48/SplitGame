package ns.spacepirate.game.components.Collision;

/**
 * Created by sukhmac on 2018-02-03.
 */

public interface Collider
{
    public void setPosition(float x, float y);
    public boolean collides(Collider otherCollider);
}
