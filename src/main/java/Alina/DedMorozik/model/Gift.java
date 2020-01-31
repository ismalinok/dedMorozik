package Alina.DedMorozik.model;

import javax.persistence.*;

@Entity
@Table(name = "GIFT")
public class Gift {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", length = 64, nullable = false)
    private String title;

    public Gift() {
        this.title = "test";
    }

    public Gift(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}