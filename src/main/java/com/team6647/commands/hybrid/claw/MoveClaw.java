/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.hybrid.claw;

import com.team6647.Constants.ClawConstants;
import com.team6647.subsystems.ClawSubsytem;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MoveClaw extends CommandBase {
  ClawSubsytem claw;
  double speed;

  public MoveClaw(ClawSubsytem claw) {
    this.claw = claw;

    addRequirements(claw);
  }

  @Override
  public void initialize() {
    if (claw.getSolenoidState() == Value.kForward) {
      speed = ClawConstants.clawSpeedCones;
    } else {
      speed = ClawConstants.clawSpeedCubes;
    }
    claw.setVelocity(speed);
  }

  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    claw.setVelocity(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
