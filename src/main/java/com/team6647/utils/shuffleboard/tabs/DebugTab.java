package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.Constants.ArmConstants;
import com.team6647.Constants.DriveConstants;
import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DebugTab extends ShuffleboardTabBase {

    static ShuffleboardTab tab;

    GenericEntry angleKp;
    GenericEntry angleKi;
    GenericEntry angleKd;
    GenericEntry angleTolerance;

    GenericEntry armSpeed;
    GenericEntry pivot1Speed;
    GenericEntry pivot2Speed;

    public DebugTab(ShuffleboardTab tab) {
        this.angleKp = tab.add("Angle Kp", DriveConstants.angleKp).withPosition(3, 1).getEntry();
        this.angleKi = tab.add("Angle Ki", DriveConstants.angleKi).withPosition(4, 1).getEntry();
        this.angleKd = tab.add("Angle Kd", DriveConstants.angleKd).withPosition(5, 1).getEntry();
        this.armSpeed = tab.add("Arm Speed", ArmConstants.armSpeed).withPosition(6, 1).getEntry();
        this.pivot1Speed = tab.add("Pivot 1 Speed", ArmSubsystem.getInstance().getPivot1Velocity()).withPosition(7, 1).getEntry();
        this.pivot2Speed = tab.add("Pivot 2 Speed", ArmSubsystem.getInstance().getPivot2Velocity()).withPosition(8, 1).getEntry();
    }

    @Override
    public void updateTelemetry() {
        DriveConstants.angleKp = angleKp.getDouble(0);
        DriveConstants.angleKi = angleKi.getDouble(0);
        DriveConstants.angleKd = angleKd.getDouble(0);

        ArmConstants.armSpeed = armSpeed.getDouble(0);

        pivot1Speed.setDouble(ArmSubsystem.getInstance().getPivot1Velocity());
        pivot2Speed.setDouble(ArmSubsystem.getInstance().getPivot2Velocity());
    }
}
