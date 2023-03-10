/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.ArmSubsystem;
import com.team6647.utils.Constants.ArmConstants;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExtendArm extends CommandBase {
  ArmSubsystem arm;
  double speed;

  public ExtendArm(ArmSubsystem arm, double speed) {
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
    if (speed > 0 && !arm.getLimitState()) {
      return false;
    }
    if (speed < 0 && Math.abs(arm.getExtendPosition()) >= Math.abs(ArmConstants.forwardLimit)) {
      return false;
    }
    return !arm.getLimitState() || (Math.abs(arm.getExtendPosition()) >= Math.abs(ArmConstants.forwardLimit));
  }
}
