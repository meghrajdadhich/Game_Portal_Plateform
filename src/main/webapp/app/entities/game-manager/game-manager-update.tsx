import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './game-manager.reducer';
import { IGameManager } from 'app/shared/model/game-manager.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGameManagerUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameManagerUpdate = (props: IGameManagerUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gameManagerEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/game-manager' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...gameManagerEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gamePortalApp.gameManager.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.gameManager.home.createOrEditLabel">Create or edit a GameManager</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gameManagerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="game-manager-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="game-manager-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="game-manager-name">
                  <Translate contentKey="gamePortalApp.gameManager.name">Name</Translate>
                </Label>
                <AvField id="game-manager-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="game-manager-address">
                  <Translate contentKey="gamePortalApp.gameManager.address">Address</Translate>
                </Label>
                <AvField id="game-manager-address" type="text" name="address" />
              </AvGroup>
              <AvGroup>
                <Label id="contactNumberLabel" for="game-manager-contactNumber">
                  <Translate contentKey="gamePortalApp.gameManager.contactNumber">Contact Number</Translate>
                </Label>
                <AvField id="game-manager-contactNumber" type="string" className="form-control" name="contactNumber" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/game-manager" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  gameManagerEntity: storeState.gameManager.entity,
  loading: storeState.gameManager.loading,
  updating: storeState.gameManager.updating,
  updateSuccess: storeState.gameManager.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameManagerUpdate);
