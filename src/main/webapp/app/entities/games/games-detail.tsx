import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './games.reducer';
import { IGames } from 'app/shared/model/games.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGamesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GamesDetail = (props: IGamesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gamesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.games.detail.title">Games</Translate> [<b>{gamesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="gameId">
              <Translate contentKey="gamePortalApp.games.gameId">Game Id</Translate>
            </span>
          </dt>
          <dd>{gamesEntity.gameId}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="gamePortalApp.games.name">Name</Translate>
            </span>
          </dt>
          <dd>{gamesEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/games" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/games/${gamesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ games }: IRootState) => ({
  gamesEntity: games.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamesDetail);
