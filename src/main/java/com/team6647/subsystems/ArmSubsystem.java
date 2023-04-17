/**
 * Written by Juan Pablo Gutíerrez
 */
package com.team6647.subsystems;

import com.andromedalib.math.Functions;
import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.andromedalib.sensors.RevAbsoluteEncoder;
import com.team6647.utils.Constants.ArmConstants;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  private static ArmSubsystem instance;

  private static SuperSparkMax pivotSpark1 = new SuperSparkMax(ArmConstants.armNeo1ID, GlobalIdleMode.brake, false, 50);
  private static SuperSparkMax pivotSpark2 = new SuperSparkMax(ArmConstants.armNeo2ID, GlobalIdleMode.brake, true, 50);


  private static ProfiledPIDController profiledController = new ProfiledPIDController(ArmConstants.pivotkP, 0, 0,
      new TrapezoidProfile.Constraints(60, 50));

  private static RevAbsoluteEncoder armEncoder = new RevAbsoluteEncoder(7);

  private double pidOutput;
  private double feedOutput;

  private double setPoint;
  private double totalOutput;

  public ArmSubsystem() {

    pivotSpark2.follow(pivotSpark1, true);

    this.setPoint = -14; //-149

    resetPID();
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
    calculateArm();

    SmartDashboard.putNumber("Arm encoder", armEncoder.getAbsolutePosition() -
        180);
  }

  /**
   * Calculates the Arm PID and FeedForward
   * Applies these values
   */
  public void calculateArm() {
    double actualPoint = getArmPosition();

    double pidOut = profiledController.calculate(actualPoint, setPoint);

    pidOut = Functions.clamp(pidOut, -0.4, 0.4);

    double feedForwardValue = 0;

    if (actualPoint > 0) {
      feedForwardValue = (actualPoint / 112) * 0.31 * 12;
      if (actualPoint > 90) {
        feedForwardValue -= (actualPoint - 90) / 112 * 12;
      }
    } else {
      feedForwardValue = (actualPoint / -156) * 0.31 * 12;
      if (actualPoint < -90) {
        feedForwardValue -= (actualPoint + 90) / -156 * 12;
      }
    }

    double total = (pidOut * 12) + feedForwardValue;

    total = Functions.clamp(total, -12, 12);

    this.totalOutput = total;
    this.feedOutput = feedForwardValue;
    this.pidOutput = pidOut;

    pivotSpark1.setVoltage(total);
  }

  /**
   * Gets encoder position converted to degrees
   * 
   * @return Encoder measurement
   */
  public static double getArmPosition() {
    return (pivotSpark1.getPosition() / ((double) 188 / 360));// - 154.2;// - 144.52;// - 148.7;
  }

  /**
   * Sets arm Position to zero
   */
  public void resetArmPositiom() {
    pivotSpark1.resetEncoder();
  }

  /**
   * Security method for movement. Use this method always while moving the arm
   * 
   * @param change Setpoint change
   */
  public void changeSetpoint(double change) {
    if (change < -156 || change > 112) // TUNE
      change = Functions.clamp(change, -156, 112);

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

  public void resetPID() {
    profiledController.reset(getArmPosition(), 0);
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

  /**
   * Sets arm position to that of the absolute encoder
   */
  public static void startArm(){
    double pos = (47.0 * (armEncoder.getAbsolutePosition() -180)) / 90.0;
    pivotSpark1.setPosition(pos);
  }
}
