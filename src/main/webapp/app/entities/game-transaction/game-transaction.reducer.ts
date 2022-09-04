import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGameTransaction, defaultValue } from 'app/shared/model/game-transaction.model';

export const ACTION_TYPES = {
  FETCH_GAMETRANSACTION_LIST: 'gameTransaction/FETCH_GAMETRANSACTION_LIST',
  FETCH_GAMETRANSACTION: 'gameTransaction/FETCH_GAMETRANSACTION',
  CREATE_GAMETRANSACTION: 'gameTransaction/CREATE_GAMETRANSACTION',
  UPDATE_GAMETRANSACTION: 'gameTransaction/UPDATE_GAMETRANSACTION',
  DELETE_GAMETRANSACTION: 'gameTransaction/DELETE_GAMETRANSACTION',
  RESET: 'gameTransaction/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGameTransaction>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GameTransactionState = Readonly<typeof initialState>;

// Reducer

export default (state: GameTransactionState = initialState, action): GameTransactionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMETRANSACTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMETRANSACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMETRANSACTION):
    case REQUEST(ACTION_TYPES.UPDATE_GAMETRANSACTION):
    case REQUEST(ACTION_TYPES.DELETE_GAMETRANSACTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMETRANSACTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMETRANSACTION):
    case FAILURE(ACTION_TYPES.CREATE_GAMETRANSACTION):
    case FAILURE(ACTION_TYPES.UPDATE_GAMETRANSACTION):
    case FAILURE(ACTION_TYPES.DELETE_GAMETRANSACTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMETRANSACTION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMETRANSACTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMETRANSACTION):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMETRANSACTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMETRANSACTION):
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

const apiUrl = 'api/game-transactions';

// Actions

export const getEntities: ICrudGetAllAction<IGameTransaction> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMETRANSACTION_LIST,
    payload: axios.get<IGameTransaction>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGameTransaction> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMETRANSACTION,
    payload: axios.get<IGameTransaction>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGameTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMETRANSACTION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGameTransaction> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMETRANSACTION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGameTransaction> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMETRANSACTION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
