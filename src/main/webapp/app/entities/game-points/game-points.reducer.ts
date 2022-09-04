import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGamePoints, defaultValue } from 'app/shared/model/game-points.model';

export const ACTION_TYPES = {
  FETCH_GAMEPOINTS_LIST: 'gamePoints/FETCH_GAMEPOINTS_LIST',
  FETCH_GAMEPOINTS: 'gamePoints/FETCH_GAMEPOINTS',
  CREATE_GAMEPOINTS: 'gamePoints/CREATE_GAMEPOINTS',
  UPDATE_GAMEPOINTS: 'gamePoints/UPDATE_GAMEPOINTS',
  DELETE_GAMEPOINTS: 'gamePoints/DELETE_GAMEPOINTS',
  RESET: 'gamePoints/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGamePoints>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GamePointsState = Readonly<typeof initialState>;

// Reducer

export default (state: GamePointsState = initialState, action): GamePointsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMEPOINTS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMEPOINTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMEPOINTS):
    case REQUEST(ACTION_TYPES.UPDATE_GAMEPOINTS):
    case REQUEST(ACTION_TYPES.DELETE_GAMEPOINTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMEPOINTS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMEPOINTS):
    case FAILURE(ACTION_TYPES.CREATE_GAMEPOINTS):
    case FAILURE(ACTION_TYPES.UPDATE_GAMEPOINTS):
    case FAILURE(ACTION_TYPES.DELETE_GAMEPOINTS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEPOINTS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEPOINTS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMEPOINTS):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMEPOINTS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMEPOINTS):
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

const apiUrl = 'api/game-points';

// Actions

export const getEntities: ICrudGetAllAction<IGamePoints> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEPOINTS_LIST,
    payload: axios.get<IGamePoints>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGamePoints> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEPOINTS,
    payload: axios.get<IGamePoints>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGamePoints> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMEPOINTS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGamePoints> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMEPOINTS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGamePoints> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMEPOINTS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
