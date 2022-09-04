import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GamePoints from './game-points';
import GamePointsDetail from './game-points-detail';
import GamePointsUpdate from './game-points-update';
import GamePointsDeleteDialog from './game-points-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GamePointsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GamePointsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GamePointsDetail} />
      <ErrorBoundaryRoute path={match.url} component={GamePoints} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GamePointsDeleteDialog} />
  </>
);

export default Routes;
