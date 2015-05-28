package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class TabithaOI {
  
	//Declare Joysticks
	public static Joystick joyLeft = new Joystick(RobotMap.LEFT_JOY_PORT);
	public static Joystick joyRight = new Joystick(RobotMap.RIGHT_JOY_PORT);
	public static Joystick joyGunner = new Joystick(RobotMap.GUNNER_JOY_PORT);
	public static Joystick gamePadGunner = new Joystick(RobotMap.GAMEPAD_PORT);
	
	//Declare Buttons
	//Gunner Joystick
	Button g1 = new JoystickButton(joyGunner, 1);
	Button g2 = new JoystickButton(joyGunner, 2);
	Button g3 = new JoystickButton(joyGunner, 3);
	Button g4 = new JoystickButton(joyGunner, 4);
	Button g5 = new JoystickButton(joyGunner, 5);
	Button g6 = new JoystickButton(joyGunner, 6);
	Button g7 = new JoystickButton(joyGunner, 7);
	Button g8 = new JoystickButton(joyGunner, 8);
	Button g9 = new JoystickButton(joyGunner, 9);
	Button g10 = new JoystickButton(joyGunner, 10);
	Button g11 = new JoystickButton(joyGunner, 11);
	Button g12 = new JoystickButton(joyGunner, 12);
	
	//Drive Joysticks
	Button l1 = new JoystickButton(joyLeft, 1);
	Button r1 = new JoystickButton(joyRight, 1);
	Button l10 = new JoystickButton(joyLeft, 10);
	Button l11 = new JoystickButton(joyLeft, 11);
	Button l12 = new JoystickButton(joyLeft, 12);
		
	public TabithaOI(){
		
	//Gunner Joystick Buttons
		g1.whenPressed(new ISBAddTote("g1"));
		g2.toggleWhenPressed(new ISBPistonGrab());
		g3.whenPressed(new ISBReleaseAndLower("Gunner 3"));
		g4.whileHeld(new TLADown());
		g5.whenPressed(new ISBMoveToLift("Gunner 5"));
		g6.whileHeld(new TLAUp());
		g7.whenPressed(new TLAMoveToBottom());
		g8.whenPressed(new TLAMoveToCan());
		g9.whenPressed(new ISBGrabAndDrive("Gunner 9"));
		g10.whenPressed(new TLAMoveToTop());
		g11.whenPressed(new ISBMoveToGrab("Gunner 11"));
	}
}

