import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './membership-history.reducer';
import { IMembershipHistory } from 'app/shared/model/membership-history.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMembershipHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MembershipHistoryUpdate = (props: IMembershipHistoryUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { membershipHistoryEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/membership-history' + props.location.search);
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
        ...membershipHistoryEntity,
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
          <h2 id="gamePortalApp.membershipHistory.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.membershipHistory.home.createOrEditLabel">Create or edit a MembershipHistory</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : membershipHistoryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="membership-history-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="membership-history-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="memberIdLabel" for="membership-history-memberId">
                  <Translate contentKey="gamePortalApp.membershipHistory.memberId">Member Id</Translate>
                </Label>
                <AvField id="membership-history-memberId" type="string" className="form-control" name="memberId" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentIdLabel" for="membership-history-paymentId">
                  <Translate contentKey="gamePortalApp.membershipHistory.paymentId">Payment Id</Translate>
                </Label>
                <AvField id="membership-history-paymentId" type="string" className="form-control" name="paymentId" />
              </AvGroup>
              <AvGroup>
                <Label id="paymentDateLabel" for="membership-history-paymentDate">
                  <Translate contentKey="gamePortalApp.membershipHistory.paymentDate">Payment Date</Translate>
                </Label>
                <AvField id="membership-history-paymentDate" type="string" className="form-control" name="paymentDate" />
              </AvGroup>
              <AvGroup>
                <Label id="expereDateLabel" for="membership-history-expereDate">
                  <Translate contentKey="gamePortalApp.membershipHistory.expereDate">Expere Date</Translate>
                </Label>
                <AvField id="membership-history-expereDate" type="string" className="form-control" name="expereDate" />
              </AvGroup>
              <AvGroup>
                <Label id="memberStatusLabel" for="membership-history-memberStatus">
                  <Translate contentKey="gamePortalApp.membershipHistory.memberStatus">Member Status</Translate>
                </Label>
                <AvField id="membership-history-memberStatus" type="text" name="memberStatus" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/membership-history" replace color="info">
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
  membershipHistoryEntity: storeState.membershipHistory.entity,
  loading: storeState.membershipHistory.loading,
  updating: storeState.membershipHistory.updating,
  updateSuccess: storeState.membershipHistory.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MembershipHistoryUpdate);
