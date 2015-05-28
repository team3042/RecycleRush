package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ISBGrabAndDrive extends CommandGroup {
	
	String caller;
    
    public  ISBGrabAndDrive(String caller) {
        this.caller = caller;
        
    	addSequential(new ISBPistonGrabFinish(caller));
    	addSequential(new ISBMoveToDrive());

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
    
	
	private Logger logger = LogControl.getLogger(getClass());
	protected void initialize() {
		logger.info("initialize("+caller+")");
	}

	protected void end() {
		logger.info("end("+caller+")");
	}

	protected void interrupted() {
		logger.info("interrupted("+caller+")");
	}
}
