package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;

import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CVelocity;

/**
 * Created by sukhmac on 17-10-28.
 */
public class SpawnSystem extends EntitySystem
{
    Engine engine;

    Entity player;
    CPosition playerPos;
    CVelocity playerVel;

    Brahma creator;
    float distMoved;

    boolean obstacleFlag;

    public SpawnSystem(Entity player, Brahma creator)
    {
        distMoved=0;
        playerPos = player.getComponent(CPosition.class);
        playerVel = player.getComponent(CVelocity.class);
        this.creator = creator;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        this.engine = engine;

        generateCoins();
        obstacleFlag=true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        distMoved+= playerVel.vel.y;

        if(distMoved> SpacePirate.V_HEIGHT) {
            if(obstacleFlag) {
                generateObstacles();
            }else {
                generateCoins();
            }

            obstacleFlag=!obstacleFlag;
            distMoved=0;
        }
    }

    private void generateObstacles() {
        float startYPos = playerPos.y+800;

        float width = MathUtils.random(50,200);
        float height = MathUtils.random(80,500);
        float randX = (SpacePirate.V_WIDTH/2f);//-(width/2f);//MathUtils.random((SpacePirate.V_WIDTH/2f)-200,(SpacePirate.V_WIDTH/2f)+200);
        engine.addEntity(creator.createObstacle(randX, startYPos+400, width, height));

        //randX = MathUtils.random((SpacePirate.V_WIDTH/2f)-200,(SpacePirate.V_WIDTH/2f)+200);
        //engine.addEntity(creator.createObstacle(randX, startYPos+400));
    }

    private void generateCoins() {
        float startYPos = playerPos.y+800;

        float speedX = 5f;
        float attr = 0.5f;
        float xval;
        int count=1;
        int yCount=0;
        float v1=5;
        float pressed=MathUtils.random(30);

        float alt = MathUtils.random(80,260);
        for(int i=0; i<250; i+=12) {

            //xval = 0.5f * (-1/2f+(1*count)) * (i*i) + (v1*i);

            xval = alt*MathUtils.sin(i/28f);

            float offsetX = (SpacePirate.V_WIDTH/2f)-xval;

            //System.out.println(xval);
            //if(i%12==0) {
                engine.addEntity(creator.createCoin(offsetX, startYPos + (playerVel.vel.y * yCount * 12)));
                engine.addEntity(creator.createCoin(offsetX+(xval*2), startYPos+(playerVel.vel.y * yCount * 12)));
            //}
            //engine.addEntity(creator.createCoin(offsetX+(xval*2), startYPos+(5*yCount)));

            if(i==pressed) {
                count = 0;
                //if(i==11) {
                    v1 = v1+Math.abs((1 - 1.5f) * i);//11.7f;
                    //System.out.println("V1: "+v1);
                //}

            }
            yCount++;
        }
    }
}
