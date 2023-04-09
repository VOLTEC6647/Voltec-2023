/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.subsystems;

import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.team6647.utils.Constants.ClawConstants;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClawSubsytem extends SubsystemBase {
  private static ClawSubsytem instance;

  private static SuperSparkMax neo1 = new SuperSparkMax(ClawConstants.clawNeo1ID, GlobalIdleMode.brake, false, 50);
  private static SuperSparkMax neo2 = new SuperSparkMax(ClawConstants.clawNeo2ID, GlobalIdleMode.brake, false, 50);

/*   private static SuperTalonFX wristTalon = new SuperTalonFX(ClawConstants.wristFalconID, GlobalIdleMode.brake, false);
 */
  /* private static ProfiledPIDController wrisController = new ProfiledPIDController(ClawConstants.wristkP, 0, 0,
      new TrapezoidProfile.Constraints(2, 2)); */

  private static DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM,
      ClawConstants.clawForwardPistonID, ClawConstants.clawBackwarddPistonID);

/*   double setpoint = 0;
  boolean parallelWirst = true;
 */
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
   /*  if (parallelWirst) {
      setWristParallelToFloor(ArmSubsystem.getArmPosition());
    }

    calculateWrist(); */
  }

  /* public void calculateWrist() {
    double pidOut = wrisController.calculate(setpoint, getWristPosition());
    pidOut = Functions.clamp(pidOut, -0.2, 0.2);

    double feedForwardValue = 0;

    double total = (pidOut * 12) + feedForwardValue;

    total = Functions.clamp(total, -12, 12);

    wristTalon.setVoltage(total);
  } */

  /**
   * Security method for changing the setpoint
   * 
   * @param change New setpoint
   */
  /* public void changeSetpoint(double change) {
    if (change < -90 || change > -90) // TUNE
      change = Functions.clamp(change, -90, -90);

    this.setpoint = change;
  } */

  /**
   * Gets the relative wristPosition
   * 
   * @return Relative position
   */
  /* public double getWristPosition() {
    return (wristTalon.getSelectedSensorPosition() / 2048) / 360; // TODO ADD CONVERSIONS
  } */

  /**
   * Sets the wrist always parallel to the floor
   * 
   * @param armPosition Current arm position
   */
  /* public void setWristParallelToFloor(double armPosition) {
    double goalPosition = armPosition > 0 ? -Math.abs(armPosition) - 90 : Math.abs(armPosition) - 90;

    changeSetpoint(goalPosition);
  } */

  /**
   * Toggles the wrist movement for it to always be parallel to the floor
   */
  /* public void toggleParallel() {
    parallelWirst = !parallelWirst;
  } */

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
