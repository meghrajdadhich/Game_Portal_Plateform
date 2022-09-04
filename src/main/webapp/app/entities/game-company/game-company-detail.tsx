import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-company.reducer';
import { IGameCompany } from 'app/shared/model/game-company.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameCompanyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameCompanyDetail = (props: IGameCompanyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gameCompanyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gameCompany.detail.title">GameCompany</Translate> [<b>{gameCompanyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="gamePortalApp.gameCompany.name">Name</Translate>
            </span>
          </dt>
          <dd>{gameCompanyEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="gamePortalApp.gameCompany.address">Address</Translate>
            </span>
          </dt>
          <dd>{gameCompanyEntity.address}</dd>
        </dl>
        <Button tag={Link} to="/game-company" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-company/${gameCompanyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gameCompany }: IRootState) => ({
  gameCompanyEntity: gameCompany.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameCompanyDetail);
