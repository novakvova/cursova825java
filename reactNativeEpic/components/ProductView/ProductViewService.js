import axios from 'axios';
import {serverUrl} from '../../config';

export default class ProductViewService {
    static getInfo(model){
        return axios.get(`${serverUrl}api/v1/products/`+model.id);
    }
    static createOrder(model){
        return axios.post(`${serverUrl}api/v1/order/create`,model);
    }
}