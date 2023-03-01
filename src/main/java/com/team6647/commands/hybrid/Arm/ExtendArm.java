/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.Arm;

import org.opencv.objdetect.FaceDetectorYN;

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
    System.out.println(speed);
    if (!arm.getLimitState()) {
      arm.resetExtendPosition();
      end(true);
      return;
    } else if (Math.abs(arm.getExtendPosition()) >= Math.abs(ArmConstants.forwardLimit)) {
      end(true);
      return;
    }
    /*
     * if (speed > 0 && !arm.getLimitState()) {
     * System.out.println("TAAAAA");
     * arm.extendArm(speed);
     * } else if (speed < 0 && Math.abs(arm.getExtendPosition()) >=
     * Math.abs(ArmConstants.forwardLimit)) {
     * System.out.println("WAAA");
     * arm.extendArm(speed);
     * } else if (!arm.getLimitState()) {
     * arm.resetExtendPosition();
     * System.out.println("TOUCHED");
     * end(true);
     * return;
     * } else if (Math.abs(arm.getExtendPosition()) >=
     * Math.abs(ArmConstants.forwardLimit)) {
     * end(true);
     * System.out.println("AAAA");
     * return;
     * }
     */

    arm.extendArm(speed);

  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    arm.extendArm(0);
  }

  @Override
  public boolean isFinished() {
    if ((speed > 0 && !arm.getLimitState())
        || (speed < 0 && Math.abs(arm.getExtendPosition()) >= Math.abs(ArmConstants.forwardLimit))) {
      System.out.println("FINISSSSAA");
      return false;
    }
    System.out.println("SDBIUAFBD");
    return !arm.getLimitState() || (Math.abs(arm.getExtendPosition()) >= Math.abs(ArmConstants.forwardLimit));
  }
}
