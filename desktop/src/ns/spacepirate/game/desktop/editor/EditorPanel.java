package ns.spacepirate.game.desktop.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ns.spacepirate.game.editor.Sandbox;
import ns.spacepirate.game.utils.GameSector;

/**
 * Created by sukhmac on 17-11-11.
 */
public class EditorPanel extends JPanel implements ActionListener, ListSelectionListener
{
    JList levelList;
    JButton newButton;
    JButton saveButton;
    JButton loadButton;
    JButton resetPlayerButton;
    JButton playButton;

    JButton obstacleButton;
    JButton coinButton;
    JButton editModeButton;

    Controller controller;

    public EditorPanel(Controller controller)
    {
        super();

        this.controller = controller;

        levelList = new JList();
        levelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        levelList.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(levelList);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("Save");

        newButton = new JButton("New");
        newButton.addActionListener(this);
        newButton.setActionCommand("New");

        loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        loadButton.setActionCommand("Load");

        resetPlayerButton = new JButton("Reset");
        resetPlayerButton.addActionListener(this);
        resetPlayerButton.setActionCommand("Reset");

        obstacleButton = new JButton("Obstacle");
        obstacleButton.addActionListener(this);
        obstacleButton.setActionCommand("Obstacle");

        coinButton = new JButton("Coin");
        coinButton.addActionListener(this);
        coinButton.setActionCommand("Coin");

        editModeButton = new JButton("Edit");
        editModeButton.addActionListener(this);
        editModeButton.setActionCommand("Edit");

        playButton = new JButton("Play");
        playButton.addActionListener(this);
        playButton.setActionCommand("Play");

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(scrollPane);
        add(newButton);
        add(loadButton);
        add(saveButton);
        add(resetPlayerButton);
        add(obstacleButton);
        add(coinButton);
        add(editModeButton);
        add(playButton);
    }

    public void updateLevelList(String list[])
    {
        levelList.setListData(list);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equalsIgnoreCase("Load")) {
            controller.loadLevels();
        }else if(e.getActionCommand().equalsIgnoreCase("Reset")) {
            controller.resetPlayer();
        }else if(e.getActionCommand().equalsIgnoreCase("New")) {
            controller.createNewLevel();
        }else if(e.getActionCommand().equalsIgnoreCase("Save")) {
            controller.save();
        }else if(e.getActionCommand().equalsIgnoreCase("Edit")) {
            controller.setMode(Sandbox.MODE_EDIT);
        }else if(e.getActionCommand().equalsIgnoreCase("Obstacle")) {
            controller.setMode(Sandbox.MODE_CREATE);
            controller.setCreateType(GameSector.TYPE_OBSTACLE);
        }else if(e.getActionCommand().equalsIgnoreCase("Coin")) {
            controller.setMode(Sandbox.MODE_CREATE);
            controller.setCreateType(GameSector.TYPE_COIN);
        }else if(e.getActionCommand().equalsIgnoreCase("Play")) {
            controller.setMode(Sandbox.MODE_PLAY);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if(!e.getValueIsAdjusting()) {
            int selectedIndex = levelList.getSelectedIndex();
            System.out.println(levelList.getSelectedIndex());

            controller.setSelectedLevel(selectedIndex);
        }

    }

}
