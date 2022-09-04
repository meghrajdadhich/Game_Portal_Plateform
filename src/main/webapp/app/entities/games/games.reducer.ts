import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGames, defaultValue } from 'app/shared/model/games.model';

export const ACTION_TYPES = {
  FETCH_GAMES_LIST: 'games/FETCH_GAMES_LIST',
  FETCH_GAMES: 'games/FETCH_GAMES',
  CREATE_GAMES: 'games/CREATE_GAMES',
  UPDATE_GAMES: 'games/UPDATE_GAMES',
  DELETE_GAMES: 'games/DELETE_GAMES',
  RESET: 'games/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGames>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GamesState = Readonly<typeof initialState>;

// Reducer

export default (state: GamesState = initialState, action): GamesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMES):
    case REQUEST(ACTION_TYPES.UPDATE_GAMES):
    case REQUEST(ACTION_TYPES.DELETE_GAMES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMES):
    case FAILURE(ACTION_TYPES.CREATE_GAMES):
    case FAILURE(ACTION_TYPES.UPDATE_GAMES):
    case FAILURE(ACTION_TYPES.DELETE_GAMES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMES):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMES):
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

const apiUrl = 'api/games';

// Actions

export const getEntities: ICrudGetAllAction<IGames> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMES_LIST,
    payload: axios.get<IGames>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGames> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMES,
    payload: axios.get<IGames>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGames> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGames> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGames> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMES,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
