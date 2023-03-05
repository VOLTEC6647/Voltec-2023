/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.auto;

import com.team6647.Constants.DriveConstants;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalance extends CommandBase {
  ChassisSubsystem chassis;
  DriveSubsystem drive;

  boolean climbing = false;

  double drivePower = 0;
  double currentAngle = 0;
  double error = 0;

  public AutoBalance(ChassisSubsystem chassis, DriveSubsystem drive) {
    this.chassis = chassis;
    this.drive = drive;

    addRequirements(drive, chassis);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentAngle = drive.getNavxRoll();
    error = DriveConstants.balanceGoal - currentAngle;

    drivePower = -Math.min(DriveConstants.balanceKp * error, 1);


    if(Math.abs(drivePower) > 0.5){
      drivePower = Math.copySign(0.4, drivePower);
    }

    chassis.tankDrive(drivePower, drivePower);
    System.out.println("Angle: " + currentAngle);
    System.out.println("Error: " + error);
    System.out.println("Drivepower: " + drivePower);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    chassis.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(error) < DriveConstants.balanceTolerance;
  }
}
