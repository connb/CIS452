//package com.madsen.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Panel to display all information about a single process.
 */
public class ProcessPanel extends JPanel {

    /** Colors to display states of process execution */
    public static final Color BLOCKED = new Color(orange);
    public static final Color DEADLOCKED = new Color(red);
    public static final Color RUNNING = new Color(green);

    /** Displays the process name and current state */
    private JPanel processLabel;

    /** Height of a ProcessPanel */
    public static final int PREF_HEIGHT = 100;

    /** Width of a ProcessPanel */
    public static final int PREF_WIDTH = 780;

    /** Panel responsible for displaying resourcesDisplay */
    private JPanel resourcesDisplay;

    /** List of all resources this panel displays */
    private ArrayList<ResourcePanel> resources;

    /** Unique color of this process */
    private Color col;

    /**
     * Constructs a new object for the process panel with all of the necessary
     * components to display process execution and resourcesDisplay held.
     *
     * @param name Name of the process being created.
     */
    public ProcessPanel(String name) {
        super();

        // Set the name of this panel
        this.setName(name);

        // Create the list of displayed resources
        this.resources = new ArrayList<>();

        // Set the color of this panel
        Random r = new Random();
        this.col = new Color(r.nextInt(256), r.nextInt(256),
                r.nextInt(256));

        // Create a border around this panel
        this.setBorder(BorderFactory.createLineBorder(this.col,3));

        // Set the size of this panel
        this.setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));

        // Setup the layout of this panel
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Setup the label of this panel
        processLabel = new JPanel();
        processLabel.setPreferredSize(new Dimension(100, 100));
        processLabel.setLayout(new GridBagLayout());
        JLabel label = new JLabel(name);
        processLabel.add(label);
        this.add(processLabel);
        setRunning();

        // Setup the resource tracking of this process
        this.resourcesDisplay = new JPanel();
        this.resourcesDisplay.setPreferredSize(new Dimension(674,100));
        this.resourcesDisplay.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(this.resourcesDisplay);
    }

    /**
     * Adds the specified resource name to the list of held resourcesDisplay.
     *
     * @param r ResourcePanel to display.
     */
    public void addResource(ResourcePanel r) {
        this.resources.add(r);
        this.resourcesDisplay.add(r);
    }

    /**
     * Removes the specified resource name from the list of held resourcesDisplay.
     *
     * @param rName Name of resource to remove from this panel.
     */
    public void removeResource(String rName) {
        for (ResourcePanel r: this.resources) {
            if (r.getName().equals(rName)) {
                this.resourcesDisplay.remove(r);
                this.resources.remove(r);
                this.resourcesDisplay.repaint();
                break;
            }
        }
    }

    /**
     * Sets this panel to show that the process is currently blocked.
     */
    public void setBlocked() {
        processLabel.setBackground(ProcessPanel.BLOCKED);
    }

    /**
     * Sets this panel to show that the process is currently deadlocked.
     */
    public void setDeadlocked() {
        processLabel.setBackground(ProcessPanel.DEADLOCKED);
    }

    /**
     * Sets this panel to show that the process is currently running.
     */
    public void setRunning() {
        processLabel.setBackground(ProcessPanel.RUNNING);
    }

}
