//package com.madsen.model;

import java.util.ArrayList;

/**
 * Data model of a single process.
 */
public class Process {

    /** Various states that a process can be in */
    private enum State {
        BLOCKED, DEADLOCKED, RUNNING
    }

    /** State of this processes execution */
    private State state;

    /** Name of this process */
    private String name;

    /** Resources currently held by this process */
    private ArrayList<Resource> resources;

    /** Requested resource of this process */
    private Resource requested;

    /**
     * Constructs a process with the given name.
     *
     * @param name Name of this process.
     */
    public Process(String name) {
        // Set the name of this process
        this.name = name;

        // Create an empty list of resources
        this.resources = new ArrayList<>();

        // No requested resource to start
        this.requested = null;

        // Processes begin in running state
        setRunning();
    }

    /**
     * Returns the name of this process.
     *
     * @return The name of this process.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the current state of this process to blocked.
     */
    public void setBlocked() {
        this.state = State.BLOCKED;
    }

    /**
     * Returns whether this process is blocked.
     *
     * @return Whether this process is blocked.
     */
    public boolean isBlocked() {
        return this.state == State.BLOCKED;
    }

    /**
     * Sets the current state of this process to deadlocked.
     */
    public void setDeadlocked() {
        this.state = State.DEADLOCKED;
    }

    /**
     * Returns whether this process is deadlocked.
     *
     * @return Whether this process is deadlocked.
     */
    public boolean isDeadlocked() {
        return this.state == State.DEADLOCKED;
    }

    /**
     * Sets the current state of this process to running.
     */
    public void setRunning() {
        this.state = State.RUNNING;
    }

    /**
     * Returns whether this process is running.
     *
     * @return Whether this process is running.
     */
    public boolean isRunning() {
        return this.state == State.RUNNING;
    }

    /**
     * Returns the resource currently requested by this process.
     *
     * @return Resource currently requested.
     */
    public Resource getRequested() {
        return this.requested;
    }

    /**
     * Sets the requested resource of this process.
     *
     * @param r Resource requested by this process.
     */
    public void setRequested(Resource r) {
        this.requested = r;
    }

    /**
     * Adds specified resource to the resources held by this process.
     *
     * @param r Resource to add.
     */
    public void addResource(Resource r) {
        this.resources.add(r);
    }

    /**
     * Remove the specified resource from this process.
     *
     * @param r Resource to remove.
     */
    public void releaseResource(Resource r) {
        this.resources.remove(r);
    }

}
