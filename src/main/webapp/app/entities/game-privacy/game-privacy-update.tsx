import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './game-privacy.reducer';
import { IGamePrivacy } from 'app/shared/model/game-privacy.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGamePrivacyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GamePrivacyUpdate = (props: IGamePrivacyUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gamePrivacyEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/game-privacy' + props.location.search);
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
        ...gamePrivacyEntity,
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
          <h2 id="gamePortalApp.gamePrivacy.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.gamePrivacy.home.createOrEditLabel">Create or edit a GamePrivacy</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gamePrivacyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="game-privacy-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="game-privacy-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="agreementDetailsLabel" for="game-privacy-agreementDetails">
                  <Translate contentKey="gamePortalApp.gamePrivacy.agreementDetails">Agreement Details</Translate>
                </Label>
                <AvField id="game-privacy-agreementDetails" type="text" name="agreementDetails" />
              </AvGroup>
              <AvGroup>
                <Label id="personalinfoLabel" for="game-privacy-personalinfo">
                  <Translate contentKey="gamePortalApp.gamePrivacy.personalinfo">Personalinfo</Translate>
                </Label>
                <AvField id="game-privacy-personalinfo" type="text" name="personalinfo" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/game-privacy" replace color="info">
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
  gamePrivacyEntity: storeState.gamePrivacy.entity,
  loading: storeState.gamePrivacy.loading,
  updating: storeState.gamePrivacy.updating,
  updateSuccess: storeState.gamePrivacy.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamePrivacyUpdate);
