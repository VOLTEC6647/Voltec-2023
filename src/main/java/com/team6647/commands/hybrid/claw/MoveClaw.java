/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.claw;

import com.team6647.subsystems.ClawSubsytem;
import com.team6647.utils.Constants.ClawConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Moves the claw with a desired mult
 */
public class MoveClaw extends CommandBase {
  ClawSubsytem claw;
  double speed;
  double mult;
  boolean both;

  public MoveClaw(ClawSubsytem claw, double mult, boolean both) {
    this.claw = claw;
    this.mult = mult;
    this.both = both;

    addRequirements(claw);
  }

  @Override
  public void initialize() {
    if (claw.getSolenoidState() == Value.kForward) {
      speed = ClawConstants.clawSpeedCubes * mult;
    } else {
      speed = ClawConstants.clawSpeedCones * mult;
    }

    if (both) {
      claw.setBothVelocity(speed);
    } else {
      claw.setVelocity(speed);
    }
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    claw.setBothVelocity(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
