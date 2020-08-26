/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import React from 'react';

const RNRedux = () => (
    <App/>
);

AppRegistry.registerComponent(appName, () => RNRedux);
