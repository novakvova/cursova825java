/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import React from 'react';
import configureStore, {history} from './store/configureStore';
import {Provider} from 'react-redux';
import {AsyncStorage} from 'react-native';
import jwt_decode from 'jwt-decode';
import * as loginActions from './components/LoginPage/reducer';

const store = configureStore(history, initialState);
const initialState = window.initialReduxState;

const check = async () => {
  if (await AsyncStorage.getItem('@jwtToken')) {
    let data = {token: await AsyncStorage.getItem('@jwtToken')};
    loginActions.loginByJWT(data, store.dispatch);
  } else {
    loginActions.logout();
  }
};
check();
const RNRedux = () => (
  <Provider store={store}>
    <App />
  </Provider>
);

AppRegistry.registerComponent(appName, () => RNRedux);
