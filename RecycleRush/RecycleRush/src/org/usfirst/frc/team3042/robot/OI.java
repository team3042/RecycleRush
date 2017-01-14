  package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.*;
import org.usfirst.frc.team3042.robot.triggers.POVButton;
import org.usfirst.frc.team3042.robot.triggers.PhotoEye;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  
	//Declare Joysticks
	public Joystick joyLeft = new Joystick(RobotMap.LEFT_JOY_PORT);
	public Joystick joyRight = new Joystick(RobotMap.RIGHT_JOY_PORT);
	public Joystick joyGunner = new Joystick(RobotMap.GUNNER_JOY_PORT);
	public Joystick gamePadGunner = new Joystick(RobotMap.GAMEPAD_PORT);
	
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

	//GamePad Joystick
	Button gpA = new JoystickButton(gamePadGunner, 1);
	Button gpB = new JoystickButton(gamePadGunner, 2);
	Button gpX = new JoystickButton(gamePadGunner, 3);
	Button gpY = new JoystickButton(gamePadGunner, 4);
	Button gpLBack = new JoystickButton(gamePadGunner, 5);
	Button gpRBack= new JoystickButton(gamePadGunner, 6);
	Button gpBack = new JoystickButton(gamePadGunner, 7);
	Button gpStart = new JoystickButton(gamePadGunner, 8);
	Button gpJoyLeft = new JoystickButton(gamePadGunner, 9);
	Trigger gpovu = new POVButton(gamePadGunner, 0);
	Trigger gpovr = new POVButton(gamePadGunner, 90);
	Trigger gpovd = new POVButton(gamePadGunner, 180);
	Trigger gpovl = new POVButton(gamePadGunner, 270);

	//Drive Joysticks
	Button r1 = new JoystickButton(joyRight, 1);
	Button r2 = new JoystickButton(joyRight, 2);
	Button r7 = new JoystickButton(joyRight, 7);
	Button r8 = new JoystickButton(joyRight, 8);
	Button r9 = new JoystickButton(joyRight, 9);
	Button r12 = new JoystickButton(joyRight, 12);
	
	Button l1 = new JoystickButton(joyLeft, 1);
	Button l2 = new JoystickButton(joyLeft, 2);
	Button l10 = new JoystickButton(joyLeft, 10);
	Button l11 = new JoystickButton(joyRight, 11);
	Button l12 = new JoystickButton(joyRight, 12);
		
	//PhotoEye
	Trigger photoEye = new PhotoEye();
		
	public OI(){
		
	//Gunner Joystick Buttons
		
		//g1.whenPressed(new );
		
		g2.toggleWhenPressed(new ISBPistonGrab());
		g3.whenPressed(new ISBReleaseAndLower("Gunner 3"));
		g4.whileHeld(new TLADown());
		g5.whenPressed(new ISBMoveToLift("Gunner 5"));
		g6.whileHeld(new TLAUp());
		g9.whenPressed(new ISBMoveToDrive());
		g10.whenPressed(new ISBAddTote("Gunner 10"));
		g11.whenPressed(new ISBMoveToGrab("Gunner 11"));
		
		//Drive Joystick Buttons
		l10.whenPressed(new TLAMoveToCan());
		l11.whenPressed(new TLAMoveToBottom());
		l12.whenPressed(new TLAMoveToTop());
		
	}
}

