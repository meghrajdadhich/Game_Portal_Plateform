import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-blog.reducer';
import { IGameBlog } from 'app/shared/model/game-blog.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameBlogDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameBlogDetail = (props: IGameBlogDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gameBlogEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gameBlog.detail.title">GameBlog</Translate> [<b>{gameBlogEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="gamePortalApp.gameBlog.title">Title</Translate>
            </span>
          </dt>
          <dd>{gameBlogEntity.title}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="gamePortalApp.gameBlog.category">Category</Translate>
            </span>
          </dt>
          <dd>{gameBlogEntity.category}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gamePortalApp.gameBlog.description">Description</Translate>
            </span>
          </dt>
          <dd>{gameBlogEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/game-blog" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-blog/${gameBlogEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gameBlog }: IRootState) => ({
  gameBlogEntity: gameBlog.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameBlogDetail);
