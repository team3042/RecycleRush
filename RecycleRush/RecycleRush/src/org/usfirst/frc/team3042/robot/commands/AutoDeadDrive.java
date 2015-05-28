package org.usfirst.frc.team3042.robot.commands;

import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.LogControl;
import org.usfirst.frc.team3042.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Performs an <b>Auto</b>nomous, <b>Dead</b> reckoning <b>spin</b>
 */
public class AutoDeadDrive extends Command {
	
	private Logger logger = LogControl.getLogger(getClass());

	private double leftGoal;
	private double rightGoal;
	private double leftSpeed;
	private double rightSpeed;
	private boolean test; //if true, get the goals and speeds from the smart dashboard
		
    public AutoDeadDrive(double leftGoal, double rightGoal, double leftSpeed, double rightSpeed, boolean test) {
    	requires(Robot.leftDrivetrain);
        requires(Robot.rightDrivetrain);
        this.leftGoal = leftGoal;
        this.rightGoal = rightGoal;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.test = test;
        
        if(test){
        	//Add the goals to the dashboard
        	SmartDashboard.putNumber("leftGoal", leftGoal);
        	SmartDashboard.putNumber("rightGoal", rightGoal);
        	
        	//Add the speeds to the dashboard
        	SmartDashboard.putNumber("leftSpeed", leftSpeed);
        	SmartDashboard.putNumber("rightSpeed", rightSpeed);
        }
    }

    public AutoDeadDrive(double leftGoal, double rightGoal, double leftSpeed, double rightSpeed) {
        this(leftGoal, rightGoal, leftSpeed, rightSpeed, false);
    }
        
    public AutoDeadDrive(double goal, double speed, boolean test) {
    	this (goal, goal, speed, speed, test);
    }
    
    public AutoDeadDrive(double goal, double speed) {
    	this(goal, speed, false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	logger.info("initialize()");
    	Robot.leftDrivetrain.resetEncoder();
    	Robot.rightDrivetrain.resetEncoder();
    	if (test){
    		//Set the goals to the numbers from the dashboard
    		leftGoal = SmartDashboard.getNumber("leftGoal");
    		rightGoal= SmartDashboard.getNumber("rightGoal");
    		
    		//Set the speeds to the numbers from the dashboard
    		leftSpeed = SmartDashboard.getNumber("leftSpeed");
    		rightSpeed = SmartDashboard.getNumber("rightSpeed");
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.leftDrivetrain.drive(leftReachedGoal() ? 0 : leftSpeed);
    	Robot.rightDrivetrain.drive(rightReachedGoal() ? 0 : rightSpeed);
    }
    
    private boolean leftReachedGoal() {
    	return leftDistance() >= leftGoal;
    }
    
    private boolean rightReachedGoal() {
    	return rightDistance() >= rightGoal;
    }
    
    protected boolean isFinished() {
    	return leftReachedGoal() && rightReachedGoal();
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
    
    //the distance traveled from the start position
    private double leftDistance(){
    	return Math.abs(Robot.leftDrivetrain.getDistance());
    }
    
    //the distance traveled from the start position
    private double rightDistance(){
    	return Math.abs(Robot.rightDrivetrain.getDistance());
    }
}

