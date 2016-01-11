package ro.redmotor.kartgame.game.vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Gabi on 12/11/2015.
 */
public class Engine
{
    private double rpmm = 400;
    private double rpmMax = 5000;
    private double rpmMin = 400;
    private double internalLoad = 5;
    private double throttle; //current throttle position
    private HashMap<Integer, Integer> powerCurve = new HashMap<>();


    public Engine() {
        powerCurve.put(0,7);
        powerCurve.put(200,7);
        powerCurve.put(1000,7);
        powerCurve.put(1500,8);
        powerCurve.put(2000,9);
        powerCurve.put(2500,10);
        powerCurve.put(3000,11);
        powerCurve.put(3500,12);
        powerCurve.put(4000,12);
        powerCurve.put(4500,12);
        powerCurve.put(5000,11);
        powerCurve.put(5500, 10);
    }

    public double getRpm() {
        return rpmm;
    }

    public void setRpm(double rpm) {
        this.rpmm = rpm;
        if (rpm < rpmMin) rpmm = rpmMin;
        if (rpm > rpmMax) rpmm = rpmMax;

    }

    public double getThrottle() {
        return throttle;
    }

    public void setThrottle(double throttle) {
        this.throttle = throttle;
    }


    public EngineMode getEngineMode() {
            return throttle > 0 ? EngineMode.PullingLoad : EngineMode.EngineBraking;
    }

    /// <summary>
    /// Perform engine calculation (deltaEngine)
    /// </summary>
    /// <param name="elapsedTime"></param>
    /// <param name="wheelSpeed"></param>
    /// <param name="transmissionRatio"></param>
    public void update(double elapsedTime, double shaftSpeed, double load)
    {

        if (getEngineMode() == EngineMode.PullingLoad)
        {
            updateLoad(elapsedTime, load);
        }

        if (getEngineMode() == EngineMode.EngineBraking)
        {
            updateEngineBraking(elapsedTime, load, shaftSpeed);
        }
    }


    private void updateLoad(double elapsedTime, double load)
    {
        setRpm(getRpm() + getRpmStep(elapsedTime, load));
    }

    private void updateEngineBraking(double elapsedTime, double load, double shaftSpeed)
    {
        //if we have load connected to the engine then force rpm, else let the engine manage its rpm
        if (load > 0) setRpm(shaftSpeed);
        setRpm(getRpm() -getRpmStepEngineBrake(elapsedTime, load));
    }

    private double getRpmStep(double elapsedTime, double load)
    {
        //power from power curse converted in watts
        double power = getPowerForRpm(getRpm());

        //increasing rpm
        double rpm = (power / (load + internalLoad) ) * throttle  * elapsedTime * 3; // <- magic number
        //decreasing rpm
        double resistorRpm = load * 0.2 * elapsedTime;
        //return diference
        return rpm-resistorRpm;
    }

    private double getRpmStepEngineBrake(double elapsedTime, double load)
    {
        //power from power curse converted in watts
        double power = getPowerForRpm(getRpm());
        double rpm = (power / (load + internalLoad)) * elapsedTime * 3; // <- magic number
        return rpm;
    }

    private double getPowerForRpm(double rpm)
    {

        ArrayList<Integer> list = new ArrayList(powerCurve.keySet());
        Collections.sort(list);

        for (int i = 1; i < list.size(); i++)
        {
            int prevKey = list.get(i - 1);
            int key = list.get(i);
            if (rpm < key && rpm >= prevKey)
            {
                return powerCurve.get(prevKey) * 745.5; // in Watts
            }
        }
        return 0;
    }

    public enum EngineMode
    {
        PullingLoad,
        EngineBraking
    }
}
