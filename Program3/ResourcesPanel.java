//package com.madsen.view;

//import com.madsen.model.Resource;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * Displays all system resources in the simulation.
 */
public class ResourcesPanel extends JPanel {

    /** Number of columns of the grid to display resources in */
    private static final int COLS = 8;

    /** Gap between displayed resources */
    private static final int GAP = 5;

    /** Panel to display resources */
    private JPanel panel;

    /** Resource panels displayed */
    private ArrayList<ResourcePanel> resources;

    /**
     * Constructs a panel to display all system resources.
     *
     * @param numResources Number of system resources.
     */
    public ResourcesPanel(int numResources) {
        super();

        // Set up this panel
        this.setPreferredSize(new Dimension(800, 200));
//        this.getVerticalScrollBar().setUnitIncrement(10);
        this.setLayout(new BorderLayout(0,0));

        // Add label to panel
        JLabel label = new JLabel("Resources", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(this.getPreferredSize().width,
                20));
        this.add(label, BorderLayout.PAGE_START);

        // Constructs a scrollable panel to display resources
        JScrollPane scrollPane = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(this.getPreferredSize().width,
                this.getPreferredSize().height));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        int rows = (int)Math.ceil(numResources / (double)COLS);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, GAP, GAP));
        panel.setPreferredSize(new Dimension(this.getPreferredSize().width,
                (ResourcePanel.HEIGHT + GAP) * rows + GAP));
        scrollPane.setViewportView(panel);
        this.add(scrollPane, BorderLayout.CENTER);

        // Create the resources
        this.resources = new ArrayList<>(numResources);
        for (int i = 0; i < numResources; i++) {
            addResource("r" + i);
        }
    }

    /**
     * Returns all resources displayed by this panel.
     *
     * @return All resources displayed by this panel.
     */
    public ArrayList<ResourcePanel> getResources() {
        return this.resources;
    }

    /**
     * Add new resource to panel.
     *
     * @param name Name of the new resource to add.
     */
    private void addResource(String name) {
        ResourcePanel r = new ResourcePanel(name);
        this.resources.add(r);
        panel.add(r);
    }

    /**
     * Allocates resource r.
     *
     * @param r Resource to allocate.
     */
    public void allocateResource(ResourcePanel r) {
        r.setHeld();
    }

    /**
     * Frees resource r.
     *
     * @param r Resource to free.
     */
    public void freeResource(ResourcePanel r) {
        r.setFree();
    }

}
