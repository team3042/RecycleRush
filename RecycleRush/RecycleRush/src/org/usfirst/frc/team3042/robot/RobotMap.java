package org.usfirst.frc.team3042.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public final class RobotMap {
	
	public static enum ELI { FAT, LITTLE; }
	
	public static final ELI eli = ELI.LITTLE;
	
	public static boolean isLittle() {return eli == ELI.LITTLE;}
	
	//Camera IP
	public static final String CAMERA_IP = "10.30.42.11";
	
	//USB
	public static final int LEFT_JOY_PORT = 0;
	public static final int RIGHT_JOY_PORT = 1;
	public static final int GUNNER_JOY_PORT = 2;
	public static final int GAMEPAD_PORT =	3;

	//CAN ID	
	public static final int DRIVETRAIN_TALON_LEFT_1 = (isLittle()) ? 1 : 2;
	public static final int DRIVETRAIN_TALON_LEFT_2 = (isLittle()) ? 4 : 3;
	public static final int DRIVETRAIN_TALON_RIGHT_1 = (isLittle()) ? 3 : 1;
	public static final int DRIVETRAIN_TALON_RIGHT_2 = (isLittle()) ? 2 : 4;
	public static final int ISB_TALON_LEFT = (isLittle()) ? 5 : 5;
	public static final int TLA_TALON_1 = (isLittle()) ? 7 : 8; //This is the encoder talon
	public static final int TLA_TALON_2 = (isLittle()) ? 6 : 6;
	public static final int ISB_TALON_RIGHT = (isLittle()) ? 8 : 7;
	
	//PCM
	public static final int ISB_SOLENOID_RIGHT = (isLittle()) ? 2 : 3;
	public static final int ISB_SOLENOID_LEFT = (isLittle()) ? 3 : 7;
	//Relay Ports
	public static final int IRS_SPIKE_LEFT = 0;
	public static final int IRS_SPIKE_RIGHT = 1;
	
	//Digital IO
	public static final int PHOTO_EYE_PORT = 0;
}
