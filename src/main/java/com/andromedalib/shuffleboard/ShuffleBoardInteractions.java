package com.andromedalib.shuffleboard;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class ShuffleBoardInteractions {

    public static ShuffleBoardInteractions instance;

    public static String tabName;
    private static ShuffleboardTab tab;

    private ShuffleBoardInteractions() {
    }

    public static ShuffleBoardInteractions getInstance(String name) {
        if (instance == null) {
            instance = new ShuffleBoardInteractions();
            tabName = name;
            tab = Shuffleboard.getTab(tabName);
        }
        return instance;
    }

    public static ShuffleBoardInteractions getInstance(ShuffleboardTab shuffleTab) {
        if (instance == null) {
            instance = new ShuffleBoardInteractions();
            tab = shuffleTab;
        }
        return instance;
    }


    public void addTab(String name, Sendable sendable) {
        tab.add(name, sendable);
    }

    public void addTab(String name, Sendable sendable, int width, int height) {
        tab.add(name, sendable).withSize(width, height);
    }

    public void addTab(String name, Sendable sendable, int width, int height, int x, int y) {
        tab.add(name, sendable).withSize(width, height).withPosition(x, y);
    }
}
