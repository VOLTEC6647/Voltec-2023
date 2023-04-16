// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.hybrid.claw;

import com.team6647.subsystems.ClawSubsytem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveClaw extends CommandBase {
  ClawSubsytem claw;

  public MoveClaw(ClawSubsytem claw) {
    this.claw = claw;

    addRequirements(claw);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
