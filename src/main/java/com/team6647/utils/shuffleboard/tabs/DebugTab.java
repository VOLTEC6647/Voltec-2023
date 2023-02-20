/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.Constants.ArmConstants;
import com.team6647.Constants.OperatorConstants;
import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DebugTab extends ShuffleboardTabBase {

    static ShuffleboardTab tab;

    GenericEntry extendSpeed;
    GenericEntry pivot1Speed;
    GenericEntry pivot2Speed;

    GenericEntry rampTimeSeconds;

    GenericEntry armPosition;
    GenericEntry armOutput;
    
    public DebugTab(ShuffleboardTab tab) {

        this.extendSpeed = tab.add("Extend Arm speed", ArmConstants.extendSped).withPosition(3, 1).getEntry();
        this.pivot1Speed = tab.add("Pivot 1 Speed", ArmSubsystem.getInstance().getPivot1Velocity()).withPosition(4, 1).getEntry();
        this.pivot2Speed = tab.add("Pivot 2 Speed", ArmSubsystem.getInstance().getPivot2Velocity()).withPosition(5, 1).getEntry();
        this.rampTimeSeconds = tab.add("Ramp Time", OperatorConstants.rampTimeSeconds).withPosition(6, 1).getEntry();
        this.armPosition = tab.add("Arm Position", ArmSubsystem.getInstance().getMeasurement()).withPosition(7, 1).withWidget(BuiltInWidgets.kGraph).getEntry();
        this.armOutput = tab.add("Arm Output", ArmSubsystem.getInstance().getMeasurement()).withPosition(10, 1).getEntry();

    }

    @Override
    public void updateTelemetry() {
        ArmConstants.extendSped = extendSpeed.getDouble(0);

        pivot1Speed.setDouble(ArmSubsystem.getInstance().getPivot1Velocity());
        pivot2Speed.setDouble(ArmSubsystem.getInstance().getPivot2Velocity());

        OperatorConstants.rampTimeSeconds = rampTimeSeconds.getDouble(0);

        armPosition.setDouble(ArmSubsystem.getInstance().getMeasurement());
        armOutput.setDouble(ArmSubsystem.getInstance().getOutput());
    }
}
