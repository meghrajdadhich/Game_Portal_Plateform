import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameCompany from './game-company';
import GameCompanyDetail from './game-company-detail';
import GameCompanyUpdate from './game-company-update';
import GameCompanyDeleteDialog from './game-company-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameCompanyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameCompanyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameCompanyDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameCompany} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameCompanyDeleteDialog} />
  </>
);

export default Routes;
