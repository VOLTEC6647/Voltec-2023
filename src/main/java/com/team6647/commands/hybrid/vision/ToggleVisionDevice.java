/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.commands.hybrid.vision;

import com.team6647.Constants.VisionConstants;
import com.team6647.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ToggleVisionDevice extends CommandBase {
  VisionSubsystem vision;

  public ToggleVisionDevice(VisionSubsystem vision) {
    this.vision = vision;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    int state = vision.getPhotonPipe();

    if (state == 0) {
      vision.setPhotonPipe(VisionConstants.retroPhotonPipe);
      vision.setLimePipe(VisionConstants.aprilLimePipe);
    } else if (state == 1) {
      vision.setPhotonPipe(VisionConstants.aprilPhotonPipe);
      vision.setLimePipe(VisionConstants.retroLimePipe);
    }
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
