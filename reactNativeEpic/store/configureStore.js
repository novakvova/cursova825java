import { applyMiddleware, combineReducers, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import { connectRouter, routerMiddleware } from 'connected-react-router';


///reducers
import {productsListReducer} from '../components/ProductsList/reducer';
import {userProfileViewReducer} from '../components/UserProfile/reducer';
import {productViewReducer} from '../components/ProductView/reducer';
import {loginReducer} from '../components/LoginPage/reducer';




// Create browser history to use in the Redux store


export default function configureStore( initialState) {
  const reducers = {
    productsList: productsListReducer,
    productView:productViewReducer,
    userProfileView: userProfileViewReducer,
    login:loginReducer,
  };
  const middleware = [
    thunk
  ];
  const enhancers = [];
  const rootReducer = combineReducers({
    ...reducers
  });

  return createStore(
    rootReducer,
    initialState,
    compose(applyMiddleware(...middleware), ...enhancers)
  );
}
