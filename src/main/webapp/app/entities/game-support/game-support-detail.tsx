import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-support.reducer';
import { IGameSupport } from 'app/shared/model/game-support.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameSupportDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameSupportDetail = (props: IGameSupportDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gameSupportEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gameSupport.detail.title">GameSupport</Translate> [<b>{gameSupportEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="gameQuestions">
              <Translate contentKey="gamePortalApp.gameSupport.gameQuestions">Game Questions</Translate>
            </span>
          </dt>
          <dd>{gameSupportEntity.gameQuestions}</dd>
          <dt>
            <span id="accQuestions">
              <Translate contentKey="gamePortalApp.gameSupport.accQuestions">Acc Questions</Translate>
            </span>
          </dt>
          <dd>{gameSupportEntity.accQuestions}</dd>
          <dt>
            <span id="tecnicalQuestions">
              <Translate contentKey="gamePortalApp.gameSupport.tecnicalQuestions">Tecnical Questions</Translate>
            </span>
          </dt>
          <dd>{gameSupportEntity.tecnicalQuestions}</dd>
          <dt>
            <span id="financialQuestions">
              <Translate contentKey="gamePortalApp.gameSupport.financialQuestions">Financial Questions</Translate>
            </span>
          </dt>
          <dd>{gameSupportEntity.financialQuestions}</dd>
          <dt>
            <span id="rulesQuestions">
              <Translate contentKey="gamePortalApp.gameSupport.rulesQuestions">Rules Questions</Translate>
            </span>
          </dt>
          <dd>{gameSupportEntity.rulesQuestions}</dd>
        </dl>
        <Button tag={Link} to="/game-support" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-support/${gameSupportEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gameSupport }: IRootState) => ({
  gameSupportEntity: gameSupport.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameSupportDetail);
