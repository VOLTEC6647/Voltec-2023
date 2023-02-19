// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.hybrid.claw;

import com.team6647.subsystems.ClawSubsytem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveClaw extends CommandBase {
  ClawSubsytem claw;
  double speed;

  public MoveClaw(double speed) {
    claw = ClawSubsytem.getInstance();
    this.speed = speed;

    addRequirements(claw);
  }

  @Override
  public void initialize() {
    claw.setVelocity(speed);
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    claw.setVelocity(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
