/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.commands.teleop;

import com.team6647.subsystems.ChassisSubsystem;
import com.team6647.utils.Constants.OperatorConstants;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class ArcadeDriveCommand extends CommandBase {
  ChassisSubsystem chassis;
  CommandXboxController controller;
  double forwardY;
  double turnX;
  SlewRateLimiter slewFilter;

  public ArcadeDriveCommand(ChassisSubsystem chassis, CommandXboxController controller) {
    this.chassis = chassis;
    this.controller = controller;
    slewFilter = new SlewRateLimiter(1.0 / OperatorConstants.rampTimeSeconds);

    addRequirements(chassis);
  }

  @Override
  public void execute() {
    forwardY = -controller.getLeftY() * OperatorConstants.yMultiplier;
    turnX = -controller.getRightX() * OperatorConstants.xMultiplier;

    chassis.arcadeDrive(forwardY, turnX);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
