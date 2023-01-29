// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.subsystems;

import com.andromedalib.motorControllers.SuperTalonFX;
import com.andromedalib.subsystems.DifferentialDriveSubsystem;
import com.team6647.Constants.ChassisConstants;

/**
 * Singleton class
 */
public class ChassisSubsystem extends DifferentialDriveSubsystem {
  private static ChassisSubsystem instance;

  static SuperTalonFX frontLeft = new SuperTalonFX(ChassisConstants.frontLeftID, false,
      ChassisConstants.motorConfig);
  static SuperTalonFX frontRight = new SuperTalonFX(ChassisConstants.frontRightID,
      false,
      ChassisConstants.motorConfig);
  static SuperTalonFX backLeft = new SuperTalonFX(ChassisConstants.backLeftID,
      false,
      ChassisConstants.motorConfig);
  static SuperTalonFX backRight = new SuperTalonFX(ChassisConstants.backRightID,
      false,
      ChassisConstants.motorConfig);

  static SuperTalonFX[] listLeft = { frontLeft, backLeft };
  static SuperTalonFX[] listRight = { frontRight, backRight };

  /**
   * Creates a new ChassisSubsystem.
   */
  private ChassisSubsystem() {
    super(listLeft, listRight);
  }

  /**
   * Returns an instance of {@link ChassisSubsystem} for singleton purposes.
   * 
   * @return {@link ChassisSubsystem} global instance
   */
  public static ChassisSubsystem getInstance() {
    if (instance == null) {
      instance = new ChassisSubsystem();
    }
    return instance;
  }

  @Override
  public void outputTelemetry() {
  }

}
