import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './membership-history.reducer';
import { IMembershipHistory } from 'app/shared/model/membership-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMembershipHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MembershipHistoryDetail = (props: IMembershipHistoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { membershipHistoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.membershipHistory.detail.title">MembershipHistory</Translate> [
          <b>{membershipHistoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="memberId">
              <Translate contentKey="gamePortalApp.membershipHistory.memberId">Member Id</Translate>
            </span>
          </dt>
          <dd>{membershipHistoryEntity.memberId}</dd>
          <dt>
            <span id="paymentId">
              <Translate contentKey="gamePortalApp.membershipHistory.paymentId">Payment Id</Translate>
            </span>
          </dt>
          <dd>{membershipHistoryEntity.paymentId}</dd>
          <dt>
            <span id="paymentDate">
              <Translate contentKey="gamePortalApp.membershipHistory.paymentDate">Payment Date</Translate>
            </span>
          </dt>
          <dd>{membershipHistoryEntity.paymentDate}</dd>
          <dt>
            <span id="expereDate">
              <Translate contentKey="gamePortalApp.membershipHistory.expereDate">Expere Date</Translate>
            </span>
          </dt>
          <dd>{membershipHistoryEntity.expereDate}</dd>
          <dt>
            <span id="memberStatus">
              <Translate contentKey="gamePortalApp.membershipHistory.memberStatus">Member Status</Translate>
            </span>
          </dt>
          <dd>{membershipHistoryEntity.memberStatus}</dd>
        </dl>
        <Button tag={Link} to="/membership-history" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/membership-history/${membershipHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ membershipHistory }: IRootState) => ({
  membershipHistoryEntity: membershipHistory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MembershipHistoryDetail);
