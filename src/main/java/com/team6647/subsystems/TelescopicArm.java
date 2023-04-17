/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.subsystems;

import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.team6647.utils.Constants.ArmConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TelescopicArm extends SubsystemBase {
  private static TelescopicArm instance;
  private static SuperSparkMax extendingSpark = new SuperSparkMax(ArmConstants.extendNeoID, GlobalIdleMode.brake, true,
      50);

  private static DigitalInput limitSwitch = new DigitalInput(ArmConstants.armLimitID);

  /** Creates a new TelescopicArm. */
  public TelescopicArm() {
  }

  /**
   * Returns an instance of {@link TelescopicArm} for singleton purposes.
   * 
   * @return {@link ArmSubsystem} global instance
   */
  public static TelescopicArm getInstance() {
    if (instance == null) {
      instance = new TelescopicArm();
    }
    return instance;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
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
    return extendingSpark.getPosition();
  }

  /**
   * Sets the extending position to 0
   */
  public void resetExtendPosition() {
    extendingSpark.resetEncoder();
  }
}
