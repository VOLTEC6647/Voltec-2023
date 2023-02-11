package com.team6647.utils;

import com.team6647.Constants.OperatorConstants;
import com.team6647.Constants.ShuffleboardConstants;
import com.team6647.commands.teleop.ArcadeDriveCommand;
import com.team6647.commands.teleop.CurvatureDriveCommand;
import com.team6647.commands.teleop.TankDriveCommand;
import com.team6647.subsystems.ChassisSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Manages the Shuffleboard {@link SendableChooser} that allows rapid change
 * between driving modes
 */
public class DriveModeSelector {
    private enum DriveMode {
        TANK,
        ARCADE,
        CURVATURE
    }

    private SendableChooser<DriveMode> driveModeChooser = new SendableChooser<>();

    Command[] driveModes = {
            new TankDriveCommand(ChassisSubsystem.getInstance(),
                    OperatorConstants.driverController1),
            new ArcadeDriveCommand(ChassisSubsystem.getInstance(),
                    OperatorConstants.driverController1),
            new CurvatureDriveCommand(ChassisSubsystem.getInstance(),
                    OperatorConstants.driverController1)
    };

    /**
     * Inits the {@link DriveModeSelector}
     */
    public DriveModeSelector() {
        driveModeChooser.setDefaultOption("Tank", DriveMode.TANK);
        driveModeChooser.addOption("Arcade", DriveMode.ARCADE);
        driveModeChooser.addOption("Curvature", DriveMode.CURVATURE);
        ShuffleboardConstants.kShuffleboardTab.add("Drive Mode", driveModeChooser).withPosition(5, 0);
    }

    /**
     * Gets the selected driving mode
     * 
     * @return Selected driving mode
     */
    public Command getDriveMode() {
        switch (driveModeChooser.getSelected()) {
            case TANK:
                return driveModes[0];
            case ARCADE:
                return driveModes[1];
            case CURVATURE:
                return driveModes[2];
            default:
                return driveModes[0];
        }

    }
}
