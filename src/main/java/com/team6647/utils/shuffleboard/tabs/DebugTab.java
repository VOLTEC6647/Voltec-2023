package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.Constants.ArmConstants;
import com.team6647.Constants.DriveConstants;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DebugTab extends ShuffleboardTabBase {

    static ShuffleboardTab tab;

    GenericEntry angleKp;
    GenericEntry angleKi;
    GenericEntry angleKd;
    GenericEntry angleTolerance;

    GenericEntry armSpeed;

    public DebugTab(ShuffleboardTab tab) {
        this.angleKp = tab.add("Angle Kp", DriveConstants.angleKp).withPosition(3, 1).getEntry();
        this.angleKi = tab.add("Angle Ki", DriveConstants.angleKi).withPosition(4, 1).getEntry();
        this.angleKd = tab.add("Angle Kd", DriveConstants.angleKd).withPosition(5, 1).getEntry();
        this.armSpeed = tab.add("Arm Speed", ArmConstants.armSpeed).withPosition(6, 1).getEntry();
    }

    @Override
    public void updateTelemetry() {
        DriveConstants.angleKp = angleKp.getDouble(0);
        DriveConstants.angleKi = angleKi.getDouble(0);
        DriveConstants.angleKd = angleKd.getDouble(0);

        ArmConstants.armSpeed = armSpeed.getDouble(0);

    }
}
