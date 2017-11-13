package ns.spacepirate.game.editor.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;

import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.InputListener;
import ns.spacepirate.game.controllers.InputActionListener;
import ns.spacepirate.game.editor.Sandbox;

/**
 * Created by sukhmac on 17-11-12.
 */
public class InputModeSystem extends EntitySystem
{
    Sandbox sandbox;
    Brahma creator;
    Engine engine;

    InputActionListener inputListener;

    public InputModeSystem(Brahma creator, Sandbox sandbox)
    {
        this.creator = creator;
        this.sandbox = sandbox;

        inputListener = InputActionListener.getInstance();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine = engine;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if(sandbox.getMode()==Sandbox.MODE_CREATE) {
            if (inputListener.getState()==InputActionListener.FIRST_TOUCH) {
                Vector2 touchedPoint = InputListener.touchedPos;
                Entity obstacle = creator.createObstacle(touchedPoint.x, touchedPoint.y, 50, 50);
                engine.addEntity(obstacle);
                System.out.println("Created Obstacle");
            }
        }
    }
}
