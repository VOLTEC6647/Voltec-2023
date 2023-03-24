/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.andromedalib.vision.LimelightCamera;
import com.team6647.utils.Constants.DriveConstants;
import com.team6647.utils.Constants.VisionConstants;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  private static VisionSubsystem instance;
  private static ChassisSubsystem chassis;

  private AprilTagFieldLayout field;
  private PhotonCamera photonCamera;
  private PhotonPoseEstimator photonPoseEstimator;

  private PIDController forwardController = new PIDController(DriveConstants.kpDriveVelocity, 0, 0);
  private PIDController turnController = new PIDController(DriveConstants.kpDriveVelocity, 0, 0);

  private LimelightCamera limelightCamera;

  private boolean aimingPhoton;
  private boolean aimingLimelight;

  private VisionSubsystem(String cameraName) {
    photonCamera = new PhotonCamera(cameraName);
    photonCamera.setPipelineIndex(VisionConstants.aprilPhotonPipe);

    limelightCamera = new LimelightCamera();
    limelightCamera.setPipeline(VisionConstants.retroLimePipe);

    try {
      field = new AprilTagFieldLayout(AprilTagFields.k2023ChargedUp.m_resourceFile);
      // TODO SET THE ROBOT TO POSE
      photonPoseEstimator = new PhotonPoseEstimator(field, PoseStrategy.CLOSEST_TO_CAMERA_HEIGHT, photonCamera, null);
    } catch (Exception e) {
      DriverStation.reportWarning("Cannot find field layout", true);
      photonPoseEstimator = null;
    }

    chassis = ChassisSubsystem.getInstance();

  }

  /**
   * Returns an instance of {@link VisionSubsystem} for singleton purposes.
   * 
   * @return {@link VisionSubsystem} global instance
   */
  public static VisionSubsystem getInstance(String name) {
    if (instance == null) {
      instance = new VisionSubsystem(name);
    }
    return instance;
  }

  @Override
  public void periodic() {
    if (aimingPhoton) {
      calculatePhoton();
    }

    if (aimingLimelight) {
      calculateLime();
    } else {
      setLimeLEDMode(1);
    }
  }

  /**
   * Calculates the position to the AprilTags target and applies it to chassis
   */
  public void calculatePhoton() {
    var result = photonCamera.getLatestResult();

    if (!result.hasTargets())
      return;

    PhotonTrackedTarget target = result.getBestTarget();

    double range = PhotonUtils.calculateDistanceToTargetMeters(VisionConstants.cameraHeight,
        VisionConstants.targetHeight, VisionConstants.cameraPitch, Units.degreesToRadians(target.getPitch()));

    double forwardSpeed = -forwardController.calculate(range, VisionConstants.goalRange);
    double turnSpeed = -turnController.calculate(result.getBestTarget().getPitch(), 0);

    double leftSpeed = forwardSpeed + turnSpeed;
    double rightSpeed = forwardSpeed - turnSpeed;

    chassis.tankDrive(leftSpeed * 0.5, rightSpeed * 0.5);
    chassis.getDrive().feed();
  }

  /**
   * Get the Estimated Global Pose from the {@link PhotonPoseEstimator}. Used to
   * update the {@link DifferentialDrivePoseEstimator}
   * 
   * @param prevEstimatedPosition
   * @return
   */
  public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedPosition) {
    if (photonPoseEstimator == null) {
      return Optional.empty();
    }

    photonPoseEstimator.setReferencePose(prevEstimatedPosition);
    return photonPoseEstimator.update();
  }

  /**
   * Calculates the position to the Limelight Target and applies it to the chassis
   */
  public void calculateLime() {
    setLimeLEDMode(3);
    if (!limelightCamera.hasValidTarget()) {
      System.out.println("NO VALID TARGET");
      return;
    }

    double tx = limelightCamera.getX(),
        ty = limelightCamera.getY();

    double kpAim = VisionConstants.kpAim, kpDistance = VisionConstants.kpDistance,
        min_aim_command = VisionConstants.min_aim_command;

    var steeringAdjust = tx > 1 ? (kpAim * -tx - min_aim_command)
        : (tx < -1 ? (kpAim * -tx + min_aim_command) : 0.0);
    var distanceAdjust = kpDistance * ty;

    double leftSpeed = distanceAdjust + steeringAdjust;
    double rightSpeed = distanceAdjust - steeringAdjust;
    SmartDashboard.putNumber("Lime left speed",leftSpeed);
    SmartDashboard.putNumber("Lime right speed",rightSpeed);

    chassis.getDrive().feed();
    chassis.tankDrive((leftSpeed) * 0.5, (rightSpeed) * 0.5);
  }

  /**
   * Sets the Limelight LED mode
   */
  public void setLimeLEDMode(int mode) {
    limelightCamera.setLedMode(mode);
  }

  /**
   * Toggles the aim for PhotonCamera
   */
  public void togglePhotonAim() {
    aimingPhoton = !aimingPhoton;
  }

  /**
   * Gets the status of the PhotonAim
   * 
   * @return PhotonAim status
   */
  public boolean getPhotonAim() {
    return aimingPhoton;
  }

  /**
   * Toggles the aim for LimelightCamera
   */
  public void toggleLimelightAim() {
    aimingLimelight = !aimingLimelight;
  }

  /**
   * Gets the status of the LimelightAim
   * 
   * @return LimelightAim status
   */
  public boolean getLimelightAim() {
    return aimingLimelight;
  }

  /**
   * Sets the {@link LimelightCamera} pipeline
   * 
   * @param pipeLine Pipeline to be used
   */
  public void setLimePipe(int pipeLine) {
    limelightCamera.setPipeline(pipeLine);
  }

  /**
   * Get current Limelight Pipe
   * 
   * @return Current Limelight pipeline
   */
  public int getLimePipe() {
    return (int) limelightCamera.getPipeline();
  }

  /**
   * Sets the {@link PhotonCamera} pipeline
   * 
   * @param pipeLine Pipeline to be used
   */
  public void setPhotonPipe(int pipeLine) {
    photonCamera.setPipelineIndex(pipeLine);
  }

  /**
   * Get current PhotonCamera Pipe
   * 
   * @return Current PhotonCamera pipeline
   */
  public int getPhotonPipe() {
    return photonCamera.getPipelineIndex();
  }
}
