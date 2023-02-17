package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.Constants.ArmConstants;
import com.team6647.Constants.DriveConstants;
import com.team6647.Constants.OperatorConstants;
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

    GenericEntry rampTimeSeconds;

    public DebugTab(ShuffleboardTab tab) {
        this.angleKp = tab.add("Angle Kp", DriveConstants.angleKp).withPosition(3, 1).getEntry();
        this.angleKi = tab.add("Angle Ki", DriveConstants.angleKi).withPosition(4, 1).getEntry();
        this.angleKd = tab.add("Angle Kd", DriveConstants.angleKd).withPosition(5, 1).getEntry();
        this.armSpeed = tab.add("Arm Speed", ArmConstants.armSpeed).withPosition(6, 1).getEntry();
        this.pivot1Speed = tab.add("Pivot 1 Speed", ArmSubsystem.getInstance().getPivot1Velocity()).withPosition(7, 1).getEntry();
        this.pivot2Speed = tab.add("Pivot 2 Speed", ArmSubsystem.getInstance().getPivot2Velocity()).withPosition(8, 1).getEntry();
        this.rampTimeSeconds = tab.add("Ramp Time Seconds", OperatorConstants.rampTimeSeconds).withPosition(9, 1).getEntry();
    }

    @Override
    public void updateTelemetry() {
        DriveConstants.angleKp = angleKp.getDouble(DriveConstants.angleKp);
        DriveConstants.angleKi = angleKi.getDouble(DriveConstants.angleKi);
        DriveConstants.angleKd = angleKd.getDouble(  DriveConstants.angleKd);

        ArmConstants.armSpeed = armSpeed.getDouble(ArmConstants.armSpeed);

        pivot1Speed.setDouble(ArmSubsystem.getInstance().getPivot1Velocity());
        pivot2Speed.setDouble(ArmSubsystem.getInstance().getPivot2Velocity());

        OperatorConstants.rampTimeSeconds = rampTimeSeconds.getDouble(OperatorConstants.rampTimeSeconds);
    }
}
