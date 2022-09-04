import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './game-blog.reducer';
import { IGameBlog } from 'app/shared/model/game-blog.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IGameBlogUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameBlogUpdate = (props: IGameBlogUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { gameBlogEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/game-blog' + props.location.search);
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
        ...gameBlogEntity,
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
          <h2 id="gamePortalApp.gameBlog.home.createOrEditLabel">
            <Translate contentKey="gamePortalApp.gameBlog.home.createOrEditLabel">Create or edit a GameBlog</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : gameBlogEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="game-blog-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="game-blog-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="game-blog-title">
                  <Translate contentKey="gamePortalApp.gameBlog.title">Title</Translate>
                </Label>
                <AvField id="game-blog-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="categoryLabel" for="game-blog-category">
                  <Translate contentKey="gamePortalApp.gameBlog.category">Category</Translate>
                </Label>
                <AvField id="game-blog-category" type="text" name="category" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="game-blog-description">
                  <Translate contentKey="gamePortalApp.gameBlog.description">Description</Translate>
                </Label>
                <AvField id="game-blog-description" type="text" name="description" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/game-blog" replace color="info">
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
  gameBlogEntity: storeState.gameBlog.entity,
  loading: storeState.gameBlog.loading,
  updating: storeState.gameBlog.updating,
  updateSuccess: storeState.gameBlog.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameBlogUpdate);
