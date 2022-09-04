import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGameHitRatio, defaultValue } from 'app/shared/model/game-hit-ratio.model';

export const ACTION_TYPES = {
  FETCH_GAMEHITRATIO_LIST: 'gameHitRatio/FETCH_GAMEHITRATIO_LIST',
  FETCH_GAMEHITRATIO: 'gameHitRatio/FETCH_GAMEHITRATIO',
  CREATE_GAMEHITRATIO: 'gameHitRatio/CREATE_GAMEHITRATIO',
  UPDATE_GAMEHITRATIO: 'gameHitRatio/UPDATE_GAMEHITRATIO',
  DELETE_GAMEHITRATIO: 'gameHitRatio/DELETE_GAMEHITRATIO',
  RESET: 'gameHitRatio/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGameHitRatio>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GameHitRatioState = Readonly<typeof initialState>;

// Reducer

export default (state: GameHitRatioState = initialState, action): GameHitRatioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMEHITRATIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMEHITRATIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMEHITRATIO):
    case REQUEST(ACTION_TYPES.UPDATE_GAMEHITRATIO):
    case REQUEST(ACTION_TYPES.DELETE_GAMEHITRATIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMEHITRATIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMEHITRATIO):
    case FAILURE(ACTION_TYPES.CREATE_GAMEHITRATIO):
    case FAILURE(ACTION_TYPES.UPDATE_GAMEHITRATIO):
    case FAILURE(ACTION_TYPES.DELETE_GAMEHITRATIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEHITRATIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEHITRATIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMEHITRATIO):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMEHITRATIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMEHITRATIO):
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

const apiUrl = 'api/game-hit-ratios';

// Actions

export const getEntities: ICrudGetAllAction<IGameHitRatio> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEHITRATIO_LIST,
    payload: axios.get<IGameHitRatio>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGameHitRatio> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEHITRATIO,
    payload: axios.get<IGameHitRatio>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGameHitRatio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMEHITRATIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGameHitRatio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMEHITRATIO,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGameHitRatio> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMEHITRATIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
