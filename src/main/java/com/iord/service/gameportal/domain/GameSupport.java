package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GameSupport.
 */
@Entity
@Table(name = "game_support")
public class GameSupport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "game_questions")
    private String gameQuestions;

    @Column(name = "acc_questions")
    private String accQuestions;

    @Column(name = "tecnical_questions")
    private String tecnicalQuestions;

    @Column(name = "financial_questions")
    private String financialQuestions;

    @Column(name = "rules_questions")
    private String rulesQuestions;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameQuestions() {
        return gameQuestions;
    }

    public GameSupport gameQuestions(String gameQuestions) {
        this.gameQuestions = gameQuestions;
        return this;
    }

    public void setGameQuestions(String gameQuestions) {
        this.gameQuestions = gameQuestions;
    }

    public String getAccQuestions() {
        return accQuestions;
    }

    public GameSupport accQuestions(String accQuestions) {
        this.accQuestions = accQuestions;
        return this;
    }

    public void setAccQuestions(String accQuestions) {
        this.accQuestions = accQuestions;
    }

    public String getTecnicalQuestions() {
        return tecnicalQuestions;
    }

    public GameSupport tecnicalQuestions(String tecnicalQuestions) {
        this.tecnicalQuestions = tecnicalQuestions;
        return this;
    }

    public void setTecnicalQuestions(String tecnicalQuestions) {
        this.tecnicalQuestions = tecnicalQuestions;
    }

    public String getFinancialQuestions() {
        return financialQuestions;
    }

    public GameSupport financialQuestions(String financialQuestions) {
        this.financialQuestions = financialQuestions;
        return this;
    }

    public void setFinancialQuestions(String financialQuestions) {
        this.financialQuestions = financialQuestions;
    }

    public String getRulesQuestions() {
        return rulesQuestions;
    }

    public GameSupport rulesQuestions(String rulesQuestions) {
        this.rulesQuestions = rulesQuestions;
        return this;
    }

    public void setRulesQuestions(String rulesQuestions) {
        this.rulesQuestions = rulesQuestions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameSupport)) {
            return false;
        }
        return id != null && id.equals(((GameSupport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameSupport{" +
            "id=" + getId() +
            ", gameQuestions='" + getGameQuestions() + "'" +
            ", accQuestions='" + getAccQuestions() + "'" +
            ", tecnicalQuestions='" + getTecnicalQuestions() + "'" +
            ", financialQuestions='" + getFinancialQuestions() + "'" +
            ", rulesQuestions='" + getRulesQuestions() + "'" +
            "}";
    }
}
