
import update from '../../helpers/update';
import ProductViewService from "./ProductViewService"
//action types
export const PRODUCT_VIEW_STARTED = "PRODUCT_VIEW_STARTED";
export const PRODUCT_VIEW_SUCCESS = "PRODUCT_VIEW_SUCCESS";
export const PRODUCT_VIEW_FAILED = "PRODUCT_VIEW_FAILED";


const initialState = {
    list: {
        data: [],
        loading: false,
        success: false,
        failed: false,
    },   
}

export const createOrder = (model) => {
    return (dispatch) => {
        ProductViewService.createOrder(model)
            .then((response) => {            
            }, err=> { throw err; })
            .catch(err=> {               
                dispatch(getListActions.failed(err.response.data));               
            });
    }
}
export const getInfo = (model) => {
    return (dispatch) => {
        dispatch(getListActions.started());
        ProductViewService.getInfo(model)
            .then((response) => {
                dispatch(getListActions.success(response));               
            }, err=> { throw err; })
            .catch(err=> {               
                dispatch(getListActions.failed(err.response.data));               
            });
    }
}



export const getListActions = {
    started: () => {
        return {
            type: PRODUCT_VIEW_STARTED
        }
    },  
    success: (response) => {
        return {
            type: PRODUCT_VIEW_SUCCESS,
            response:response.data          
        }
    },      
    failed: (error) => {
        return {           
            type: PRODUCT_VIEW_FAILED,
            errors: error
        }
    }
  }

export const productViewReducer = (state = initialState, action) => { 
  let newState = state;

  switch (action.type) {

      case PRODUCT_VIEW_STARTED: {
          newState = update.set(state, 'list.loading', true);
          newState = update.set(newState, 'list.success', false);
          newState = update.set(newState, 'list.failed', false);
          break;
      }
      case PRODUCT_VIEW_SUCCESS: {
          newState = update.set(state, 'list.loading', false);
          newState = update.set(newState, 'list.failed', false);
          newState = update.set(newState, 'list.success', true);       
          newState = update.set(newState, 'list.data', action.response);       

          break;
      }
      case PRODUCT_VIEW_FAILED: {
          newState = update.set(state, 'list.loading', false);
          newState = update.set(newState, 'list.success', false);
          newState = update.set(newState, 'list.failed', true);
          newState = update.set(newState, "list.errors", action.errors);
          break;
      }
      default: {
          return newState;
      }
  }
  return newState;
}