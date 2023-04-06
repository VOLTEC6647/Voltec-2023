/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.robot;

import com.andromedalib.robot.SuperRobot;
import com.team6647.subsystems.DriveSubsystem;

public class Robot extends SuperRobot {

  private RobotContainer robotContainer;

  @Override
  public void robotInit() {
    robotContainer = RobotContainer.getInstance();
    setRobotContainer(robotContainer);

    super.robotInit();
  }

  @Override
  public void autonomousInit() {
    DriveSubsystem.getInstance().resetNavx();
    DriveSubsystem.getInstance().resetEncoders();
    robotContainer.resetArm();
    super.autonomousInit();
  }

  @Override
  public void teleopInit() {
    super.teleopInit();
    robotContainer.setChassisCommand();
  }

}
