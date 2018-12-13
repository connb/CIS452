//package com.madsen.view;

//import com.madsen.model.Process;

import javax.swing.*;
import java.awt.*;

/**
 * Class responsible for displaying the processes and resources of a single
 * simulation.
 */
public class SimulationPanel extends JPanel {

    /** Processes display */
    ProcessesPanel processesPanel;

    /** Resources display */
    ResourcesPanel resourcesPanel;

    /**
     * Constructs a panel displaying processes and resources of a simulation.
     *
     * @param processes Number of processes in the simulation.
     * @param resources Number of resources in the simulation.
     */
    public SimulationPanel(int processes, int resources) {
        super();

        // Ensure that processes and resources are valid
        processes = processes > 0 ? processes : 0;
        resources = resources > 0 ? resources : 0;

        // Set the color of this panel
        this.setBackground(new Color(150,150,150));

        // Set size of this panel
        this.setPreferredSize(new Dimension(800, 600));

        // Set the layout of this panel
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Add a panel to display processes
        processesPanel = new ProcessesPanel(processes);
        this.add(processesPanel);

        // Add a panel to display resources
        resourcesPanel = new ResourcesPanel(resources);
        this.add(resourcesPanel);
    }

    /**
     * Gets the process with the name pName.
     *
     * @param pName Name of process to get.
     * @return Null or process with the specified name.
     */
    private ProcessPanel getProcess(String pName) {
        for (ProcessPanel p: this.processesPanel.getProcesses()) {
            if (p.getName().equals(pName)) {
                return p;
            }
        }

        // Process not found
        return null;
    }

    /**
     * Gets the resourcewith the name rName.
     *
     * @param rName Name of resource to get.
     * @return Null or resource with the specified name.
     */
    private ResourcePanel getResource(String rName) {
        for (ResourcePanel r: this.resourcesPanel.getResources()) {
            if (r.getName().equals(rName)) {
                return r;
            }
        }

        // Resource not found
        return null;
    }

    /**
     * Sets the display of the specified process to the blocked state.
     *
     * @param pName Name of process to mark as blocked.
     */
    public void setBlocked(String pName) {
        ProcessPanel p = getProcess(pName);
        p.setBlocked();
    }

    /**
     * Sets the display of the specified process to the running state.
     *
     * @param pName Name of process to mark as running.
     */
    public void setRunning(String pName) {
        ProcessPanel p = getProcess(pName);
        p.setRunning();
    }

    /**
     * Allocates resource rName to process pName.
     *
     * @param pName Name of the process to receive resource rName.
     * @param rName Name of the resource being allocated.
     */
    public void allocateResource(String pName, String rName) {
        // Get the process
        ProcessPanel p = getProcess(pName);

        // Get the resource
        ResourcePanel r = getResource(rName);

        // Allocate the resource
        this.processesPanel.allocateResource(p, new ResourcePanel(r));
        this.resourcesPanel.allocateResource(r);
    }

    /**
     * Frees resource rName from process pName.
     *
     * @param pName Name of process to free resource from.
     * @param rName Name of resource to free.
     */
    public void freeResource(String pName, String rName) {
        // Get the process
        ProcessPanel p = getProcess(pName);

        // Get the resource
        ResourcePanel r = getResource(rName);

        // Free the resource
        this.processesPanel.freeResource(p, rName);
        this.resourcesPanel.freeResource(r);
    }

}
