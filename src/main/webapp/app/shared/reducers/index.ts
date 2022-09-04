import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import gameAdmin, {
  GameAdminState
} from 'app/entities/game-admin/game-admin.reducer';
// prettier-ignore
import gameManager, {
  GameManagerState
} from 'app/entities/game-manager/game-manager.reducer';
// prettier-ignore
import membership, {
  MembershipState
} from 'app/entities/membership/membership.reducer';
// prettier-ignore
import membershipHistory, {
  MembershipHistoryState
} from 'app/entities/membership-history/membership-history.reducer';
// prettier-ignore
import gameRules, {
  GameRulesState
} from 'app/entities/game-rules/game-rules.reducer';
// prettier-ignore
import gameSupport, {
  GameSupportState
} from 'app/entities/game-support/game-support.reducer';
// prettier-ignore
import games, {
  GamesState
} from 'app/entities/games/games.reducer';
// prettier-ignore
import gamePlan, {
  GamePlanState
} from 'app/entities/game-plan/game-plan.reducer';
// prettier-ignore
import gameCompany, {
  GameCompanyState
} from 'app/entities/game-company/game-company.reducer';
// prettier-ignore
import gamePlatform, {
  GamePlatformState
} from 'app/entities/game-platform/game-platform.reducer';
// prettier-ignore
import gamePoints, {
  GamePointsState
} from 'app/entities/game-points/game-points.reducer';
// prettier-ignore
import gameHitRatio, {
  GameHitRatioState
} from 'app/entities/game-hit-ratio/game-hit-ratio.reducer';
// prettier-ignore
import gameRating, {
  GameRatingState
} from 'app/entities/game-rating/game-rating.reducer';
// prettier-ignore
import gameTransaction, {
  GameTransactionState
} from 'app/entities/game-transaction/game-transaction.reducer';
// prettier-ignore
import gamePrivacy, {
  GamePrivacyState
} from 'app/entities/game-privacy/game-privacy.reducer';
// prettier-ignore
import comments, {
  CommentsState
} from 'app/entities/comments/comments.reducer';
// prettier-ignore
import gameBlog, {
  GameBlogState
} from 'app/entities/game-blog/game-blog.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly gameAdmin: GameAdminState;
  readonly gameManager: GameManagerState;
  readonly membership: MembershipState;
  readonly membershipHistory: MembershipHistoryState;
  readonly gameRules: GameRulesState;
  readonly gameSupport: GameSupportState;
  readonly games: GamesState;
  readonly gamePlan: GamePlanState;
  readonly gameCompany: GameCompanyState;
  readonly gamePlatform: GamePlatformState;
  readonly gamePoints: GamePointsState;
  readonly gameHitRatio: GameHitRatioState;
  readonly gameRating: GameRatingState;
  readonly gameTransaction: GameTransactionState;
  readonly gamePrivacy: GamePrivacyState;
  readonly comments: CommentsState;
  readonly gameBlog: GameBlogState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  gameAdmin,
  gameManager,
  membership,
  membershipHistory,
  gameRules,
  gameSupport,
  games,
  gamePlan,
  gameCompany,
  gamePlatform,
  gamePoints,
  gameHitRatio,
  gameRating,
  gameTransaction,
  gamePrivacy,
  comments,
  gameBlog,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
