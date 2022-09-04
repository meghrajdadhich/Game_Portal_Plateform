import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameManager from './game-manager';
import GameManagerDetail from './game-manager-detail';
import GameManagerUpdate from './game-manager-update';
import GameManagerDeleteDialog from './game-manager-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameManagerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameManagerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameManagerDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameManager} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameManagerDeleteDialog} />
  </>
);

export default Routes;
