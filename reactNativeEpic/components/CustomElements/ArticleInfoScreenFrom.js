import React, { Component } from 'react';

import { Image, Divider, Button } from 'react-native-elements';
import { View, Text, StyleSheet } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';


const styles = StyleSheet.create({
    textTitle: {
        fontSize: 25,
        color: 'black',
        textAlign: 'center',

        marginTop: 15
    },
    container: {
        paddingVertical: 15,
        paddingHorizontal: 15,
    }

});

const CreditInfoScreenForm = ({ id, title, text }) => (
    <ScrollView>
        <Text style={ styles.textTitle }>{ title }</Text>
        <View style={ styles.container }>
            { text }
        </View>
    </ScrollView>

)
export default CreditInfoScreenForm;