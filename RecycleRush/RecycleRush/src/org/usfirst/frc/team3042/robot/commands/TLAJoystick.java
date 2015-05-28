package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TLAJoystick extends Command {

	private Logger logger = LogControl.getLogger(getClass());

	public TLAJoystick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.tla);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize()");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double joystickValue = Robot.oi.gamePadGunner.getRawAxis(5);
    	Robot.tla.joyControl(joystickValue);
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
