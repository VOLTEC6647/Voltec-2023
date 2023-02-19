// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.commands.hybrid.claw;

import com.team6647.subsystems.ClawSubsytem;

import edu.wpi.first.wpilibj2.command.InstantCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ToggleClaw extends InstantCommand {
  ClawSubsytem claw;
  ClawModes mode;

  public enum ClawModes {
    CONE,
    CUBE
  }

  public ToggleClaw(ClawModes mode) {
    this.mode = mode;
    claw = ClawSubsytem.getInstance();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (mode == ClawModes.CONE) {
      claw.ConeSet();
    } else if (mode == ClawModes.CUBE) {
      claw.cubeSet();
    }
  }
}
