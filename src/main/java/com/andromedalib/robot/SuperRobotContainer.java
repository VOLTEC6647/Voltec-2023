package com.andromedalib.robot;

import com.team6647.utils.shuffleboard.ShuffleboardManager;

import edu.wpi.first.wpilibj2.command.Command;

public class SuperRobotContainer {
    private ShuffleboardManager interactions;


    public SuperRobotContainer() {
    }

    public void initSubsystems() {
    }

    public void initTelemetry() {
        interactions = ShuffleboardManager.getInstance();
    }

    public void updateTelemetry() {
        interactions.updateTelemetry();

    }

    public void configureBindings() {
    }

    public Command getAutonomousCommand() {
        return null;
    }

    public Command getTestCommand() {
        return null;
    }
}
