package ns.spacepirate.game.editor;

import com.badlogic.ashley.core.Entity;

import java.util.ArrayList;

import ns.spacepirate.game.utils.LevelParser;

/**
 * Created by sukhmac on 17-11-11.
 */
public class LevelSector
{
    ArrayList<Entity> entities;

    public LevelSector()
    {
        entities = new ArrayList<Entity>();
    }

    public void addEntity(Entity entity)
    {
        entities.add(entity);
    }

    public ArrayList<Entity> getEntities()
    {
        return entities;
    }
}
