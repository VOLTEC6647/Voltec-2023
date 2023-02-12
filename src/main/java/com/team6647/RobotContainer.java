// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647;

import com.team6647.Constants.ArmConstants;
import com.team6647.Constants.OperatorConstants;
import com.team6647.commands.auto.AutoBalance;
import com.team6647.commands.hybrid.RotateArm;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.utils.shuffleboard.DriveModeSelector;
import com.team6647.utils.shuffleboard.ShuffleboardManager;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  private static RobotContainer instance;

  private DriveModeSelector selector;
  private ShuffleboardManager interactions;

  private ChassisSubsystem chassis;
  private DriveSubsystem drive;

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
    drive = DriveSubsystem.getInstance();
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
   * Sets the button bidings
   */
  public void configureBindings() {
    chassis.setDefaultCommand(selector.getDriveMode());

    OperatorConstants.driverController1.y().whileTrue(new AutoBalance(drive, chassis));

    OperatorConstants.driverController2.x().whileTrue(new RotateArm(ArmConstants.armSpeed));
    OperatorConstants.driverController2.b().whileTrue(new RotateArm(-ArmConstants.armSpeed));
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
