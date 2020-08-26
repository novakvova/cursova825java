import React, { Component } from 'react';
import { Image, Divider, Button } from 'react-native-elements';
import { View, Text, StyleSheet } from 'react-native';
import { ScrollView } from 'react-native-gesture-handler';
import CreditInfoScreenForm from "../CustomElements/CreditInfoScreenForm"





class MicroCreditDetailsScreen extends Component {
    static navigationOptions = {
        title: 'Мікропозика',
    };
    state = {}

    render() {

        return (
            <CreditInfoScreenForm
                title="Bank Name"
                image="https://image.shutterstock.com/image-vector/bank-icon-vector-isolated-260nw-668137015.jpg"
                description={
                    <>
                    <Text>dfsfsdfsdfs dfsdfddfsdfd dsf sd fs dfsdf dfsfsdfsdfs d dsf sd fs dfsdf dfsfsdfsdfs dfsdfd dsf sd fs dfsdf</Text>
                    <Text>dfsfsdfsdfs dfsdfddfsdfd dsf sd fs dfsdf dfsfsdfsdfs d dsf sd fs dfsdf dfsfsdfsdfs dfsdfd dsf sd fs dfsdf</Text>

                    <Text>dfsfsdfsdfs dfsdfddfsdfd dsf sd fs dfsdf dfsfsdfsdfs d dsf sd fs dfsdf dfsfsdfsdfs dfsdfd dsf sd fs dfsdf</Text>
                    </>
                    
            }
                details={
                    <Text>dfsfsdfsdfs dfsdfddfsdfd dsf sd fs dfsdf dfsfsdfsdfs d dsf sd fs dfsdf dfsfsdfsdfs dfsdfd dsf sd fs dfsdf</Text>
                }
                themeColor="#8e24aa"
            />
        );
    }
}

export default MicroCreditDetailsScreen;