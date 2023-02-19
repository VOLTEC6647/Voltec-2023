// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.subsystems;

import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.team6647.Constants.ArmConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Singleton class
 */
public class ArmSubsystem extends SubsystemBase {
  private static ArmSubsystem instance;

  private static SuperSparkMax pivotSpark1 = new SuperSparkMax(ArmConstants.armNeo1ID, GlobalIdleMode.brake, false, 50);
  private static SuperSparkMax pivotSpark2 = new SuperSparkMax(ArmConstants.armNeo2ID, GlobalIdleMode.brake, true, 50);

  private static SuperSparkMax extendingSpark = new SuperSparkMax(ArmConstants.extendNeoID, GlobalIdleMode.brake, true,
      50);

  /*
   * private SparkMaxPIDController pivotController;
   * 
   * private ArmFeedforward armFeedforward;
   */

  /** Creates a new ArmSubsystem. */
  private ArmSubsystem() {
    /*
     * pivotController = pivotSpark.getPIDController();
     * pivotController.setP(0);
     */

  }

  /**
   * Returns an instance of {@link ArmSubsystem} for singleton purposes.
   * 
   * @return {@link ArmSubsystem} global instance
   */
  public static ArmSubsystem getInstance() {
    if (instance == null) {
      instance = new ArmSubsystem();
    }
    return instance;
  }

  public void setAngle(double degree) {
    pivotSpark1.set(degree);
    pivotSpark2.set(degree);
  }

  public void extendArm(double speed) {
    extendingSpark.set(speed);
  }

  public double getExtendPosition(){
    return extendingSpark.getPosition();
  }

  public void resetExtendPosition(){
    extendingSpark.resetEncoder();
  }

  public double getPivot1Velocity() {
    return pivotSpark1.getVelocity();
  }

  public double getPivot2Velocity() {
    return pivotSpark2.getVelocity();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
