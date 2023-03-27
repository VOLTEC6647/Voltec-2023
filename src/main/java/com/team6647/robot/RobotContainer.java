/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.robot;

import com.andromedalib.robot.SuperRobotContainer;
import com.team6647.commands.auto.AutoBalance;
import com.team6647.commands.hybrid.Arm.ArmControl;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.commands.hybrid.Arm.StartArm;
import com.team6647.commands.hybrid.claw.MoveClaw;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.VisionSubsystem;
import com.team6647.utils.AutoUtils;
import com.team6647.utils.Constants.ArmConstants;
import com.team6647.utils.Constants.OperatorConstants;
import com.team6647.utils.Constants.VisionConstants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;

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

    OperatorConstants.driverController1.leftTrigger().whileTrue(new SequentialCommandGroup(
        new InstantCommand(() -> vision.setLimePipe(VisionConstants.aprilLimePipe), vision),
        new StartEndCommand(() -> vision.toggleLimelightAim(), () -> vision.toggleLimelightAim(), vision, chassis)));

    OperatorConstants.driverController1.rightTrigger().whileTrue(new SequentialCommandGroup(
        new InstantCommand(() -> vision.setLimePipe(VisionConstants.retroLimePipe), vision),
        new StartEndCommand(() -> vision.toggleLimelightAim(), () -> vision.toggleLimelightAim(), vision, chassis)));

    OperatorConstants.driverController1.rightBumper().whileTrue(new InstantCommand(() -> chassis.setBrake(), chassis))
        .whileFalse(new InstantCommand(() -> chassis.setCoast(), chassis));

    /*
     * arm.setDefaultCommand(new RunCommand(() -> arm.manualControl(
     * Math.copySign(Functions
     * .clamp(Math.abs(Functions.handleDeadband(OperatorConstants.driverController2.
     * getLeftY(), 0.1)), 0, 0.5),
     * -OperatorConstants.driverController2.getLeftY())),
     * arm));
     */

    OperatorConstants.driverController2.x().whileTrue(new RunCommand(() -> {
      arm.manualControl(0.5);
    }, arm));

    OperatorConstants.driverController2.b().whileTrue(new RunCommand(() -> {
      arm.manualControl(-0.5);
    }, arm));

    OperatorConstants.driverController2.pov(0).toggleOnTrue(new ArmControl(arm, -55));

    OperatorConstants.driverController2.pov(180).toggleOnTrue(new ArmControl(arm, -120));

    OperatorConstants.driverController2.y().whileTrue(new ExtendArm(arm, ArmConstants.extendSped))
        .toggleOnFalse(new RunCommand(() -> arm.extendArm(0), arm));
    OperatorConstants.driverController2.a().whileTrue(new ExtendArm(arm, -ArmConstants.extendSped))
        .toggleOnFalse(new RunCommand(() -> arm.extendArm(0), arm));

    OperatorConstants.driverController2.rightTrigger(0.1).whileTrue(new MoveClaw(claw, 1, false));
    OperatorConstants.driverController2.leftTrigger(0.1).whileTrue(new MoveClaw(claw, -1, false));
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
    System.out.println(telemetryManager.getGridPlacementSelection());
    try {
      return Commands.sequence(
          new InstantCommand(() -> ChassisSubsystem.toggleSecondGear()),
          new StartArm(arm),
          AutoUtils.getAuto());
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
