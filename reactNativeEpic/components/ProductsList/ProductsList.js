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

class ProductsList extends Component {
  static navigationOptions = {
    title: 'Products',
  };

  state = {
    refreshing: false,
  };
  componentDidMount() {
    this.props.getInfo();
  }
  _onRefresh = () => {
    console.log('ref');
    this.setState({refreshing: true});
    this.props.getInfo();

    setTimeout(() => {
      this.setState({refreshing: false});
    }, 1000);
  };
  render() {
    const redirect = (id) => {
      console.log('ProductView ' + id);
      this.props.navigation.navigate('ProductView', {
        id: `${id}`,
      });
    };

    const {navigation, loading} = this.props;

    console.log('REFst', this.props.login);
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
          <ScrollView
            refreshControl={
              <RefreshControl
                refreshing={this.state.refreshing}
                onRefresh={this._onRefresh.bind(this)}
              />
            }>
            {/* {loading ? (
            <ActivityIndicator size="large" />
          ) : (
            <React.Fragment></React.Fragment>
          )} */}
            {this.props.data.map(function (el) {
              //console.log("ELE",el)
              return (
                <View style={{backgroundColor:"white",margin:10,borderRadius: 20, marginBottom: 10}}>
                  <Image style={{borderRadius: 20,height:200}} source={{
                      uri:
                        `${serverUrl}files/` + el.productImages[0].image_name,
                    }}/>
                    <View style={{marginLeft:15}}>

                    <Text style={{fontSize: 25,marginTop:5, marginBottom: 10}}>
                      {el.name}
                    </Text>
                    {el.discount != null || el.discount != 0 ? (
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
                          {el.price - (el.price / 100) * el.discount}
                        </Text>
                      </View>
                    ) : (
                      <Text style={{marginBottom: 10, fontSize: 25}}>
                        {el.price}
                      </Text>
                    )}
                    <Button
                      type="clear"
                      onPress={() => redirect(el.id)}
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
    success: get(state, 'productsList.list.success'),
    failed: get(state, 'productsList.list.failed'),
    errors: get(state, 'productsList.list.errors'),
    data: get(state, 'productsList.list.data'),
    loading: get(state, 'productsList.list.loading'),
    login: get(state, 'login'),

  };
}
const mapDispatchToProps = (dispatch) => {
  return {
    getInfo: () => {
      dispatch(getListActions.getInfo());
    },
  };
};
export default connect(mapStateToProps, mapDispatchToProps)(ProductsList);
