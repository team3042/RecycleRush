package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Drive extends Command {
		
	private Logger logger = LogControl.getLogger(getClass());
	private static final double JOY_SCALE = 0.75;
	
    public Drive() {    	
    	requires(Robot.leftDrivetrain);
    	requires(Robot.rightDrivetrain);
    }
    	
    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize()");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftJoystickValue = -Robot.oi.joyLeft.getY()*JOY_SCALE;
    	double rightJoystickValue = -Robot.oi.joyRight.getY()*JOY_SCALE;
    	
    	// 'Software Straight' hotkeys
    	if (Robot.oi.joyLeft.getTrigger()) {
    		rightJoystickValue = leftJoystickValue;
    	} else if (Robot.oi.joyRight.getTrigger()) {
    		leftJoystickValue = rightJoystickValue;
    	}
    	
    	//Simple drive system
    	Robot.leftDrivetrain.drive(leftJoystickValue);
    	Robot.rightDrivetrain.drive(rightJoystickValue);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
