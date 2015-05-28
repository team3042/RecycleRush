package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ISBPistonGrabFinish extends Command {
	
	private Logger logger = LogControl.getLogger(getClass());
	String caller;

	Timer timer = new Timer();

    public ISBPistonGrabFinish(String caller) {
    	this.caller = caller;
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.isbPiston);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initalize("+caller+")");
    	timer.start();
    	Robot.isbPiston.grab();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (timer.get() < .25)
    		return false;
    	else
    		return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("end("+caller+")");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("interrupted("+caller+")");
    }
}
