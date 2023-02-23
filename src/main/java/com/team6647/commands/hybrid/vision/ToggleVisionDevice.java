// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
