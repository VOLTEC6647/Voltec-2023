/**
 * Written by Juan Pablo Gutierrez
 */

package com.team6647.subsystems;

import com.andromedalib.math.Functions;
import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.andromedalib.sensors.RevAbsoluteEncoder;
import com.team6647.utils.Constants.ClawConstants;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WristSubsystem extends SubsystemBase {
  private static WristSubsystem instance;

  private static SuperSparkMax wristNeo = new SuperSparkMax(ClawConstants.wristNeoID, GlobalIdleMode.brake, false, 50);

  private static ProfiledPIDController wrisController = new ProfiledPIDController(ClawConstants.wristkP, 0, 0,
      new TrapezoidProfile.Constraints(145, 75)); // 55, 20

  private static RevAbsoluteEncoder wristEncoder = new RevAbsoluteEncoder(0);
  private static DigitalInput topLimitSwitch = new DigitalInput(4);
  private static DigitalInput bottomLimitSwitch = new DigitalInput(5);

  double setpoint = 0;

  /** Creates a new WristSubsystem. */
  public WristSubsystem() {

    wrisController.reset(getWristPosition());

    setpoint = 114;
  }

  /**
   * Returns an instance of {@link WristSubsystem} for singleton purposes.
   * 
   * @return {@link WristSubsystem} global instance
   */
  public static WristSubsystem getInstance() {
    if (instance == null) {
      instance = new WristSubsystem();
    }
    return instance;
  }

  @Override
  public void periodic() {
    calculateWrist();

    SmartDashboard.putNumber("Position", getWristPosition());
    SmartDashboard.putNumber("Setpoint", setpoint);
    SmartDashboard.putBoolean("Top", topLimitSwitch.get());
    SmartDashboard.putBoolean("Bottom", bottomLimitSwitch.get());
    SmartDashboard.putNumber("Encoder", wristEncoder.getAbsolutePosition() - 90);
  }

  public void calculateWrist() {
    double actualPoint = (double) getWristPosition();

    double pidOut = wrisController.calculate(actualPoint, setpoint);

    SmartDashboard.putNumber("PIDOUT CLAW", pidOut);
    pidOut = Functions.clamp(pidOut, -3, 3);

    double feedForwardValue = 0;

    double total = (pidOut * 12) + feedForwardValue;

    total = Functions.clamp(total, -12, 12);

    SmartDashboard.putNumber("Total", total);

    safeMoveClaw(total);
  }

  /**
   * Safety method for moving the claw;
   * 
   * @param voltage
   */
  public void safeMoveClaw(double voltage) {
    boolean stopped = false;
    double appliedOutput = voltage;
    if (topLimit()) {
      if (voltage < 0) {
        stopped = false;
        appliedOutput = voltage;
        wristNeo.setVoltage(voltage);
        return;
      } else {
        stopped = true;
        appliedOutput = 0;
        changeSetpoint(this.setpoint);
        wristNeo.setVoltage(0);
        return;
      }
    } else if (bottomLimit()) {
      if (voltage > 0) {
        stopped = false;
        appliedOutput = voltage;
        wristNeo.setVoltage(voltage);
        return;
      } else {
        stopped = true;
        appliedOutput = 0;
        changeSetpoint(this.setpoint);
        wristNeo.setVoltage(0);
        return;
      }
    }
    SmartDashboard.putBoolean("STOPPED", stopped);

    SmartDashboard.putNumber("Applied outout", appliedOutput);
    wristNeo.setVoltage(voltage);
  }

  /**
   * Security method for changing the setpoint
   * 
   * @param change New setpoint
   */
  public void changeSetpoint(double change) {
    if (change < ClawConstants.minClaw || change > ClawConstants.maxClaw) // TUNE
      change = Functions.clamp(change, ClawConstants.minClaw, ClawConstants.maxClaw);

    this.setpoint = change;
  }

  /**
   * Gets the relative wristPosition
   * 
   * @return Relative position
   */
  public double getWristPosition() {
    return (wristNeo.getPosition() / ((double) 252 / 360));
  }

  /**
   * Adds a given value to the setpoint
   * 
   * @param value Value to be added
   */
  public void manualControl(double value) {
    changeSetpoint(setpoint + value * 1);
  }

  /**
   * Reset PID
   * 
   * @return
   */
  public void resetPID() {
    wrisController.reset(getWristPosition(), 0);
  }

  /**
   * Gets the status of the wrist top limit
   * 
   * @return Wrist Top limit status
   */
  public boolean topLimit() {
    return topLimitSwitch.get();
  }

  /**
   * Gets the status of the wrist bottom limit
   * 
   * @return Wrist bottom limit status
   */
  public boolean bottomLimit() {
    return bottomLimitSwitch.get();
  }

  public double getSetpoint() {
    return setpoint;
  }

  /**
   * Method for setting the wrist to the position of the absolute encoder
   */
  public static void startClaw() {
    double pos = (7 * (wristEncoder.getAbsolutePosition() - 90)) / 10;
    wristNeo.setPosition(pos);
    wristNeo.setMode(GlobalIdleMode.brake);
  }

  /**
   * Mode for easy movement while in disabled
   */
  public static void disabledClaw() {
    wristNeo.setMode(GlobalIdleMode.Coast);
  }
}
