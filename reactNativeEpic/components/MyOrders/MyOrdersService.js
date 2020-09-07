import axios from 'axios';
import {serverUrl} from '../../config';

export default class MyOrdersService {
    static getInfo(model){
        //console.log("AXIS",axios.defaults.headers);
        return axios.post(`${serverUrl}api/v1/order/myorders`,model);
    }
}