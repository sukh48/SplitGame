package ns.spacepirate.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

import ns.spacepirate.game.Assets;
import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.GameWorld;
import ns.spacepirate.game.SpacePirate;
import ns.spacepirate.game.components.CBackground;
import ns.spacepirate.game.components.CPosition;
import ns.spacepirate.game.components.CTexture;
import ns.spacepirate.game.components.CVelocity;
import ns.spacepirate.game.utils.GameObj;
import ns.spacepirate.game.utils.GameSector;
import ns.spacepirate.game.utils.LevelParser;

/**
 * Created by sukhmac on 17-10-28.
 */
public class BackgroundSystem extends EntitySystem
{
    private PooledEngine engine;

    private Entity player;
    private Entity back1;
    private Entity back2;
    private Entity back3;
    private Entity currBottom;
    private Entity currMid;
    private Entity currTop;

    private ComponentMapper<CPosition> posMap;
    private ComponentMapper<CVelocity> velMap;

    private float movedDist;
    private ArrayList<GameSector> sectors;
    private int currSecIndex;

    private Brahma creator;

    public BackgroundSystem(GameWorld world)
    {
        super();

        this.posMap = ComponentMapper.getFor(CPosition.class);
        this.velMap = ComponentMapper.getFor(CVelocity.class);
        this.movedDist=0;

        this.player = world.getPlayer();
        this.creator = world.getCreator();

        this.sectors = LevelParser.parse();
        this.currSecIndex=0;
    }

    @Override
    public void update(float deltaTime)
    {
        super.update(deltaTime);

        CPosition currBottomPos = posMap.get(currBottom);
        CPosition currTopPos = posMap.get(currTop);

        float playerYVel = velMap.get(player).vel.y;

        movedDist+=playerYVel;

        if(movedDist>=SpacePirate.V_HEIGHT) {
            currBottomPos.y = currTopPos.y + SpacePirate.V_HEIGHT;
            createSector(MathUtils.random(sectors.size()-1), currBottomPos.y);
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
        GameSector sector = sectors.get(sectorIndex);
        for (GameObj obj : sector.getObjs())
        {
            if(obj.type==GameSector.TYPE_OBSTACLE) {
                Entity obstacle = creator.createObstacle(obj.pos.x, obj.pos.y + pos, 50, 50);
                engine.addEntity(obstacle);
            }else if(obj.type==GameSector.TYPE_COIN) {
                Entity coin = creator.createCoin(obj.pos.x, obj.pos.y + pos);
                engine.addEntity(coin);
            }

        }
    }

    @Override
    public void addedToEngine(Engine engine)
    {
        super.addedToEngine(engine);
        this.engine= (PooledEngine)engine;

        back1 = createBackground(0,0, Assets.T_BACKGROUND2);
        back2 = createBackground(0,SpacePirate.V_HEIGHT, Assets.T_BACKGROUND2);
        back3 = createBackground(0,SpacePirate.V_HEIGHT*2,Assets.T_BACKGROUND);
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
