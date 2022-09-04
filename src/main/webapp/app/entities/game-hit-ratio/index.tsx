import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameHitRatio from './game-hit-ratio';
import GameHitRatioDetail from './game-hit-ratio-detail';
import GameHitRatioUpdate from './game-hit-ratio-update';
import GameHitRatioDeleteDialog from './game-hit-ratio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameHitRatioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameHitRatioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameHitRatioDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameHitRatio} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameHitRatioDeleteDialog} />
  </>
);

export default Routes;
