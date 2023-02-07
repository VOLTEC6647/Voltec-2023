package com.andromedalib.shuffleboard;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleboardInteractions extends ShuffleboardTabBase {

    public static ShuffleboardInteractions instance;

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

}
