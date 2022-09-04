package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GameRating.
 */
@Entity
@Table(name = "game_rating")
public class GameRating implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gameid")
    private Long gameid;

    @Column(name = "rating")
    private Long rating;

    @Column(name = "timestamp")
    private Long timestamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGameid() {
        return gameid;
    }

    public GameRating gameid(Long gameid) {
        this.gameid = gameid;
        return this;
    }

    public void setGameid(Long gameid) {
        this.gameid = gameid;
    }

    public Long getRating() {
        return rating;
    }

    public GameRating rating(Long rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public GameRating timestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameRating)) {
            return false;
        }
        return id != null && id.equals(((GameRating) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameRating{" +
            "id=" + getId() +
            ", gameid=" + getGameid() +
            ", rating=" + getRating() +
            ", timestamp=" + getTimestamp() +
            "}";
    }
}
