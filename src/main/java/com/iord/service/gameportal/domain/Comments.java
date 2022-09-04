package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Comments.
 */
@Entity
@Table(name = "comments")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gamename")
    private String gamename;

    @Column(name = "comment")
    private String comment;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGamename() {
        return gamename;
    }

    public Comments gamename(String gamename) {
        this.gamename = gamename;
        return this;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getComment() {
        return comment;
    }

    public Comments comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comments)) {
            return false;
        }
        return id != null && id.equals(((Comments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Comments{" +
            "id=" + getId() +
            ", gamename='" + getGamename() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
