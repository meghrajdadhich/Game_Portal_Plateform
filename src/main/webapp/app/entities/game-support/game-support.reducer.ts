import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGameSupport, defaultValue } from 'app/shared/model/game-support.model';

export const ACTION_TYPES = {
  FETCH_GAMESUPPORT_LIST: 'gameSupport/FETCH_GAMESUPPORT_LIST',
  FETCH_GAMESUPPORT: 'gameSupport/FETCH_GAMESUPPORT',
  CREATE_GAMESUPPORT: 'gameSupport/CREATE_GAMESUPPORT',
  UPDATE_GAMESUPPORT: 'gameSupport/UPDATE_GAMESUPPORT',
  DELETE_GAMESUPPORT: 'gameSupport/DELETE_GAMESUPPORT',
  RESET: 'gameSupport/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGameSupport>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GameSupportState = Readonly<typeof initialState>;

// Reducer

export default (state: GameSupportState = initialState, action): GameSupportState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMESUPPORT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMESUPPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMESUPPORT):
    case REQUEST(ACTION_TYPES.UPDATE_GAMESUPPORT):
    case REQUEST(ACTION_TYPES.DELETE_GAMESUPPORT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMESUPPORT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMESUPPORT):
    case FAILURE(ACTION_TYPES.CREATE_GAMESUPPORT):
    case FAILURE(ACTION_TYPES.UPDATE_GAMESUPPORT):
    case FAILURE(ACTION_TYPES.DELETE_GAMESUPPORT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMESUPPORT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMESUPPORT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMESUPPORT):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMESUPPORT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMESUPPORT):
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

const apiUrl = 'api/game-supports';

// Actions

export const getEntities: ICrudGetAllAction<IGameSupport> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMESUPPORT_LIST,
    payload: axios.get<IGameSupport>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGameSupport> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMESUPPORT,
    payload: axios.get<IGameSupport>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGameSupport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMESUPPORT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGameSupport> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMESUPPORT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGameSupport> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMESUPPORT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
