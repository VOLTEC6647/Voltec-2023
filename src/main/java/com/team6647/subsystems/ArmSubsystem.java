// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.subsystems;

import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.team6647.Constants.ArmConstants;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
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

  private static ArmFeedforward feedforward = new ArmFeedforward(ArmConstants.feedkS, ArmConstants.feedkG,
      ArmConstants.feedkV, ArmConstants.feedkA);

  private static PIDController pidController = new PIDController(ArmConstants.pivotkP, ArmConstants.pivotkI,
      ArmConstants.pivotkD);

  public double setpoint;

  private ArmSubsystem() {
    pivotSpark1.follow(pivotSpark1, true);

    pidController.setTolerance(1.0);
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

  /**
   * Sets the position to the corresponding angle and velocity
   * 
   * @param degree Desired degree
   * @param velocity Desired velocity
   */
  public void setAngle(double degree, double velocity) {
    setpoint = degree;
    pivotSpark1.setVoltage(
        feedforward.calculate(degree, velocity) + pidController.calculate(pivotSpark1.getPosition(), degree));
  }

  public void stopPivot(){
    pivotSpark1.set(0);
    pivotSpark2.set(0);
  }

  /**
   * Extends the arm at the desired speed
   * 
   * @param speed
   */
  public void extendArm(double speed) {
    extendingSpark.set(speed);
  }

  /**
   * Gets the current position of the extending spark
   * 
   * @return Current position
   */
  public double getExtendPosition() {
    return extendingSpark.getPosition();
  }

  /**
   * Sets the extending position to 0
   */
  public void resetExtendPosition() {
    extendingSpark.resetEncoder();
  }

  /**
   * Gets the velocity of pivot1
   * 
   * @return Pivot1 Velocity
   */
  public double getPivot1Velocity() {
    return pivotSpark1.getVelocity();
  }

  /**
   * Gets the velocity of pivot2
   * 
   * @return Pivot2 Velocity
   */
  public double getPivot2Velocity() {
    return pivotSpark2.getVelocity();
  }

  @Override
  public void periodic() {
  }
}
