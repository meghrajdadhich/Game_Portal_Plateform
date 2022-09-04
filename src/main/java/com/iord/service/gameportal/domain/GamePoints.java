package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GamePoints.
 */
@Entity
@Table(name = "game_points")
public class GamePoints implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pid")
    private Long pid;

    @Column(name = "total_points")
    private Long totalPoints;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public GamePoints pid(Long pid) {
        this.pid = pid;
        return this;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getTotalPoints() {
        return totalPoints;
    }

    public GamePoints totalPoints(Long totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }

    public void setTotalPoints(Long totalPoints) {
        this.totalPoints = totalPoints;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GamePoints)) {
            return false;
        }
        return id != null && id.equals(((GamePoints) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GamePoints{" +
            "id=" + getId() +
            ", pid=" + getPid() +
            ", totalPoints=" + getTotalPoints() +
            "}";
    }
}
