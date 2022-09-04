import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-plan.reducer';
import { IGamePlan } from 'app/shared/model/game-plan.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGamePlanDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GamePlanDetail = (props: IGamePlanDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gamePlanEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gamePlan.detail.title">GamePlan</Translate> [<b>{gamePlanEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="planName">
              <Translate contentKey="gamePortalApp.gamePlan.planName">Plan Name</Translate>
            </span>
          </dt>
          <dd>{gamePlanEntity.planName}</dd>
        </dl>
        <Button tag={Link} to="/game-plan" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-plan/${gamePlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gamePlan }: IRootState) => ({
  gamePlanEntity: gamePlan.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamePlanDetail);
