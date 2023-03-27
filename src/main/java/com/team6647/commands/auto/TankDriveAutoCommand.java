/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.commands.auto;

import com.team6647.subsystems.ChassisSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Manages chassis movements during auto instead
 */
public class TankDriveAutoCommand extends CommandBase {
  ChassisSubsystem chassis;
  double leftY, rightY;

  public TankDriveAutoCommand(ChassisSubsystem chassis, double leftY, double rightY) {
    this.chassis = chassis;
    this.leftY = leftY;
    this.rightY = rightY;

    addRequirements(chassis);
  }

  @Override
  public void execute() {
    chassis.tankDrive(leftY, rightY);
  }

  @Override
  public void end(boolean interrupted) {
    chassis.tankDrive(0, 0);
  }

}
