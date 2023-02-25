/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.VisionSubsystem;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DebugTab extends ShuffleboardTabBase {

    ArmSubsystem arm = ArmSubsystem.getInstance();
    ChassisSubsystem chassis = ChassisSubsystem.getInstance();
    ClawSubsytem claw = ClawSubsytem.getInstance();
    DriveSubsystem drive = DriveSubsystem.getInstance();
    VisionSubsystem vision = VisionSubsystem.getInstance("Photon");

    static ShuffleboardTab tab;

    GenericEntry limit; 

    GenericEntry pivot1Speed;
    GenericEntry pivot2Speed;

    GenericEntry extendingPosition;

    GenericEntry armPositionGraph;
    GenericEntry armPositionDoublePID;
    GenericEntry desiredPosition;
    GenericEntry voltageApplied;
    GenericEntry feedOutput;
    GenericEntry pidOutput;
    GenericEntry totalOutput;

    GenericEntry limeAim;
    GenericEntry photonAim;

    GenericEntry limePipe;
    GenericEntry photonPipe;

    GenericEntry pivot1Temp;
    GenericEntry pivot2Temp;
    
    public DebugTab(ShuffleboardTab tab) {

        this.pivot1Speed = tab.add("Pivot 1 Speed", arm.getPivot1Velocity()).withPosition(3, 1).getEntry();
        this.pivot2Speed = tab.add("Pivot 2 Speed", arm.getPivot2Velocity()).withPosition(4, 1).getEntry();

        this.extendingPosition = tab.add("Extend postion", arm.getExtendPosition()).withPosition(6, 2).getEntry();

        this.armPositionGraph = tab.add("Arm Position Graph", arm.getMeasurement()).withPosition(3, 2).withWidget(BuiltInWidgets.kGraph).getEntry();
        this.armPositionDoublePID  = tab.add("Arm Position", arm.getMeasurement()).withPosition(6,3).getEntry();
        this.desiredPosition = tab.add("Desired Position", arm.getSetpoint()).withPosition(5, 1).getEntry();
        this.voltageApplied = tab.add("Voltage Applied", arm.getPivot1Voltage()).withPosition(6, 0).getEntry();
        this.feedOutput = tab.add("Feed output", arm.getFeedOutput()).withPosition(7, 1).getEntry();
        this.pidOutput = tab.add("Arm Output", arm.getPidOutput()).withPosition(8, 1).getEntry();
        this.totalOutput = tab.add("Total Output", arm.getTotal()).withPosition(6, 1).getEntry();

        this.limit = tab.add("Limit switch", arm.getLimitState()).withPosition(0, 2).getEntry();

        /* this.limeAim = tab.add("Limelight aim", vision.getLimelightAim()).withPosition(1, 2).getEntry();
        this.photonAim = tab.add("Photon aim", vision.getPhotonAim()).withPosition(2, 2).getEntry(); */
        this.limePipe = tab.add("Limelight pipe", vision.getLimePipe()).withPosition(1, 3).getEntry();
        this.photonPipe = tab.add("Photon pipe", vision.getPhotonPipe()).withPosition(2, 3).getEntry();
     
        this.pivot1Temp = tab.add("Pivot1 Temp", arm.getPivot1Temp()).withPosition(7, 0).getEntry();
        this.pivot2Temp = tab.add("Pivot2 Temp", arm.getPivot2Temp()).withPosition(8, 0).getEntry();

    }

    @Override
    public void updateTelemetry() {

        pivot1Speed.setDouble(arm.getPivot1Velocity());
        pivot2Speed.setDouble(arm.getPivot2Velocity());

        extendingPosition.setDouble(arm.getExtendPosition());

        armPositionDoublePID.setDouble(arm.getMeasurement());
        desiredPosition.setDouble(arm.getSetpoint());
        voltageApplied.setDouble(arm.getPivot1Voltage());
        feedOutput.setDouble(arm.getFeedOutput());
        pidOutput.setDouble(arm.getPidOutput());
        totalOutput.setDouble(arm.getTotal());

        limit.setBoolean(arm.getLimitState());

        /* limeAim.setBoolean(vision.getLimelightAim());
        photonAim.setBoolean(vision.getPhotonAim()); */

        limePipe.setDouble(vision.getLimePipe());
        photonPipe.setDouble(vision.getPhotonPipe());

        pivot1Temp.setDouble(arm.getPivot1Temp());
        pivot2Temp.setDouble(arm.getPivot2Temp());

     }
}
