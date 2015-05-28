package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStop extends Command {

	private Logger logger = LogControl.getLogger(getClass());
	
    public DriveStop() {
    	requires(Robot.leftDrivetrain);
    	requires(Robot.rightDrivetrain);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize()");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.leftDrivetrain.drive(0.0);
    	Robot.rightDrivetrain.drive(0.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return  (Robot.leftDrivetrain.isStopped() && Robot.rightDrivetrain.isStopped());
    }

    // Called once after isFinished returns true
    protected void end() {
    	logger.info("end()");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	logger.info("interrupted()");
    }
}
