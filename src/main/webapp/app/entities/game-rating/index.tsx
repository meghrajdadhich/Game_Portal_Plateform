import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameRating from './game-rating';
import GameRatingDetail from './game-rating-detail';
import GameRatingUpdate from './game-rating-update';
import GameRatingDeleteDialog from './game-rating-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameRatingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameRatingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameRatingDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameRating} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameRatingDeleteDialog} />
  </>
);

export default Routes;
