import React, { Component } from 'react';
import { View, Text, StyleSheet, Alert } from 'react-native';
import { Card, ListItem, Button, Icon } from 'react-native-elements';

const ArticleItem = ({ title, id, redirect }) => (
    <Card titleStyle={ { fontSize: 20 } } title={title}>   
        <Button buttonStyle={ { backgroundColor: "#f9a825" } } onPress={()=>redirect(id)} title="Читати"></Button>
    </Card>
)

export default ArticleItem;