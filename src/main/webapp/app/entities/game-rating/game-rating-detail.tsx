import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-rating.reducer';
import { IGameRating } from 'app/shared/model/game-rating.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameRatingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameRatingDetail = (props: IGameRatingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gameRatingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gameRating.detail.title">GameRating</Translate> [<b>{gameRatingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="gameid">
              <Translate contentKey="gamePortalApp.gameRating.gameid">Gameid</Translate>
            </span>
          </dt>
          <dd>{gameRatingEntity.gameid}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="gamePortalApp.gameRating.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{gameRatingEntity.rating}</dd>
          <dt>
            <span id="timestamp">
              <Translate contentKey="gamePortalApp.gameRating.timestamp">Timestamp</Translate>
            </span>
          </dt>
          <dd>{gameRatingEntity.timestamp}</dd>
        </dl>
        <Button tag={Link} to="/game-rating" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-rating/${gameRatingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gameRating }: IRootState) => ({
  gameRatingEntity: gameRating.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameRatingDetail);
