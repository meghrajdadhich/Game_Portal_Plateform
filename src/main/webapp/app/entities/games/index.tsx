import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Games from './games';
import GamesDetail from './games-detail';
import GamesUpdate from './games-update';
import GamesDeleteDialog from './games-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GamesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GamesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GamesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Games} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GamesDeleteDialog} />
  </>
);

export default Routes;
