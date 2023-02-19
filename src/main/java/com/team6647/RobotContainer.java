// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647;

import com.team6647.Constants.ArmConstants;
import com.team6647.Constants.ClawConstants;
import com.team6647.Constants.OperatorConstants;
import com.team6647.commands.hybrid.Arm.ExtendArm;
import com.team6647.commands.hybrid.Arm.RotateArm;
import com.team6647.commands.hybrid.claw.MoveClaw;
import com.team6647.commands.hybrid.claw.ToggleClaw;
import com.team6647.commands.hybrid.claw.ToggleClaw.ClawModes;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.ClawSubsytem;
import com.team6647.utils.shuffleboard.DriveModeSelector;
import com.team6647.utils.shuffleboard.ShuffleboardManager;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class RobotContainer {

  private static RobotContainer instance;

  private DriveModeSelector selector;
  private ShuffleboardManager interactions;

  private ChassisSubsystem chassis;
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
    chassis = ChassisSubsystem.getInstance();
    ArmSubsystem.getInstance();
    ClawSubsytem.getInstance();

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

    OperatorConstants.driverController2.x().whileTrue(new RotateArm(ArmConstants.armSpeed));
    OperatorConstants.driverController2.b().whileTrue(new RotateArm(-ArmConstants.armSpeed));
    OperatorConstants.driverController2.y().whileTrue(new ExtendArm(ArmConstants.extendSped));
    OperatorConstants.driverController2.a().whileTrue(new ExtendArm(-ArmConstants.extendSped));

    OperatorConstants.driverController1.rightTrigger(0.1).whileTrue(new MoveClaw(ClawConstants.clawSpeed));
    OperatorConstants.driverController1.leftTrigger(0.1).whileTrue(new MoveClaw(-ClawConstants.clawSpeed)); 
    OperatorConstants.driverController2.rightBumper().whileTrue(new ToggleClaw(ClawModes.CONE));
    OperatorConstants.driverController2.leftBumper().whileTrue(new ToggleClaw(ClawModes.CUBE));


    OperatorConstants.driverController1.y().whileTrue(new InstantCommand(() -> ChassisSubsystem.toggleReduction()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
