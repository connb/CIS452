//package com.madsen.view;

import javax.swing.*;
import java.io.File;

/**
 * Class responsible for managing all display of the Resource Manager.
 */
public class View extends JFrame {

    /** Simulation simulationPanel currently displayed */
    private SimulationPanel simulationPanel;

    /**
     * Constructs a View object with the given name in the title bar.
     *
     * @param name Name to display in the title bar.
     */
    public View(String name) {
        super(name);

        // Set up base properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        // Add menu bar
        this.setJMenuBar(constructMenuBar());

        // Add empty simulation simulationPanel
        simulationPanel = new SimulationPanel(0,0);
        this.add(simulationPanel);

        // Display the window
        this.pack();
        this.setVisible(true);
    }

    /**
     * Constructs the menu bar of the program.
     *
     * @return JMenuBar containing all submenus.
     */
    private JMenuBar constructMenuBar() {
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Build menuFile item
        JMenu menuFile = new JMenu("New");
        menuBar.add(menuFile);

        // Build menuExit item
        JMenu menuExit = new JMenu("Exit");
        menuBar.add(menuExit);

        return menuBar;
    }

    public void createSimulation(int processes, int resources) {
        this.remove(simulationPanel);
        simulationPanel = new SimulationPanel(processes,resources);
        this.add(simulationPanel);
//        this.revalidate();
//        this.pack();
    }

    /**
     * Retrieves input file to be parsed by Resource Manager for simulation.
     *
     * @return File to be parsed by Resource Manager.
     */
    public File getInputFile () {
        // Create file chooser and set it to only select files
        JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Attempt to get a file from the user
        File file = null;
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file;
    }

    /**
     * Sets the display of the specified process to the blocked state.
     *
     * @param pName Name of process to mark as blocked.
     */
    public void setBlocked(String pName) {
        this.simulationPanel.setBlocked(pName);
        this.revalidate();
//        this.pack();
    }

    /**
     * Sets the display of the specified process to the running state.
     *
     * @param pName Name of process to mark as running.
     */
    public void setRunning(String pName) {
        this.simulationPanel.setRunning(pName);
        this.revalidate();
//        this.pack();
    }

    /**
     * Allocates resource rName to process pName.
     *
     * @param pName Name of the process to receive resource rName.
     * @param rName Name of the resource being allocated.
     */
    public void allocateResource(String pName, String rName) {
        this.simulationPanel.allocateResource(pName,rName);
        this.revalidate();
//        this.pack();
    }

    /**
     * Frees resource rName from process pName.
     *
     * @param pName Name of process to free resource from.
     * @param rName Name of resource to free.
     */
    public void freeResource(String pName, String rName) {
        this.simulationPanel.freeResource(pName,rName);
        this.revalidate();
//        this.pack();
    }

}
