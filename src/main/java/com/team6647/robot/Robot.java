/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.robot;

import com.andromedalib.robot.SuperRobot;
import com.team6647.subsystems.ArmSubsystem;
import com.team6647.subsystems.DriveSubsystem;
import com.team6647.subsystems.WristSubsystem;

public class Robot extends SuperRobot {

  private RobotContainer robotContainer;

  @Override
  public void robotInit() {
    robotContainer = RobotContainer.getInstance();
    setRobotContainer(robotContainer);

    super.robotInit();

    WristSubsystem.startClaw();
    ArmSubsystem.startArm();
  }

  @Override
  public void autonomousInit() {
    DriveSubsystem.getInstance().resetNavx();
    DriveSubsystem.getInstance().resetEncoders();
    robotContainer.resetArm();
    WristSubsystem.startClaw();
    super.autonomousInit();
  }

  @Override
  public void teleopInit() {
    super.teleopInit();
    robotContainer.setChassisCommand();
    WristSubsystem.startClaw(); //TODO REMOVE
  }

  @Override
  public void disabledInit() {
    super.disabledInit();
    WristSubsystem.disabledClaw();
  }

}
