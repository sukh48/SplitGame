package ns.spacepirate.game.interfaces;

/**
 * Created by sukhmac on 2017-11-19.
 */

public class WorldEvent
{
    public static final int EVENT_GAMEOVER = 0;

    private int code;
    public WorldEvent(int code)
    {
        this.code = code;
    }

    public int get()
    {
        return code;
    }
}
