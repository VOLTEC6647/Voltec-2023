package com.team6647.utils.shuffleboard;

import com.team6647.commands.auto.AutonomousPaths;
import com.team6647.utils.Constants.ShuffleboardConstants;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Manages the Shuffleboard {@link SendableChooser} that allows rapid change
 * between autonomous modes
 */
public class AutoModeSelector {
    private enum AutoSelection {
        LeaveCommunity,
        BottomAutoCone,
        BottomAutoCube,
        MidAutoCone,
        MidAutoCube,
        TopAutoCone,
        TopAutoCube
    }

    private SendableChooser<AutoSelection> autoChooser = new SendableChooser<>();

    Command[] autoModes = {
            AutonomousPaths.leaveCommunity(),
            AutonomousPaths.bottomAutoCone(),
            AutonomousPaths.bottomAutoCube(),
            AutonomousPaths.midAutoCone(),
            AutonomousPaths.midAutoCube(),
            AutonomousPaths.topAutoCone(),
            AutonomousPaths.topAutoCube()
    };

    public AutoModeSelector() {
        autoChooser.addOption("Leave Communty", AutoSelection.LeaveCommunity);
        autoChooser.addOption("Bottom Auto Cone", AutoSelection.BottomAutoCone);
        autoChooser.addOption("Bottom Auto Cube", AutoSelection.BottomAutoCube);
        autoChooser.setDefaultOption("Mid Auto Cone", AutoSelection.MidAutoCone);
        autoChooser.addOption("Mid Auto Cube", AutoSelection.MidAutoCube);
        autoChooser.addOption("Top Auto Cube", AutoSelection.TopAutoCone);
        autoChooser.addOption("Top Auto Cube", AutoSelection.TopAutoCube);
        ShuffleboardConstants.kShuffleboardTab.add("Auto Mode", autoChooser).withPosition(6, 0);
    }

    public Command getAutoMode() {
        switch (autoChooser.getSelected()) {
            case LeaveCommunity:
                return autoModes[0];
            case BottomAutoCone:
                return autoModes[1];
            case BottomAutoCube:
                return autoModes[2];
            case MidAutoCone:
                return autoModes[3];
            case MidAutoCube:
                return autoModes[4];
            case TopAutoCone:
                return autoModes[5];
            case TopAutoCube:
                return autoModes[6];

        }
        return null;
    }
}
