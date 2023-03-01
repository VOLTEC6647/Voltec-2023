/**
 * Written by Juan Pablo Gutiérrez on Jan 7, 2023 
 * In honor of HRGD
 * 
 * Team 6647
 * 
 * "The difference in winning & losing is most often, not quitting"
 * Walt Disney
 */

package com.team6647;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {
  private Main() {
  }

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
