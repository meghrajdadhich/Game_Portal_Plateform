import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-hit-ratio.reducer';
import { IGameHitRatio } from 'app/shared/model/game-hit-ratio.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameHitRatioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameHitRatioDetail = (props: IGameHitRatioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gameHitRatioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gameHitRatio.detail.title">GameHitRatio</Translate> [<b>{gameHitRatioEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="numberOfhits">
              <Translate contentKey="gamePortalApp.gameHitRatio.numberOfhits">Number Ofhits</Translate>
            </span>
          </dt>
          <dd>{gameHitRatioEntity.numberOfhits}</dd>
          <dt>
            <span id="score">
              <Translate contentKey="gamePortalApp.gameHitRatio.score">Score</Translate>
            </span>
          </dt>
          <dd>{gameHitRatioEntity.score}</dd>
        </dl>
        <Button tag={Link} to="/game-hit-ratio" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-hit-ratio/${gameHitRatioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gameHitRatio }: IRootState) => ({
  gameHitRatioEntity: gameHitRatio.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameHitRatioDetail);
