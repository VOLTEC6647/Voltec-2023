package com.team6647.utils;

import com.andromedalib.shuffleboard.ShuffleboardInteractions;
import com.team6647.Constants.ShuffleboardConstants;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class DriveModeSelector {
    private enum DriveMode {
        TANK,
        ARCADE,
        CURVATURE
    }

    private SendableChooser<DriveMode> driveModeChooser = new SendableChooser<>();

    public DriveModeSelector() {
        driveModeChooser.setDefaultOption("Tank", DriveMode.TANK);
        driveModeChooser.addOption("Arcade", DriveMode.ARCADE);
        driveModeChooser.addOption("Curvature", DriveMode.CURVATURE);
        ShuffleboardInteractions.getInstance(ShuffleboardConstants.kShuffleboardTab).addData("Drive Mode", driveModeChooser, 2, 2, 0, 0);
    }

    public DriveMode getDriveMode() {
        return driveModeChooser.getSelected();
    }
}
