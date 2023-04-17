/*+
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.commands.hybrid.claw;

import com.team6647.subsystems.WristSubsystem;

import edu.wpi.first.wpilibj2.command.InstantCommand;

public class WristControl extends InstantCommand {
  WristSubsystem wrist;
  double setpoint;

  public WristControl(WristSubsystem wrist, double setpoint) {
    this.wrist = wrist;
    this.setpoint = setpoint;

    addRequirements(wrist);
  }

  @Override
  public void initialize() {
    wrist.resetPID();
    wrist.changeSetpoint(setpoint);
  }
}
