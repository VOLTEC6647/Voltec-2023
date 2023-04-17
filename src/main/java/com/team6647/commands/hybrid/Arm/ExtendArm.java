/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.TelescopicArm;
import com.team6647.utils.Constants.ArmConstants;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Extends the arm at a desired speed
 */
public class ExtendArm extends CommandBase {
  TelescopicArm arm;
  double speed;

  public ExtendArm(TelescopicArm arm, double speed) {
    this.arm = arm;
    this.speed = speed;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
    arm.extendArm(speed);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    arm.extendArm(0);
    if (!arm.getLimitState())
      arm.resetExtendPosition();
  }

  @Override
  public boolean isFinished() {
    return false;
    /* if (speed > 0 && !arm.getLimitState()) {
      return false;
    }
    if (speed < 0 && Math.abs(arm.getExtendPosition()) >= Math.abs(ArmConstants.forwardLimit)) {
      return false;
    }
    return !arm.getLimitState() || (Math.abs(arm.getExtendPosition()) >= Math.abs(ArmConstants.forwardLimit)); */
  }
}
