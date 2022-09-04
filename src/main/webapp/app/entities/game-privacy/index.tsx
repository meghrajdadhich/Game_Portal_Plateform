import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GamePrivacy from './game-privacy';
import GamePrivacyDetail from './game-privacy-detail';
import GamePrivacyUpdate from './game-privacy-update';
import GamePrivacyDeleteDialog from './game-privacy-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GamePrivacyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GamePrivacyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GamePrivacyDetail} />
      <ErrorBoundaryRoute path={match.url} component={GamePrivacy} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GamePrivacyDeleteDialog} />
  </>
);

export default Routes;
