// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team6647;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;

public final class Constants {
  public static class ChassisConstants {
    public static final int frontLeftID = 1;
    public static final int frontRightID = 2;
    public static final int backLeftID = 3;
    public static final int backRightID = 4;

    public static final StatorCurrentLimitConfiguration motorConfig = new StatorCurrentLimitConfiguration(true, 25, 30,
        2);
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kDriverControllerPort2 = 1;
  }
}
