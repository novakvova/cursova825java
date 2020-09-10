import React, {Component} from 'react';
import HomeScreen from './components/Home/HomeScreen';
import ProductsList from './components/ProductsList';
import ProductView from './components/ProductView';
import LoginPage from './components/LoginPage/LoginPage';
import MyOrders from './components/MyOrders/MyOrders';
import UserProfile from './components/UserProfile/';
import Register from './components/Register';



import firebase from 'firebase';
import {createStackNavigator} from 'react-navigation-stack';
import {createAppContainer} from 'react-navigation';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { NavigationContainer } from '@react-navigation/native';
import {DrawerContent} from "./components/CustomElements/DrawerContent"
import {StyleSheet, Text, View} from 'react-native';
const MainNavigator = createStackNavigator(
  {
    Home: {screen: HomeScreen},
    ProductsList: {screen: ProductsList},
    ProductView: {screen: ProductView},
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
const Drawer = createDrawerNavigator();
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
          jobsRef.child(item.job).once('value', (g) => {
            result.push({name: item.name, job: g.val()});
            if (count == index + 1) {
              resolve(result);
            }
          });
        });
      });
    });
  };

  UNSAFE_componentWillMount() {
    this.httpGet().then((res) => {
      console.log('Result: ', res);
      res.map((item) => {
        console.log('sss: ', item.job);
      });
    });
  }

  render() {
    return (
      //  <RootContainer />

      <NavigationContainer>
        <Drawer.Navigator  drawerContent={props => <DrawerContent {...props} />} >
          <Drawer.Screen name="Home" component={HomeScreen} />
          <Drawer.Screen name="ProductsList" component={ProductsList} />
          <Drawer.Screen name="ProductView" component={ProductView} />
          <Drawer.Screen name="LoginPage" component={LoginPage} />
          <Drawer.Screen name="UserProfile" component={UserProfile} />
          <Drawer.Screen name="MyOrders" component={MyOrders} />
          <Drawer.Screen name="Register" component={Register} />
        </Drawer.Navigator>
      </NavigationContainer>

    );
  }
}

export default App;
