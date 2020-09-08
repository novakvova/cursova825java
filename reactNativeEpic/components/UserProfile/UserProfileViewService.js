import axios from 'axios';
import {serverUrl} from '../../config';

export default class UserProfileViewService {
    static getInfo(model){
        return axios.post(`${serverUrl}api/v1/auth/profile`,model);
    }
   
}