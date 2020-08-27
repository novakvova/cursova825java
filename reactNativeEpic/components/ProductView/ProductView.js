import React, {Component} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Card, ListItem, Button, Icon} from 'react-native-elements';
import {ScrollView} from 'react-native-gesture-handler';
import {connect} from 'react-redux';
import * as getListActions from './reducer';
import get from 'lodash.get';
import {serverUrl} from '../../config';

class ProductView extends Component {
  static navigationOptions = {
    title: 'Product',
  };

  state = {};
  // componentDidMount() {
  //   const { route } = this.props;
  //   const { id } = route.params;

  //   this.props.getInfo({id});
  // }


  render() {
    
    const {data} = this.props;
    return (
      
      <ScrollView>
        <Text>{data.name}</Text>
       
      </ScrollView>
    );
  }
}
function mapStateToProps(state) {
  return {
    success: get(state, 'productView.list.success'),
    failed: get(state, 'productView.list.failed'),
    errors: get(state, 'productView.list.errors'),
    data: get(state, 'productView.list.data'),
  };
}
const mapDispatchToProps = (dispatch) => {
  return {
    getInfo: (model) => {
      dispatch(getListActions.getInfo(model));
    },
  };
};
export default connect(mapStateToProps, mapDispatchToProps)(ProductView);
