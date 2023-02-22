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
    if(climbing && drive.getNavxPitch() < 2){
      execute();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (drive.calculatePID() > 0.5) {
      chasssis.tankDrive(0.5, 0.5);
    } else if (drive.calculatePID() < -0.5) {
      chasssis.tankDrive(-0.5, -0.5);
    } else {
      chasssis.tankDrive(drive.calculatePID(), drive.calculatePID());

    }
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
