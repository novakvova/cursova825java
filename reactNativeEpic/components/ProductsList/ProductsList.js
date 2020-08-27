import React, {Component} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Card, ListItem, Button, Icon} from 'react-native-elements';
import {ScrollView} from 'react-native-gesture-handler';
import {connect} from 'react-redux';
import * as getListActions from './reducer';
import get from 'lodash.get';
import {serverUrl} from '../../config';

class ProductsList extends Component {
  static navigationOptions = {
    title: 'Products',
  };

  state = {};
  componentDidMount() {
    this.props.getInfo();
  }
  // redirect=(id)=>{
  //     console.log("MicroCreditScreen "+id);
  //     this.props.navigation.navigate('MicroCreditDetails', {
  //         id: `${id}`,
  //     });
  // }

  render() {
    return (
      <ScrollView>
        
        {this.props.data.map(function (el) {
          return (
            <Card title={el.name} image={{uri:`${serverUrl}files/`+el.productImages[0].image_name}}>
              
          <Text style={{marginBottom: 10}}>
            {el.price}
          </Text>
        </Card>); 
        })}
      </ScrollView>
    );
  }
}
function mapStateToProps(state) {
  return {
    success: get(state, 'productsList.list.success'),
    failed: get(state, 'productsList.list.failed'),
    errors: get(state, 'productsList.list.errors'),
    data: get(state, 'productsList.list.data'),
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
