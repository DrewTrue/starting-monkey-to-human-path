package RPIS61.Gubanov.wdad.learn.xml;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private int cost;

    public Item(String name, int cost){
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
