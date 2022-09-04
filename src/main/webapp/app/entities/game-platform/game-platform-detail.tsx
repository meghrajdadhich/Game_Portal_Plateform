import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-platform.reducer';
import { IGamePlatform } from 'app/shared/model/game-platform.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGamePlatformDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GamePlatformDetail = (props: IGamePlatformDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gamePlatformEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gamePlatform.detail.title">GamePlatform</Translate> [<b>{gamePlatformEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="gamePortalApp.gamePlatform.name">Name</Translate>
            </span>
          </dt>
          <dd>{gamePlatformEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/game-platform" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-platform/${gamePlatformEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gamePlatform }: IRootState) => ({
  gamePlatformEntity: gamePlatform.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamePlatformDetail);
