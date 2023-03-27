/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Moves arm to a desired position smoothly
 */
public class ArmControl extends CommandBase {
  ArmSubsystem arm;
  double value;
  boolean goingDown;

  public ArmControl(ArmSubsystem arm, double value) {
    this.arm = arm;
    this.value = value;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
    goingDown = arm.getMeasurement() > value;
  }

  @Override
  public void execute() {
    if (goingDown)
      arm.manualControl(-0.5);
    else if (!goingDown)
      arm.manualControl(0.5);
  }

  @Override
  public void end(boolean interrupted) {
    arm.manualControl(0);
  }

  @Override
  public boolean isFinished() {
    if (goingDown)
      return arm.getMeasurement() < value;
    else
      return arm.getMeasurement() > value;
  }
}
