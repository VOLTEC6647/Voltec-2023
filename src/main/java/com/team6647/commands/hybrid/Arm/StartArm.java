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

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    arm.extendArm(-0.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.changeSetpoint(-130);
    arm.extendArm(0);
    arm.resetExtendPosition();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !arm.getLimitState();
  }
}
