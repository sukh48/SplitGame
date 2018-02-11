package ns.spacepirate.game.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by sukhmac on 17-11-10.
 */
public class GameSector
{
    public static final int MAX_OBJ = 50;

    public static final int TYPE_OBSTACLE = 0;
    public static final int TYPE_COIN = 1;

    private ArrayList<GameObj> obj;

    public GameSector()
    {
        obj = new ArrayList<GameObj>(MAX_OBJ);
    }

    public void addObj(float x, float y, int type)
    {
        GameObj gameObj = new GameObj();
        gameObj.pos = new Vector2(x,y);
        gameObj.type = type;
        obj.add(gameObj);
    }

    public ArrayList<GameObj> getObjs()
    {
        return obj;
    }

    public void clearObs()
    {
        obj.clear();
    }
}
