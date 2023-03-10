/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.auto;

import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.VisionSubsystem;
import com.team6647.utils.Constants.DriveConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AutoBalance extends CommandBase {
  ChassisSubsystem chassis;
  DriveSubsystem drive;
  VisionSubsystem vision;

  double drivePower = 0;
  double currentAngle = 0;
  double error = 0;
  public AutoBalance(ChassisSubsystem chassis, DriveSubsystem drive) {
    this.chassis = chassis;
    this.drive = drive;
    this.vision = VisionSubsystem.getInstance("Photon");

    addRequirements(drive, chassis);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    vision.setLimeLEDMode(2);

    currentAngle = drive.getNavxRoll();
    error = DriveConstants.balanceGoal - currentAngle;

    drivePower = -Math.min(DriveConstants.balanceKp * error, 1);

    SmartDashboard.putNumber("Drivepower 2: " , drivePower);

    if(drivePower < 0){
      drivePower *= 0.8; //1.35
    }

    if(Math.abs(drivePower) > 0.35){
      drivePower = Math.copySign(0.35, drivePower);
    }

    chassis.tankDrive(drivePower, drivePower);
    SmartDashboard.putNumber("Angle: ",currentAngle);
    SmartDashboard.putNumber("Error: " , error);
    SmartDashboard.putNumber("Drivepower: " , drivePower);

  }

  @Override
  public void end(boolean interrupted) {
    chassis.tankDrive(0, 0);
    chassis.setBrake();
    vision.setLimeLEDMode(1);
  }

  @Override
  public boolean isFinished() {
    if (Math.abs(error) < DriveConstants.balanceTolerance){
      double fpgaTimestamp = Timer.getFPGATimestamp();
      if(Math.abs(Timer.getFPGATimestamp() - fpgaTimestamp) > 2){
        return true;
      }
    }
    return false;
  }
}
