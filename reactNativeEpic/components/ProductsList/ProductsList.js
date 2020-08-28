import React, {Component} from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {Card, ListItem, Button} from 'react-native-elements';
import {ScrollView} from 'react-native-gesture-handler';
import {connect} from 'react-redux';
import * as getListActions from './reducer';
import get from 'lodash.get';
import {serverUrl} from '../../config';
import Icon from 'react-native-vector-icons/Ionicons';

class ProductsList extends Component {
  static navigationOptions = {
    title: 'Products',
  };

  state = {};
  componentDidMount() {
    this.props.getInfo();
  }
  

  render() {
    const redirect = (id) => {
        console.log("ProductView "+id);
        this.props.navigation.navigate('ProductView', {
          id: `${id}`,
        });
      };
      const {navigation} = this.props;

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
        {this.props.data.map(function (el) {
          return (
            <Card
              title={el.name}
              image={{
                uri: `${serverUrl}files/` + el.productImages[0].image_name,
              }}>
              <Text style={{marginBottom: 10}}>{el.price}</Text>
              <Button  onPress={() => redirect(el.id)}  title="More info"></Button>
            </Card>
          );
        })}
      </ScrollView>
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
