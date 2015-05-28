package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoStack extends CommandGroup {

	public static enum DIRECTION {RIGHT, LEFT;}
	
    public  AutoStack(DIRECTION direction, int count) {
    	
    	//set the direction for the turn in the auto zone
    	double TURN_SPEED = 0.7;
    	double SPEED = 0.5;
    	double turnSpeed = (direction == DIRECTION.LEFT) ? -TURN_SPEED : TURN_SPEED;
    	
    	//set the distance of the initial drive, to determine how many totes are picked up
    	double totePickUpDistance;
    	switch (count) {
    		case 1: 
    			totePickUpDistance = 4.0;
    			SPEED = 0.35;
    			break;
    		case 2:
    			totePickUpDistance = 90.0;
    			break;
    		case 3:
    			totePickUpDistance = 186.0;
    			SPEED = 0.5;
    			break;
    		default: 
    			totePickUpDistance = 0.0;
    			break;
    	}
    	
    	//Add commands to the command group
    	addSequential(new PhotoEyeOn());
    	addParallel(new TLAMoveToAutoTop());
    	addSequential(new AutoStraight(totePickUpDistance, 0.3)); //drive over autonomous totes, picking them up with the photo eye
    	addSequential(new PhotoEyeOff());
    	if(count == 1){
    		addSequential(new AutoDeadDrive(20, 20, 0.5, -0.5)); //Turn right
    	}
    	
    	else{
    		addSequential(new AutoDeadDrive(20, 20, 0.7, -0.7)); //Turn right
    	}

    	addSequential(new AutoLowerISB());
        addSequential(new AutoDeadDrive(135, SPEED)); //Drive into auto zone
        
        if(count == 3){
        	addSequential(new AutoDeadDrive(20, 20 , turnSpeed, -turnSpeed)); //turn either left or right
        	addSequential(new AutoDeadDrive(24, -0.35)); //back up, leaving totes
        }
        
    	addSequential(new DriveStop());    
    }
    
	private Logger logger = LogControl.getLogger(getClass());
    
    protected void initialize() {
		logger.info("initialize()");
	}

	protected void end() {
		logger.info("end()");
	}

	protected void interrupted() {
		logger.info("interrupted()");
	}
}
