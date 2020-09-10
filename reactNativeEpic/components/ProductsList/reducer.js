
import update from '../../helpers/update';
import ProductsListService from "./ProductsListService"
//action types
export const PRODUCTS_STARTED = "PRODUCTS_STARTED";
export const PRODUCTS_SUCCESS = "PRODUCTS_SUCCESS";
export const PRODUCTS_FAILED = "PRODUCTS_FAILED";


const initialState = {
    list: {
        data: [],
        loading: false,
        success: false,
        failed: false,
    },   
}

export const getInfo = () => {
    return (dispatch) => {
        dispatch(getListActions.started());
        ProductsListService.getInfo()
            .then((response) => {
                //console.log("resp",response);

                dispatch(getListActions.success(response));               
            }, err=> { throw err; })
            .catch(err=> {           
                //console.log("errrr",err);
    
                dispatch(getListActions.failed(err.response.data));               
            });
    }
}



export const getListActions = {
    started: () => {
        return {
            type: PRODUCTS_STARTED
        }
    },  
    success: (response) => {
        return {
            type: PRODUCTS_SUCCESS,
            response:response.data          
        }
    },      
    failed: (error) => {
        return {           
            type: PRODUCTS_FAILED,
            errors: error
        }
    }
  }

export const productsListReducer = (state = initialState, action) => { 
  let newState = state;

  switch (action.type) {

      case PRODUCTS_STARTED: {
          newState = update.set(state, 'list.loading', true);
          newState = update.set(newState, 'list.success', false);
          newState = update.set(newState, 'list.failed', false);
          break;
      }
      case PRODUCTS_SUCCESS: {
          newState = update.set(state, 'list.loading', false);
          newState = update.set(newState, 'list.failed', false);
          newState = update.set(newState, 'list.success', true);       
          newState = update.set(newState, 'list.data', action.response);       

          break;
      }
      case PRODUCTS_FAILED: {
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