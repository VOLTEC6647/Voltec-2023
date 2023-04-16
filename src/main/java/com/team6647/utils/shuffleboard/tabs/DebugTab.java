/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.utils.shuffleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;
import com.team6647.robot.RobotContainer;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.VisionSubsystem;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DebugTab extends ShuffleboardTabBase {

    ArmSubsystem arm = ArmSubsystem.getInstance();
    ChassisSubsystem chassis = ChassisSubsystem.getInstance();
    ClawSubsytem claw = ClawSubsytem.getInstance();
    DriveSubsystem drive = DriveSubsystem.getInstance();
    VisionSubsystem vision = VisionSubsystem.getInstance();
    RobotContainer container = RobotContainer.getInstance();

    GenericEntry armLimit;

    GenericEntry extendingPosition;

    GenericEntry armPositionDoublePID;
    GenericEntry desiredPosition;
    GenericEntry feedOutput;
    GenericEntry pidOutput;
    GenericEntry totalOutput;

    GenericEntry limeAim;

    GenericEntry pivot1Temp;
    GenericEntry pivot2Temp;

    GenericEntry chassisAngle;

    GenericEntry clawTopLimit;
    GenericEntry clawBottomLimit;

    GenericEntry clawPosition;

    public DebugTab(ShuffleboardTab tab) {

        this.extendingPosition = tab.add("Extend postion", arm.getExtendPosition()).withPosition(5, 1).getEntry();

        this.armPositionDoublePID = tab.add("Arm Position", ArmSubsystem.getArmPosition()).withPosition(3, 1)
                .getEntry();
        this.desiredPosition = tab.add("Desired Position", arm.getSetpoint()).withPosition(4, 1).getEntry();
        this.pidOutput = tab.add("Arm Output", arm.getPidOutput()).withPosition(5, 2).getEntry();
        this.feedOutput = tab.add("Feed output", arm.getFeedOutput()).withPosition(4, 2).getEntry();
        this.totalOutput = tab.add("Total Output", arm.getTotal()).withPosition(3, 2).getEntry();

        this.armLimit = tab.add("Limit switch", arm.getLimitState()).withPosition(3, 3).getEntry();

        this.limeAim = tab.add("Limelight aim", vision.getLimelightAim()).withPosition(4, 3).getEntry();

        this.pivot1Temp = tab.add("Pivot1 Temp", arm.getPivot1Temp()).withPosition(6, 3).getEntry();
        this.pivot2Temp = tab.add("Pivot2 Temp", arm.getPivot2Temp()).withPosition(7, 3).getEntry();
        this.chassisAngle = tab.add("Chassis Angle", drive.getNavxRoll()).withPosition(6, 2).getEntry();

        this.clawTopLimit = tab.add("Claw Top Limit", claw.topLimit()).withPosition(8, 1).getEntry();
        this.clawBottomLimit = tab.add("Claw Bottom Limit", claw.bottomLimit()).withPosition(9, 1).getEntry();
        this.clawPosition = tab.add("Claw Position", claw.getWristPosition()).withPosition(9, 2).getEntry();
    }

    @Override
    public void updateTelemetry() {

        extendingPosition.setDouble(arm.getExtendPosition());

        armPositionDoublePID.setDouble(ArmSubsystem.getArmPosition());
        desiredPosition.setDouble(arm.getSetpoint());
        feedOutput.setDouble(arm.getFeedOutput());
        pidOutput.setDouble(arm.getPidOutput());
        totalOutput.setDouble(arm.getTotal());

        armLimit.setBoolean(arm.getLimitState());

        limeAim.setBoolean(vision.getLimelightAim());

        pivot1Temp.setDouble(arm.getPivot1Temp());
        pivot2Temp.setDouble(arm.getPivot2Temp());

        chassisAngle.setDouble(drive.getNavxRoll());

        clawTopLimit.setBoolean(claw.topLimit());
        clawBottomLimit.setBoolean(claw.bottomLimit());
        clawPosition.setDouble(claw.getWristPosition());

    }
}
