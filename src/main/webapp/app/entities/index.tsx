import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GameAdmin from './game-admin';
import GameManager from './game-manager';
import Membership from './membership';
import MembershipHistory from './membership-history';
import GameRules from './game-rules';
import GameSupport from './game-support';
import Games from './games';
import GamePlan from './game-plan';
import GameCompany from './game-company';
import GamePlatform from './game-platform';
import GamePoints from './game-points';
import GameHitRatio from './game-hit-ratio';
import GameRating from './game-rating';
import GameTransaction from './game-transaction';
import GamePrivacy from './game-privacy';
import Comments from './comments';
import GameBlog from './game-blog';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}game-admin`} component={GameAdmin} />
      <ErrorBoundaryRoute path={`${match.url}game-manager`} component={GameManager} />
      <ErrorBoundaryRoute path={`${match.url}membership`} component={Membership} />
      <ErrorBoundaryRoute path={`${match.url}membership-history`} component={MembershipHistory} />
      <ErrorBoundaryRoute path={`${match.url}game-rules`} component={GameRules} />
      <ErrorBoundaryRoute path={`${match.url}game-support`} component={GameSupport} />
      <ErrorBoundaryRoute path={`${match.url}games`} component={Games} />
      <ErrorBoundaryRoute path={`${match.url}game-plan`} component={GamePlan} />
      <ErrorBoundaryRoute path={`${match.url}game-company`} component={GameCompany} />
      <ErrorBoundaryRoute path={`${match.url}game-platform`} component={GamePlatform} />
      <ErrorBoundaryRoute path={`${match.url}game-points`} component={GamePoints} />
      <ErrorBoundaryRoute path={`${match.url}game-hit-ratio`} component={GameHitRatio} />
      <ErrorBoundaryRoute path={`${match.url}game-rating`} component={GameRating} />
      <ErrorBoundaryRoute path={`${match.url}game-transaction`} component={GameTransaction} />
      <ErrorBoundaryRoute path={`${match.url}game-privacy`} component={GamePrivacy} />
      <ErrorBoundaryRoute path={`${match.url}comments`} component={Comments} />
      <ErrorBoundaryRoute path={`${match.url}game-blog`} component={GameBlog} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
