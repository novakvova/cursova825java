import React, {Component} from 'react';
import {View, Text, TextInput, Pressable} from 'react-native';
import {
  Card,
  ListItem,
  Image,
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

class UserProfileView extends Component {
  static navigationOptions = {
    title: 'Product',
  };

  state = {};
  componentDidMount() {
    this.props.getInfo({username: this.props.login.user.sub});
  }

  render() {
    const {data, navigation} = this.props;
    const {route} = this.props;
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
        <ScrollView>
          <Pressable>
            <View style={{flexDirection: 'row', justifyContent: 'center'}}>
              <Avatar
                size="xlarge"
                rounded
                source={{
                  uri: `${serverUrl}files/` + data.image,
                }}></Avatar>
            </View>
          </Pressable>
          <View style={{marginLeft: 25}}>
            <Text style={{marginTop:5,fontSize:25}}>Name: {data.name}</Text>
            <Text style={{marginTop:5,fontSize:25}}>Last Name: {data.lastName}</Text>
            <Text style={{marginTop:5,fontSize:25}}>Email: {data.email}</Text>
            <Text style={{marginTop:5,fontSize:25}}>City: {data.city}</Text>
            <Text style={{marginTop:5,fontSize:25}}>Post office: {data.postOffice}</Text>

          </View>
        </ScrollView>
      </React.Fragment>
    );
  }
}
function mapStateToProps(state) {
  return {
    success: get(state, 'userProfileView.list.success'),
    failed: get(state, 'userProfileView.list.failed'),
    errors: get(state, 'userProfileView.list.errors'),
    data: get(state, 'userProfileView.list.data'),
    login: get(state, 'login'),
  };
}
const mapDispatchToProps = (dispatch) => {
  return {
    getInfo: (model) => {
      dispatch(getListActions.getInfo(model));
    },
  };
};
export default connect(mapStateToProps, mapDispatchToProps)(UserProfileView);
