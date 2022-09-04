import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './game-hit-ratio.reducer';
import { IGameHitRatio } from 'app/shared/model/game-hit-ratio.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGameHitRatioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameHitRatioUpdate = (props: IGameHitRatioUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gameHitRatioEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/game-hit-ratio' + props.location.search);
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
        ...gameHitRatioEntity,
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
          <h2 id="gamePortalApp.gameHitRatio.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.gameHitRatio.home.createOrEditLabel">Create or edit a GameHitRatio</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gameHitRatioEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="game-hit-ratio-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="game-hit-ratio-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numberOfhitsLabel" for="game-hit-ratio-numberOfhits">
                  <Translate contentKey="gamePortalApp.gameHitRatio.numberOfhits">Number Ofhits</Translate>
                </Label>
                <AvField id="game-hit-ratio-numberOfhits" type="string" className="form-control" name="numberOfhits" />
              </AvGroup>
              <AvGroup>
                <Label id="scoreLabel" for="game-hit-ratio-score">
                  <Translate contentKey="gamePortalApp.gameHitRatio.score">Score</Translate>
                </Label>
                <AvField id="game-hit-ratio-score" type="string" className="form-control" name="score" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/game-hit-ratio" replace color="info">
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
  gameHitRatioEntity: storeState.gameHitRatio.entity,
  loading: storeState.gameHitRatio.loading,
  updating: storeState.gameHitRatio.updating,
  updateSuccess: storeState.gameHitRatio.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameHitRatioUpdate);
