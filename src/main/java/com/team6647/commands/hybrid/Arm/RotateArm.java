// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateArm extends CommandBase {
  ArmSubsystem arm;
  double speed;

  /** Creates a new RotateArm. */
  public RotateArm(double speed) {
    arm = ArmSubsystem.getInstance();
    this.speed = speed;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
    arm.setAngle(speed);
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.setAngle(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
