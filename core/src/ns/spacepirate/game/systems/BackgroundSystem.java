package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import ns.spacepirate.game.Assets;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CBackground;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.utils.GameSector;
import ns.spacepirate.game.utils.LevelParser;

/**
 * Created by sukhmac on 17-10-28.
 */
public class BackgroundSystem extends EntitySystem
{
    PooledEngine engine;

    Entity player;
    Entity back1;
    Entity back2;
    Entity back3;
    Entity currBottom;
    Entity currMid;
    Entity currTop;

    ComponentMapper<CPosition> posMap;
    ComponentMapper<CVelocity> velMap;

    float movedDist;
    ArrayList<GameSector> sectors;
    int currSecIndex;

    Brahma creator;

    public BackgroundSystem(Entity player, Brahma creator)
    {
        super();
        posMap = ComponentMapper.getFor(CPosition.class);
        velMap = ComponentMapper.getFor(CVelocity.class);
        movedDist=0;
        this.player = player;

        sectors = LevelParser.parse();
        currSecIndex=0;

        this.creator = creator;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        CPosition currBottomPos = posMap.get(currBottom);
        CPosition currTopPos = posMap.get(currTop);

        float playerYVel = velMap.get(player).vel.y;

        movedDist+=playerYVel;

        if(movedDist>=SpacePirate.V_HEIGHT) {
            currBottomPos.y = currTopPos.y + SpacePirate.V_HEIGHT;
            createSector(currSecIndex, currBottomPos.y);
            currSecIndex += 1;
            currSecIndex = currSecIndex % sectors.size();

            movedDist=0;

            Entity temp = currBottom;
            currBottom = currMid;
            currMid = currTop;
            currTop = temp;
        }


    }

    public void createSector(int sectorIndex, float pos)
    {
        Gdx.app.log("MyTag", "my informative message");
        Gdx.app.error("MyTag", "my error message");
        Gdx.app.debug("MyTag", "my debug message");


        GameSector sector = sectors.get(sectorIndex);
        for (Vector2 objPos : sector.getObjs())
        {
            Entity obstacle = creator.createObstacle(objPos.x, objPos.y+pos, 50, 50);
            engine.addEntity(obstacle);
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        this.engine= (PooledEngine)engine;

        back1 = createBackground(0,0, Assets.T_BACKGROUND2);
        back2 = createBackground(0,SpacePirate.V_HEIGHT, Assets.T_BACKGROUND2);
        back3 = createBackground(0,SpacePirate.V_HEIGHT*2,Assets.T_BACKGROUND2);
        engine.addEntity(back1);
        engine.addEntity(back2);
        engine.addEntity(back3);

        currBottom = back1;
        currMid = back2;
        currTop = back3;
    }

    private Entity createBackground(float x, float y, String tex)
    {
        Texture backTexture = Assets.inst.getTexture(tex);
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        graphicsComponent.sprite.setRegion(backTexture);
        graphicsComponent.sprite.setSize(SpacePirate.V_WIDTH, SpacePirate.V_HEIGHT);

        // set position to center
        CPosition positionComponent = new CPosition();
        positionComponent.x = x;
        positionComponent.y = y;

        CBackground backgroundComp = new CBackground();

        Entity background = engine.createEntity();
        background.add(backgroundComp);
        background.add(positionComponent);
        background.add(graphicsComponent);

        return background;
    }
}
