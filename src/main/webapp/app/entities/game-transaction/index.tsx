import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameTransaction from './game-transaction';
import GameTransactionDetail from './game-transaction-detail';
import GameTransactionUpdate from './game-transaction-update';
import GameTransactionDeleteDialog from './game-transaction-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameTransactionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameTransactionDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameTransaction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameTransactionDeleteDialog} />
  </>
);

export default Routes;
