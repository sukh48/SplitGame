package ns.spacepirate.game.editor.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;

import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.InputListener;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.editor.Sandbox;
import ns.spacepirate.game.utils.GameSector;

/**
 * Created by sukhmac on 17-11-12.
 */
public class InputModeSystem extends EntitySystem
{
    private Sandbox sandbox;
    private Brahma creator;
    private Engine engine;

    private boolean firstTouch;
    private Entity draggedEntity;

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
                float offsetX = 10;
                Vector2 touchedPoint = InputListener.touchedPos;
                Entity entity = null;
                if(sandbox.getCreatingType() == GameSector.TYPE_OBSTACLE) {
                    entity = creator.createObstacle(touchedPoint.x, touchedPoint.y, 50, 50);
                }else if(sandbox.getCreatingType() == GameSector.TYPE_COIN) {
                    entity = creator.createCoin(touchedPoint.x, touchedPoint.y);
                }

                CPosition position = entity.getComponent(CPosition.class);
                position.x = (Math.round(touchedPoint.x/50) * 50) + offsetX;
                position.y = Math.round(touchedPoint.y/50) * 50;

                engine.addEntity(entity);

                System.out.println("Created Obstacle");
            }else if(!InputListener.touched) {
                firstTouch=true;
            }
        }else if(sandbox.getMode()==Sandbox.MODE_EDIT) {
            if (InputListener.touched && firstTouch) {

                Vector2 touchPos = new Vector2(InputListener.touchedPos);
                for(Entity e : engine.getEntities())
                {
//                    CCollider collider = e.getComponent(CCollider.class);
//                    Rectangle rect = new Rectangle(collider.rect);
//                    rect.x -= rect.width/2f;
//                    rect.y -= rect.height/2f;
//                    if(collider!=null && rect.contains(touchPos)) {
//                        draggedEntity = e;
//                        System.out.println("Selected Obj");
//                    }
                }
            }else if(InputListener.dragged) {
                float offsetX = 10;
                if(draggedEntity!=null) {
                    CPosition position = draggedEntity.getComponent(CPosition.class);
                    position.x = (Math.round(InputListener.dragPos.x/50) * 50) + offsetX;
                    position.y = Math.round(InputListener.dragPos.y/50) * 50;
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
