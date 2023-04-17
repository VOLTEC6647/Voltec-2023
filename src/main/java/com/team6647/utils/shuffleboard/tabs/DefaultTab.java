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
import com.team6647.subsystems.TelescopicArm;
import com.team6647.subsystems.VisionSubsystem;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class DefaultTab extends ShuffleboardTabBase {

    ArmSubsystem arm = ArmSubsystem.getInstance();
    TelescopicArm teleArm = TelescopicArm.getInstance();
    ChassisSubsystem chassis = ChassisSubsystem.getInstance();
    ClawSubsytem claw = ClawSubsytem.getInstance();
    DriveSubsystem drive = DriveSubsystem.getInstance();
    VisionSubsystem vision = VisionSubsystem.getInstance();
    RobotContainer container = RobotContainer.getInstance();

    GenericEntry finishedAuto;

    /* Not set */
    GenericEntry clawTopLimit;
    GenericEntry clawBottomLimit;
    GenericEntry clawBeamBrake;

    GenericEntry armLimit;

    GenericEntry limeAim;

    public DefaultTab(ShuffleboardTab tab) {
        this.finishedAuto = tab.add("Finished autp", container.getAutonomousCommand().isFinished()).withPosition(9, 3).getEntry();

        this.armLimit = tab.add("Limit switch", teleArm.getLimitState()).withPosition(3, 3).getEntry();

        this.limeAim = tab.add("Limelight aim", vision.getLimelightAim()).withPosition(4, 3).getEntry();
    }

    @Override
    public void updateTelemetry() {
        finishedAuto.setBoolean(container.getAutonomousCommand().isFinished());

        limeAim.setBoolean(vision.getLimelightAim());

        armLimit.setBoolean(teleArm.getLimitState());
    }
}
