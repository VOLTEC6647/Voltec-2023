// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.subsystems;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {

  private static VisionSubsystem instance;

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
  PhotonCamera camera;
  PhotonPoseEstimator pose;

  public VisionSubsystem(String cameraName) {
    camera = new PhotonCamera(cameraName);
    camera.setPipelineIndex(0);

    try {
      field = new AprilTagFieldLayout(AprilTagFields.k2023ChargedUp.m_resourceFile);
    } catch (Exception e) {
      DriverStation.reportError("Cannot find field layout", false);
    }

    pose = new PhotonPoseEstimator(field, PoseStrategy.CLOSEST_TO_LAST_POSE, camera, null);
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
    // This method will be called once per scheduler run
  }
}
