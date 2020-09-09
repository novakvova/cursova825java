import axios from 'axios';
import {serverUrl} from '../../config';

export default class RegisterService {
    static register(model){
        return axios.post(`${serverUrl}api/v1/auth/register`,model);
    }
   
}