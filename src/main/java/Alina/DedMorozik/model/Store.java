package Alina.DedMorozik.model;

import javax.persistence.*;

@Entity
@Table(name = "Store")
public class Store {   // extends Gift

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    private Gift gift;

    @Column(name = "quantity")
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Store (){
        this.gift = null;
        this.quantity = 0;
    }

    public Store (Gift gift, int quantity ){
        this.gift = gift;
        this.quantity = quantity;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}