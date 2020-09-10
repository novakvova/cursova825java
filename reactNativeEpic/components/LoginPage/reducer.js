import update from "../../helpers/update";
import LoginService from "./LoginService";
import isEmpty from "lodash/isEmpty";

//import AsyncStorage from '@react-native-community/async-storage';
import { AsyncStorage } from 'react-native';

import setAuthorizationToken from "../../utils/setAuthorizationToken";
import jwt_decode from "jwt-decode";

export const LOGIN_POST_STARTED = "login/LOGIN_POST_STARTED";
export const LOGIN_POST_SUCCESS = "login/LOGIN_POST_SUCCESS";
export const LOGIN_POST_FAILED = "login/LOGIN_POST_FAILED";
export const LOGIN_SET_CURRENT_USER = "login/SET_CURRENT_USER";

const initialState = {
  post: {
    loading: false,
    success: false,
    failed: false,
    errors: {}
  },
  isAuthenticated: false,
  user: null
};

export const loginReducer = (state = initialState, action) => {
  let newState = state;

  switch (action.type) {
    case LOGIN_POST_STARTED: {
      newState = update.set(state, "post.loading", true);
      newState = update.set(newState, "post.success", false);
      newState = update.set(newState, "post.errors", {});
      newState = update.set(newState, "post.failed", false);
      break;
    }
    case LOGIN_SET_CURRENT_USER: {
      return {
        ...state,
        isAuthenticated: !isEmpty(action.user),
        user: action.user
      };
    }
    case LOGIN_POST_SUCCESS: {
      newState = update.set(state, "post.loading", false);
      newState = update.set(newState, "post.failed", false);
      newState = update.set(newState, "post.errors", {});
      newState = update.set(newState, "post.success", true);
      break;
    }
    case LOGIN_POST_FAILED: {
      newState = update.set(state, "post.loading", false);
      newState = update.set(newState, "post.success", false);
      newState = update.set(newState, "post.errors", action.errors);
      newState = update.set(newState, "post.failed", true);
      break;
    }
    default: {
      return newState;
    }
  }

  return newState;
};

export const login = model => {
  return dispatch => {
    dispatch(loginActions.started());
    LoginService.login(model)
      .then(
        response => {
          console.log("resp",response);

          dispatch(loginActions.success());
          loginByJWT(response.data, dispatch);
          //const pushUrl = getUrlToRedirect();   
          //dispatch(push(pushUrl));
        },
        err => {
          throw err;
        }
      )
      .catch(err => {
        console.log(err);

        dispatch(loginActions.failed(err.response));
      });
  };
};



export const loginActions = {
  started: () => {
    return {
      type: LOGIN_POST_STARTED
    };
  },

  success: () => {
    return {
      type: LOGIN_POST_SUCCESS
    };
  },

  failed: response => {
    
    return {
      type: LOGIN_POST_FAILED,
      errors: response.data
    };
  },

  setCurrentUser: user => {
    ////console.log('LOGIN_SET_CURRENT_USER: ', user);
    return {
      type: LOGIN_SET_CURRENT_USER,
      user
    };
  }
};

export function logout() {
  console.log("logout1-");
  return dispatch => {
    logoutByJWT(dispatch);
  };
}

export const loginByJWT = async (tokens, dispatch) => {
  const { token, refreshToken } = tokens;
  setAuthorizationToken(token);
  ///console.log('Hello app Token: ', token);
  var user = jwt_decode(token);
  console.log('Hello app User: ', user);
  if (!Array.isArray(user.roles)) {
    user.roles = Array.of(user.roles);
  }
  await AsyncStorage.setItem(
    '@jwtToken',
    token
  );
  console.log("token",token);
  console.log("token",await AsyncStorage.getItem(
    '@jwtToken'
  ));
  //localStorage.setItem("jwtToken", token);
  //localStorage.setItem("refreshToken", refreshToken);
  
  dispatch(loginActions.setCurrentUser(user));
};

export const logoutByJWT = async (dispatch) => {
  console.log("logout2")
  await AsyncStorage.removeItem(
    '@jwtToken'
    );
  //localStorage.removeItem("jwtToken");
  //localStorage.removeItem("refreshToken");
  setAuthorizationToken(false);
  dispatch(loginActions.setCurrentUser({}));
};
