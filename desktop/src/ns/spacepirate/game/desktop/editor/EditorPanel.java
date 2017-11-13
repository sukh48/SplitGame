package ns.spacepirate.game.desktop.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import ns.spacepirate.game.editor.LevelSector;
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

    Controller controller;
    Sandbox sandbox;

    public EditorPanel(Controller controller)
    {
        super();

//        DefaultMutableTreeNode top =
//                new DefaultMutableTreeNode("The Java Series");
//        createNodes(top);
//        JTree tree = new JTree(top);
//        JScrollPane scrollPane = new JScrollPane(tree);

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

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(scrollPane);
        add(newButton);
        add(loadButton);
        add(saveButton);
        add(resetPlayerButton);
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

    public Sandbox getSandbox()
    {
        return sandbox;
    }

//    private void createNodes(DefaultMutableTreeNode top) {
//        DefaultMutableTreeNode category = null;
//        DefaultMutableTreeNode book = null;
//
//        category = new DefaultMutableTreeNode("Books for Java Programmers");
//        top.add(category);
//
//        category = new DefaultMutableTreeNode("Test 2");
//        top.add(category);
//    }
}
