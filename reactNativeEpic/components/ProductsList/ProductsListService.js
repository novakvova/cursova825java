import axios from 'axios';
import {serverUrl} from '../../config';

export default class ProductsListService {
    static getInfo(){
        //console.log("AXIS",axios.defaults.headers);
        return axios.get(`${serverUrl}api/v1/products`);
    }
}