package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;

import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CTexture;

public class ShapeRenderingSystem extends EntitySystem
{
    private ComponentMapper<CPosition> posMap;
    private ComponentMapper<CTexture> graphicsMap;

    private ShapeRenderer shapeRenderer;
    private PolygonSpriteBatch polyBatch;
    private PolygonSprite polySprite;
    private Camera camera;

    FloatArray verts;

    public ShapeRenderingSystem(Camera camera)
    {
        super();

        this.shapeRenderer = new ShapeRenderer();
        this.polyBatch = new PolygonSpriteBatch();
        this.camera = camera;

        posMap = ComponentMapper.getFor(CPosition.class);
        graphicsMap = ComponentMapper.getFor(CTexture.class);

        Vector2 center = new Vector2(50,1280/2f);
        verts = new FloatArray(new float[]{0,0,50,0,100,500,50,1280,0,1280,0,0});

        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(Color.BLACK); // DE is red, AD is green and BE is blue.
        pix.fill();
        Texture textureSolid = new Texture(pix);

        //triangulate the polygon vertices
        EarClippingTriangulator triangulator = new EarClippingTriangulator();
        ShortArray triangleIndices = triangulator.computeTriangles(verts.toArray());

        PolygonRegion polyReg = new PolygonRegion(new TextureRegion(textureSolid), verts.toArray(), triangleIndices.toArray());
        polySprite = new PolygonSprite(polyReg);
        //polySprite.
        //polySprite.setOrigin(a, b);
        //polyBatch = new PolygonSpriteBatch();
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);
        //camera.update();

//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.setColor(Color.BLACK);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        //shapeRenderer.rect(0,0,100,1280);
//
//        shapeRenderer.polygon(verts);
//        shapeRenderer.end();

        polyBatch.setProjectionMatrix(camera.combined);
        polyBatch.begin();
        polySprite.draw(polyBatch);
        polyBatch.end();

    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);

        shapeRenderer.dispose();
    }
}
