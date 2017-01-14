package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.RobotMap;
import org.usfirst.frc.team3042.robot.commands.TLAStop;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Thing Lifting Apparatus
 * Controls the elevator on the front of the robot.
 */
public class TLA extends PIDSubsystem {

	//The constants for the PID loop
	private static final double Kp = 0.0020;
	private static final double Ki = 0.0;
	private static final double Kd = 0.00085;
	
	//PID Setpoint Positions
	private static final double AUTO_TOP_POSITION = -12540.0;
	private static final double TOP_POSITION = -13290.0; //0.0;
	private static final double BOTTOM_POSITION = 6660.0; //18400.0; //18520 was the observed value, but we trimmed it for safety
	private static final double CAN_POSITION = 0.0; //11740.0;
	private static final double TOTE_POSITION = 5000.0;

	//Encoder setpoint tolerance
	static final double SETPOINT_TOLERANCE = 80.0;

	//Speed of the motors
	private static final double MOTOR_SPEED = (RobotMap.isLittle()) ? -1.0 : -1.0;
	static final double MAX_MOTOR = 1.0;
	static final double MIN_MOTOR = -1.0;
	
	//Motor Controllers 
	CANTalon talon1 = new CANTalon(RobotMap.TLA_TALON_1);
	CANTalon talon2 = new CANTalon(RobotMap.TLA_TALON_2);
	CANTalon encTalon = talon1;
		
	/**
	 * Constructor
	 * 	 */
	public TLA(){
		super("TLA", Kp, Ki, Kd);
		
		encTalon.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);

		//Reset the encoder value
		//	currently assumes the robot starts in the zero position
		resetEncoder();
	}
	
	/**
	 * Set the default command for the system
	 */
    public void initDefaultCommand() {
        setDefaultCommand(new TLAStop());
    }
    
    /**
     * Set the motor speeds
     */
    private void setMotors(double speed){
    	speed = safetyCheck(speed);
    	talon1.set(speed);
    	talon2.set(speed);
    }
    
    /**
     * Make sure the motor values to not exceed the bounds of the motor controller
     * @param input Ensures motor speed is in range [-1, 1]
     * @return Checked value
     */
    private double safetyCheck(double input){
    	if(input > MAX_MOTOR) input = MAX_MOTOR;
    	if(input < MIN_MOTOR) input = MIN_MOTOR;
    	return input;
    }
    
    /**
     * Stops the TLA
     */
    public void stop(){
    	disablePID();
    	setMotors(0.0);
    }
    
    /**
     * Moves the TLA carriage up
     */
    public void up(){
    	disablePID();
    	setMotors(MOTOR_SPEED);
    	/*
    	if (getEncPosition() > TOP_POSITION){
    		setMotors(MOTOR_SPEED);
    	}
    	else {
    		logger.finer("up(): refusing to send TLA up");
    		setMotors(0.0);
    	}
    	*/
    }
    
    public void joyControl(double joyValue){
    	setMotors(-joyValue);
    }
    
    /**
     * Moves the TLA carriage down
     */
    public void down(){
    	disablePID();
		setMotors(-MOTOR_SPEED);
    	/*
    	if (getEncPosition() < BOTTOM_POSITION){
    		setMotors(-MOTOR_SPEED);
    	}
    	else {
    	    logger.finer("down(): refusing to send TLA down");
    		setMotors(0.0);
    	}
    	*/
    	
    }
    
    /**
     * Goto Top
     */
    public void moveToTop(){
    	setSetpoint(TOP_POSITION);
    	enablePID();
    }
    
    public void moveToAutoTop(){
    	setSetpoint(AUTO_TOP_POSITION);
    	enablePID();
    }
    
    public void moveToCan(){
    	setSetpoint(CAN_POSITION);
    	enablePID();
    }
    
    public void moveToTote(){
    	setSetpoint(TOTE_POSITION);
    	enablePID();
    }
   
    public boolean reachedSetpoint(){
    	if (Math.abs(this.getEncPosition() - this.getSetpoint()) < SETPOINT_TOLERANCE)
    		return true;
    	else
    		return false;
    }
    
    /**
     * Goto Bottom
     */
    public void moveToBottom(){
    	setSetpoint(BOTTOM_POSITION);
    	enablePID();
    }
    
    public void moveToZero(){
    	setSetpoint(TOP_POSITION);
    	enablePID();
    }
    
    /**
     * Reset the encoder position
     */
    public void resetEncoder(){
    	encTalon.setPosition(0.0);
    	this.setSetpoint(0.0);
    }
    
    /**
     * Get the current encoder position
     */
    public double getEncPosition(){
    	return encTalon.getEncPosition();
    }
    
    /**
     *The sensor input value for the PID loop
     */
    protected double returnPIDInput() {
     	return getEncPosition();
    }
    
    /**
     *The output of the PID loop, used to set the motor values.
     */
    public void usePIDOutput(double output) {
    	setMotors(output);
    }
    
    /**
     * Enable PID loop, if it is not already running
     */
    private void enablePID(){
    	if (!getPIDController().isEnable()) enable();
    }
    /**
     * Disable PID loop if it is running
     */
    private void disablePID(){
    	if (getPIDController().isEnable()) disable();
    }
}

