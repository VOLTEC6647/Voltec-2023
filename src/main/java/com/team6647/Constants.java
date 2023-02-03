// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kDriverControllerPort2 = 1;

    public static final double xMultiplier = 1;
    public static final double yMultiplier = 1;
  }

  public static class ChassisConstants {
    public static final int frontLeftID = 1;
    public static final int frontRightID = 2;
    public static final int backLeftID = 3;
    public static final int backRightID = 4;

    public static final StatorCurrentLimitConfiguration motorConfig = new StatorCurrentLimitConfiguration(true, 25, 30,
        2);
  }

  public static class ArmConstants {
    public static final int armNeo1ID = 5;
    public static final int armNeo2ID = 6;

    public static final double pivotkP = 0;
    public static final double pivotkI = 0;
    public static final double pivotkD = 0;
    public static final double pivotkp = 0;
  }

  public static class DriveConstants {
    public static final double ksVolts = 0;
    public static final double kvVoltSecondsPerMeter = 0;
    public static final double kaVoltSecondsSquaredPerMeter = 0;
    public static final double kpDriveVelocity = 0;

    public static final double kTrackWidthMeters = Units.inchesToMeters(19);
    public static final DifferentialDriveKinematics kDrivekinematics = new DifferentialDriveKinematics(
        kTrackWidthMeters);

    public static final double kramseteB = 2;
    public static final double kRamseteZeta = 0.7;

    /**
     * Make sure that the reduction is not in place
     */
    public static final double kGearRatio = 7.29;

    public static final double kWheelRadiusInches = 3;
    public static final double kWheelDiameterMeter = Units.inchesToMeters(kWheelRadiusInches);
    public static final double kWheelCircumference = kWheelDiameterMeter * Math.PI;
  }
}
