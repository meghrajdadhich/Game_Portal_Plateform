import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GamePlatform from './game-platform';
import GamePlatformDetail from './game-platform-detail';
import GamePlatformUpdate from './game-platform-update';
import GamePlatformDeleteDialog from './game-platform-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GamePlatformUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GamePlatformUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GamePlatformDetail} />
      <ErrorBoundaryRoute path={match.url} component={GamePlatform} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GamePlatformDeleteDialog} />
  </>
);

export default Routes;
