import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/game-admin">
      <Translate contentKey="global.menu.entities.gameAdmin" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-manager">
      <Translate contentKey="global.menu.entities.gameManager" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/membership">
      <Translate contentKey="global.menu.entities.membership" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/membership-history">
      <Translate contentKey="global.menu.entities.membershipHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-rules">
      <Translate contentKey="global.menu.entities.gameRules" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-support">
      <Translate contentKey="global.menu.entities.gameSupport" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/games">
      <Translate contentKey="global.menu.entities.games" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-plan">
      <Translate contentKey="global.menu.entities.gamePlan" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-company">
      <Translate contentKey="global.menu.entities.gameCompany" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-platform">
      <Translate contentKey="global.menu.entities.gamePlatform" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-points">
      <Translate contentKey="global.menu.entities.gamePoints" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-hit-ratio">
      <Translate contentKey="global.menu.entities.gameHitRatio" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-rating">
      <Translate contentKey="global.menu.entities.gameRating" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-transaction">
      <Translate contentKey="global.menu.entities.gameTransaction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-privacy">
      <Translate contentKey="global.menu.entities.gamePrivacy" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/comments">
      <Translate contentKey="global.menu.entities.comments" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/game-blog">
      <Translate contentKey="global.menu.entities.gameBlog" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
