import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IGamePrivacy } from 'app/shared/model/game-privacy.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './game-privacy.reducer';

export interface IGamePrivacyDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GamePrivacyDeleteDialog = (props: IGamePrivacyDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/game-privacy' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.gamePrivacyEntity.id);
  };

  const { gamePrivacyEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="gamePortalApp.gamePrivacy.delete.question">
        <Translate contentKey="gamePortalApp.gamePrivacy.delete.question" interpolate={{ id: gamePrivacyEntity.id }}>
          Are you sure you want to delete this GamePrivacy?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-gamePrivacy" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ gamePrivacy }: IRootState) => ({
  gamePrivacyEntity: gamePrivacy.entity,
  updateSuccess: gamePrivacy.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamePrivacyDeleteDialog);
