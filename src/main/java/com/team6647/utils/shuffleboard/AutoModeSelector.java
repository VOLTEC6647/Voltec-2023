package com.team6647.utils.shuffleboard;

import com.team6647.Constants.ShuffleboardConstants;
import com.team6647.commands.auto.AutonomousPaths;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Manages the Shuffleboard {@link SendableChooser} that allows rapid change
 * between autonomous modes
 */
public class AutoModeSelector {
    private enum AutoSelection {
        LeaveTop,
        LeaveMidTop,
        LeaveMidBottom,
        LeaveBottom,
        CubeAndCone,
        ConeAndCone,
    }

    private SendableChooser<AutoSelection> autoChooser = new SendableChooser<>();

    Command[] autoModes = {
            AutonomousPaths.leaveCommunity("Top"),
            AutonomousPaths.leaveCommunity("Mid Top"),
            AutonomousPaths.leaveCommunity("Mid Bottom"),
            AutonomousPaths.leaveCommunity("Bottom"),
            AutonomousPaths.dropCubeAndConeBottom(),
            AutonomousPaths.dropConeAndConeBottom()
    };

    public AutoModeSelector() {
        autoChooser.setDefaultOption("Leave Top", AutoSelection.LeaveTop);
        autoChooser.addOption("Leave Mid Top", AutoSelection.LeaveMidTop);
        autoChooser.addOption("Leave Mid Bottom", AutoSelection.LeaveMidBottom);
        autoChooser.addOption("Leave Bottom", AutoSelection.LeaveBottom);
        autoChooser.addOption("Cube and Cone", AutoSelection.CubeAndCone);
        autoChooser.addOption("Cone and Cone", AutoSelection.ConeAndCone);

        ShuffleboardConstants.kShuffleboardTab.add("Auto Mode", autoChooser).withPosition(6, 0);
    }

    public Command getAutoMode() {
        switch (autoChooser.getSelected()) {
            case LeaveTop:
                return autoModes[0];
            case LeaveMidTop:
                return autoModes[1];
            case LeaveMidBottom:
                return autoModes[2];
            case LeaveBottom:
                return autoModes[3];
            case CubeAndCone:
                return autoModes[4];
            case ConeAndCone:
                return autoModes[5];

        }
        return null;
    }
}
