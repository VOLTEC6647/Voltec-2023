// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.teleop;

import com.team6647.Constants.OperatorConstants;
import com.team6647.subsystems.ChassisSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class CurvatureDriveCommand extends CommandBase {
  ChassisSubsystem chassis;
  CommandXboxController controller;
  double forwardY;
  double turnX;

  /** Creates a new ArcadeDriveCommand. */
  public CurvatureDriveCommand(ChassisSubsystem chassis, CommandXboxController controller) {
    this.chassis = chassis;
    this.controller = controller;

    addRequirements(chassis);
  }

  @Override
  public void execute() {
    forwardY = -controller.getLeftY() * OperatorConstants.yMultiplier;
    turnX = controller.getRightX() * OperatorConstants.xMultiplier;

    controller.y().onTrue(new RunCommand(() -> {
      chassis.toggleInverted();
    }, chassis));
    if (chassis.isInverted()) {
      forwardY = -forwardY;
      turnX = -turnX;
    }

    chassis.curvatureDrive(forwardY, turnX);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}