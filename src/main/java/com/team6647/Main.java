/**
 * Written by Juan Pablo Gutiérrez on Jan 7, 2023 
 * In honor of HRGD
 * 
 * Team 6647
 */

package com.team6647;

import com.team6647.robot.Robot;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {
  private Main() {}

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
