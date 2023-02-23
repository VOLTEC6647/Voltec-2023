/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class RotateArm extends CommandBase {
  ArmSubsystem arm;
  double positionRads;

  /** Creates a new RotateArm. */
  public RotateArm(ArmSubsystem arm, double positionRads) {
    this.arm = arm;
    this.positionRads = positionRads;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
/*     arm.resetEverything();
 */    arm.setGoal(positionRads);
    arm.enable();
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
