// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.hybrid.claw;

import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.WristSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ParallelWrist extends CommandBase {
  WristSubsystem wrist;
  ArmSubsystem arm;

  double setpoint;

  public ParallelWrist(WristSubsystem wrist) {
    this.wrist = wrist;
    this.arm = ArmSubsystem.getInstance();

    addRequirements(wrist);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    double armPosition = ArmSubsystem.getArmPosition();

    setpoint = armPosition > 0 ? -(Math.abs(armPosition) - 90) : Math.abs(armPosition) - 90;

    wrist.changeSetpoint(setpoint);
  }

}
