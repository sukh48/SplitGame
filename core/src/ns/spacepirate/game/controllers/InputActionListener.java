package ns.spacepirate.game.controllers;

/**
 * Created by sukhmac on 17-11-10.
 */
public class InputActionListener
{
    public static final int NONE = 0;
    public static final int FIRST_TOUCH = 1;
    public static final int PRESSED = 2;

    private final static InputActionListener singleton = new InputActionListener();

    private int state;

    private InputActionListener()
    {
        // singleton
    }

    public static InputActionListener getInstance()
    {
        return singleton;
    }

    public void set(int state)
    {
        this.state = state;
    }

    public int getState()
    {
        return state;
    }

}
