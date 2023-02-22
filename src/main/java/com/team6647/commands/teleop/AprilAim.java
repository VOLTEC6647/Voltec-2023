// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.teleop;

import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.VisionSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AprilAim extends CommandBase {
  VisionSubsystem vision;
  ChassisSubsystem chassis;

  public AprilAim() {
    vision = VisionSubsystem.getInstance("Photon");
    chassis = ChassisSubsystem.getInstance();
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
