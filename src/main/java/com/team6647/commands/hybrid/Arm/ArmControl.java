/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.commands.hybrid.Arm;

import com.team6647.subsystems.ArmSubsystem;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class ArmControl extends InstantCommand {
  ArmSubsystem arm;
  double setpoint;

  public ArmControl(ArmSubsystem arm, double setpoint) {
    this.arm = arm;
    this.setpoint = setpoint;

    addRequirements(arm);
  }

  @Override
  public void initialize() {
    arm.changeSetpoint(setpoint);
  }
}
