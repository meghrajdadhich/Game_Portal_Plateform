import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './game-rules.reducer';
import { IGameRules } from 'app/shared/model/game-rules.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGameRulesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameRulesUpdate = (props: IGameRulesUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gameRulesEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/game-rules' + props.location.search);
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
        ...gameRulesEntity,
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
          <h2 id="gamePortalApp.gameRules.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.gameRules.home.createOrEditLabel">Create or edit a GameRules</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gameRulesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="game-rules-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="game-rules-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="ruleNameLabel" for="game-rules-ruleName">
                  <Translate contentKey="gamePortalApp.gameRules.ruleName">Rule Name</Translate>
                </Label>
                <AvField id="game-rules-ruleName" type="text" name="ruleName" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="game-rules-description">
                  <Translate contentKey="gamePortalApp.gameRules.description">Description</Translate>
                </Label>
                <AvField id="game-rules-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="defaultValueLabel" for="game-rules-defaultValue">
                  <Translate contentKey="gamePortalApp.gameRules.defaultValue">Default Value</Translate>
                </Label>
                <AvField id="game-rules-defaultValue" type="text" name="defaultValue" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/game-rules" replace color="info">
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
  gameRulesEntity: storeState.gameRules.entity,
  loading: storeState.gameRules.loading,
  updating: storeState.gameRules.updating,
  updateSuccess: storeState.gameRules.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameRulesUpdate);
