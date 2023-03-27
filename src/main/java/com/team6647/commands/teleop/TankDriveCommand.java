/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.teleop;

import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.utils.Constants.OperatorConstants;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * Tank drive command for teleop
 */
public class TankDriveCommand extends CommandBase {
  ChassisSubsystem chassis;
  CommandXboxController controller;
  double leftY;
  double rightY;

  public TankDriveCommand(ChassisSubsystem chassis, CommandXboxController controller) {
    this.chassis = chassis;
    this.controller = controller;

    addRequirements(chassis);

  }

  @Override
  public void execute() {
    leftY = -controller.getLeftY() * OperatorConstants.xMultiplier;
    rightY = -controller.getRightY() * OperatorConstants.xMultiplier;

    chassis.tankDrive(leftY, rightY);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
