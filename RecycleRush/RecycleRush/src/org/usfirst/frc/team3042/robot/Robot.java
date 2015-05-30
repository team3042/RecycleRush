
package org.usfirst.frc.team3042.robot;


import java.util.logging.Logger;

import org.usfirst.frc.team3042.robot.commands.*;
import org.usfirst.frc.team3042.robot.subsystems.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// Initialize the logging
	static {
		//
		LogControl.startConsole();
		LogControl.startFile();
	}
	public final Logger logger = LogControl.getLogger(getClass());

	
	//Instantiate the subsystems
	public static final Drivetrain leftDrivetrain = new Drivetrain(
			"left",
			RobotMap.DRIVETRAIN_TALON_LEFT_1, 
			RobotMap.DRIVETRAIN_TALON_LEFT_2,
			1.0);
	public static final Drivetrain rightDrivetrain = new Drivetrain(
			"right",
			RobotMap.DRIVETRAIN_TALON_RIGHT_1, 
			RobotMap.DRIVETRAIN_TALON_RIGHT_2,
			-1.0);
		
	public static final ISB leftISB = new ISB(RobotMap.ISB_TALON_LEFT, ISB.SIDE.LEFT);
	public static final ISB rightISB = new ISB(RobotMap.ISB_TALON_RIGHT, ISB.SIDE.RIGHT);
	
	public static final TLA tla = new TLA();	
	public static final ISBPiston isbPiston = new ISBPiston();

	public static OI oi;
	
	//Autonomous
    Command autonomousCommand;
    SendableChooser autoChooser;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	logger.info("robotInit()");
		oi = new NinaOI();
				
		//Autonomous Command Chooser
		autoChooser = new SendableChooser();
        autoChooser.addDefault("Default (Do Nothing)", new AutoDoNothing());
        autoChooser.addObject("AutoStack 3 Left", new AutoStack(AutoStack.DIRECTION.LEFT, 3));
        autoChooser.addObject("AutoStack 3 Right", new AutoStack(AutoStack.DIRECTION.RIGHT, 3));
        autoChooser.addObject("AutoStack 1 LEFT", new AutoStack(AutoStack.DIRECTION.LEFT, 1));
        autoChooser.addObject("AutoStack 1 RIGHT", new AutoStack(AutoStack.DIRECTION.RIGHT, 1));
        autoChooser.addObject("AutoCanAndBack", new AutoCanAndBack());
        autoChooser.addObject("AutoDriveToZone", new AutoDriveToZone());
        SmartDashboard.putData("Autonomous", autoChooser);    
        
        //Encoder resets
        //This should be redundant because encoders are reset in the object constructors
    	leftISB.resetEncoder();
    	rightISB.resetEncoder();
    	tla.resetEncoder();
    	
    	//Output all encoder values
        SmartDashboard.putNumber("Left Drivetrain Encoder Position", leftDrivetrain.getDistance());
        SmartDashboard.putNumber("Right Drivetrain Encoder Position", rightDrivetrain.getDistance());
        SmartDashboard.putNumber("Left ISB Encoder Position", leftISB.getEncPosition());
        SmartDashboard.putNumber("Right ISB Encoder Position", rightISB.getEncPosition());
        SmartDashboard.putNumber("TLA Encoder Position", tla.getEncPosition());
        
    }
    
    // FMS state tracking
    boolean isFMSConnected = false;
    public void pollDriverStation() {
    	if (DriverStation.getInstance().isFMSAttached() != isFMSConnected) {
    		isFMSConnected = !isFMSConnected;
    		if (isFMSConnected) {
    	    	logger.info("FMS connected!");
    		} else {
    			logger.info("Lost connection to FMS!");
    		}
    	}
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		pollDriverStation();
	}

    public void autonomousInit() {    	
    	logger.info("autonomousInit()");
    	pollDriverStation();
    	autonomousCommand = (Command) autoChooser.getSelected();
        if (autonomousCommand != null) {
        	autonomousCommand.start();
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        SmartDashboard.putNumber("Left Drivetrain Encoder Position", leftDrivetrain.getDistance());
        SmartDashboard.putNumber("Right Drivetrain Encoder Position", rightDrivetrain.getDistance());
        SmartDashboard.putNumber("Left ISB Encoder Position", leftISB.getEncPosition());
        SmartDashboard.putNumber("Right ISB Encoder Position", rightISB.getEncPosition());
        SmartDashboard.putNumber("TLA Encoder Position", tla.getEncPosition());
        
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	logger.info("teleopInit()");
    	pollDriverStation();
    	
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
       ISB.PhotoEyeOff();
        
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){
    	pollDriverStation();
    	logger.info("disabledInit()");
    	Scheduler.getInstance().removeAll(); 
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        SmartDashboard.putNumber("Left Drivetrain Encoder Position", leftDrivetrain.getDistance());
        SmartDashboard.putNumber("Right Drivetrain Encoder Position", rightDrivetrain.getDistance());
        SmartDashboard.putNumber("Left ISB Encoder Position", leftISB.getEncPosition());
        SmartDashboard.putNumber("Right ISB Encoder Position", rightISB.getEncPosition());
        SmartDashboard.putNumber("TLA Encoder Position", tla.getEncPosition());

        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
