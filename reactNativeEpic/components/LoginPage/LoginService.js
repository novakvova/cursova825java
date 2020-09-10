import axios from "axios";
import {serverUrl} from '../../config';

export default class LoginService {
    static login(model) {
        console.log(model);
        return axios.post(`${serverUrl}api/v1/auth/authenticate`, model)
    };
    // static refresh(model) {
    //     console.log("refreshM",model)
    //     return axios.post(`${serverUrl}api/Auth/refresh`, model)
    // };
}