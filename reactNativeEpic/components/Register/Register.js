import React, {Component} from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  ActivityIndicator,
  RefreshControl,
  SafeAreaView,
  TextInput
} from 'react-native';
import {
  Card,
  ListItem,  
  Button,
  Badge,
  Avatar,
  Accessory,
} from 'react-native-elements';
import {ScrollView} from 'react-native-gesture-handler';
import {connect} from 'react-redux';
import * as getListActions from './reducer';
import get from 'lodash.get';
import {serverUrl} from '../../config';
import Icon from 'react-native-vector-icons/Ionicons';

class Register extends Component {
  static navigationOptions = {
    title: 'Product',
  };

  state = {
    email: '',
    lastName:"",
    name:"",
    city:"",
    postOffice:"",
    password: '',
  };


  render() {
    const {data, navigation} = this.props;
    const {route} = this.props;
    const {email, password,name,lastName,postOffice,city} = this.state;
    return (
      <React.Fragment>
      <View style={{flexDirection: 'row'}}>
        <Button
          type="clear"
          onPress={() => {
            navigation.openDrawer();
          }}
          icon={<Icon name="menu" size={40} />}
        />
      </View>
      <View style={{marginLeft:25,marginRight:25}}>
        <TextInput
          placeholder="First name"
          onChangeText={(value) => this.setState({name: value})}
        />
         <TextInput
          placeholder="Last name"
          onChangeText={(value) => this.setState({lastName: value})}
        />
        <TextInput
          placeholder="City"
          onChangeText={(value) => this.setState({city: value})}
        />
         <TextInput
          placeholder="Post office"
          onChangeText={(value) => this.setState({postOffice: value})}
        />
        <TextInput
          placeholder="Email"
          onChangeText={(value) => this.setState({email: value})}
        />
        <TextInput
          placeholder="Password"
          secureTextEntry={true}
          onChangeText={(value) => this.setState({password: value})}
        />
        <Button
          title="register"
          onPress={(e) => {
            this.props.register({email, password,name,lastName,postOffice,city});
            this.props.navigation.navigate('LoginPage');
          }}></Button>
      </View>
    </React.Fragment>
    );
  }
}
function mapStateToProps(state) {
  return {
    success: get(state, 'register.list.success'),
    failed: get(state, 'register.list.failed'),
    errors: get(state, 'register.list.errors'),
    data: get(state, 'register.list.data'),
    login: get(state, 'login'),
  };
}
const mapDispatchToProps = (dispatch) => {
  return {
    register: (model) => {
      dispatch(getListActions.register(model));
    },
  };
};
export default connect(mapStateToProps, mapDispatchToProps)(Register);
