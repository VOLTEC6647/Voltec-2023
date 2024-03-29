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
}
