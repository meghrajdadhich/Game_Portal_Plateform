import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './membership.reducer';
import { IMembership } from 'app/shared/model/membership.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMembershipUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MembershipUpdate = (props: IMembershipUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { membershipEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/membership' + props.location.search);
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
        ...membershipEntity,
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
          <h2 id="gamePortalApp.membership.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.membership.home.createOrEditLabel">Create or edit a Membership</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : membershipEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="membership-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="membership-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="memberidLabel" for="membership-memberid">
                  <Translate contentKey="gamePortalApp.membership.memberid">Memberid</Translate>
                </Label>
                <AvField id="membership-memberid" type="string" className="form-control" name="memberid" />
              </AvGroup>
              <AvGroup>
                <Label id="nameLabel" for="membership-name">
                  <Translate contentKey="gamePortalApp.membership.name">Name</Translate>
                </Label>
                <AvField id="membership-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="gameTitleLabel" for="membership-gameTitle">
                  <Translate contentKey="gamePortalApp.membership.gameTitle">Game Title</Translate>
                </Label>
                <AvField id="membership-gameTitle" type="text" name="gameTitle" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/membership" replace color="info">
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
  membershipEntity: storeState.membership.entity,
  loading: storeState.membership.loading,
  updating: storeState.membership.updating,
  updateSuccess: storeState.membership.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MembershipUpdate);
