import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGamePlatform, defaultValue } from 'app/shared/model/game-platform.model';

export const ACTION_TYPES = {
  FETCH_GAMEPLATFORM_LIST: 'gamePlatform/FETCH_GAMEPLATFORM_LIST',
  FETCH_GAMEPLATFORM: 'gamePlatform/FETCH_GAMEPLATFORM',
  CREATE_GAMEPLATFORM: 'gamePlatform/CREATE_GAMEPLATFORM',
  UPDATE_GAMEPLATFORM: 'gamePlatform/UPDATE_GAMEPLATFORM',
  DELETE_GAMEPLATFORM: 'gamePlatform/DELETE_GAMEPLATFORM',
  RESET: 'gamePlatform/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGamePlatform>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GamePlatformState = Readonly<typeof initialState>;

// Reducer

export default (state: GamePlatformState = initialState, action): GamePlatformState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMEPLATFORM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMEPLATFORM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMEPLATFORM):
    case REQUEST(ACTION_TYPES.UPDATE_GAMEPLATFORM):
    case REQUEST(ACTION_TYPES.DELETE_GAMEPLATFORM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMEPLATFORM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMEPLATFORM):
    case FAILURE(ACTION_TYPES.CREATE_GAMEPLATFORM):
    case FAILURE(ACTION_TYPES.UPDATE_GAMEPLATFORM):
    case FAILURE(ACTION_TYPES.DELETE_GAMEPLATFORM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEPLATFORM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEPLATFORM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMEPLATFORM):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMEPLATFORM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMEPLATFORM):
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

const apiUrl = 'api/game-platforms';

// Actions

export const getEntities: ICrudGetAllAction<IGamePlatform> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEPLATFORM_LIST,
    payload: axios.get<IGamePlatform>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGamePlatform> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEPLATFORM,
    payload: axios.get<IGamePlatform>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGamePlatform> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMEPLATFORM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGamePlatform> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMEPLATFORM,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGamePlatform> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMEPLATFORM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
