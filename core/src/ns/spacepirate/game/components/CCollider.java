package ns.spacepirate.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import ns.spacepirate.game.interfaces.CollisionHandler;

/**
 * Created by sukhmac on 2016-02-07.
 */
public class CCollider extends Component implements Pool.Poolable
{
    public Rectangle rect;
    public boolean collision;
    private CollisionHandler handler;

    public CCollider()
    {
        rect = new Rectangle();
    }

    public CollisionHandler getHandler()
    {
        return handler;
    }

    public void setHandler(CollisionHandler handler)
    {
        this.handler = handler;
    }

    public boolean hasCollisionHandler()
    {
        return handler!=null;
    }

    @Override
    public void reset()
    {
        rect.setPosition(0,0);
        collision=false;
        handler=null;
    }
}
