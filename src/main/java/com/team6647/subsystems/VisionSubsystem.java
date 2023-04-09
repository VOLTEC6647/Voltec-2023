/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import com.andromedalib.vision.LimelightCamera;
import com.team6647.utils.Constants.VisionConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  private static VisionSubsystem instance;
  private static ChassisSubsystem chassis;

  private LimelightCamera limelightCamera;

  private boolean aimingLimelight;

  private VisionSubsystem() {
    limelightCamera = new LimelightCamera();
    limelightCamera.setPipeline(VisionConstants.retroLimePipe);

    chassis = ChassisSubsystem.getInstance();

    setLimeLEDMode(3);

  }

  /**
   * Returns an instance of {@link VisionSubsystem} for singleton purposes.
   * 
   * @return {@link VisionSubsystem} global instance
   */
  public static VisionSubsystem getInstance() {
    if (instance == null) {
      instance = new VisionSubsystem();
    }
    return instance;
  }

  @Override
  public void periodic() {
    if (aimingLimelight) {
      calculateLime();
    }
  }

  /**
   * Calculates the position to the Limelight Target and applies it to the chassis
   */
  public void calculateLime() {
    setLimeLEDMode(3);
    if (limelightCamera.hasValidTarget() == 0) {
      chassis.getDrive().feed();
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

  public LimelightCamera getCamera(){
    return limelightCamera;
  }
}
