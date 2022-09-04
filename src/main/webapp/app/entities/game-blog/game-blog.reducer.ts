import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGameBlog, defaultValue } from 'app/shared/model/game-blog.model';

export const ACTION_TYPES = {
  FETCH_GAMEBLOG_LIST: 'gameBlog/FETCH_GAMEBLOG_LIST',
  FETCH_GAMEBLOG: 'gameBlog/FETCH_GAMEBLOG',
  CREATE_GAMEBLOG: 'gameBlog/CREATE_GAMEBLOG',
  UPDATE_GAMEBLOG: 'gameBlog/UPDATE_GAMEBLOG',
  DELETE_GAMEBLOG: 'gameBlog/DELETE_GAMEBLOG',
  RESET: 'gameBlog/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGameBlog>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type GameBlogState = Readonly<typeof initialState>;

// Reducer

export default (state: GameBlogState = initialState, action): GameBlogState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GAMEBLOG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GAMEBLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GAMEBLOG):
    case REQUEST(ACTION_TYPES.UPDATE_GAMEBLOG):
    case REQUEST(ACTION_TYPES.DELETE_GAMEBLOG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GAMEBLOG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GAMEBLOG):
    case FAILURE(ACTION_TYPES.CREATE_GAMEBLOG):
    case FAILURE(ACTION_TYPES.UPDATE_GAMEBLOG):
    case FAILURE(ACTION_TYPES.DELETE_GAMEBLOG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEBLOG_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_GAMEBLOG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GAMEBLOG):
    case SUCCESS(ACTION_TYPES.UPDATE_GAMEBLOG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GAMEBLOG):
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

const apiUrl = 'api/game-blogs';

// Actions

export const getEntities: ICrudGetAllAction<IGameBlog> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEBLOG_LIST,
    payload: axios.get<IGameBlog>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IGameBlog> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GAMEBLOG,
    payload: axios.get<IGameBlog>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGameBlog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GAMEBLOG,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGameBlog> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GAMEBLOG,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGameBlog> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GAMEBLOG,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
