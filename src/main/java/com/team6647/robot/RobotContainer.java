/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.robot;

import com.andromedalib.robot.SuperRobotContainer;
import com.team6647.commands.auto.AutoBalance;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.commands.hybrid.Arm.StartArm;
import com.team6647.commands.hybrid.claw.MoveClaw;
import com.team6647.commands.teleop.AprilAim;
import com.team6647.commands.teleop.LimelightAim;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.VisionSubsystem;
import com.team6647.utils.Constants.ArmConstants;
import com.team6647.utils.Constants.OperatorConstants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class RobotContainer extends SuperRobotContainer {

  private static RobotContainer instance;
  private TelemetryManager telemetryManager;

  private ArmSubsystem arm;
  private ChassisSubsystem chassis;
  private ClawSubsytem claw;
  private DriveSubsystem drive;
  private VisionSubsystem vision;

  private RobotContainer() {
  }

  public static RobotContainer getInstance() {
    if (instance == null) {
      instance = new RobotContainer();
    }
    return instance;
  }

  /**
   * Initializes every  the subsystems. Call this function from the
   * {@link Robot} class
   */
  public void initSubsystems() {
    arm = ArmSubsystem.getInstance();
    chassis = ChassisSubsystem.getInstance();
    claw = ClawSubsytem.getInstance();
    drive = DriveSubsystem.getInstance();
    vision = VisionSubsystem.getInstance("Photon");

    telemetryManager = TelemetryManager.getInstance();
  }

  /**
   * Sets the {@link ChassisSubsystem} default command
   */
  public void setChassisCommand() {
    chassis.setDefaultCommand(telemetryManager.getDriveSelection());
  }

  /**
   * Sets the button bindings
   */
  public void configureBindings() {
    setChassisCommand();

    OperatorConstants.driverController1.x().whileTrue(new InstantCommand(() -> ChassisSubsystem.toggleFirstGear()));
    OperatorConstants.driverController1.y().whileTrue(new InstantCommand(() -> ChassisSubsystem.toggleSecondGear()));
    OperatorConstants.driverController1.a().whileTrue(new AutoBalance(chassis, drive));
    OperatorConstants.driverController1.leftTrigger().whileTrue(new AprilAim(vision, chassis));
    OperatorConstants.driverController1.rightTrigger().whileTrue(new LimelightAim(vision, chassis));
    OperatorConstants.driverController1.rightBumper().whileTrue(new InstantCommand(() -> chassis.setBrake(), chassis))
        .whileFalse(new InstantCommand(() -> chassis.setCoast(), chassis));

    OperatorConstants.driverController2.x().whileTrue(new RunCommand(() -> {
      arm.manualControl(0.5);
    }, arm));

    OperatorConstants.driverController2.b().whileTrue(new RunCommand(() -> {
      arm.manualControl(-0.5);
    }, arm));

    OperatorConstants.driverController2.pov(0).whileTrue(new RunCommand(() -> {
      arm.changeSetpoint(-70);
    }, arm));

    OperatorConstants.driverController2.pov(180).whileTrue(new RunCommand(() -> {
      arm.changeSetpoint(-120);
    }, arm));

    OperatorConstants.driverController2.y().whileTrue(new ExtendArm(arm, ArmConstants.extendSped))
        .toggleOnFalse(new RunCommand(() -> arm.extendArm(0), arm));
    OperatorConstants.driverController2.a().whileTrue(new ExtendArm(arm, -ArmConstants.extendSped))
        .toggleOnFalse(new RunCommand(() -> arm.extendArm(0), arm));

    OperatorConstants.driverController2.rightTrigger(0.1).whileTrue(new MoveClaw(claw, 1));
    OperatorConstants.driverController2.leftTrigger(0.1).whileTrue(new MoveClaw(claw, -1));
    OperatorConstants.driverController2.rightBumper().whileTrue(new InstantCommand(() -> {
      claw.CubeSet();
    }));
    OperatorConstants.driverController2.leftBumper().whileTrue(new InstantCommand(() -> {
      claw.ConeSet();
    }));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   * Always retract arm before any autonomous
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    try {
      return Commands.sequence(
          new InstantCommand(() -> ChassisSubsystem.toggleSecondGear()),
          new StartArm(arm),
          telemetryManager.getAutoSelection());
    } catch (Exception e) {
      DriverStation.reportWarning("Could not run auto", true);
      return null;
    }
  }

  /**
   * Use this to pass the test command to the main {@link Robot} class.
   *
   * @return the command to run in test
   */
  public Command getTestCommand() {
    return Commands.sequence(new StartArm(arm), telemetryManager.getProtocolSelection());
  }
}
