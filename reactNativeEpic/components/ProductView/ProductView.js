import React, {Component} from 'react';
import {View, Text, TextInput} from 'react-native';
import {Card, ListItem, Image, Button, Badge} from 'react-native-elements';
import {ScrollView} from 'react-native-gesture-handler';
import {connect} from 'react-redux';
import * as getListActions from './reducer';
import get from 'lodash.get';
import {serverUrl} from '../../config';
import Icon from 'react-native-vector-icons/Ionicons';

class ProductView extends Component {
  static navigationOptions = {
    title: 'Product',
  };

  state = {count: ''};
  componentDidMount() {
    console.log('DDDDDD');
    const {route} = this.props;
    const {id} = route.params;

    this.props.getInfo({id});
  }

  render() {
    const {data, navigation} = this.props;
    const {route} = this.props;
    const {id} = route.params;
    if (id != data.id) {
      this.props.getInfo({id});
    }
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
          <ScrollView horizontal={true}>
            {data.productImages != undefined ? (
              data.productImages.map(function (el) {
                console.log(`${serverUrl}files/` + el.image_name);
                return (
                  <Image
                    style={{
                      borderRadius: 20,
                      width: 200,
                      height: 200,
                      marginLeft: 25,
                    }}
                    source={{
                      uri: `${serverUrl}files/` + el.image_name,
                    }}
                  />
                );
              })
            ) : (
              <React.Fragment></React.Fragment>
            )}
          </ScrollView>
          <Text
            style={{
              marginLeft: 25,
              fontSize: 35,
              fontWeight: 'bold',
              marginBottom: 10,
            }}>
            {data.name}
          </Text>
          <View
            style={{
              flexDirection: 'row',
              alignItems: 'flex-end',
              marginLeft: 25,
            }}>
            <Text style={{textDecorationLine: 'line-through', fontSize: 25}}>
              {data.price}
            </Text>
            <View style={{flexDirection: 'column', marginLeft: 10}}>
              <View style={{marginLeft: -25, flexDirection: 'row-reverse'}}>
                <Badge value={'-' + data.discount + '%'} status="error" />
              </View>
              <Text style={{color: 'red', fontSize: 25}}>
                {data.price - (data.price / 100) * data.discount}
              </Text>
            </View>
          </View>
          <Text style={{marginLeft: 25, marginTop: 10, fontSize: 15}}>
            Description:
          </Text>
          <Text style={{marginLeft: 25, marginTop: 10, fontSize: 12}}>
            {data.description}
          </Text>
          {this.props.login.isAuthenticated ? (
            <View style={{flexDirection: 'row', marginTop: 10}}>
              <TextInput
                style={{marginLeft: 25, marginRight: 5}}
                placeholder="Count"
                autoCompleteType="cc-number"
                onChangeText={(value) => this.setState({count: value})}
              />
              <Button
                title="Order"
                onPress={(e) => {
                  this.props.createOrder({
                    username: this.props.login.user.sub,
                    productId: data.id,
                    count: this.state.count,
                  });
                  this.props.navigation.navigate('Home');
                }}></Button>
            </View>
          ) : (
            <></>
          )}
        </ScrollView>
      </React.Fragment>
    );
  }
}
function mapStateToProps(state) {
  return {
    success: get(state, 'productView.list.success'),
    failed: get(state, 'productView.list.failed'),
    errors: get(state, 'productView.list.errors'),
    data: get(state, 'productView.list.data'),
    login: get(state, 'login'),
  };
}
const mapDispatchToProps = (dispatch) => {
  return {
    getInfo: (model) => {
      dispatch(getListActions.getInfo(model));
    },
    createOrder: (model) => {
      dispatch(getListActions.createOrder(model));
    },
  };
};
export default connect(mapStateToProps, mapDispatchToProps)(ProductView);
