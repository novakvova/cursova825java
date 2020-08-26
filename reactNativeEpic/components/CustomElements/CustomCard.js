import React, { Component } from 'react';
import { View, Text, StyleSheet} from 'react-native';

// import { Button, ThemeProvider, Icon } from "react-native-elements"
import { Card, ListItem, Button, Icon } from 'react-native-elements';

const CustomCard = ({id, image, title, description, btnColor, redirect }) => (
    <Card titleStyle={ { fontSize: 20 } } title={title}
        image={ { uri: image } }
    >   
        <Text style={ { marginBottom: 10, fontSize: 16 } }>
            {description}
        </Text>
        <Button buttonStyle={ { backgroundColor: btnColor } } onPress={()=>redirect(id)}  title="Детальніше"></Button>
    </Card>
)

export default CustomCard;