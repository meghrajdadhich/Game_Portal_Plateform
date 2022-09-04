import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameBlog from './game-blog';
import GameBlogDetail from './game-blog-detail';
import GameBlogUpdate from './game-blog-update';
import GameBlogDeleteDialog from './game-blog-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GameBlogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GameBlogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GameBlogDetail} />
      <ErrorBoundaryRoute path={match.url} component={GameBlog} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GameBlogDeleteDialog} />
  </>
);

export default Routes;
