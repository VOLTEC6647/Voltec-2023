/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import java.util.Optional;

import org.photonvision.EstimatedRobotPose;

import com.andromedalib.sensors.SuperNavx;
import com.team6647.Constants.DriveConstants;

import edu.wpi.first.math.controller.PIDController;
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
  private static PIDController anglePID = new PIDController(DriveConstants.kpDriveVelocity, 0, 0);
  private static DriveSubsystem instance;

  public SuperNavx navx = SuperNavx.getInstance();
  /*
   * PIDController angleController;//, velocityController;
   */ /* int angleSetpoint, velocitySetpoint; */
  Field2d field = new Field2d();

  DifferentialDrivePoseEstimator poseEstimator;

  VisionSubsystem visionSubystem = VisionSubsystem.getInstance("Photon");

  private DriveSubsystem() {

    anglePID.setTolerance(DriveConstants.angleTolerance);

    resetEncoders();

    navx.zeroHeading();

    poseEstimator = new DifferentialDrivePoseEstimator(DriveConstants.kDrivekinematics, navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        new Pose2d());

    poseEstimator.resetPosition(navx.getRotation(),
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
    poseEstimator.update(navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio));

    Optional<EstimatedRobotPose> result = visionSubystem.getEstimatedGlobalPose(poseEstimator.getEstimatedPosition());

    if (result.isPresent()) {
      EstimatedRobotPose pose = result.get();
      poseEstimator.addVisionMeasurement(pose.estimatedPose.toPose2d(), pose.timestampSeconds);
    }
  }

  public void resetOdometry(Pose2d pose) {

    resetEncoders();

    poseEstimator.resetPosition(navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        pose);
  }

  public void resetEncoders() {
    ChassisSubsystem.frontLeft.resetEncoder();
    ChassisSubsystem.frontRight.resetEncoder();
  }

  public Pose2d getPose() {
    return poseEstimator.getEstimatedPosition();
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

  public double calculatePID() {
    return anglePID.calculate(navx.getPitch(), 0);
  }

  public boolean inTolerance() {
    return anglePID.atSetpoint();
  }

  public double getNavxPitch() {
    return navx.getPitch();
  }
}
