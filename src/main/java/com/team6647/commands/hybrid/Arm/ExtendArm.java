// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.hybrid.Arm;

import com.team6647.Constants.ArmConstants;
import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExtendArm extends CommandBase {
  ArmSubsystem arm;
  double speed;

  DigitalInput extendLimitSwitch = ArmConstants.limitSwitch;

  public ExtendArm(double speed) {
    arm = ArmSubsystem.getInstance();
    this.speed = speed;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
    if (speed < 0) {
      if (extendLimitSwitch.get()) {
        arm.resetExtendPosition();
        arm.extendArm(0);
      } else {
        arm.extendArm(speed);
      }
    } else {
      if (arm.getExtendPosition() >= ArmConstants.forwardLimit) {
        arm.extendArm(0);
      } else {
        arm.extendArm(speed);
      }
    }
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.extendArm(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
