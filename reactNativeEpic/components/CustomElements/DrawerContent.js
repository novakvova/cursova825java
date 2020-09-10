import React from 'react';
import {View, StyleSheet, Text, Image} from 'react-native';
// import {
//     useTheme,
//     Avatar,
//     Title,
//     Caption,
//     Paragraph,
//     Drawer,
//     Text,
//     TouchableRipple,
//     Switch
// } from 'react-native-paper';
import {logoutByJWT} from '../LoginPage/reducer';
import {useStore} from 'react-redux';

import {connect} from 'react-redux';
import get from 'lodash.get';
import {DrawerContentScrollView, DrawerItem} from '@react-navigation/drawer';
import {Button} from 'react-native-elements';

import Icon from 'react-native-vector-icons/Ionicons';

export function DrawerContent(props) {
  //const paperTheme = useTheme();
  const store = useStore();
  const signOut = (e) => {
    e.preventDefault();
    logoutByJWT(store.dispatch);
    props.navigation.navigate('Home');
  };
  //const { signOut, toggleTheme } = React.useContext(AuthContext);

  return (
    <View style={{flex: 1}}>
      <DrawerContentScrollView {...props}>
        <View style={styles.drawerContent}>
          <View style={styles.userInfoSection}>
            <View
              style={{
                flexDirection: 'row',
                marginTop: 15,
                justifyContent: 'space-between',
              }}>
              <Text style={{fontSize: 20}}>Epicentr</Text>

              <Button
                type="clear"
                style={{marginRight: 25}}
                onPress={() => {
                  props.navigation.closeDrawer();
                }}
                icon={<Icon name="arrow-back-outline" size={20} />}
              />
            </View>
            {store.getState().login.isAuthenticated ? (
              <Text>{store.getState().login.user.sub}</Text>
            ) : (
              <></>
            )}
          </View>
          <DrawerItem
            icon={({color, size}) => (
              <Icon name="home-outline" color={color} size={size} />
            )}
            label="Home"
            onPress={() => {
              props.navigation.navigate('Home');
            }}
          />
          <DrawerItem
            icon={({color, size}) => (
              <Icon name="images" color={color} size={size} />
            )}
            label="Products"
            onPress={() => {
              props.navigation.navigate('ProductsList');
            }}
          />
          {store.getState().login.isAuthenticated == false ? (
            <>
            <DrawerItem
              icon={({color, size}) => (
                <Icon name="person-add-outline" color={color} size={size} />
              )}
              label="Register"
              onPress={() => {
                props.navigation.navigate('Register');
              }}
            />
            <DrawerItem
              icon={({color, size}) => (
                <Icon name="person-circle-outline" color={color} size={size} />
              )}
              label="Login"
              onPress={() => {
                props.navigation.navigate('LoginPage');
              }}
            />
            </>
          ) : (
            <>
            <DrawerItem
                icon={({color, size}) => (
                  <Icon name="person-outline" color={color} size={25} />
                )}
                label="My Profile"
                onPress={(e) => {
                  props.navigation.navigate('UserProfile');
                }}
              />
              <DrawerItem
                icon={({color, size}) => (
                  <Icon name="albums-outline" color={color} size={25} />
                )}
                label="My Orders"
                onPress={(e) => {
                  props.navigation.navigate('MyOrders');
                }}
              />
              <DrawerItem
                icon={({color, size}) => (
                  <Icon name="exit-outline" color={color} size={25} />
                )}
                label="Logout"
                onPress={(e) => {
                  signOut(e);
                  props.navigation.navigate('Home');
                }}
              />
            </>
          )}
        </View>
      </DrawerContentScrollView>
    </View>
  );
}

const styles = StyleSheet.create({
  drawerContent: {
    flex: 1,
  },
  userInfoSection: {
    paddingLeft: 20,
  },
  title: {
    fontSize: 16,
    marginTop: 3,
    fontWeight: 'bold',
  },
  caption: {
    fontSize: 14,
    lineHeight: 14,
  },
  row: {
    marginTop: 20,
    flexDirection: 'row',
    alignItems: 'center',
  },
  section: {
    flexDirection: 'row',
    alignItems: 'center',
    marginRight: 15,
  },
  paragraph: {
    fontWeight: 'bold',
    marginRight: 3,
  },
  drawerSection: {
    marginTop: 15,
  },
  bottomDrawerSection: {
    marginBottom: 15,
    borderTopColor: '#f4f4f4',
    borderTopWidth: 1,
  },
  preference: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingVertical: 12,
    paddingHorizontal: 16,
  },
});
function mapStateToProps(state) {
  return {
    login: get(state, 'login'),
  };
}
const mapDispatchToProps = (dispatch) => {
  return {};
};
export default connect(mapStateToProps, mapDispatchToProps)(DrawerContent);
