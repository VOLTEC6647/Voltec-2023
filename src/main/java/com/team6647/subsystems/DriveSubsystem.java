// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.subsystems;

import com.andromedalib.sensors.SuperNavx;
import com.team6647.Constants.DriveConstants;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Singleton class. It controls autonomus movement and similar purposes
 */
public class DriveSubsystem extends SubsystemBase {
  private static DriveSubsystem instance;

  public SuperNavx navx = SuperNavx.getInstance();
  /*
   * PIDController angleController;//, velocityController;
   */ /* int angleSetpoint, velocitySetpoint; */
  Field2d field = new Field2d();

  DifferentialDrivePoseEstimator odometry;

  private DriveSubsystem() {
    /*
     * angleController = new PIDController(DriveConstants.angleKp,
     * DriveConstants.angleKi, DriveConstants.angleKd);
     */

    resetEncoders();

    navx.zeroHeading();

    odometry = new DifferentialDrivePoseEstimator(DriveConstants.kDrivekinematics, navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        new Pose2d());

    odometry.resetPosition(navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        new Pose2d());

    resetOdometry(new Pose2d());

    field.setRobotPose(getPose());
  }

  public static DriveSubsystem getInstance() {
    if (instance == null) {
      instance = new DriveSubsystem();
    }
    return instance;
  }

  @Override
  public void periodic() {
    updateRotation2D();
    field.setRobotPose(getPose());
    SmartDashboard.putData(field);
  }

  private void updateRotation2D() {
    odometry.update(navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio));
  }

  public void resetOdometry(Pose2d pose) {

    resetEncoders();

    odometry.resetPosition(navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        pose);
  }

  public void resetEncoders() {
    ChassisSubsystem.frontLeft.resetEncoder();
    ChassisSubsystem.frontRight.resetEncoder();
  }

  public Pose2d getPose() {
    return odometry.getEstimatedPosition();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
        ChassisSubsystem.frontLeft.getVelocity(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        ChassisSubsystem.frontRight.getVelocity(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio));
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    ChassisSubsystem.leftMotorController.setVoltage(leftVolts);
    ChassisSubsystem.rightMotorController.setVoltage(rightVolts);
  }
}
