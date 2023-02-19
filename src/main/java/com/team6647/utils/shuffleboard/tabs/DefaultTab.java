package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.subsystems.DriveSubsystem;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DefaultTab extends ShuffleboardTabBase {

    GenericEntry field;

    public DefaultTab(ShuffleboardTab tab) {
        field = tab.add("Field position", DriveSubsystem.getInstance().getPose()).withWidget(BuiltInWidgets.kField)
                .withPosition(0, 2).getEntry();
    }

    @Override
    public void updateTelemetry() {}
}
