import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameRules from './game-rules';
import GameRulesDetail from './game-rules-detail';
import GameRulesUpdate from './game-rules-update';
import GameRulesDeleteDialog from './game-rules-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameRulesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameRulesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameRulesDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameRules} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameRulesDeleteDialog} />
  </>
);

export default Routes;
