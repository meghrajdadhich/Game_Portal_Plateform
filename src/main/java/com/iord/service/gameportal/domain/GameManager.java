package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GameManager.
 */
@Entity
@Table(name = "game_manager")
public class GameManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_number")
    private Long contactNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GameManager name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public GameManager address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public GameManager contactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameManager)) {
            return false;
        }
        return id != null && id.equals(((GameManager) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameManager{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", contactNumber=" + getContactNumber() +
            "}";
    }
}
