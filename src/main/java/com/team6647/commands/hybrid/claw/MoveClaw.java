/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.claw;

import com.team6647.subsystems.ClawSubsytem;
import com.team6647.utils.Constants.ClawConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveClaw extends CommandBase {
  ClawSubsytem claw;
  double speed;
  int mult;

  public MoveClaw(ClawSubsytem claw, int mult) {
    this.claw = claw;
    this.mult = mult;

    addRequirements(claw);
  }

  @Override
  public void initialize() {
    if (claw.getSolenoidState() == Value.kForward) {
      speed = ClawConstants.clawSpeedCubes * mult;
    } else {
      speed = ClawConstants.clawSpeedCones * mult;
    }
    claw.setVelocity(speed);
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    claw.setVelocity(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
