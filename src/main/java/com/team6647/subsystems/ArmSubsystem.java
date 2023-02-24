/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.subsystems;

import com.andromedalib.math.Conversions;
import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.team6647.Constants.ArmConstants;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;

public class ArmSubsystem extends ProfiledPIDSubsystem {
  private static ArmSubsystem instance;

  private static SuperSparkMax pivotSpark1 = new SuperSparkMax(ArmConstants.armNeo1ID, GlobalIdleMode.brake, false, 50);
  private static SuperSparkMax pivotSpark2 = new SuperSparkMax(ArmConstants.armNeo2ID, GlobalIdleMode.brake, true, 50);

  private static SuperSparkMax extendingSpark = new SuperSparkMax(ArmConstants.extendNeoID, GlobalIdleMode.brake, true,
      50);

  private static ArmFeedforward feedforward = new ArmFeedforward(ArmConstants.feedkS, ArmConstants.feedkG,
      ArmConstants.feedkV, ArmConstants.feedkA);

  private static DigitalInput limitSwitch = new DigitalInput(3);

  private double pidOutput;
  private double feedOutput;

  public ArmSubsystem() {
    super(
        new ProfiledPIDController(
            ArmConstants.pivotkP,
            ArmConstants.pivotkI,
            ArmConstants.pivotkD,
            // The motion profile constraints
            new TrapezoidProfile.Constraints(ArmConstants.kMaxVelocityRadPerSecond,
                ArmConstants.kMaxAccelerationRadPerSecSquared)));
    pivotSpark2.follow(pivotSpark1, true);

    setGoal(ArmConstants.startPositionRads);
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
    super.periodic();
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    this.pidOutput = output;
    double feedForwardValue = feedforward.calculate(setpoint.position, setpoint.velocity);

    feedOutput = feedForwardValue;
    pivotSpark1.setVoltage(feedForwardValue);
  }

  //TODO SET TO CORRECT CONVERSION
  @Override
  public double getMeasurement() {
    return pivotSpark1.getPosition() + ArmConstants.startPositionRads;
  }

  /**
   * Sets the angle via a defined speed
   * 
   * @param speed Speed for the arm
   */
  public void setAngle(double speed) {
    disable();
    pivotSpark1.set(speed);
    enable();
  }

  /**
   * Completely resets setpoint & encoder data
   */
  public void resetEverything() {
    stopPivot();
    pivotSpark1.resetEncoder();
  }

  /**
   * Stops the PID control and sets the motors to 0
   */
  public void stopPivot() {
    disable();
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
   * Gets the state of the limit switch
   * 
   * @return
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

  /**
   * Gets the position of Pivo1
   * 
   * @return Pivot1 Position
   */
  public double getPivot1Position() {
    return Conversions.neoToDegrees(pivotSpark1.getPosition(), ArmConstants.gearRatio);
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
}
