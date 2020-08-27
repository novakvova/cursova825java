/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import React from 'react';
import configureStore, { history } from './store/configureStore';
import { Provider } from 'react-redux';

const store = configureStore(history, initialState);
const initialState = window.initialReduxState;

const RNRedux = () => (
    <Provider store={ store }>
        <App/>
    </Provider>
);

AppRegistry.registerComponent(appName, () => RNRedux);
