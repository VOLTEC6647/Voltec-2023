/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.robot;

import com.andromedalib.robot.SuperRobot;
import com.team6647.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends SuperRobot {

  private RobotContainer robotContainer;

  @Override
  public void robotInit(){
    robotContainer = RobotContainer.getInstance();
    setRobotContainer(robotContainer); 

    super.robotInit();
  }

  @Override
  public void autonomousInit() {
    Shuffleboard.update();
    SmartDashboard.updateValues();
    DriveSubsystem.getInstance().resetNavx();
    DriveSubsystem.getInstance().resetEncoders();
    super.autonomousInit();
  }

  @Override
  public void teleopInit() {
    super.teleopInit();
    robotContainer.setChassisCommand();
  }


  DutyCycleEncoder absEncoder = new DutyCycleEncoder(4);

  @Override
  public void teleopPeriodic(){
    super.teleopPeriodic();
    double val = ((absEncoder.getAbsolutePosition() * 2048.0 )/ 360) - 148.7;
    SmartDashboard.putNumber("Position", absEncoder.getDistance());
    SmartDashboard.putNumber("Position Absolute", absEncoder.getAbsolutePosition());
  }
}
