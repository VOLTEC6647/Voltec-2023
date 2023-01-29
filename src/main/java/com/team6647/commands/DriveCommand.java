// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands;

import com.team6647.Constants.OperatorConstants;
import com.team6647.subsystems.ChassisSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class DriveCommand extends CommandBase {
  ChassisSubsystem chassis;
  CommandXboxController controller;
  double leftY;
  double rightY;

  public DriveCommand(ChassisSubsystem chassis, CommandXboxController controller) {
    this.chassis = chassis;
    this.controller = controller;
    addRequirements(chassis);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    leftY = -controller.getLeftY() * OperatorConstants.xMultiplier;
    rightY = -controller.getRightY() * OperatorConstants.xMultiplier;
    controller.y().onTrue(new RunCommand(() -> {
      chassis.driveInverted = !chassis.driveInverted;
    }, chassis));
    new Trigger(chassis::isInverted)
        .onTrue(new RunCommand(() -> {
          leftY = -leftY;
          rightY = -rightY;
        }, chassis));

    chassis.tankDrive(leftY, rightY);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
