import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GamePlan from './game-plan';
import GamePlanDetail from './game-plan-detail';
import GamePlanUpdate from './game-plan-update';
import GamePlanDeleteDialog from './game-plan-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GamePlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GamePlanUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GamePlanDetail} />
      <ErrorBoundaryRoute path={match.url} component={GamePlan} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GamePlanDeleteDialog} />
  </>
);

export default Routes;
