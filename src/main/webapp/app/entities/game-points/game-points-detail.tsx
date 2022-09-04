import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-points.reducer';
import { IGamePoints } from 'app/shared/model/game-points.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGamePointsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GamePointsDetail = (props: IGamePointsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gamePointsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gamePoints.detail.title">GamePoints</Translate> [<b>{gamePointsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="pid">
              <Translate contentKey="gamePortalApp.gamePoints.pid">Pid</Translate>
            </span>
          </dt>
          <dd>{gamePointsEntity.pid}</dd>
          <dt>
            <span id="totalPoints">
              <Translate contentKey="gamePortalApp.gamePoints.totalPoints">Total Points</Translate>
            </span>
          </dt>
          <dd>{gamePointsEntity.totalPoints}</dd>
        </dl>
        <Button tag={Link} to="/game-points" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-points/${gamePointsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gamePoints }: IRootState) => ({
  gamePointsEntity: gamePoints.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamePointsDetail);
