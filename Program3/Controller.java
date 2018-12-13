
//import java.Model;
//import java.Process;
//import java.Resource;
//import java.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Controls the operation of Resource Manager.
 */
public class Controller implements MenuListener {

    /** Default delay time between commands in seconds */
    private static final int DELAY = 3;

    /** Name of the program to display */
    private final String PROGRAM_NAME = "Resource Manager";

    /** Model containing the data of Resource Manager. */
    private Model model;

    /** View handling the display of Resource Manager. */
    private View view;

    /** Number of processes in the current simulation */
    private int processes;

    /** Number of resources in the current simulation */
    private int resources;

    /** List of commands process/resource commands to operate */
    private LinkedList<String> commands;

    /**
     * Constructs all necessary pieces of Resource Manager.
     */
    public Controller() {
        // Wait for view to be initialized
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    view = new View(PROGRAM_NAME);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Listen for GUI actions
        for (int i = 0; i < this.view.getJMenuBar().getMenuCount(); i++) {
            this.view.getJMenuBar().getMenu(i).addMenuListener(this);
        }

        // Get new simulation
        newSimulation();
    }

    /**
     * Parses the specified file for simulation parameters and commands.
     *
     * @param file File to parse for simulation parameters and commands.
     */
    private void parseFile(File file) {
        // Clear old simulation parameters and commands
        this.processes = 0;
        this.resources = 0;
        this.commands = new LinkedList<>();

        // Parse file for simulation parameters and commands
        try {
            Scanner scanner = new Scanner(file);
            this.processes = scanner.nextInt();
            scanner.nextLine();
            this.resources = scanner.nextInt();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                this.commands.addLast(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the simulation by executing all parsed commands.
     */
    private void runSimulation() {
        runSimulation(Controller.DELAY);
    }

    /**
     * Runs the simulation by executing all parsed commands with given delay
     * between each command.
     *
     * @param delay Duration in second to wait between commands.
     */
    private void runSimulation(int delay) {
        try {
            // Pause before starting commands
            Thread.sleep(1000);

            // Execute all commands
            boolean running = true;
            int i;
            String c = null;
            Scanner scanner;
            String pName = null;
            String action = null;
            String rName = null;
            Process p;
            Resource r;
            while (true) {
                // Select next operable command
                i = 0;
                while (true) {
                    if (i >= this.commands.size()) {
                        running = false;
                        break;
                    }
                    c = this.commands.get(i);

                    // Check that next command is operable
                    scanner = new Scanner(c);
                    pName = scanner.next();
                    action = scanner.next();
                    rName = scanner.next();
                    p = this.model.getProcess(pName);
                    if (!p.isBlocked() && action.equals("requests")) {
                        // Valid requests command, operate on it
                        this.commands.remove(i);
                        break;
                    }
                    else if (!p.isBlocked() && action.equals("releases")){
                        // Valid releases command, operate on it
                        this.commands.remove(i);
                        break;
                    }

                    i++;
                }

                // Simulation finished
                if (!running && i == 0) {
                    System.out.println("All commands finished");
                    break;
                }
                else if (!running && i > 0) {
                    System.out.println("System deadlocked");
                    break;
                }

                // Print out command being executed
                System.out.println(c);

                // Resolve action of command
                switch (action) {
                    case "requests":
                        actionRequests(pName,rName);
                        break;
                    case "releases":
                        actionReleases(pName,rName);
                        break;
                }

                // Resolve requests
                resolveRequests();

                // Pause between each command
                Thread.sleep(delay * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the specified process to the blocked state.
     *
     * @param p Process to set in blocked state.
     */
    private void setBlocked(Process p) {
        p.setBlocked();
        this.view.setBlocked(p.getName());
    }

    /**
     * Sets the specified process to the running state.
     *
     * @param p Process to set in running state.
     */
    private void setRunning(Process p) {
        p.setRunning();
        this.view.setRunning(p.getName());
    }

    /**
     * Process pName requests resource rName.
     *
     * @param pName Requesting process.
     * @param rName Requested resource.
     */
    private void actionRequests(String pName, String rName) {
        this.model.requestResource(pName,rName);
    }

    /**
     * Process rName releases resource rName.
     *
     * @param pName Releasing process.
     * @param rName Released resource.
     */
    private void actionReleases(String pName, String rName) {

        this.model.releaseResource(pName,rName);
        this.view.freeResource(pName, rName);
    }

    /**
     * Resolves pending resource requests by either allocating a resource or
     * blocking a process.
     */
    private void resolveRequests() {
        // Temporary resource holder
        Resource r;

        // Check each process
        for (Process p: this.model.getProcesses()) {
            // Only processes requesting resources are of concern
            r = p.getRequested();
            if (r != null) {
                //TODO implement deadlock avoidance here


                // Give the resource to the requesting program if it is free
                if (r.getOwner() == null) {
                    setRunning(p);
                    allocateResource(p, r);
                }
                else {
                    setBlocked(p);
                }
            }
        }
    }

    /**
     * Adds resource r to process p.
     *
     * @param p Process receiving resource.
     * @param r Resource being given.
     */
    private void allocateResource(Process p, Resource r) {
        System.out.println("Allocating " + p.getName() + " to " + r.getName());
        p.addResource(r);
        p.setRequested(null);
        r.setOwner(p);
        view.allocateResource(p.getName(), r.getName());
    }

    /**
     * Prompts the user to select a simulation to run and runs selected valid
     * simulation.
     */
    private void newSimulation() {
        // Get a file from the user
        File file = this.view.getInputFile();

        // Parse file, create and run new simulation
        if (file != null) {
            parseFile(file);
            this.view.createSimulation(this.processes,this.resources);
            this.model = new Model(this.processes, this.resources);
            runSimulation();
        }
    }

    /**
     * Invoked when a menu is selected.
     *
     * @param e a MenuEvent object
     */
    @Override
    public void menuSelected(MenuEvent e) {
        if (e.getSource().equals(this.view.getJMenuBar().getMenu(0))) {
            newSimulation();
        }
        else {
            System.exit(0);
        }
    }

    /**
     * Invoked when the menu is deselected.
     *
     * @param e a MenuEvent object
     */
    @Override
    public void menuDeselected(MenuEvent e) {

    }

    /**
     * Invoked when the menu is canceled.
     *
     * @param e a MenuEvent object
     */
    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
