//package com.madsen.view;

//import com.madsen.model.Resource;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel to display all processes in the simulation.
 */
public class ProcessesPanel extends JPanel {

    /** Preferred dimensions */
    private static final int PREF_HEIGHT = 400;
    private static final int PREF_WIDTH = 800;

    /** Gap between displayed processes */
    private static final int GAP = 5;

    /** Panel to display processes */
    private JPanel panel;

    /** Process panels displayed */
    private ArrayList<ProcessPanel> processes;

    /**
     * Constructs a panel to display all processes in the simulation.
     *
     * @param numProcesses Number of processes.
     */
    public ProcessesPanel(int numProcesses) {
        super();

        // Set up this panel
        this.setPreferredSize(new Dimension(PREF_WIDTH,PREF_HEIGHT));
        this.setLayout(new BorderLayout(0,0));

        // Add label to panel
        JLabel label = new JLabel("Processes", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(this.getPreferredSize().width,
                20));
        this.add(label, BorderLayout.PAGE_START);

        // Construct a scrollable panel to display processes
        JScrollPane scrollPane = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(PREF_WIDTH,PREF_HEIGHT));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        panel = new JPanel();
        panel.setBackground(new Color(150,150,150));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,GAP));
        panel.setPreferredSize(new Dimension(ProcessPanel.PREF_WIDTH,
                (ProcessPanel.PREF_HEIGHT + GAP) * numProcesses + GAP));
        scrollPane.setViewportView(panel);
        this.add(scrollPane,BorderLayout.CENTER);

        // Create the processes
        this.processes = new ArrayList<>(numProcesses);
        for (int i = 0; i < numProcesses; i++) {
            addProcess("p" + i);
        }
    }

    /**
     * Returns all processes displayed by this panel.
     *
     * @return All processes displayed by this panel.
     */
    public ArrayList<ProcessPanel> getProcesses() {
        return this.processes;
    }

    /**
     * Add new process to panel.
     *
     * @param name Name of the new process to add.
     */
    private void addProcess(String name) {
        ProcessPanel p = new ProcessPanel(name);
        panel.add(p);
        this.processes.add(p);
    }

    /**
     * Allocates resource r to process p.
     *
     * @param p Process receiving the resource.
     * @param r Resource being allocated.
     */
    public void allocateResource(ProcessPanel p, ResourcePanel r) {
        p.addResource(r);
    }

    /**
     * Frees resource r from process p.
     *
     * @param p Process to free resource from.
     * @param rName Name of resource to free.
     */
    public void freeResource(ProcessPanel p, String rName) {
        p.removeResource(rName);
    }

}
