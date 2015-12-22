package ro.redmotor.kartgame.Game.Vehicle;

import ro.redmotor.kartgame.Game.Utilities.Interfaces.ICollidable;
import ro.redmotor.kartgame.Game.Utilities.Point;
import ro.redmotor.kartgame.Game.Utilities.Timer;

/**
 * Created by Gabi on 12/11/2015.
 */
public class Vehicle implements ICollidable{

    private double speed = 0;
    private double tractionWheelSpeed = 0;
    private double steeringAngle = 0;
    private double wheelBase = 2.8;
    private double length = 3;
    private double width = 1.5;
    private double maxSteering = Math.PI / 6;
    private double mass = 170;
    private Timer timer;
    private double angle;
    private Powertrain powertrain;
    private BrakingSystem brakingSystem;
    private Point position;

    @Override
    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public ro.redmotor.kartgame.Game.Vehicle.Powertrain getPowertrain() {
        return powertrain;
    }

    public void setPowertrain(ro.redmotor.kartgame.Game.Vehicle.Powertrain powertrain) {
        this.powertrain = powertrain;
    }

    public BrakingSystem getBrakingSystem() {
        return brakingSystem;
    }

    public void setBrakingSystem(BrakingSystem brakingSystem) {
        this.brakingSystem = brakingSystem;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getWheelBase() {
        return wheelBase;
    }

    @Override
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        tractionWheelSpeed = speed;
        getPowertrain().updateEngineRpm();
    }

    public double getTractionWheelSpeed() {
        return tractionWheelSpeed;
    }

    public void setTractionWheelSpeed(double tractionWheelSpeed) {
        this.tractionWheelSpeed = tractionWheelSpeed;
    }



    /// <summary>
    /// Steering amount in -1..0..1
    /// </summary>
    public double getSteering()
    {
         return getSteeringAmount();
    }
    public void setSteering(double steering) {

            if (steering < -1) steering = -1;
            if (steering > 1) steering = 1;
            setSteeringAngle(steering);

    }

    /// <summary>
    /// Throttle in 0..1
    /// </summary>
    public double getThrottle() {
        return getPowertrain().getEngine().getThrottle();
    }
    public void setThrottle(double throttle) {
            if (throttle < 0) throttle = 0;
            if (throttle > 1) throttle = 1;
            getPowertrain().getEngine().setThrottle(throttle);

    }

    /// <summary>
    /// Braking in -1..0
    /// </summary>
    public double getBraking() {
            return getBrakingSystem().getBraking();
    }


    public void setBraking(double braking) {
        if (braking < -1) braking = -1;
        if (braking > 0) braking = 0;
        getBrakingSystem().setBraking(braking);
    }



    @Override
    public double getLength() {
        return length;
    }

    @Override
    public double getWidth() {
        return width;
    }


    public Vehicle(double length, double width, double wheelBase, double mass)
    {
        this.length = length;
        this.width = width;
        this.wheelBase = wheelBase;
        this.mass = mass;

        this.setPosition(new Point(0, 0));
        setPowertrain(new Powertrain(this));
        setBrakingSystem(new BrakingSystem(this));
        timer = new Timer();
        timer.getETime(); //reset timer
    }

    public void update()
    {
        float elapsedTime = timer.getETime();

        //update powertrain
        getPowertrain().update(elapsedTime, mass);

        //update braking system
        if (getBraking() != 0)
        {
            this.getBrakingSystem().update(elapsedTime);
        }

        speed = tractionWheelSpeed;

        steerVehicle(elapsedTime);

        handleDynamics(elapsedTime);

        //Hack as we're not skidding now
        getPowertrain().updateEngineRpm();

    }

    private void handleDynamics(float elapsedTime)
    {
        //do the interesting stuff here
    }

    //Hack for skidding right now (first version)
    private double getSpeedCorrection() {
        return getSpeed() > 5 ? getSpeed() / 5 : 1;
    }

    public boolean IsSkidding() {
        return (getSteering() > 0.8 || getSteering() < - 0.8) && getSpeedCorrection() != 1;
    }

    private void steerVehicle(float elapsedTime)
    {
        //perform steering calculations (compute the next vehicle position based on steering alone)
        double strAngle = (steeringAngle * elapsedTime) / getSpeedCorrection();
        //brake down the speed vector
        double speedX = Math.sin(strAngle) * speed;
        double speedY = Math.cos(strAngle) * speed;
        //angle of the car compared to the original position
        double deltaSteeringAngle = Math.asin(speedX / getWheelBase());
        //bit in the middle
        double Ly = Math.cos(deltaSteeringAngle) * getWheelBase();
        //how much the back wheels have moved along the Y axis (along the original position as they don't steer)
        double deltaY = getWheelBase() - (Ly - speedY);
        deltaY = deltaY * elapsedTime;

        //compute the new aspect of the car;
        double y = getPosition().getY() + deltaY * Math.cos(getAngle());
        double x = getPosition().getX() + deltaY * Math.sin(getAngle());
        setPosition(new Point(x, y));
        setAngle(getAngle() + deltaSteeringAngle);

    }

    private void setSteeringAngle(double amount)
    {
        steeringAngle = maxSteering * amount;
    }
    private double getSteeringAmount()
    {
        return steeringAngle / maxSteering;
    }

}
