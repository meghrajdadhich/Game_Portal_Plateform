import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMembershipHistory, defaultValue } from 'app/shared/model/membership-history.model';

export const ACTION_TYPES = {
  FETCH_MEMBERSHIPHISTORY_LIST: 'membershipHistory/FETCH_MEMBERSHIPHISTORY_LIST',
  FETCH_MEMBERSHIPHISTORY: 'membershipHistory/FETCH_MEMBERSHIPHISTORY',
  CREATE_MEMBERSHIPHISTORY: 'membershipHistory/CREATE_MEMBERSHIPHISTORY',
  UPDATE_MEMBERSHIPHISTORY: 'membershipHistory/UPDATE_MEMBERSHIPHISTORY',
  DELETE_MEMBERSHIPHISTORY: 'membershipHistory/DELETE_MEMBERSHIPHISTORY',
  RESET: 'membershipHistory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMembershipHistory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type MembershipHistoryState = Readonly<typeof initialState>;

// Reducer

export default (state: MembershipHistoryState = initialState, action): MembershipHistoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEMBERSHIPHISTORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEMBERSHIPHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_MEMBERSHIPHISTORY):
    case REQUEST(ACTION_TYPES.UPDATE_MEMBERSHIPHISTORY):
    case REQUEST(ACTION_TYPES.DELETE_MEMBERSHIPHISTORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_MEMBERSHIPHISTORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEMBERSHIPHISTORY):
    case FAILURE(ACTION_TYPES.CREATE_MEMBERSHIPHISTORY):
    case FAILURE(ACTION_TYPES.UPDATE_MEMBERSHIPHISTORY):
    case FAILURE(ACTION_TYPES.DELETE_MEMBERSHIPHISTORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEMBERSHIPHISTORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEMBERSHIPHISTORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEMBERSHIPHISTORY):
    case SUCCESS(ACTION_TYPES.UPDATE_MEMBERSHIPHISTORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEMBERSHIPHISTORY):
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

const apiUrl = 'api/membership-histories';

// Actions

export const getEntities: ICrudGetAllAction<IMembershipHistory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MEMBERSHIPHISTORY_LIST,
    payload: axios.get<IMembershipHistory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IMembershipHistory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEMBERSHIPHISTORY,
    payload: axios.get<IMembershipHistory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IMembershipHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEMBERSHIPHISTORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMembershipHistory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEMBERSHIPHISTORY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMembershipHistory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEMBERSHIPHISTORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
