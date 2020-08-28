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

import {DrawerContentScrollView, DrawerItem} from '@react-navigation/drawer';
import {Button} from 'react-native-elements';

import Icon from 'react-native-vector-icons/Ionicons';

export function DrawerContent(props) {
  //const paperTheme = useTheme();

  //const { signOut, toggleTheme } = React.useContext(AuthContext);

  return (
    <View style={{flex: 1}}>
      <DrawerContentScrollView {...props}>
        <View style={styles.drawerContent}>
          <View style={styles.userInfoSection}>
              <View style={{flexDirection: 'row', marginTop: 15,justifyContent: 'space-between'}}>
                <Text style={{fontSize: 20}}>Epicentr</Text>
               
                <Button
                type="clear"
                style={{marginRight: 25}}
                onPress={() => {
                    props.navigation.closeDrawer();
                }}
                icon={ <Icon name="arrow-back-outline"  size={20} />}
              />
              </View>
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
