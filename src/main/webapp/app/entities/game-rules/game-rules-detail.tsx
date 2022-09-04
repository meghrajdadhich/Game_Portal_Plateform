import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-rules.reducer';
import { IGameRules } from 'app/shared/model/game-rules.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameRulesDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameRulesDetail = (props: IGameRulesDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gameRulesEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gameRules.detail.title">GameRules</Translate> [<b>{gameRulesEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="ruleName">
              <Translate contentKey="gamePortalApp.gameRules.ruleName">Rule Name</Translate>
            </span>
          </dt>
          <dd>{gameRulesEntity.ruleName}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="gamePortalApp.gameRules.description">Description</Translate>
            </span>
          </dt>
          <dd>{gameRulesEntity.description}</dd>
          <dt>
            <span id="defaultValue">
              <Translate contentKey="gamePortalApp.gameRules.defaultValue">Default Value</Translate>
            </span>
          </dt>
          <dd>{gameRulesEntity.defaultValue}</dd>
        </dl>
        <Button tag={Link} to="/game-rules" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-rules/${gameRulesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gameRules }: IRootState) => ({
  gameRulesEntity: gameRules.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameRulesDetail);
