package ns.spacepirate.game.desktop.editor;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

import ns.spacepirate.game.Brahma;
import ns.spacepirate.game.editor.LevelSector;
import ns.spacepirate.game.editor.Sandbox;
import ns.spacepirate.game.utils.GameSector;

/**
 * Created by sukhmac on 17-11-11.
 */
public class Controller
{
    EditorPanel editorPanel;
    Sandbox sandbox;
    ArrayList<GameSector> sectorList;

    int selectedLevel;

    public Controller()
    {
        this.sectorList = new ArrayList<GameSector>();
    }

    public void init(EditorPanel panel , Sandbox sandbox)
    {
        this.editorPanel = panel;
        this.sandbox = sandbox;
    }

    public void setSelectedLevel(int selectedLevel)
    {
        if(selectedLevel>=0 && selectedLevel<sectorList.size()) {
            this.selectedLevel = selectedLevel;
            this.sandbox.setupLevel(sectorList.get(selectedLevel));
        }
    }

    public void createNewLevel()
    {
        GameSector newGameLevel = new GameSector();
        sectorList.add(newGameLevel);

        String levelNames[] = new String[sectorList.size()];
        int index=0;
        for(GameSector sector : sectorList) {
            levelNames[index] = "lvl"+index;
            index++;
        }
        editorPanel.updateLevelList(levelNames);
    }

    public void save()
    {
//        try {
//
//            FileWriter writer = new FileWriter(Gdx.files.internal("levels/level1.json").path());
//            JsonWriter json = new JsonWriter(writer);
//            JsonValue root = json.
//
//            for (GameSector sector : sectorList) {
//                root.remove("levels");
//            }
//        }catch (Exception e) {
//
//        }

        ArrayList<Vector2> entities = sandbox.getEntities();
        GameSector sector = sectorList.get(selectedLevel);
        sector.clearObs();
        for(Vector2 e : entities) {
            sector.addObj(e.x, e.y);
        }

        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        System.out.println(json.prettyPrint(sectorList));

        try {
            FileWriter writer = new FileWriter(Gdx.files.internal("levels/level2.json").path());
            writer.write(json.prettyPrint(sectorList));
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void loadLevels()
    {
//        JsonReader json = new JsonReader();
//        JsonValue root = json.parse(Gdx.files.internal("levels/level1.json"));
//        System.out.println(root);
//
//        JsonValue levels = root.get("levels");
//        Iterator i = levels.iterator();
//        //LevelSector sectors[] = new LevelSector[levels.size];
//
//        String levelNames[] = new String[levels.size];
//
//        int currIndex=0;
//        while(i.hasNext()) {
//            JsonValue level = (JsonValue)i.next();
//            JsonValue objs = level.get("Objs");
//
//            GameSector sector = new GameSector();
//
//            Iterator objIter = objs.iterator();
//            while (objIter.hasNext()) {
//                JsonValue obj = (JsonValue)objIter.next();
//                System.out.println(obj.getFloat("x"));
//                //Entity entity = creator.createObstacle(obj.getFloat("x"), obj.getFloat("y"), 50, 50);
//                sector.addObj(obj.getFloat("x"), obj.getFloat("y"));
//            }
//
//            sectorList.add(sector);
//
//            levelNames[currIndex]="lvl"+currIndex;
//            currIndex++;
//            System.out.println(objs.size);
//        }
//
//        System.out.println("Success");
//
//        editorPanel.updateLevelList(levelNames);

        try {
            InputStream is = new FileInputStream(Gdx.files.internal("levels/level2.json").file());
            BufferedReader buf = new BufferedReader(new InputStreamReader(is));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            while(line != null)
            {
                sb.append(line).append("\n");
                line = buf.readLine();
            }

            String fileAsString = sb.toString();

            Json json = new Json();
            sectorList = json.fromJson(ArrayList.class, fileAsString);
            //System.out.println(sectors);

            is.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        String levelNames[] = new String[sectorList.size()];
        int index=0;
        for(GameSector sector : sectorList) {
            levelNames[index] = "lvl"+index;
            index++;
        }
        editorPanel.updateLevelList(levelNames);
    }

    public void resetPlayer()
    {
        sandbox.resetPlayer();
    }

    public void setMode(int mode) {
        sandbox.setMode(mode);
    }
}
