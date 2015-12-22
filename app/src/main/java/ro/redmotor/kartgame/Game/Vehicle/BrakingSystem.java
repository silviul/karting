package ro.redmotor.kartgame.Game.Vehicle;

/**
 * Created by Gabi on 12/11/2015.
 */
public class BrakingSystem
{
    private Vehicle vehicle;
    private double maxDeceleration = 5;

    public double braking;

    public double getBraking() {
        return braking;
    }

    public void setBraking(double braking) {
        this.braking = braking;
    }

    public BrakingSystem(Vehicle vehicle)
    {
        this.vehicle = vehicle;
    }

    public void update(float elapsedTime)
    {
        vehicle.setTractionWheelSpeed(vehicle.getTractionWheelSpeed() + maxDeceleration * getBraking() * elapsedTime);
        if (vehicle.getTractionWheelSpeed() < 0) vehicle.setTractionWheelSpeed(0);
    }
}
