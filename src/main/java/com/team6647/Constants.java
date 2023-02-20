/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647;

import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kDriverControllerPort2 = 1;

    public static final CommandXboxController driverController1 = new CommandXboxController(
        OperatorConstants.kDriverControllerPort);
    public static final CommandXboxController driverController2 = new CommandXboxController(
        OperatorConstants.kDriverControllerPort2);

    public static final double xMultiplier = 1;
    public static final double yMultiplier = 1;

    public static double rampTimeSeconds = 2;
  }

  public static class ShuffleboardConstants {
    private static final String kShuffleboardTabName = "Team 6647";
    public static final ShuffleboardTab kShuffleboardTab = Shuffleboard.getTab(kShuffleboardTabName);
  }

  public static class ChassisConstants {
    public static final int frontLeftID = 1;
    public static final int frontRightID = 2;
    public static final int backLeftID = 3;
    public static final int backRightID = 4;

    public static final StatorCurrentLimitConfiguration motorConfig = new StatorCurrentLimitConfiguration(true, 50, 55,
        2);

    public static final int forwardSolenoidID = 0;
    public static final int backwardSolenoidID = 1;
  }

  public static class ArmConstants {
    public static final int armNeo1ID = 5;
    public static final int armNeo2ID = 6;
    public static final int extendNeoID = 7;

    public static double extendSped = 0.2;

    public static final double kMaxVelocityRadPerSecond = 594.38;
    public static final double kMaxAccelerationRadPerSecSquared = 118.876;

    public static final int extendLimitSwitchPort = 0;
    public static final DigitalInput limitSwitch = new DigitalInput(extendLimitSwitchPort);

    // TODO SET
    public static final double forwardLimit = 0;

    public static final double gearRatio = 1 / 94;
    public static final double startPositionRads = (-Math.PI / 4);

    public static final double feedkS = 0.00;
    public static final double feedkG = 1.38;
    public static final double feedkV = 0.12;
    public static final double feedkA = 0.03;

    public static final double pivotkP = 0;
    public static final double pivotkI = 0;
    public static final double pivotkD = 0;
  }

  public static class ClawConstants {
    public static final int clawNeo1ID = 8;
    public static final int clawNeo2ID = 9;

    public static final int clawForwardPistonID = 7;
    public static final int clawBackwarddPistonID = 4;

    public static final double clawSpeed = 1;
  }

  public static class DriveConstants {
    // TODO SET
    public static final double ksVolts = 0.59085; // 0.1519
    public static final double kvVoltSecondsPerMeter = 2.4174; // 1.6277 //0.63 //2.4174
    public static final double kaVoltSecondsSquaredPerMeter = 0.14582; // 0.22002 //0.03
    public static final double kpDriveVelocity = 2.5106; // 2.0958 //0.2

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
    public static final double kWheelDiameterMeter = Units.inchesToMeters(kWheelRadiusInches * 2);
    public static final double kWheelCircumference = kWheelDiameterMeter * Math.PI;
  }

  public static class VisionConstants {
    public static final double cameraHeight = Units.inchesToMeters(2);
    public static final double targetHeight = Units.inchesToMeters(27);
    public static final double cameraPitch = Units.degreesToRadians(0);
    public static final double goalRange = 0.36;
  }
}
