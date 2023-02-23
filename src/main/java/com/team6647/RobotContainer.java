/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647;

import com.andromedalib.auto.Load;
import com.team6647.Constants.ArmConstants;
import com.team6647.Constants.ClawConstants;
import com.team6647.Constants.OperatorConstants;
import com.team6647.commands.auto.AutoBalance;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.commands.hybrid.Arm.StartArm;
import com.team6647.commands.hybrid.claw.MoveClaw;
import com.team6647.commands.hybrid.vision.ToggleVisionDevice;
import com.team6647.commands.teleop.AprilAim;
import com.team6647.commands.teleop.LimelightAim;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.VisionSubsystem;
import com.team6647.utils.shuffleboard.DriveModeSelector;
import com.team6647.utils.shuffleboard.ShuffleboardManager;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class RobotContainer {

  private static RobotContainer instance;

  private DriveModeSelector selector;
  private ShuffleboardManager interactions;

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
  }

  
  /**
   * Initializes the sending of telemetry
   */
  public void initTelemetry() {
    selector = new DriveModeSelector();
    interactions = ShuffleboardManager.getInstance();
  }

  /**
   * Constantly updates Telemetry data.
   * Call this function from the {@link Robot} class
   */
  public void updateTelemetry() {
    interactions.updateTelemetry();
  }

  /**
   * Sets the {@link ChassisSubsystem} default command
   */
  public void setChassisCommand() {
    chassis.setDefaultCommand(selector.getDriveMode());
  }

  /**
   * Sets the button bidings
   */
  public void configureBindings() {
    setChassisCommand();

    OperatorConstants.driverController1.y().whileTrue(new InstantCommand(() -> ChassisSubsystem.toggleReduction()));
    OperatorConstants.driverController1.a().whileTrue(new AutoBalance(chassis, drive));
    OperatorConstants.driverController1.x().whileTrue(new AprilAim(vision, chassis));
    OperatorConstants.driverController1.b().whileTrue(new LimelightAim(vision, chassis));
    OperatorConstants.driverController1.rightBumper().whileTrue(new ToggleVisionDevice(vision));
/* 
    OperatorConstants.driverController2.x().onTrue(Commands.runOnce(() -> {arm.setGoal(Math.PI); arm.enable();}, arm));
    OperatorConstants.driverController2.b().onTrue(Commands.runOnce(() -> {arm.setGoal(0); arm.enable();}, arm));
    */  
/* 
    OperatorConstants.driverController2.x().whileTrue(new RotateArm(0.5));
    OperatorConstants.driverController2.b().whileTrue(new RotateArm(-0.5)); */
    OperatorConstants.driverController2.y().whileTrue(new ExtendArm(arm, ArmConstants.extendSped));
    OperatorConstants.driverController2.a().whileTrue(new ExtendArm(arm, -ArmConstants.extendSped));

    OperatorConstants.driverController2.rightTrigger(0.1).whileTrue(new MoveClaw(claw, ClawConstants.clawSpeed));
    OperatorConstants.driverController2.leftTrigger(0.1).whileTrue(new MoveClaw(claw, -ClawConstants.clawSpeed));
    OperatorConstants.driverController2.rightBumper().whileTrue(new InstantCommand(() -> {
      claw.ConeSet();
    }));
    OperatorConstants.driverController2.leftBumper().whileTrue(new InstantCommand(() -> {
      claw.cubeSet();
    }));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new StartArm(arm);
    /* return Load.loadTrajectory(Filesystem.getDeployDirectory()
        + "/pathplanner/generatedJSON/LeaveCommunityDown.wpilib.json",
        true); */
  }
}
