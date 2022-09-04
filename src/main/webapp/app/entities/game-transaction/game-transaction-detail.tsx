import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './game-transaction.reducer';
import { IGameTransaction } from 'app/shared/model/game-transaction.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGameTransactionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const GameTransactionDetail = (props: IGameTransactionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { gameTransactionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="gamePortalApp.gameTransaction.detail.title">GameTransaction</Translate> [<b>{gameTransactionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="transactionId">
              <Translate contentKey="gamePortalApp.gameTransaction.transactionId">Transaction Id</Translate>
            </span>
          </dt>
          <dd>{gameTransactionEntity.transactionId}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="gamePortalApp.gameTransaction.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{gameTransactionEntity.userId}</dd>
          <dt>
            <span id="transactionTimeStamp">
              <Translate contentKey="gamePortalApp.gameTransaction.transactionTimeStamp">Transaction Time Stamp</Translate>
            </span>
          </dt>
          <dd>{gameTransactionEntity.transactionTimeStamp}</dd>
        </dl>
        <Button tag={Link} to="/game-transaction" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/game-transaction/${gameTransactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ gameTransaction }: IRootState) => ({
  gameTransactionEntity: gameTransaction.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(GameTransactionDetail);
