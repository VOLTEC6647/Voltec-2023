/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.TelescopicArm;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Auto command for resetting the arm position, run before any autonomous
 * command
 */
public class StartArm extends CommandBase {
  TelescopicArm arm;

  public StartArm(TelescopicArm arm) {
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
    arm.extendArm(0);
    arm.resetExtendPosition();

  }

  @Override
  public boolean isFinished() {
    return !arm.getLimitState();
  }
}
