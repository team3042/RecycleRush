package org.usfirst.frc.team3042.robot.subsystems;

import org.usfirst.frc.team3042.robot.commands.Drive;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Drivetrain Subsystem. Designed to handle two different modes:
 * 
 * 1. Simple response to two joysticks.
 * 2. Master-Slave response to two joysticks
 * 
 * One instance controls a single side of the drivetrain. 
 * Full implementation of a drivetrain requires an instance for both the left and the right.
 * 
 * Implements a reduced sensitivity at slow speeds. 
 * A higher SENSITIVTY value leads to less sensitivity at slow speeds.
 * 
 * Implements a restricted speed differential, so that the motor speed can't change too quickly.
 * A lower MAX_DV_RATE leads to a slower response of speed changes.
 * 
 */
public class Drivetrain extends PIDSubsystem {

	//Motor Controllers
	CANTalon talon1, talon2, encTalon;
	
	//Sensitivity constant for limited speed differential
	static final double SENSITIVITY = 1.0; //the exponent for the response to the joystick.

	//Motor input limits
	static final double MAX_MOTOR = 1.0;
	static final double MIN_MOTOR = -1.0;
	
	//Differential constants and variables
	static final double MAX_DV_RATE = 2.0; //percent change per second.
	Timer timer = new Timer();
	double oldTime = 0.0;
	double oldMotorValue = 0.0;
	
	//Encoder count per inch
	private static final double ENCODER_COUNT = 67.2; //counts per inch
	private static final double SPEED_CONVERT = 0.1; //speed per 100 ms
		
	//The constants for the PID loop
	private static final double Kp = 0.00005; //0.00028;
	private static final double Ki = 0.0000;
	private static final double Kd = 0.0; //0.0001;
	private static final double SETPOINT_TOLERANCE = 100.0;
	private boolean positionPID = false;
	
	
	//The sign of the drive side
	double direction = 1.0;
	int offset = 0; // the talon value that was last set as the "virtual zero"

	/**
	 * Constructor
	 * 
	 * Creates instances of the motors and the encoder.
	 * Start the timer.
	 */
	public Drivetrain(String name, int talon1ID, int talon2ID, double direction){
		super(name + " Drivetrain", Kp, Ki, Kd);
		
		//Instantiate the talons and the encoder
		talon1 = new CANTalon(talon1ID);
		talon2 = new CANTalon(talon2ID);
		encTalon = talon1;
		encTalon.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, 10);
		
		//Set the direction of the motors
		this.direction =  (direction < 0.0) ? -1.0: 1.0;
		
		//Start the timer for the differential limits
		timer.start();
	}
	
	/**
	 *Set the default command for the subsystem
	 */
    public void initDefaultCommand() {
    	setDefaultCommand(new Drive());
    }
  
	/**
     * The simple drive system, based on a joystick value
     * @param joystickValue Joystick input
     */
    public void drive(double joystickValue){
    	positionPID = false;
    	disablePID();
    	double motorValue = applySensitivityCurve(joystickValue);
    	setMotorValue(motorValue);
    }
    
    /**
     * Calculate the motor value from the sensitivity curve
     * @param joystickValue Value of corresponding joystick
     * @return Motor speed based on joystick position
     */
    private double applySensitivityCurve(double joystickValue) {
    	double motorValue = Math.pow(Math.abs(joystickValue), SENSITIVITY);
    	// Because we abs value'd the base, we need to reapply a sign to it
    	if (joystickValue < 0) motorValue *= -1;
    	
    	return motorValue;
    }
    
    /**
     * Set the motor values
     * Limit the differential
     * Perform a safety check
     * @param motorValue Desired motor value
     */
    private void setMotorValue(double motorValue){
    	motorValue = limitDifferential(motorValue);
    	setTalonValues(motorValue);
    }
    
    
    /**
    * Set the talon values, with a safety check first.
    * @param motorValue
    */
    public void setTalonValues(double motorValue){
    	motorValue = direction * safetyCheck(motorValue);
    	talon1.set(motorValue);
    	talon2.set(motorValue);
    }
    
    /**
     * Determines if the motors have been stopped.
     * 
     * @return
     */
    public boolean isStopped(){
    	return (getMotorSetpoint() == 0.0);
    }
    
    /**
     * Limit the differential of the speed change, 
     * so that the speed does not change too abruptly.
     * @param motorValue Desired motor speed
     * @return Limited Speed
     */
    private double limitDifferential(double motorValue){
    	//Update the time.
    	double currentTime = timer.get();
    	double dt = currentTime - oldTime;
    	oldTime = currentTime;
    	
    	//Find the differential value.
    	double dV = motorValue - oldMotorValue;
    	double maxDV = MAX_DV_RATE * dt;
    	if (dV > maxDV)	dV = maxDV;
    	if (dV < -maxDV) dV = -maxDV;
    	oldMotorValue = oldMotorValue + dV;
    	
    	return oldMotorValue;
    }
    
    /**
     * Make sure the motor values to not exceed the bounds of the motor controller
     * @param input Ensures motor speed is in range [-1, 1]
     * @return Checked value
     */
    private double safetyCheck(double input) {
    	if(input > MAX_MOTOR) input = MAX_MOTOR;
    	if(input < MIN_MOTOR) input = MIN_MOTOR;
    	return input;
    }
    
    /**
     * Return the motor setpoint for the talons
     */
    public double getMotorSetpoint(){
    	return direction * encTalon.getSetpoint();
    }
   
    /**
     * Get the encoder speed
     * @return encTalon Speed
     */
    public double getEncSpeed(){
    	return direction * encTalon.getEncVelocity();
    }
    
    /**
     * Get the encoder position
     */
    private double getEncPosition() {
    	return direction * (encTalon.getEncPosition() - offset);
    }
    
    /**
     * Get distance, scaled to encoder value
     */
    public double getDistance(){
    	return ((double)getEncPosition()) / ENCODER_COUNT;
    }
    
    /**
     * Reset the encoder position to zero
     */
    public void resetEncoder() {
    	// Set our "virtual zero" to the talon's value right now
    	offset = encTalon.getEncPosition();
    }
    
    /**
     * Drive at a constant speed controlled by the PID loop
     * It expects a speed in real units, to be scaled by ENCODER_COUNT
     */
    public void driveSpeedPID(double speed){
    	positionPID = false;
    	setSetpoint(speed * SPEED_CONVERT * ENCODER_COUNT);
    	enablePID();
    }
    
    /**
     * Drive a parameter-set distance
     * @param distance
     */
    public void driveDistancePID(double distance){
    	positionPID = true;
    	//resetEncoder(); //Does not work reliably
    	//find relative distance
    	distance += getDistance();
    	setSetpoint(distance * ENCODER_COUNT);
    	enablePID();
    }
    
    /**
     * Determines if the setpoint has been reached in the PID position loop
     */
    public boolean setPointReached(){
    	return (Math.abs(getEncPosition() - getSetpoint()) < SETPOINT_TOLERANCE);
    }
    
    /**
     * The sensor input value for the PID loop
     * @return Encoder speed
     * @see #getEncSpeed()
     */
    protected double returnPIDInput() {
    	return (positionPID) ? getEncPosition() : getEncSpeed();
    }
    
    /**
     * The output of the PID loop, used to set the motor values.
     * @param output PID loop return
     */
    public void usePIDOutput(double output) {
    	double motorValue = (positionPID) ? output : (getMotorSetpoint() + output);   			
    	//setMotorValue(motorValue);
    	setTalonValues(motorValue); //Use to bypass the inertial dampening
    }
    
    /**
     * Enable PID loop, if it is not already running
     */
    public void enablePID(){
    	if (!getPIDController().isEnable()) enable();
    }
    /**
     * Disable PID loop if it is running
     */
    public void disablePID(){
    	if (getPIDController().isEnable()) disable();
    }

}
