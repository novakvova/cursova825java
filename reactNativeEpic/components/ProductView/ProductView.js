import React, {Component} from 'react';
import {View, Text} from 'react-native';
import {Card, ListItem, Image, Button,Badge} from 'react-native-elements';
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

  state = {};
  componentDidMount() {
    console.log("DDDDDD");
    const {route} = this.props;
    const {id} = route.params;

    this.props.getInfo({id});
  }

  render() {
    const {data, navigation} = this.props;
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
          <ScrollView   horizontal={true}>
            {data.productImages != undefined ? (
              data.productImages.map(function (el) {
                console.log(`${serverUrl}files/` + el.image_name);
                return (
                  <Image
                    style={{width: 200, height: 200,marginLeft:25}}
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
          <Text style={{marginLeft:25,fontSize:25}}>{data.name}</Text>
          <View style={{flexDirection:"row",marginLeft:25}}>
            <Text style={{textDecorationLine:"line-through",fontSize:25}}>{data.price}</Text>
            <View style={{flexDirection:"column",marginLeft:10}}>
              <View style={{flexDirection:"row-reverse"}}>
                <Badge value={"-"+data.discount+"%"} status="error" />

              </View>
              <Text  style={{color:"red",fontSize:25}}>{data.price - (data.price / 100 * data.discount)}</Text>

            </View>

          </View> 

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
