import update from '../../helpers/update';
import RegisterService from "./RegisterService"
//action types
export const REGISTER_VIEW_STARTED = "REGISTER_VIEW_STARTED";
export const REGISTER_VIEW_SUCCESS = "REGISTER_VIEW_SUCCESS";
export const REGISTER_VIEW_FAILED = "REGISTER_VIEW_FAILED";


const initialState = {
    list: {
        data: [],
        loading: false,
        success: false,
        failed: false,
    },   
}


export const register = (model) => {
    return (dispatch) => {
        dispatch(getListActions.started());
        RegisterService.register(model)
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
            type: REGISTER_VIEW_STARTED
        }
    },  
    success: (response) => {
        return {
            type: REGISTER_VIEW_SUCCESS,
            response:response.data          
        }
    },      
    failed: (error) => {
        return {           
            type: REGISTER_VIEW_FAILED,
            errors: error
        }
    }
  }

export const registerReducer = (state = initialState, action) => { 
  let newState = state;

  switch (action.type) {

      case REGISTER_VIEW_STARTED: {
          newState = update.set(state, 'list.loading', true);
          newState = update.set(newState, 'list.success', false);
          newState = update.set(newState, 'list.failed', false);
          break;
      }
      case REGISTER_VIEW_SUCCESS: {
          newState = update.set(state, 'list.loading', false);
          newState = update.set(newState, 'list.failed', false);
          newState = update.set(newState, 'list.success', true);       
          newState = update.set(newState, 'list.data', action.response);       

          break;
      }
      case REGISTER_VIEW_FAILED: {
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