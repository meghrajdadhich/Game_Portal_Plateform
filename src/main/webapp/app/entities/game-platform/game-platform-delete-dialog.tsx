import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IGamePlatform } from 'app/shared/model/game-platform.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './game-platform.reducer';

export interface IGamePlatformDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GamePlatformDeleteDialog = (props: IGamePlatformDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/game-platform' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.gamePlatformEntity.id);
  };

  const { gamePlatformEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="gamePortalApp.gamePlatform.delete.question">
        <Translate contentKey="gamePortalApp.gamePlatform.delete.question" interpolate={{ id: gamePlatformEntity.id }}>
          Are you sure you want to delete this GamePlatform?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-gamePlatform" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ gamePlatform }: IRootState) => ({
  gamePlatformEntity: gamePlatform.entity,
  updateSuccess: gamePlatform.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamePlatformDeleteDialog);
