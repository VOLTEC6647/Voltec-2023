/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.auto;

import com.andromedalib.math.Functions;
import com.team6647.Constants.DriveConstants;
import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    /*
     * chasssis.tankDrive(0.2, 0.2);
     * if (drive.getNavxPitch() > 4) {
     * climbing = true;
     * }
     * if (climbing && drive.getNavxPitch() < 2) {
     * execute();
     * }
     */
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentAngle = drive.getNavxPitch();
    SmartDashboard.putNumber("Angle", currentAngle);
    error = DriveConstants.balanceGoal - currentAngle;
    drivePower = -Math.min(DriveConstants.balanceKp * error, 1);

    drivePower = Functions.clamp(drivePower, -0.5, 0.5);

    chassis.tankDrive(-drivePower, -drivePower);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    chassis.tankDrive(0, 0);
    chassis.setBrake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(error) < DriveConstants.balanceTolerance;
  }
}
