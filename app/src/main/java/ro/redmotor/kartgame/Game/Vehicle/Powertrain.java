package ro.redmotor.kartgame.game.vehicle;

/**
 * Created by Gabi on 12/11/2015.
 */
public class Powertrain
{

    private Vehicle vehicle;
    private Engine engine;
    private Transmission transmission;
    private boolean clutchIn;
    private double clutchEngageRpm = 1000;
    private double tireLength = 0.75; // move this from here

    public Powertrain(Vehicle vehicle)
    {
        this.vehicle = vehicle;
        this.engine = new Engine();
        this.transmission = new Transmission();
    }

    public Engine getEngine() {
        return engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public boolean isClutchIn() {
        return clutchIn;
    }

    public void setClutchIn(boolean clutchIn) {
        this.clutchIn = clutchIn;
    }


    public void update(double elapsedTime, double load)
    {
        //if we're almost stopped clutch in so we don't stall the engine
        if (!isClutchIn() && getEngine().getRpm() < clutchEngageRpm) setClutchIn(true);

        //load transmitted to the engine through the gear ratio
        double dynamicLoad = isClutchIn() ? 0 : (load / getTransmission().getRatio());

        this.getEngine().update(elapsedTime, getShaftSpeed(), dynamicLoad);
        //update vehicle wheel speed if we're not declutched
        vehicle.setTractionWheelSpeed(isClutchIn() ?
                vehicle.getTractionWheelSpeed() :
                ((getEngine().getRpm() / 60) / getTransmission().getRatio()) * tireLength);

    }

    public void changeUp()
    {
        //cut power
        getEngine().setThrottle(0);
        //change gear
        getTransmission().changeUp();
        //update
        vehicle.update();
    }

    public void ChangeDown()
    {
        //cut power
        getEngine().setThrottle(0);
        //change gear
        getTransmission().changeDown();
        //update
        vehicle.update();
    }

    /// <summary>
    /// Speed of the primary transmission shaft (the one connected to the engine)
    /// </summary>
    /// <returns></returns>
    private double getShaftSpeed()
    {
        double shaftSpeed = ((vehicle.getTractionWheelSpeed() / tireLength) * getTransmission().getRatio()) * 60; //in rot/s
        return shaftSpeed;
    }

    //Hack for now
    public void updateEngineRpm()
    {
        if (!isClutchIn()) getEngine().setRpm(getShaftSpeed());
    }
}
