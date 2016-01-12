package ro.redmotor.kartgame.game.vehicle;

import java.util.ArrayList;

/**
 * Created by Gabi on 12/11/2015.
 */
public class Transmission
{

    private ArrayList<Double> ratios;
    private int gear = 2;

    public int getGear() {
        return gear;
    }


    public Transmission()
    {
        this.ratios = new ArrayList();
        ratios.add(4.5);
        ratios.add(3.0);
        ratios.add(2.2);
        ratios.add(1.8);
        ratios.add(1.5);
        ratios.add(1.2);
    }


    public double getRatio()
    {
            return ratios.get(getGear()-1);
    }


    public void changeUp()
    {
        gear += 1;
        gear = gear >= ratios.size() ? ratios.size() : gear;
    }

    public void changeDown()
    {
        gear -= 1;
        gear = gear < 1 ? 1 : gear;
    }

}