import { applyMiddleware, combineReducers, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import { connectRouter, routerMiddleware } from 'connected-react-router';


///reducers
import {productsListReducer} from '../components/ProductsList/reducer';





// Create browser history to use in the Redux store


export default function configureStore( initialState) {
  const reducers = {
    productsList: productsListReducer,

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
