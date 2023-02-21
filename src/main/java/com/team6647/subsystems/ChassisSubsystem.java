/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import com.andromedalib.motorControllers.SuperTalonFX;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.andromedalib.subsystems.DifferentialDriveSubsystem;
import com.team6647.Constants.ChassisConstants;
import com.team6647.Constants.DriveConstants;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * Singleton class. It controls the actual movement and interactions in the
 * drivetrain
 */
public class ChassisSubsystem extends DifferentialDriveSubsystem {
  private static ChassisSubsystem instance;

  static SuperTalonFX frontLeft = new SuperTalonFX(ChassisConstants.frontLeftID,
      ChassisConstants.motorConfig);
  static SuperTalonFX frontRight = new SuperTalonFX(ChassisConstants.frontRightID,
      ChassisConstants.motorConfig);
  static SuperTalonFX backLeft = new SuperTalonFX(ChassisConstants.backLeftID,
      ChassisConstants.motorConfig);
  static SuperTalonFX backRight = new SuperTalonFX(ChassisConstants.backRightID,
      ChassisConstants.motorConfig);

  private static SuperTalonFX[] listLeft = { frontLeft, backLeft };
  private static SuperTalonFX[] listRight = { frontRight, backRight };

  private static Solenoid forwardSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM,
      ChassisConstants.forwardSolenoidID);
  private static Solenoid backSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM,
      ChassisConstants.backwardSolenoidID);

  // TOOD DEBUG
  private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DriveConstants.ksVolts,
      DriveConstants.kvVoltSecondsPerMeter, DriveConstants.kaVoltSecondsSquaredPerMeter);

  /**
   * Creates a new ChassisSubsystem.
   */
  private ChassisSubsystem() {
    super(listLeft, listRight, "Right");
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

  /**
   * Changes reduction state
   */
  public static void toggleReduction() {
    var currentState = forwardSolenoid.get();

    forwardSolenoid.set(!currentState);
    backSolenoid.set(currentState);
  }

  /**
   * Uses a FeedForward control to aid in TankDrive movement
   * 
   * @param leftSpeed  Left side speed
   * @param rightSpeed Right side speed
   */
  public void tankDriveFeedForward(double leftSpeed, double rightSpeed) {
    leftMotorController.setVoltage(feedforward.calculate(leftSpeed));
    rightMotorController.setVoltage(feedforward.calculate(rightSpeed));
  }

  /**
   * Sets all motors to brake
   * Be careful to not damage motors
   */
  public void setBrake(){
    frontLeft.setMode(GlobalIdleMode.brake);
    frontRight.setMode(GlobalIdleMode.brake);
    backLeft.setMode(GlobalIdleMode.brake);
    backRight.setMode(GlobalIdleMode.brake);
  }
}
