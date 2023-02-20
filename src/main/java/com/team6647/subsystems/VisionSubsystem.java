/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

import com.andromedalib.vision.LimelightCamera;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {

  private static VisionSubsystem instance;

  DriveSubsystem drive;

  /*
   * AprilTag hi;
   * AprilTagFieldLayout j = new
   * AprilTagFieldLayout(AprilTagFields.k2023ChargedUp.toString());
   * AprilTagDetection a;
   * AprilTagDetector x;
   * AprilTagFields xzs;
   * AprilTagPoseEstimate aX;
   * AprilTagPoseEstimator as;
   */
  /** Cretes a new VisionSubsystem. */
  AprilTagFieldLayout field;
  PhotonCamera photonCamera;
  PhotonPoseEstimator pose;

  LimelightCamera limeCamera = new LimelightCamera("Limelight");

  public VisionSubsystem(String cameraName) {
    photonCamera = new PhotonCamera(cameraName);
    photonCamera.setPipelineIndex(0);
    drive = DriveSubsystem.getInstance();

    try {
      field = new AprilTagFieldLayout(AprilTagFields.k2023ChargedUp.m_resourceFile);
    } catch (Exception e) {
      DriverStation.reportError("Cannot find field layout", false);
    }

    pose = new PhotonPoseEstimator(field, PoseStrategy.CLOSEST_TO_CAMERA_HEIGHT, photonCamera, null);
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
    
  }
}
