import React, { Component } from 'react';

import { Image, Divider, Button } from 'react-native-elements';
import { View, Text, StyleSheet } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';


const styles = StyleSheet.create({
    textTitle: {
        fontSize: 35,
        color: 'black',
        textAlign: 'center',
        marginBottom: 20,
        marginTop: 20
    },
    container: {
        paddingVertical: 15,
        paddingHorizontal: 15,
    }

});

const CreditInfoScreenForm = ({id, title, image, description, details, themeColor})=>(
    <ScrollView style={{backgroundColor: "#f5f5f5"}}>
        <View style={{backgroundColor: "#fafafa", paddingTop: 15, paddingHorizontal: 15}}>
                    <Image
                        style={ { width: '100%', height: 200 } }
                        source={ { uri: image } } />

                    <Text style={ styles.textTitle }>{title}</Text>
                </View>
                
                <View  style={{paddingBottom: 15, paddingHorizontal: 15}}>
                <Button buttonStyle={{backgroundColor:themeColor, marginBottom: 15}} titleStyle={{fontSize: 20}} title="Відправити заяву"></Button>

                    {details}
                    <Divider style={{backgroundColor: themeColor, marginVertical: 15}}></Divider>
                    {description}

                </View>
    </ScrollView>
    
)
export default CreditInfoScreenForm;