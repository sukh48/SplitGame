package ns.spacepirate.game.editor.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;

import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.InputListener;
import ns.spacepirate.game.components.CCollider;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.editor.Sandbox;

/**
 * Created by sukhmac on 17-11-12.
 */
public class InputModeSystem extends EntitySystem
{
    Sandbox sandbox;
    Brahma creator;
    Engine engine;

    boolean firstTouch;

    Entity draggedEntity;

    public InputModeSystem(Brahma creator, Sandbox sandbox)
    {
        this.creator = creator;
        this.sandbox = sandbox;
        this.firstTouch = true;
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
            if (InputListener.touched && firstTouch)
            {
                firstTouch=false;
                Vector2 touchedPoint = InputListener.touchedPos;
                Entity obstacle = creator.createObstacle(touchedPoint.x, touchedPoint.y, 50, 50);
                engine.addEntity(obstacle);
                System.out.println("Created Obstacle");
            }else if(!InputListener.touched) {
                firstTouch=true;
            }
        }else if(sandbox.getMode()==Sandbox.MODE_EDIT) {
            if (InputListener.touched && firstTouch) {

                Vector2 touchPos = new Vector2(InputListener.touchedPos);
                for(Entity e : engine.getEntities())
                {
                    CCollider collider = e.getComponent(CCollider.class);
                    if(collider!=null && collider.rect.contains(touchPos)) {
                        draggedEntity = e;
                        System.out.println("Selected Obj");
                    }

                }
            }else if(InputListener.dragged) {

                if(draggedEntity!=null) {
                    System.out.println("DRAG");
                    CPosition position = draggedEntity.getComponent(CPosition.class);
                    position.x = InputListener.dragPos.x;
                    position.y = InputListener.dragPos.y;
                }
            }

            if(InputListener.touched && firstTouch) {
                firstTouch = false;
            }else if(!InputListener.touched) {
                firstTouch = true;
                draggedEntity = null;
            }
        }
    }
}
