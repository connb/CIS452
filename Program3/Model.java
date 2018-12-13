//package com.madsen.model;

import java.util.ArrayList;

/**
 * Manages the data model of the Resource Manager.
 */
public class Model {

    /** Processes of the simulation */
    private ArrayList<Process> processes;

    /** Resources of the simulation */
    private ArrayList<Resource> resources;

    /**
     * Constructs a model to manage simulation data.
     *
     * @param numProcesses Number of processes in the simulation.
     * @param numResources Number of resources in the simulation.
     */
    public Model(int numProcesses, int numResources) {
        // Create all processes in the simulation
        this.processes = new ArrayList<>(numProcesses);
        for (int i = 0; i < numProcesses; i++) {
            this.processes.add(new Process("p" + i));
        }

        // Create all resources in the simulation
        this.resources = new ArrayList<>(numResources);
        for (int i = 0; i < numResources; i++) {
            this.resources.add(new Resource("r" + i));
        }
    }

    /**
     * Returns all process of the model.
     *
     * @return All processes of the model.
     */
    public ArrayList<Process> getProcesses() {
        return this.processes;
    }

    /**
     * Returns all resources of the model.
     *
     * @return All resources of the model.
     */
    public ArrayList<Resource> getResources() {
        return this.resources;
    }

    /**
     * Gets the process with the name pName.
     *
     * @param pName Name of the process to get.
     * @return Null or the process with the specified name.
     */
    public Process getProcess(String pName) {
        for (Process p: this.processes) {
            if (p.getName().equals(pName)) {
                return p;
            }
        }

        // Process not found
        return null;
    }

    /**
     * Gets the resource with the name rName.
     *
     * @param rName Name of the resource to get.
     * @return Null or the resource with the specified name.
     */
    public Resource getResource(String rName) {
        for (Resource r: this.resources) {
            if (r.getName().equals(rName)) {
                return r;
            }
        }

        // Resource not found
        return null;
    }

    /**
     * Sets the requested resource rName of process pName.
     *
     * @param pName Name of the requesting process.
     * @param rName Name of the requested resource.
     */
    public void requestResource(String pName, String rName) {
        // Get the requesting process
        Process p = getProcess(pName);

        // Get the requested resource
        Resource r = getResource(rName);

        // Set the requested resource of process p to resource r
        if (p != null && r != null) {
            p.setRequested(r);
        }
    }

    /**
     * Releases resource rName from process pName.
     *
     * @param pName Name of the releasing process.
     * @param rName Name of the released resource.
     */
    public void releaseResource(String pName, String rName) {
        // Get the requesting process
        Process p = getProcess(pName);

        // Get the requested resource
        Resource r = getResource(rName);

        // Remove the resource r from process p
        if (p != null && r != null) {
            //FIXME
            p.releaseResource(r);
            r.setFree();
        }
    }

}
