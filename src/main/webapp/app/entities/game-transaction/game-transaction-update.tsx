import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './game-transaction.reducer';
import { IGameTransaction } from 'app/shared/model/game-transaction.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGameTransactionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameTransactionUpdate = (props: IGameTransactionUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gameTransactionEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/game-transaction' + props.location.search);
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
        ...gameTransactionEntity,
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
          <h2 id="gamePortalApp.gameTransaction.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.gameTransaction.home.createOrEditLabel">Create or edit a GameTransaction</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gameTransactionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="game-transaction-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="game-transaction-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="transactionIdLabel" for="game-transaction-transactionId">
                  <Translate contentKey="gamePortalApp.gameTransaction.transactionId">Transaction Id</Translate>
                </Label>
                <AvField id="game-transaction-transactionId" type="string" className="form-control" name="transactionId" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="game-transaction-userId">
                  <Translate contentKey="gamePortalApp.gameTransaction.userId">User Id</Translate>
                </Label>
                <AvField id="game-transaction-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="transactionTimeStampLabel" for="game-transaction-transactionTimeStamp">
                  <Translate contentKey="gamePortalApp.gameTransaction.transactionTimeStamp">Transaction Time Stamp</Translate>
                </Label>
                <AvField id="game-transaction-transactionTimeStamp" type="string" className="form-control" name="transactionTimeStamp" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/game-transaction" replace color="info">
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
  gameTransactionEntity: storeState.gameTransaction.entity,
  loading: storeState.gameTransaction.loading,
  updating: storeState.gameTransaction.updating,
  updateSuccess: storeState.gameTransaction.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameTransactionUpdate);
