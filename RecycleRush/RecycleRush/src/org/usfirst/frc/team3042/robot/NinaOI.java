package org.usfirst.frc.team3042.robot;

import org.usfirst.frc.team3042.robot.commands.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class NinaOI extends OI {
  
	public NinaOI(){
		

		//GamePad Buttons
		gpA.whenPressed(new ISBGrabAndDrive("Game Pad A"));
		gpB.whenPressed(new TLAMoveToBottom());
		gpX.whenPressed(new TLAMoveToCan());
		gpY.whenPressed(new TLAMoveToTop());
		gpLBack.whileHeld(new TLAUp());
		gpRBack.whileHeld(new TLADown());
		gpBack.whenPressed(new ButtonTen("Game Pad Back"));
		gpStart.toggleWhenPressed(new ISBPistonGrab());
		gpJoyLeft.whenPressed(new TLAMoveToTote());
		
		//gpovu.whenActive(new ISBAddTote("POV Up"));
		gpovr.whenActive(new ISBMoveToGrab("POV Right"));
		gpovd.whenActive(new ISBReleaseAndLower("POV Down"));
		gpovl.whenActive(new ISBMoveToLift("POV Left")); 
		
		//Drive Joystick Buttons
		l2.whenPressed(new DriveAdjust(true));
		r2.whenPressed(new DriveAdjust(false));
		
		r7.whileHeld(new ISBUp());
		r8.whileHeld(new ISBDown());
		//r12.whenPressed(new ISBLiftMatch());

		//PhotoEye Trigger
		photoEye.whenActive(new ButtonTen("Photo Eye"));
	}
}

