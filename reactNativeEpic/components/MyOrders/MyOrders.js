import React, {Component} from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  ActivityIndicator,
  RefreshControl,
  SafeAreaView,
} from 'react-native';
import {Card, ListItem, Button} from 'react-native-elements';
import {ScrollView} from 'react-native-gesture-handler';
import {connect} from 'react-redux';
import get from 'lodash.get';
import * as getListActions from './reducer';
import {serverUrl} from '../../config';
import Icon from 'react-native-vector-icons/Ionicons';

class MyOrders extends Component {
  static navigationOptions = {
    title: 'Orders',
  };

  state = {
    refreshing: false,
  };
  componentDidMount() {
    this.props.getInfo({username:this.props.login.user.sub});
  }
  
  render() {
    const {navigation, loading} = this.props;
    const redirect = (id) => {
      console.log('ProductView ' + id);
      this.props.navigation.navigate('ProductView', {
        id: `${id}`,
      });
    };
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
        {/* <View style={{flex: 1, justifyContent: 'center'}}>
          <ActivityIndicator />
        </View> */}
        <>
          <ScrollView>
            {this.props.data.map(function (el) {
              console.log("ELE",el)
              return (
                <View style={{backgroundColor:"white",margin:10,borderRadius: 20, marginBottom: 10}}>
                  <Image style={{borderRadius: 20,height:200}} source={{
                      uri:
                        `${serverUrl}files/` + el.product.productImages[0].image_name,
                    }}/>
                    <View style={{marginLeft:15}}>

                    <Text style={{fontSize: 25,marginTop:5, marginBottom: 10}}>
                      {el.product.name}
                    </Text>
                    {el.product.discount != null || el.product.discount != 0 ? (
                      <View style={{flexDirection: 'row'}}>
                        <Text
                          style={{
                            marginBottom: 10,
                            marginRight: 10,
                            textDecorationLine: 'line-through',
                            fontSize: 18,
                          }}>
                          {el.price}
                        </Text>
                        <Text style={{color: 'red', fontSize: 25}}>
                          {el.product.price - (el.product.price / 100) * el.product.discount}
                        </Text>
                      </View>
                    ) : (
                      <Text style={{marginBottom: 10, fontSize: 25}}>
                        {el.product.price}
                      </Text>
                    )}
                    <Text style={{marginBottom: 10, fontSize: 25}}>
                        Count: {el.count}
                      </Text>
                      <Text style={{marginBottom: 10, fontSize: 25}}>
                        Status: {el.orderStatus.name}
                      </Text>
                    <Button
                      type="clear"
                      onPress={() => redirect(el.product.id)}
                      title="More info"></Button>
                    </View>
                
                </View>
              );
            })}
          </ScrollView>
        </>
      </React.Fragment>
    );
  }
}
function mapStateToProps(state) {
  return {
    success: get(state, 'myOrders.list.success'),
    failed: get(state, 'myOrders.list.failed'),
    errors: get(state, 'myOrders.list.errors'),
    data: get(state, 'myOrders.list.data'),
    loading: get(state, 'myOrders.list.loading'),
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
export default connect(mapStateToProps, mapDispatchToProps)(MyOrders);
