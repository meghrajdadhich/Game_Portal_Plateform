package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GamePlan.
 */
@Entity
@Table(name = "game_plan")
public class GamePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name")
    private String planName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public GamePlan planName(String planName) {
        this.planName = planName;
        return this;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GamePlan)) {
            return false;
        }
        return id != null && id.equals(((GamePlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GamePlan{" +
            "id=" + getId() +
            ", planName='" + getPlanName() + "'" +
            "}";
    }
}
