// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647.subsystems;

import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.andromedalib.subsystems.DifferentialDriveSubsystem;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.team6647.Constants.ChassisConstants;

public class ChassisSubsystem extends DifferentialDriveSubsystem {
  private static ChassisSubsystem instance;

  static SuperSparkMax frontLeft = new SuperSparkMax(ChassisConstants.frontLeftID, MotorType.kBrushless, GlobalIdleMode.Coast, false, 20, 0);
  static SuperSparkMax frontRight = new SuperSparkMax(ChassisConstants.frontRightID, MotorType.kBrushless, GlobalIdleMode.Coast, false, 20, 0); 
  static SuperSparkMax backLeft = new SuperSparkMax(ChassisConstants.backLeftID, MotorType.kBrushless, GlobalIdleMode.Coast, false, 20, 0);
  static SuperSparkMax backRight = new SuperSparkMax(ChassisConstants.backRightID, MotorType.kBrushless, GlobalIdleMode.Coast, false, 20, 0); 

  static SuperSparkMax[] listLeft = { frontLeft, backLeft };
  static SuperSparkMax[] listRight = { frontRight, backRight };

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
  public void outputTelemetry() {}

}
