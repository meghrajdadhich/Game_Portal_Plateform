import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameAdmin from './game-admin';
import GameAdminDetail from './game-admin-detail';
import GameAdminUpdate from './game-admin-update';
import GameAdminDeleteDialog from './game-admin-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameAdminUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameAdminUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameAdminDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameAdmin} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameAdminDeleteDialog} />
  </>
);

export default Routes;
