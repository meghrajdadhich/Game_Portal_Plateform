import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-privacy.reducer';
import { IGamePrivacy } from 'app/shared/model/game-privacy.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGamePrivacyDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GamePrivacyDetail = (props: IGamePrivacyDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gamePrivacyEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gamePrivacy.detail.title">GamePrivacy</Translate> [<b>{gamePrivacyEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="agreementDetails">
              <Translate contentKey="gamePortalApp.gamePrivacy.agreementDetails">Agreement Details</Translate>
            </span>
          </dt>
          <dd>{gamePrivacyEntity.agreementDetails}</dd>
          <dt>
            <span id="personalinfo">
              <Translate contentKey="gamePortalApp.gamePrivacy.personalinfo">Personalinfo</Translate>
            </span>
          </dt>
          <dd>{gamePrivacyEntity.personalinfo}</dd>
        </dl>
        <Button tag={Link} to="/game-privacy" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-privacy/${gamePrivacyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gamePrivacy }: IRootState) => ({
  gamePrivacyEntity: gamePrivacy.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GamePrivacyDetail);
