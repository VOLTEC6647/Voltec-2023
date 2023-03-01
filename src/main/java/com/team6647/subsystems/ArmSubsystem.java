/**
 * Written by Juan Pablo Gutíerrez
 */
package com.team6647.subsystems;

import com.andromedalib.math.Functions;
import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.team6647.Constants.ArmConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  private static ArmSubsystem instance;

  private static SuperSparkMax pivotSpark1 = new SuperSparkMax(ArmConstants.armNeo1ID, GlobalIdleMode.brake, false, 50);
  private static SuperSparkMax pivotSpark2 = new SuperSparkMax(ArmConstants.armNeo2ID, GlobalIdleMode.brake, true, 50);

  private static SuperSparkMax extendingSpark = new SuperSparkMax(ArmConstants.extendNeoID, GlobalIdleMode.brake, true,
      50);

  private static DigitalInput limitSwitch = new DigitalInput(3);

  private static PIDController pidController = new PIDController(ArmConstants.pivotkP, 0,
      0);

  private static PIDController dynamicController = new PIDController(ArmConstants.dynamickP, 0, 0);

  private double pidOutput;
  private double feedOutput;

  private double setPoint;
  private double totalOutput;
  private double error;

  public ArmSubsystem() {
    pivotSpark2.follow(pivotSpark1, true);

    this.setPoint = -140; // When initialize
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

  @Override
  public void periodic() {

    double actualPoint = getMeasurement();

    double error = Math.abs(actualPoint - setPoint);
    this.error = error;

    double pidOut = 0;

    if (error < 10) {
      pidOut = dynamicController.calculate(actualPoint, this.setPoint);
      pidOut = Functions.clamp(pidOut, -0.5, 0.5);
    } else {
      // MEJOR PARA EL MANUAl
      pidOut = pidController.calculate(actualPoint, this.setPoint);
      pidOut = Functions.clamp(pidOut, -0.4, 0.4);
    }

    double feedForwardValue = 0;

    if (actualPoint > 0) {
      feedForwardValue = (actualPoint / 143) * 0.31 * 12;
      if (actualPoint > 90) {
        feedForwardValue -= (actualPoint - 90) / 143 * 12;
      }
    } else {
      feedForwardValue = (actualPoint / -148) * 0.31 * 12;
      if (actualPoint < -90) {
        feedForwardValue -= (actualPoint + 90) / -148 * 12;
      }
    }

    this.feedOutput = feedForwardValue;
    this.pidOutput = pidOut;

    double total = (pidOut * 12) + feedForwardValue;

    total = Functions.clamp(total, -12, 12);

    this.totalOutput = total;

    pivotSpark1.setVoltage(total);
  }

  /**
   * Gets encoder measuremt converted to degrees
   * 
   * @return Encoder measurement
   */
  public double getMeasurement() {
    return (pivotSpark1.getPosition() / ((double) 94 / 360)) - 148.7;
  }

  /**
   * Security method for movement. Use this method always while moving the arm
   * 
   * @param change Setpoint change
   */
  public void changeSetpoint(double change) {
    if (change < -148 || change > 143) // TUNE
      change = Functions.clamp(change, -148, 143);

    this.setPoint = change;
  }

  /**
   * Adds a given value to the setpoint
   * 
   * @param value Value to be added
   */
  public void manualControl(double value) {
    changeSetpoint(setPoint + value * 1);
  }

  /**
   * Extends the arm at the desired speed
   * 
   * @param speed Arm speed
   */
  public void extendArm(double speed) {
    extendingSpark.set(speed);
  }

  /**
   * Gets the state of the limit switch
   * 
   * @return Current limit switch state
   */
  public boolean getLimitState() {
    return limitSwitch.get();
  }

  /**
   * Gets the current position of the extending spark
   * 
   * @return Current position
   */
  public double getExtendPosition() {
    return extendingSpark.get();
  }

  /**
   * Sets the extending position to 0
   */
  public void resetExtendPosition() {
    extendingSpark.resetEncoder();
  }

  /**
   * Gets the voltage applied to Pivot1 Voltage
   * 
   * @return Voltage applied
   */
  public double getPivot1Voltage() {
    return pivotSpark1.getBusVoltage();
  }

  /**
   * Gets the FeedForward's Output
   * 
   * @return FeedForward output
   */
  public double getFeedOutput() {
    return feedOutput;
  }

  /**
   * Gets the PID output
   * 
   * @return PID Output
   */
  public double getPidOutput() {
    return pidOutput;
  }

  /**
   * Gets the current setpoint
   * 
   * @return Current setpoint
   */
  public double getSetpoint() {
    return setPoint;
  }

  /**
   * Gets total output applied to the motors
   * 
   * @return Output applied to the motors
   */
  public double getTotal() {
    return totalOutput;
  }

  /**
   * Gets the difference between the setpoint and the current position;
   * 
   * @return Arm error
   */
  public double getError() {
    return error;
  }

  /**
   * Gets the temperature of Pivot1
   * 
   * @return Pivot1 Temperature
   */
  public double getPivot1Temp() {
    return pivotSpark1.getMotorTemperature();
  }

  /**
   * Gets tne temperature of Pivot2
   * 
   * @return Pivot2 Temperature
   */
  public double getPivot2Temp() {
    return pivotSpark2.getMotorTemperature();
  }
}
