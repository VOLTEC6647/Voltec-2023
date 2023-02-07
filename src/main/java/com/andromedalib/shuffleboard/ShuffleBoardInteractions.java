package com.andromedalib.shuffleboard;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardInteractions {

    public static ShuffleboardInteractions instance;

    public static String tabName;
    private static ShuffleboardTab tab;

    /**
     * Private Constructor
     */
    private ShuffleboardInteractions() {
    }

    /**
     * Initializes a new {@link ShuffleboardInteractions}
     * 
     * @param name Name of the ShuffleboardTab
     * @return {@link ShuffleboardInteractions} singleton instance
     */
    public static ShuffleboardInteractions getInstance(String name) {
        if (instance == null) {
            instance = new ShuffleboardInteractions();
            tabName = name;
            tab = Shuffleboard.getTab(tabName);
        }
        return instance;
    }

    /**
     * Initializes a new {@link ShuffleboardInteractions}
     * 
     * @param shuffleTab ShuffleboardTab to be used
     * @return {@link ShuffleboardInteractions} singleton instance
     */
    public static ShuffleboardInteractions getInstance(ShuffleboardTab shuffleTab) {
        if (instance == null) {
            instance = new ShuffleboardInteractions();
            tab = shuffleTab;
        }
        return instance;
    }

    /**
     * Returns the {@link ShuffleboardInteractions} instance
     * 
     * @return {@link ShuffleboardInteractions} singleton instance
     */
    public static ShuffleboardInteractions getInstance() {
        return instance;
    }

    /**
     * Adds data to the tab
     * 
     * @param name     Name of the data
     * @param sendable {@link Sendable} to be published to Shuffleboard
     */
    public void addData(String name, Sendable sendable) {
        tab.add(name, sendable);
    }

    /**
     * Adds double data to the tab
     * 
     * @param name       Name of the data
     * @param doubleData Double data to be published to Shuffleboard
     */
    public void addData(String name, double doubleData) {
        tab.add(name, doubleData);
    }

    /**
     * Adds double data to the tab with a selected width and height
     * 
     * @param name       Name of the data
     * @param doubleData Double data to be published to Shuffleboard
     * @param width      Width of the dat awidget
     * @param height     Height of the data widget
     */
    public void addData(String name, double doubleData, int width, int height) {
        tab.add(name, doubleData);
    }

    /**
     * Adds data to the tab with a selected width and height
     * 
     * @param name     Name of the data
     * @param sendable {@link Sendable} to be published to Shuffleboard
     * @param width    Width of the data widget
     * @param height   Height of the data widget
     */
    public void addData(String name, Sendable sendable, int width, int height) {
        tab.add(name, sendable).withSize(width, height);
    }

    /**
     * Adds data to the tab with a selected width, height and position
     * 
     * @param name     Name of the data
     * @param sendable {@link Sendable} to be published to Shuffleboard
     * @param width    Width of the data widget
     * @param height   Height of the data widget
     * @param x        X widget position
     * @param y        Y widget position
     */
    public void addData(String name, Sendable sendable, int width, int height, int x, int y) {
        tab.add(name, sendable).withSize(width, height).withPosition(x, y);
    }
}
