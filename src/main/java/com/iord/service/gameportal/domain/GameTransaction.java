package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A GameTransaction.
 */
@Entity
@Table(name = "game_transaction")
public class GameTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "transaction_time_stamp")
    private Long transactionTimeStamp;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public GameTransaction transactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public GameTransaction userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTransactionTimeStamp() {
        return transactionTimeStamp;
    }

    public GameTransaction transactionTimeStamp(Long transactionTimeStamp) {
        this.transactionTimeStamp = transactionTimeStamp;
        return this;
    }

    public void setTransactionTimeStamp(Long transactionTimeStamp) {
        this.transactionTimeStamp = transactionTimeStamp;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GameTransaction)) {
            return false;
        }
        return id != null && id.equals(((GameTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GameTransaction{" +
            "id=" + getId() +
            ", transactionId=" + getTransactionId() +
            ", userId=" + getUserId() +
            ", transactionTimeStamp=" + getTransactionTimeStamp() +
            "}";
    }
}
