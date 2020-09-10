import React, {Component} from 'react';
import {connect} from 'react-redux';
import * as loginActions from './reducer';
import get from 'lodash.get';
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
import {Input, Button} from 'react-native-elements';
import Icon from 'react-native-vector-icons/Ionicons';

class Login extends Component {
  static navigationOptions = {
    title: 'Products',
  };

  state = {
    email: '',
    password: '',
  };

  render() {
    const {navigation, loading} = this.props;
    const {email, password} = this.state;

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
        <>
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
            title="Login"
            onPress={(e) => {
              this.props.loginM({username: email, password});
              this.props.navigation.navigate('Home');
            }}></Button>
        </>
      </React.Fragment>
    );
  }
}
function mapStateToProps(state) {
  return {
    loading: get(state, 'login.post.loading'),
    failed: get(state, 'login.post.failed'),
    success: get(state, 'login.post.success'),
    errors: get(state, 'login.post.errors'),
    login: get(state, 'login'),
  };
}

const mapDispatch = {
  loginM: (model, history) => {
    return loginActions.login(model, history);
  },
};

export default connect(mapStateToProps, mapDispatch)(Login);
