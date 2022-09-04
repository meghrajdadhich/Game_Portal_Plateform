import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGameCompany, defaultValue } from 'app/shared/model/game-company.model';

export const ACTION_TYPES = {
  FETCH_GAMECOMPANY_LIST: 'gameCompany/FETCH_GAMECOMPANY_LIST',
  FETCH_GAMECOMPANY: 'gameCompany/FETCH_GAMECOMPANY',
  CREATE_GAMECOMPANY: 'gameCompany/CREATE_GAMECOMPANY',
  UPDATE_GAMECOMPANY: 'gameCompany/UPDATE_GAMECOMPANY',
  DELETE_GAMECOMPANY: 'gameCompany/DELETE_GAMECOMPANY',
  RESET: 'gameCompany/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGameCompany>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GameCompanyState = Readonly<typeof initialState>;

// Reducer

export default (state: GameCompanyState = initialState, action): GameCompanyState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMECOMPANY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMECOMPANY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMECOMPANY):
    case REQUEST(ACTION_TYPES.UPDATE_GAMECOMPANY):
    case REQUEST(ACTION_TYPES.DELETE_GAMECOMPANY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMECOMPANY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMECOMPANY):
    case FAILURE(ACTION_TYPES.CREATE_GAMECOMPANY):
    case FAILURE(ACTION_TYPES.UPDATE_GAMECOMPANY):
    case FAILURE(ACTION_TYPES.DELETE_GAMECOMPANY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMECOMPANY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMECOMPANY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMECOMPANY):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMECOMPANY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMECOMPANY):
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

const apiUrl = 'api/game-companies';

// Actions

export const getEntities: ICrudGetAllAction<IGameCompany> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMECOMPANY_LIST,
    payload: axios.get<IGameCompany>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGameCompany> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMECOMPANY,
    payload: axios.get<IGameCompany>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGameCompany> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMECOMPANY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGameCompany> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMECOMPANY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGameCompany> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMECOMPANY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
