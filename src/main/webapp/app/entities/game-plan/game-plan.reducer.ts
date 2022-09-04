import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGamePlan, defaultValue } from 'app/shared/model/game-plan.model';

export const ACTION_TYPES = {
  FETCH_GAMEPLAN_LIST: 'gamePlan/FETCH_GAMEPLAN_LIST',
  FETCH_GAMEPLAN: 'gamePlan/FETCH_GAMEPLAN',
  CREATE_GAMEPLAN: 'gamePlan/CREATE_GAMEPLAN',
  UPDATE_GAMEPLAN: 'gamePlan/UPDATE_GAMEPLAN',
  DELETE_GAMEPLAN: 'gamePlan/DELETE_GAMEPLAN',
  RESET: 'gamePlan/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGamePlan>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GamePlanState = Readonly<typeof initialState>;

// Reducer

export default (state: GamePlanState = initialState, action): GamePlanState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMEPLAN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMEPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMEPLAN):
    case REQUEST(ACTION_TYPES.UPDATE_GAMEPLAN):
    case REQUEST(ACTION_TYPES.DELETE_GAMEPLAN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMEPLAN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMEPLAN):
    case FAILURE(ACTION_TYPES.CREATE_GAMEPLAN):
    case FAILURE(ACTION_TYPES.UPDATE_GAMEPLAN):
    case FAILURE(ACTION_TYPES.DELETE_GAMEPLAN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEPLAN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEPLAN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMEPLAN):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMEPLAN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMEPLAN):
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

const apiUrl = 'api/game-plans';

// Actions

export const getEntities: ICrudGetAllAction<IGamePlan> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEPLAN_LIST,
    payload: axios.get<IGamePlan>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGamePlan> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEPLAN,
    payload: axios.get<IGamePlan>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGamePlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMEPLAN,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGamePlan> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMEPLAN,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGamePlan> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMEPLAN,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
