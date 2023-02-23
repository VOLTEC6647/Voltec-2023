/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.Arm;

import com.team6647.Constants.ArmConstants;
import com.team6647.subsystems.ArmSubsystem;

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
  }

  @Override
  public void execute() {
    if (speed <= 0) {
      if (!arm.getLimitState()) {
        arm.resetExtendPosition();
        arm.extendArm(0);
        end(true);
      } else {
        arm.extendArm(speed);
      }
    } else if (speed > 0) {
      if (Math.abs(arm.getExtendPosition()) >= Math.abs(ArmConstants.forwardLimit)) {
        arm.extendArm(0);
        end(true);
      } else {
        arm.extendArm(speed);
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    arm.extendArm(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
