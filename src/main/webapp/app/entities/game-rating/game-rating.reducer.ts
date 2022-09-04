import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGameRating, defaultValue } from 'app/shared/model/game-rating.model';

export const ACTION_TYPES = {
  FETCH_GAMERATING_LIST: 'gameRating/FETCH_GAMERATING_LIST',
  FETCH_GAMERATING: 'gameRating/FETCH_GAMERATING',
  CREATE_GAMERATING: 'gameRating/CREATE_GAMERATING',
  UPDATE_GAMERATING: 'gameRating/UPDATE_GAMERATING',
  DELETE_GAMERATING: 'gameRating/DELETE_GAMERATING',
  RESET: 'gameRating/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGameRating>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GameRatingState = Readonly<typeof initialState>;

// Reducer

export default (state: GameRatingState = initialState, action): GameRatingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMERATING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMERATING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMERATING):
    case REQUEST(ACTION_TYPES.UPDATE_GAMERATING):
    case REQUEST(ACTION_TYPES.DELETE_GAMERATING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMERATING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMERATING):
    case FAILURE(ACTION_TYPES.CREATE_GAMERATING):
    case FAILURE(ACTION_TYPES.UPDATE_GAMERATING):
    case FAILURE(ACTION_TYPES.DELETE_GAMERATING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMERATING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMERATING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMERATING):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMERATING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMERATING):
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

const apiUrl = 'api/game-ratings';

// Actions

export const getEntities: ICrudGetAllAction<IGameRating> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMERATING_LIST,
    payload: axios.get<IGameRating>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGameRating> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMERATING,
    payload: axios.get<IGameRating>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGameRating> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMERATING,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGameRating> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMERATING,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGameRating> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMERATING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
