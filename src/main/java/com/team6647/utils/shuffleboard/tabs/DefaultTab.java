package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.Constants.DriveConstants;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DefaultTab extends ShuffleboardTabBase {

    static ShuffleboardTab tab;

    GenericEntry angleKp;
    GenericEntry angleKi;
    GenericEntry angleKd;
    GenericEntry angleTolerance;

    public DefaultTab(ShuffleboardTab tab) {
        this.angleKp = tab.add("Angle Kp", DriveConstants.angleKp).getEntry();
        this.angleKi = tab.add("Angle Ki", DriveConstants.angleKi).getEntry();
        this.angleKd = tab.add("Angle Kd", DriveConstants.angleKd).getEntry();
    }

    @Override
    public void updateTelemetry() {
        DriveConstants.angleKp = angleKp.getDouble(0);
        DriveConstants.angleKi = angleKi.getDouble(0);
        DriveConstants.angleKd = angleKd.getDouble(0);

    }
}
