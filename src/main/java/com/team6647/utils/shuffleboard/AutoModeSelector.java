package com.team6647.utils.shuffleboard;

import com.team6647.utils.Constants.ShuffleboardConstants;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


/**
 * Manages the Shuffleboard {@link SendableChooser} that allows rapid change
 * between autonomous modes
 */
public class AutoModeSelector {
    public enum AutoSelection {
        LeaveCommunity,
        BottomAutoCone,
        BottomAutoCube,
        MidAutoCone,
        MidAutoCube,
        TopAutoCone,
        TopAutoCube,
        EmergencyAutoCone,
        EmergencyAutoCube,
        DoNothing
    }

    private static SendableChooser<AutoSelection> autoChooser = new SendableChooser<>();

    public AutoModeSelector() {

        autoChooser.addOption("Leave Communty", AutoSelection.LeaveCommunity);
        autoChooser.addOption("Bottom Auto Cone", AutoSelection.BottomAutoCone);
        autoChooser.addOption("Bottom Auto Cube", AutoSelection.BottomAutoCube);
        autoChooser.setDefaultOption("Mid Auto Cone", AutoSelection.MidAutoCone);
        autoChooser.addOption("Mid Auto Cube", AutoSelection.MidAutoCube);
        autoChooser.addOption("Top Auto Cone", AutoSelection.TopAutoCone);
        autoChooser.addOption("Top Auto Cube", AutoSelection.TopAutoCube);
        autoChooser.addOption("Emergency Cone", AutoSelection.EmergencyAutoCone);
        autoChooser.addOption("Emergency Cube", AutoSelection.EmergencyAutoCube);
        autoChooser.addOption("Do nothing", AutoSelection.DoNothing);
        

        ShuffleboardConstants.kShuffleboardTab.add("Auto Mode", autoChooser).withPosition(6, 0);
    }

    public AutoSelection getAutoMode() {
        return autoChooser.getSelected();
    }
}
