//package com.madsen.model;

/**
 * Data model of a single resource.
 */
public class Resource {

    /** Name of this resource */
    private String name;

    /** Current owner of this resource */
    private Process owner;

    /**
     * Constructs a resource with the given name.
     *
     * @param name Name of this resource.
     */
    public Resource(String name) {
        // Set the name of this resource
        this.name = name;

        // No starting owner of this resource
        this.owner = null;
    }

    /**
     * Returns the name of this resource.
     *
     * @return The name of this resource.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the current owner of this resource.
     *
     * @return The current owner of this resource.
     */
    public Process getOwner() {
        return this.owner;
    }

    /**
     * Sets the current owner of this resource.
     *
     * @return The current owner of this resource.
     */
    public void setOwner(Process p) {
        this.owner = p;
    }

    /**
     * Sets this resource as free.
     */
    public void setFree() {
        this.owner = null;
    }

}
