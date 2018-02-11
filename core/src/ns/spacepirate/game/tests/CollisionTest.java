package ns.spacepirate.game.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by sukhmac on 2018-02-04.
 */

public class CollisionTest extends Game
{
    public static final int V_WIDTH=720;
    public static final int V_HEIGHT=1280;

    Vector2 ballPos;
    Vector2 rectPos;

    Rectangle rectangle;
    Circle circle;

    ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        ballPos = new Vector2(200, 200);
        rectPos = new Vector2(200, 500);

        rectangle = new Rectangle();
        rectangle.setPosition(rectPos);
        rectangle.setSize(50,50);

        circle = new Circle();
        circle.setPosition(ballPos);
        circle.setRadius(20);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render()
    {
        GL20 gl = Gdx.gl;
        gl.glClearColor(0.5f, 1.0f, 1.0f, 0.0f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            circle.setPosition(circle.x, circle.y+1.5f);
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            circle.setPosition(circle.x, circle.y-1.5f);
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            circle.setPosition(circle.x-1.5f, circle.y);
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            circle.setPosition(circle.x+1.5f, circle.y);
        }

        if(Intersector.overlaps(circle, rectangle)) {
            shapeRenderer.setColor(Color.RED);
        }else {
            shapeRenderer.setColor(Color.BLUE);
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
        shapeRenderer.end();

    }
}
