/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.teleop;

import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AprilAim extends CommandBase {
  VisionSubsystem vision;
  ChassisSubsystem chassis;

  public AprilAim(VisionSubsystem vision, ChassisSubsystem chassis) {
    this.vision = vision;
    this.chassis = chassis;
    addRequirements(chassis, vision);
  }

  @Override
  public void initialize() {
    vision.togglePhotonAim();
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
    vision.togglePhotonAim();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
