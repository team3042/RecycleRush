package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ISBPiston extends Subsystem {

	// Solenoid
	Solenoid solenoidLeft = new Solenoid(RobotMap.ISB_SOLENOID_LEFT);
	Solenoid solenoidRight = new Solenoid(RobotMap.ISB_SOLENOID_RIGHT);

	// sOLENOID bOOLEAN
	private static final boolean GRAB = false; //was true
	private static final boolean RELEASE = true; //was false

	public ISBPiston() {
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new ISBRelease());
	}

	/**
	 * Sets ISB pistons to retract
	 */
	public void grab() {
		solenoidLeft.set(RELEASE);
		solenoidRight.set(RELEASE);
	}

	/**
	 * Sets ISB pistons to extend
	 */
	public void release() {
		solenoidLeft.set(GRAB);
		solenoidRight.set(GRAB);
	}
}
