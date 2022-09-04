import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGameAdmin, defaultValue } from 'app/shared/model/game-admin.model';

export const ACTION_TYPES = {
  FETCH_GAMEADMIN_LIST: 'gameAdmin/FETCH_GAMEADMIN_LIST',
  FETCH_GAMEADMIN: 'gameAdmin/FETCH_GAMEADMIN',
  CREATE_GAMEADMIN: 'gameAdmin/CREATE_GAMEADMIN',
  UPDATE_GAMEADMIN: 'gameAdmin/UPDATE_GAMEADMIN',
  DELETE_GAMEADMIN: 'gameAdmin/DELETE_GAMEADMIN',
  RESET: 'gameAdmin/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGameAdmin>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GameAdminState = Readonly<typeof initialState>;

// Reducer

export default (state: GameAdminState = initialState, action): GameAdminState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMEADMIN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMEADMIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMEADMIN):
    case REQUEST(ACTION_TYPES.UPDATE_GAMEADMIN):
    case REQUEST(ACTION_TYPES.DELETE_GAMEADMIN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMEADMIN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMEADMIN):
    case FAILURE(ACTION_TYPES.CREATE_GAMEADMIN):
    case FAILURE(ACTION_TYPES.UPDATE_GAMEADMIN):
    case FAILURE(ACTION_TYPES.DELETE_GAMEADMIN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEADMIN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEADMIN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMEADMIN):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMEADMIN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMEADMIN):
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

const apiUrl = 'api/game-admins';

// Actions

export const getEntities: ICrudGetAllAction<IGameAdmin> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEADMIN_LIST,
    payload: axios.get<IGameAdmin>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGameAdmin> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEADMIN,
    payload: axios.get<IGameAdmin>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGameAdmin> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMEADMIN,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGameAdmin> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMEADMIN,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGameAdmin> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMEADMIN,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
