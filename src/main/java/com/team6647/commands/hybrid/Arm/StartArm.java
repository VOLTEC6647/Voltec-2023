/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class StartArm extends CommandBase {
  ArmSubsystem arm;

  public StartArm(ArmSubsystem arm) {
    this.arm = arm;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    arm.extendArm(-0.2);
  }

  @Override
  public void end(boolean interrupted) {
    arm.changeSetpoint(-120);
    arm.extendArm(0);
    arm.resetExtendPosition();

  }

  @Override
  public boolean isFinished() {
    return !arm.getLimitState();
  }
}
