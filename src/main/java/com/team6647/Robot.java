/**
 * Written by Juan Pablo Gutiérrez
 * 
 */

package com.team6647;

import com.pathplanner.lib.server.PathPlannerServer;
import com.team6647.subsystems.DriveSubsystem;

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

    PathPlannerServer.startServer(5811);

    addPeriodic(() -> {

    }, 0.01);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

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
    autonomousCommand = robotContainer.getAutonomousCommand();

    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }

    DriveSubsystem.getInstance().resetNavx();
    DriveSubsystem.getInstance().resetEncoders();
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    robotContainer.setChassisCommand();
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();

    Command testCommand = robotContainer.getTestCommand();

    if (testCommand != null) {
      testCommand.schedule();
    }
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
