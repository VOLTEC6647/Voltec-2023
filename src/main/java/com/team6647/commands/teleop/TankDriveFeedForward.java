/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.teleop;

import com.team6647.Constants.OperatorConstants;
import com.team6647.subsystems.ChassisSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class TankDriveFeedForward extends CommandBase {
  ChassisSubsystem chassis;
  CommandXboxController controller;
  double leftY;
  double rightY;

  public TankDriveFeedForward(ChassisSubsystem chassis, CommandXboxController controller) {
    this.chassis = chassis;
    this.controller = controller;

    addRequirements(chassis);
  }

  @Override
  public void execute() {
    leftY = -controller.getLeftY() * OperatorConstants.xMultiplier;
    rightY = -controller.getRightY() * OperatorConstants.xMultiplier;

    if (controller.y().getAsBoolean()) {
      chassis.toggleInverted();
    }
    if (chassis.isInverted()) {
      leftY = -leftY;
      rightY = -rightY;
    }

    chassis.tankDriveFeedForward(leftY, rightY);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
