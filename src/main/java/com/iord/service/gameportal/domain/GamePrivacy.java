package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GamePrivacy.
 */
@Entity
@Table(name = "game_privacy")
public class GamePrivacy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agreement_details")
    private String agreementDetails;

    @Column(name = "personalinfo")
    private String personalinfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreementDetails() {
        return agreementDetails;
    }

    public GamePrivacy agreementDetails(String agreementDetails) {
        this.agreementDetails = agreementDetails;
        return this;
    }

    public void setAgreementDetails(String agreementDetails) {
        this.agreementDetails = agreementDetails;
    }

    public String getPersonalinfo() {
        return personalinfo;
    }

    public GamePrivacy personalinfo(String personalinfo) {
        this.personalinfo = personalinfo;
        return this;
    }

    public void setPersonalinfo(String personalinfo) {
        this.personalinfo = personalinfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GamePrivacy)) {
            return false;
        }
        return id != null && id.equals(((GamePrivacy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GamePrivacy{" +
            "id=" + getId() +
            ", agreementDetails='" + getAgreementDetails() + "'" +
            ", personalinfo='" + getPersonalinfo() + "'" +
            "}";
    }
}
