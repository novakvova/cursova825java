import axios from 'axios';

export default function setAuthorizationToken(token) {
  if (token) {
    console.log("Ax",token);
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  } else {
    delete axios.defaults.headers.common['Authorization'];
  }
}