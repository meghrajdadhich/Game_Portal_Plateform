import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './game-support.reducer';
import { IGameSupport } from 'app/shared/model/game-support.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGameSupportUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameSupportUpdate = (props: IGameSupportUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gameSupportEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/game-support' + props.location.search);
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
        ...gameSupportEntity,
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
          <h2 id="gamePortalApp.gameSupport.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.gameSupport.home.createOrEditLabel">Create or edit a GameSupport</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gameSupportEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="game-support-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="game-support-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="gameQuestionsLabel" for="game-support-gameQuestions">
                  <Translate contentKey="gamePortalApp.gameSupport.gameQuestions">Game Questions</Translate>
                </Label>
                <AvField id="game-support-gameQuestions" type="text" name="gameQuestions" />
              </AvGroup>
              <AvGroup>
                <Label id="accQuestionsLabel" for="game-support-accQuestions">
                  <Translate contentKey="gamePortalApp.gameSupport.accQuestions">Acc Questions</Translate>
                </Label>
                <AvField id="game-support-accQuestions" type="text" name="accQuestions" />
              </AvGroup>
              <AvGroup>
                <Label id="tecnicalQuestionsLabel" for="game-support-tecnicalQuestions">
                  <Translate contentKey="gamePortalApp.gameSupport.tecnicalQuestions">Tecnical Questions</Translate>
                </Label>
                <AvField id="game-support-tecnicalQuestions" type="text" name="tecnicalQuestions" />
              </AvGroup>
              <AvGroup>
                <Label id="financialQuestionsLabel" for="game-support-financialQuestions">
                  <Translate contentKey="gamePortalApp.gameSupport.financialQuestions">Financial Questions</Translate>
                </Label>
                <AvField id="game-support-financialQuestions" type="text" name="financialQuestions" />
              </AvGroup>
              <AvGroup>
                <Label id="rulesQuestionsLabel" for="game-support-rulesQuestions">
                  <Translate contentKey="gamePortalApp.gameSupport.rulesQuestions">Rules Questions</Translate>
                </Label>
                <AvField id="game-support-rulesQuestions" type="text" name="rulesQuestions" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/game-support" replace color="info">
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
  gameSupportEntity: storeState.gameSupport.entity,
  loading: storeState.gameSupport.loading,
  updating: storeState.gameSupport.updating,
  updateSuccess: storeState.gameSupport.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameSupportUpdate);
