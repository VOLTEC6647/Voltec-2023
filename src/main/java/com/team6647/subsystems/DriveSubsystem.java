/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import com.andromedalib.sensors.SuperNavx;
import com.andromedalib.vision.LimelightHelpers;
import com.team6647.utils.Constants.DriveConstants;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Singleton class. It controls autonomus movement and similar purposes
 */
public class DriveSubsystem extends SubsystemBase {
  private static DriveSubsystem instance;

  public SuperNavx navx = SuperNavx.getInstance();

  private Field2d field = new Field2d();

  private DifferentialDrivePoseEstimator poseEstimator;

  private Alliance alliance;

  private DriveSubsystem() {
    resetEncoders();

    navx.zeroHeading();

    poseEstimator = new DifferentialDrivePoseEstimator(DriveConstants.kDrivekinematics, navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        -ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        new Pose2d());

    poseEstimator.resetPosition(navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        -ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        new Pose2d());

    resetOdometry(new Pose2d(4.29, 1.5, Rotation2d.fromDegrees(0)));

    field.setRobotPose(getPose());

    alliance = DriverStation.getAlliance();
  }

  /**
   * Returns an instance of {@link DriveSubsystem} for singleton purposes.
   * 
   * @return {@link DriveSubsystem} global instance
   */
  public static DriveSubsystem getInstance() {
    if (instance == null) {
      instance = new DriveSubsystem();
    }
    return instance;
  }

  @Override
  public void periodic() {
    updatePosition2D();
    field.setRobotPose(getPose());
    SmartDashboard.putData(field);
  }

  /**
   * Updates the position from the {@link DifferentialDrivePoseEstimator}
   * Also includes vision measurements if present to implement Kalman Filter
   */
  private void updatePosition2D() {
    poseEstimator.update(navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        -ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio));

    LimelightHelpers.Results result = LimelightHelpers.getLatestResults("limelight").targetingResults;

    if (!(result.botpose[0] == 0 && result.botpose[1] == 0) && LimelightHelpers.getTA("limelight") < 30) {
      if (alliance == Alliance.Blue) {
        poseEstimator.addVisionMeasurement(
            LimelightHelpers.toPose2D(result.botpose_wpiblue),
            Timer.getFPGATimestamp() - (result.latency_capture / 1000.0) - (result.latency_pipeline / 1000.0));
      } else if (alliance == Alliance.Red) {
        poseEstimator.addVisionMeasurement(
            LimelightHelpers.toPose2D(result.botpose_wpired),
            Timer.getFPGATimestamp() - (result.latency_capture / 1000.0) - (result.latency_pipeline / 1000.0));
      }
    }
  }

  /**
   * Resets the odometry position along with encoder reading
   * 
   * @param pose The position of the field that the robot is in
   */
  public void resetOdometry(Pose2d pose) {

    resetEncoders();

    poseEstimator.resetPosition(navx.getRotation(),
        ChassisSubsystem.frontLeft.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        -ChassisSubsystem.frontRight.getPosition(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        pose);
  }

  /**
   * Sets encoder readings to zero
   */
  public void resetEncoders() {
    ChassisSubsystem.frontLeft.resetEncoder();
    ChassisSubsystem.frontRight.resetEncoder();
  }

  /**
   * Get the position
   * 
   * @return Estimated position
   */
  public Pose2d getPose() {
    return poseEstimator.getEstimatedPosition();
  }

  /**
   * Gets the speed of the Drive via {@link DifferentialDriveWheelSpeeds}
   * 
   * @return WheelSpeeds
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(
        ChassisSubsystem.frontLeft.getVelocity(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio),
        -ChassisSubsystem.frontRight.getVelocity(DriveConstants.kWheelCircumference, DriveConstants.kGearRatio));
  }

  /**
   * Sets the motor voltage output
   * 
   * @param leftVolts  Left side voltage applied
   * @param rightVolts Right side voltage applied
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    ChassisSubsystem.leftMotorController.setVoltage(leftVolts);
    ChassisSubsystem.rightMotorController.setVoltage(rightVolts);
    ChassisSubsystem.getInstance().getDrive().feed();
  }

  /**
   * Gets current Navx Pitch
   * 
   * @return Navx Pitch
   */
  public double getNavxPitch() {
    return navx.getPitch();
  }

  /**
   * Gets current Navx Roll
   * 
   * @return Navx Roll
   */
  public double getNavxRoll() {
    return navx.getRoll();
  }

  /**
   * Get navx angle
   * 
   * @return Navx angle
   */
  public String getNavxAngle() {
    return navx.getRotation().toString();
  }

  /**
   * Resets navx
   */
  public void resetNavx() {
    navx.reset();
  }
}
