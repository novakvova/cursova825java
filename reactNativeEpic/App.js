
import React, {Component} from 'react';
import HomeScreen from "./components/Home/HomeScreen";
import ProductsList from "./components/ProductsList";

import firebase from "firebase";
import { createStackNavigator } from 'react-navigation-stack';
import { createAppContainer } from 'react-navigation';
import { StyleSheet, Text, View } from 'react-native';
const MainNavigator = createStackNavigator(
  {
    Home: { screen: HomeScreen },
    ProductsList: { screen: ProductsList },

  },
  {
    initialRouteName: 'Home',
    navigationOptions: {
      headerStyle: {
        backgroundColor: '#777777',
      },
      headerTintColor: '#fff',
      headerTitleStyle: {
        fontWeight: 'bold',
      },
    },
  },
);

const RootContainer = createAppContainer(MainNavigator);
class App extends React.Component {
  httpGet = () => {

    return new Promise(function (resolve, reject) {

      const db = firebase.database();
      const jobsRef = db.ref('jobs');
      const usersRef = db.ref('users');
      let result = [];
      usersRef.once('value').then((snap) => {
        let data = snap.val();
        let array = Object.keys(data);
        let count = array.length;
        array.map(function (key, index) {
          let item = data[key];
          jobsRef.child(item.job).once('value', g => {
            result.push({ name: item.name, job: g.val() });
            if (count == index + 1) {
              resolve(result);
            }
          });
        });
      });

    });

  }

  UNSAFE_componentWillMount() {

    this.httpGet().then((res) => {
      console.log("Result: ", res);
      res.map(item => {
        console.log("sss: ", item.job);
      });
    });

  }

  render() {
    return (
      <RootContainer />
    );
  }
}

export default App;