package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A Membership.
 */
@Entity
@Table(name = "membership")
public class Membership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "memberid")
    private Long memberid;

    @Column(name = "name")
    private String name;

    @Column(name = "game_title")
    private String gameTitle;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberid() {
        return memberid;
    }

    public Membership memberid(Long memberid) {
        this.memberid = memberid;
        return this;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public String getName() {
        return name;
    }

    public Membership name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public Membership gameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
        return this;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Membership)) {
            return false;
        }
        return id != null && id.equals(((Membership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Membership{" +
            "id=" + getId() +
            ", memberid=" + getMemberid() +
            ", name='" + getName() + "'" +
            ", gameTitle='" + getGameTitle() + "'" +
            "}";
    }
}
