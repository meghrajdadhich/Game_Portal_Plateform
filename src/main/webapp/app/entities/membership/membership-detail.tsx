import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './membership.reducer';
import { IMembership } from 'app/shared/model/membership.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMembershipDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MembershipDetail = (props: IMembershipDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { membershipEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.membership.detail.title">Membership</Translate> [<b>{membershipEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="memberid">
              <Translate contentKey="gamePortalApp.membership.memberid">Memberid</Translate>
            </span>
          </dt>
          <dd>{membershipEntity.memberid}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="gamePortalApp.membership.name">Name</Translate>
            </span>
          </dt>
          <dd>{membershipEntity.name}</dd>
          <dt>
            <span id="gameTitle">
              <Translate contentKey="gamePortalApp.membership.gameTitle">Game Title</Translate>
            </span>
          </dt>
          <dd>{membershipEntity.gameTitle}</dd>
        </dl>
        <Button tag={Link} to="/membership" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/membership/${membershipEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ membership }: IRootState) => ({
  membershipEntity: membership.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MembershipDetail);
