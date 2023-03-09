/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.robot;

import com.team6647.subsystems.DriveSubsystem;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private Command autonomousCommand;

  private RobotContainer robotContainer;

  @Override
  public void robotInit() {
    robotContainer = RobotContainer.getInstance();

    robotContainer.initSubsystems();
    robotContainer.initTelemetry();
    robotContainer.configureBindings();

    CameraServer.startAutomaticCapture();
    addPeriodic(() -> {
      CommandScheduler.getInstance().run();
    }, 0.01);
  }

  @Override
  public void robotPeriodic() {

    robotContainer.updateTelemetry();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    DriveSubsystem.getInstance().resetNavx();
    DriveSubsystem.getInstance().resetEncoders();

    autonomousCommand = robotContainer.getAutonomousCommand();

    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    robotContainer.setChassisCommand();
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();

    Command testCommand = robotContainer.getTestCommand();

    if (testCommand != null) {
      testCommand.schedule();
    }
  }

  @Override
  public void testPeriodic() {}
}
