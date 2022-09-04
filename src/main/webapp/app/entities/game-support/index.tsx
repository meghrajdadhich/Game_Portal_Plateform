import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameSupport from './game-support';
import GameSupportDetail from './game-support-detail';
import GameSupportUpdate from './game-support-update';
import GameSupportDeleteDialog from './game-support-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameSupportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameSupportUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameSupportDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameSupport} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameSupportDeleteDialog} />
  </>
);

export default Routes;
