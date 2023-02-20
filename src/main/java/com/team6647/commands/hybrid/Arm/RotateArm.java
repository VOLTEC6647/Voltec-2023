// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateArm extends CommandBase {
  ArmSubsystem arm;
  double positionRads;

  /** Creates a new RotateArm. */
  public RotateArm(double positionRads) {
    arm = ArmSubsystem.getInstance();
    this.positionRads = positionRads;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
    arm.setGoal(positionRads);
    arm.enable();
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.stopPivot();
  }

  // Returns true when the command should end.w
  @Override
  public boolean isFinished() {
    return false;
  }
}
