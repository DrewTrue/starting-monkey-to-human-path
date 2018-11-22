package RPIS61.Gubanov.wdad.learn.rmi;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private Officiant officiant;
    private List<Item> items;

    public Order(Officiant officiant){
        this(officiant, new ArrayList<>());
    }

    public Order(Officiant officiant, List<Item> items){
        this.officiant = officiant;
        this.items = items;
    }

    public Officiant getOfficiant() {
        return officiant;
    }

    public void setOfficiant(Officiant officiant) {
        this.officiant = officiant;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
