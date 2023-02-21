/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DebugTab extends ShuffleboardTabBase {

    static ShuffleboardTab tab;

    GenericEntry pivot1Speed;
    GenericEntry pivot2Speed;

    GenericEntry armPositionGraph;
    GenericEntry armPositionDoubleRaw;
    GenericEntry armPositionDoublePID;
    GenericEntry desiredPosition;
    GenericEntry voltageApplied;
    GenericEntry feedOutput;
    GenericEntry pidOutput;
    
    public DebugTab(ShuffleboardTab tab) {

        this.pivot1Speed = tab.add("Pivot 1 Speed", ArmSubsystem.getInstance().getPivot1Velocity()).withPosition(3, 1).getEntry();
        this.pivot2Speed = tab.add("Pivot 2 Speed", ArmSubsystem.getInstance().getPivot2Velocity()).withPosition(4, 1).getEntry();

        this.armPositionGraph = tab.add("Arm Position", ArmSubsystem.getInstance().getMeasurement()).withPosition(4, 2).withWidget(BuiltInWidgets.kGraph).getEntry();
        this.armPositionDoubleRaw = tab.add("Arm Position RAW", ArmSubsystem.getInstance().getPivo1Position()).withPosition(7, 2).getEntry();
        this.armPositionDoublePID  = tab.add("Arm Position PID", ArmSubsystem.getInstance().getMeasurement()).withPosition(7,3).getEntry();
        this.desiredPosition = tab.add("Desired Position", ArmSubsystem.getInstance().getController().getGoal().position).withPosition(5, 1).getEntry();
        this.voltageApplied = tab.add("Voltage Applied", ArmSubsystem.getInstance().getPivot1Voltage()).withWidget(BuiltInWidgets.kVoltageView).withPosition(6, 1).getEntry();
        this.feedOutput = tab.add("Feed output", ArmSubsystem.getInstance().getFeedOutput()).withPosition(8, 1).getEntry();
        this.pidOutput = tab.add("Arm Output", ArmSubsystem.getInstance().getPidOutput()).withPosition(9, 1).getEntry();

    }

    @Override
    public void updateTelemetry() {

        pivot1Speed.setDouble(ArmSubsystem.getInstance().getPivot1Velocity());
        pivot2Speed.setDouble(ArmSubsystem.getInstance().getPivot2Velocity());

        armPositionGraph.setDouble(ArmSubsystem.getInstance().getMeasurement());
        desiredPosition.setString(ArmSubsystem.getInstance().getController().getGoal().toString());
        voltageApplied.setDouble(ArmSubsystem.getInstance().getPivot1Voltage());
        feedOutput.setDouble(ArmSubsystem.getInstance().getFeedOutput());
        pidOutput.setDouble(ArmSubsystem.getInstance().getPidOutput());
    }
}
