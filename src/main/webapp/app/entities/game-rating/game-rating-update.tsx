import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './game-rating.reducer';
import { IGameRating } from 'app/shared/model/game-rating.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGameRatingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameRatingUpdate = (props: IGameRatingUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gameRatingEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/game-rating' + props.location.search);
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
        ...gameRatingEntity,
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
          <h2 id="gamePortalApp.gameRating.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.gameRating.home.createOrEditLabel">Create or edit a GameRating</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gameRatingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="game-rating-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="game-rating-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="gameidLabel" for="game-rating-gameid">
                  <Translate contentKey="gamePortalApp.gameRating.gameid">Gameid</Translate>
                </Label>
                <AvField id="game-rating-gameid" type="string" className="form-control" name="gameid" />
              </AvGroup>
              <AvGroup>
                <Label id="ratingLabel" for="game-rating-rating">
                  <Translate contentKey="gamePortalApp.gameRating.rating">Rating</Translate>
                </Label>
                <AvField id="game-rating-rating" type="string" className="form-control" name="rating" />
              </AvGroup>
              <AvGroup>
                <Label id="timestampLabel" for="game-rating-timestamp">
                  <Translate contentKey="gamePortalApp.gameRating.timestamp">Timestamp</Translate>
                </Label>
                <AvField id="game-rating-timestamp" type="string" className="form-control" name="timestamp" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/game-rating" replace color="info">
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
  gameRatingEntity: storeState.gameRating.entity,
  loading: storeState.gameRating.loading,
  updating: storeState.gameRating.updating,
  updateSuccess: storeState.gameRating.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameRatingUpdate);
