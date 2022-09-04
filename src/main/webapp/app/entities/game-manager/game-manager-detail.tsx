import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-manager.reducer';
import { IGameManager } from 'app/shared/model/game-manager.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameManagerDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameManagerDetail = (props: IGameManagerDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gameManagerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gameManager.detail.title">GameManager</Translate> [<b>{gameManagerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="gamePortalApp.gameManager.name">Name</Translate>
            </span>
          </dt>
          <dd>{gameManagerEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="gamePortalApp.gameManager.address">Address</Translate>
            </span>
          </dt>
          <dd>{gameManagerEntity.address}</dd>
          <dt>
            <span id="contactNumber">
              <Translate contentKey="gamePortalApp.gameManager.contactNumber">Contact Number</Translate>
            </span>
          </dt>
          <dd>{gameManagerEntity.contactNumber}</dd>
        </dl>
        <Button tag={Link} to="/game-manager" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-manager/${gameManagerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gameManager }: IRootState) => ({
  gameManagerEntity: gameManager.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameManagerDetail);
