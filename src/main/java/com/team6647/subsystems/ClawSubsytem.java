/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import com.andromedalib.math.Functions;
import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.andromedalib.sensors.RevThroughBoreEncoder;
import com.team6647.utils.Constants.ClawConstants;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsytem extends SubsystemBase {
  private static ClawSubsytem instance;

  private static SuperSparkMax neo1 = new SuperSparkMax(ClawConstants.clawNeo1ID, GlobalIdleMode.brake, false, 50);
  private static SuperSparkMax neo2 = new SuperSparkMax(ClawConstants.clawNeo2ID, GlobalIdleMode.brake, false, 50);

  private static SuperSparkMax wristNeo = new SuperSparkMax(ClawConstants.wristNeoID, GlobalIdleMode.brake, false, 50);

  private static ProfiledPIDController wrisController = new ProfiledPIDController(ClawConstants.wristkP, 0, 0,
      new TrapezoidProfile.Constraints(60, 13)); //55, 20

  private static RevThroughBoreEncoder wristEncoder = new RevThroughBoreEncoder(6);
  private static DigitalInput topLimitSwitch = new DigitalInput(4);
  private static DigitalInput bottomLimitSwitch = new DigitalInput(5);

  private static DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
      ClawConstants.clawForwardPistonID, ClawConstants.clawBackwarddPistonID);

  private static DigitalInput beamBrake = new DigitalInput(ClawConstants.beamBrake);

  double setpoint = 0;

  public ClawSubsytem() {

    wrisController.reset(getWristPosition());

    setpoint = -90;
  }

  /**
   * Returns an instance of {@link ClawSubsytem} for singleton purposes.
   * 
   * @return {@link ClawSubsytem} global instance
   */
  public static ClawSubsytem getInstance() {
    if (instance == null) {
      instance = new ClawSubsytem();
    }
    return instance;
  }

  @Override
  public void periodic() {

    calculateWrist();

    SmartDashboard.putBoolean("Beam", getBeamBrake());

    SmartDashboard.putNumber("Position", getWristPosition());
    SmartDashboard.putNumber("Setpoint", setpoint);
    SmartDashboard.putBoolean("Top", topLimitSwitch.get());
    SmartDashboard.putBoolean("Bottom", bottomLimitSwitch.get());
    SmartDashboard.putNumber("Encoder", wristEncoder.getAbsolutePosition() - 90);
  }

  public void calculateWrist() {
    double actualPoint = getWristPosition();

    double pidOut = wrisController.calculate(actualPoint, setpoint);

    SmartDashboard.putNumber("PIDOUT CLAW", pidOut);
    pidOut = Functions.clamp(pidOut, -0.4, 0.4);

    double feedForwardValue = 0;

    double total = (pidOut * 12) + feedForwardValue;

    total = Functions.clamp(total, -12, 12);

    SmartDashboard.putNumber("Total", total);

    safeMoveClaw(total);
  }

  /**
   * Safety method for moving the claw;
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
    } else if(bottomLimit()){
      if(voltage > 0){
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
   * Sets the Claw's velocity
   * 
   * @param speed Claw speed
   */
  public void setVelocity(double speed) {
    neo1.set(speed);
  }

  /**
   * Sets the Claw's velocity for both neo motor
   * 
   * @param speed Claw speed
   */
  public void setBothVelocity(double speed) {
    neo1.set(speed);
    neo2.set(speed);
  }

  /**
   * Sets the Claw Solenoid State to Cube
   */
  public void ConeSet() {
    solenoid.set(Value.kReverse);
  }

  /**
   * Sets the Claw Solenoid State to Cone
   */
  public void CubeSet() {
    solenoid.set(Value.kForward);
  }

  /**
   * Gets the Solenoid State
   * 
   * @return Solenoid state Value
   */
  public Value getSolenoidState() {
    return solenoid.get();
  }

  /**
   * Gets the neo1 velocity
   * 
   * @return Neo1 Velocity
   */
  public double getNeo1Velocity() {
    return neo1.getVelocity();
  }

  /**
   * Gets the neo2 velocity
   * 
   * @return Neo2 Velocity
   */
  public double getNeo2Velocity() {
    return neo2.getVelocity();
  }

  /**
   * Gets beam brake status
   * 
   * @return Beam brake status
   */
  public boolean getBeamBrake() {
    return beamBrake.get();
  }

  public boolean topLimit() {
    return topLimitSwitch.get();
  }

  public boolean bottomLimit() {
    return bottomLimitSwitch.get();
  }

  public static void startClaw() {
    double pos = (7 * (wristEncoder.getAbsolutePosition() - 90)) / 10;
    wristNeo.setPosition(pos);
    wristNeo.setMode(GlobalIdleMode.brake);
  }

  public static void closeClaw() {
    wristNeo.setMode(GlobalIdleMode.Coast);
  }
}
