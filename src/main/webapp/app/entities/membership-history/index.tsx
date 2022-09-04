import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MembershipHistory from './membership-history';
import MembershipHistoryDetail from './membership-history-detail';
import MembershipHistoryUpdate from './membership-history-update';
import MembershipHistoryDeleteDialog from './membership-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MembershipHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MembershipHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MembershipHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={MembershipHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MembershipHistoryDeleteDialog} />
  </>
);

export default Routes;
