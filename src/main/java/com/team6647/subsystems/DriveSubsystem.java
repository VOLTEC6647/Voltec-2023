// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.subsystems;

import com.andromedalib.sensors.SuperNavx;
import com.team6647.Constants.DriveConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Singleton class. It controls autonomus movement and similar purposes
 */
public class DriveSubsystem extends SubsystemBase {
  private static DriveSubsystem instance;
  ChassisSubsystem chassis;

  public SuperNavx navx;
  PIDController angleController;//, velocityController;
  int angleSetpoint, velocitySetpoint;

  private DriveSubsystem() {
    chassis = ChassisSubsystem.getInstance();
    angleController = new PIDController(DriveConstants.angleKp, DriveConstants.angleKi, DriveConstants.angleKd);
  }

  public static DriveSubsystem getInstance() {
    if (instance == null) {
      instance = new DriveSubsystem();
    }
    return instance;
  }

  public void setAngleSetpoint(int aSetpoint) {
    angleSetpoint = aSetpoint;
    angleController.setSetpoint(angleSetpoint);
  }

  public boolean angleIsInTolerance() {
    return Math
        .abs(ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio)
            - angleSetpoint) < DriveConstants.angleTolerance;
  }

  public double angleCalculate(double measurement, double setpoint) {
    return angleController.calculate(measurement, setpoint);
  }

 /*  public void setVelocitySetpoint(int velSetpoint) {
    velocitySetpoint = velSetpoint;
    velocityController.setSetpoint(velocitySetpoint);
  }

  public boolean velIsIntolerance() {
    return Math
        .abs(ChassisSubsystem.frontLeft.getVelocity(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio)
            - velocitySetpoint) < DriveConstants.velocityTolerance;
  } */
}
