package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GameRules.
 */
@Entity
@Table(name = "game_rules")
public class GameRules implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "description")
    private String description;

    @Column(name = "default_value")
    private String defaultValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public GameRules ruleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getDescription() {
        return description;
    }

    public GameRules description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public GameRules defaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameRules)) {
            return false;
        }
        return id != null && id.equals(((GameRules) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameRules{" +
            "id=" + getId() +
            ", ruleName='" + getRuleName() + "'" +
            ", description='" + getDescription() + "'" +
            ", defaultValue='" + getDefaultValue() + "'" +
            "}";
    }
}
