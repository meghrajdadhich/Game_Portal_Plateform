import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGamePrivacy, defaultValue } from 'app/shared/model/game-privacy.model';

export const ACTION_TYPES = {
  FETCH_GAMEPRIVACY_LIST: 'gamePrivacy/FETCH_GAMEPRIVACY_LIST',
  FETCH_GAMEPRIVACY: 'gamePrivacy/FETCH_GAMEPRIVACY',
  CREATE_GAMEPRIVACY: 'gamePrivacy/CREATE_GAMEPRIVACY',
  UPDATE_GAMEPRIVACY: 'gamePrivacy/UPDATE_GAMEPRIVACY',
  DELETE_GAMEPRIVACY: 'gamePrivacy/DELETE_GAMEPRIVACY',
  RESET: 'gamePrivacy/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGamePrivacy>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GamePrivacyState = Readonly<typeof initialState>;

// Reducer

export default (state: GamePrivacyState = initialState, action): GamePrivacyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMEPRIVACY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMEPRIVACY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMEPRIVACY):
    case REQUEST(ACTION_TYPES.UPDATE_GAMEPRIVACY):
    case REQUEST(ACTION_TYPES.DELETE_GAMEPRIVACY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMEPRIVACY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMEPRIVACY):
    case FAILURE(ACTION_TYPES.CREATE_GAMEPRIVACY):
    case FAILURE(ACTION_TYPES.UPDATE_GAMEPRIVACY):
    case FAILURE(ACTION_TYPES.DELETE_GAMEPRIVACY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEPRIVACY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEPRIVACY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMEPRIVACY):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMEPRIVACY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMEPRIVACY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/game-privacies';

// Actions

export const getEntities: ICrudGetAllAction<IGamePrivacy> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEPRIVACY_LIST,
    payload: axios.get<IGamePrivacy>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGamePrivacy> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEPRIVACY,
    payload: axios.get<IGamePrivacy>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGamePrivacy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMEPRIVACY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGamePrivacy> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMEPRIVACY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGamePrivacy> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMEPRIVACY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
