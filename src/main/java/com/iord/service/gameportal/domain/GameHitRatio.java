package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GameHitRatio.
 */
@Entity
@Table(name = "game_hit_ratio")
public class GameHitRatio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_ofhits")
    private Long numberOfhits;

    @Column(name = "score")
    private Long score;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumberOfhits() {
        return numberOfhits;
    }

    public GameHitRatio numberOfhits(Long numberOfhits) {
        this.numberOfhits = numberOfhits;
        return this;
    }

    public void setNumberOfhits(Long numberOfhits) {
        this.numberOfhits = numberOfhits;
    }

    public Long getScore() {
        return score;
    }

    public GameHitRatio score(Long score) {
        this.score = score;
        return this;
    }

    public void setScore(Long score) {
        this.score = score;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameHitRatio)) {
            return false;
        }
        return id != null && id.equals(((GameHitRatio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameHitRatio{" +
            "id=" + getId() +
            ", numberOfhits=" + getNumberOfhits() +
            ", score=" + getScore() +
            "}";
    }
}
