package com.simpower.models.grid.buildings;

import com.simpower.models.grid.GridInfos;

public class House extends ConsumerEnergyBuilding {
    private int inhabitant;
    private int inhabitantCapacity;
    private int happiness;
    private int moneyIncome;

    //debug tool to differentiate houses
    private int test = (int) (Math.random() * (1000));

    public House(){
        super(0, 2000, 200, false, GridInfos.resourceStock.NONE, true,false);
        setHappiness(100);
        setInhabitant(1);
        setInhabitantCapacity(5);
        setMoneyIncome(200);
    }

    /* Getters and setters */

    /**
     * Return the money earned by taxes
     *
     * @return the money as a positive number
     */
    @Override
    public int changeMoneyAmount(){
        return this.moneyIncome;
    }

    /**
     * set the happiness
     *
     * @param happiness_p int to set
     */
    public void setHappiness(int happiness_p) {
        this.happiness = happiness_p;
    }

    /**
     * Set the number of inhabitant in the house
     *
     * @param inhabitant_p int
     */
    public void setInhabitant(int inhabitant_p) {
        this.inhabitant = inhabitant_p;
    }

    /**
     * Set the inhabitant capacity of the house
     *
     * @param inhabitantCapacity_p int
     */
    public void setInhabitantCapacity(int inhabitantCapacity_p) {
        this.inhabitantCapacity = inhabitantCapacity_p;
    }

    /**
     * Set the income money generated by the house
     *
     * @param moneyIncome_p int
     */
    public void setMoneyIncome(int moneyIncome_p) {
        this.moneyIncome = moneyIncome_p;
    }

    @Override
    public int updateHappiness(float electricityProvided){
        //prevents happiness to be >100
        if(electricityProvided>electricityNeeded)
            electricityProvided=electricityNeeded;

        //Calculates the happiness according to the electricityProvided/electricityNeeded ratio
        happiness =(int)(electricityProvided/electricityNeeded*100);

        //if the cell is polluted the house gains 5 or loses 5 happiness
        if(isCellPolluted()){
            if(happiness >= 5)
                happiness -=5;
        }

        //Sets moneyIncome relative to happiness
        float tmpH = this.happiness;
        tmpH /= 100;
        setMoneyIncome((int)(200*tmpH));

        return happiness;
    }
}
