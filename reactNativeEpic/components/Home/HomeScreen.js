import React, {Component} from 'react';
import {
  View,
  Text,
  Image,
  ScrollView,
  StyleSheet,
  ImageBackground,
  Alert,
} from 'react-native';
import PropTypes from 'prop-types';
import {Button, ThemeProvider} from 'react-native-elements';
import Icon from 'react-native-vector-icons/Ionicons';

const styles = StyleSheet.create({
  textTitle: {
    fontSize: 35,
    color: 'white',
    textAlign: 'center',
    marginBottom: 20,
    marginTop: 20,
  },
  container: {
    paddingVertical: 15,
    paddingHorizontal: 15,
  },
});

const theme = {
  Button: {
    buttonStyle: {
      marginBottom: 20,
    },
    titleStyle: {
      fontSize: 20,
    },
  },
};

// icon={ <Icon
//     name="cart-plus"
//     type="font-awesome"
//      /> }

class HomeScreen extends Component {
  static navigationOptions = {
    header: null,
  };
  state = {};
  render() {
    const {navigation} = this.props;

    const {navigate} = this.props.navigation;

    return (
      <ThemeProvider theme={theme}>
        <ImageBackground
          style={{width: '100%', height: '100%'}}
          source={require('../../Img/background.jpg')}>
          <View style={styles.container}>
            <View style={{flexDirection: 'row'}}>
              <Button
                type="clear"
                onPress={() => {
                  navigation.openDrawer();
                }}
                icon={<Icon name="menu" size={40} />}
              />
            </View>

            <Text style={styles.textTitle}>Epicentr</Text>
            <Button
              large
              titleStyle={{color: 'black'}}
              buttonStyle={{backgroundColor: '#fce4ec'}}
              title="Products"
              onPress={() => navigate('ProductsList')}
            />
          </View>
          <View style={{marginHorizontal: 55, marginTop: 20}}>
            <Button
              title="Інформація"
              onPress={() =>
                Alert.alert(
                  'Info',
                  'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
                )
              }
              titleStyle={{color: 'white'}}
            />
          </View>
        </ImageBackground>
      </ThemeProvider>
    );
  }
}

export default HomeScreen;
