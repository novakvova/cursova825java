import axios from 'axios';
import {serverUrl} from '../../config';

export default class ProductsListService {
    static getInfo(){
        return axios.get(`${serverUrl}api/v1/products`);
    }
}