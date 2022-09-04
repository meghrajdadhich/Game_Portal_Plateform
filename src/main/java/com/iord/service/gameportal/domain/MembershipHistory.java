package com.iord.service.gameportal.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A MembershipHistory.
 */
@Entity
@Table(name = "membership_history")
public class MembershipHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "payment_date")
    private Long paymentDate;

    @Column(name = "expere_date")
    private Long expereDate;

    @Column(name = "member_status")
    private String memberStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public MembershipHistory memberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public MembershipHistory paymentId(Long paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getPaymentDate() {
        return paymentDate;
    }

    public MembershipHistory paymentDate(Long paymentDate) {
        this.paymentDate = paymentDate;
        return this;
    }

    public void setPaymentDate(Long paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getExpereDate() {
        return expereDate;
    }

    public MembershipHistory expereDate(Long expereDate) {
        this.expereDate = expereDate;
        return this;
    }

    public void setExpereDate(Long expereDate) {
        this.expereDate = expereDate;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public MembershipHistory memberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
        return this;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipHistory)) {
            return false;
        }
        return id != null && id.equals(((MembershipHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipHistory{" +
            "id=" + getId() +
            ", memberId=" + getMemberId() +
            ", paymentId=" + getPaymentId() +
            ", paymentDate=" + getPaymentDate() +
            ", expereDate=" + getExpereDate() +
            ", memberStatus='" + getMemberStatus() + "'" +
            "}";
    }
}
