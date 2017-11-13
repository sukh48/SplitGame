package ns.spacepirate.game.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by sukhmac on 17-11-10.
 */
public class GameSector
{
    public static final int MAX_OBJ = 10;

    private ArrayList<Vector2> obj;

    public GameSector()
    {
        obj = new ArrayList<Vector2>(MAX_OBJ);
    }

    public void addObj(float x, float y)
    {
        obj.add(new Vector2(x,y));
    }

    public ArrayList<Vector2> getObjs()
    {
        return obj;
    }

    public void clearObs()
    {
        obj.clear();
    }
}
