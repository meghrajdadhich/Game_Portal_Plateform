package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Games.
 */
@Entity
@Table(name = "games")
public class Games implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "name")
    private String name;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameId() {
        return gameId;
    }

    public Games gameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public Games name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Games)) {
            return false;
        }
        return id != null && id.equals(((Games) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Games{" +
            "id=" + getId() +
            ", gameId=" + getGameId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
