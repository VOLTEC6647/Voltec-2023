/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import com.andromedalib.math.Functions;
import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.SuperTalonFX;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.team6647.utils.Constants.ClawConstants;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsytem extends SubsystemBase {
  private static ClawSubsytem instance;

  private static SuperSparkMax neo1 = new SuperSparkMax(ClawConstants.clawNeo1ID, GlobalIdleMode.brake, false, 50);
  private static SuperSparkMax neo2 = new SuperSparkMax(ClawConstants.clawNeo2ID, GlobalIdleMode.brake, false, 50);

  private static SuperTalonFX wristTalon = new SuperTalonFX(ClawConstants.wristFalconID, GlobalIdleMode.brake, false);

  private static ProfiledPIDController wrisController = new ProfiledPIDController(ClawConstants.wristkP, 0, 0,
      new TrapezoidProfile.Constraints(2, 2));

  private static DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
      ClawConstants.clawForwardPistonID, ClawConstants.clawBackwarddPistonID);

  public ClawSubsytem() {
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
  }

  public void calculateWrist() {
    double globalPos = ArmSubsystem.getArmPosition();

    double goalPosition = globalPos > 0 ? -Math.abs(globalPos) - 90 : Math.abs(globalPos) - 90;

    double pidOut = wrisController.calculate(goalPosition, getWristPosition());
    pidOut = Functions.clamp(pidOut, -0.2, 0.2);

    double feedForwardValue = 0;

    double total = (pidOut * 12) + feedForwardValue;

    total = Functions.clamp(total, -12, 12);

    wristTalon.setVoltage(total);
  }

  public double getWristPosition() {
    return (wristTalon.getSelectedSensorPosition() / 2048) / 360; // TODO ADD CONVERSIONS
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
}
