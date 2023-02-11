// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.auto;

import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalance extends CommandBase {
  DriveSubsystem drive;
  ChassisSubsystem chassis;
  double currentAngle;

  public AutoBalance(DriveSubsystem drive, ChassisSubsystem chassis) {
    this.drive = drive;
    addRequirements(chassis, chassis);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    currentAngle = drive.navx.getPitch();
    drive.setAngleSetpoint(0);
    chassis.leftMotorController.set(drive.angleCalculate(chassis.leftMotorController.get(), 0));
    chassis.rightMotorController.set(drive.angleCalculate(chassis.leftMotorController.get(), 0) * -1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drive.angleIsInTolerance();
  }
}
