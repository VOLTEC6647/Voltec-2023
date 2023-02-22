/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.commands.auto;

import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalance extends CommandBase {
  ChassisSubsystem chasssis;
  DriveSubsystem drive;

  boolean climbing = false;

  double drivePower = 0;

  public AutoBalance() {
    chasssis = ChassisSubsystem.getInstance();
    drive = DriveSubsystem.getInstance();

    addRequirements(drive, chasssis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    chasssis.tankDrive(0.2, 0.2);
    if (drive.getNavxPitch() > 4) {
      climbing = true;
    }
    if (climbing && drive.getNavxPitch() < 2) {
      execute();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivePower = drive.calculatePID();

    // This Limits max power
    if (Math.abs(drivePower) > 0.5) {
      drivePower = Math.copySign(0.5, drivePower);
    }
    chasssis.tankDrive(drivePower, drivePower);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    chasssis.setBrake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return drive.inTolerance();
  }
}
