
import update from '../../helpers/update';
import MyOrdersService from "./MyOrdersService"
//action types
export const MYORDERS_STARTED = "MYORDERS_STARTED";
export const MYORDERS_SUCCESS = "MYORDERS_SUCCESS";
export const MYORDERS_FAILED = "MYORDERS_FAILED";


const initialState = {
    list: {
        data: [],
        loading: false,
        success: false,
        failed: false,
    },   
}

export const getInfo = (model) => {
    return (dispatch) => {
        dispatch(getListActions.started());
        MyOrdersService.getInfo(model)
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
            type: MYORDERS_STARTED
        }
    },  
    success: (response) => {
        return {
            type: MYORDERS_SUCCESS,
            response:response.data          
        }
    },      
    failed: (error) => {
        return {           
            type: MYORDERS_FAILED,
            errors: error
        }
    }
  }

export const myOrdersListReducer = (state = initialState, action) => { 
  let newState = state;

  switch (action.type) {

      case MYORDERS_STARTED: {
          newState = update.set(state, 'list.loading', true);
          newState = update.set(newState, 'list.success', false);
          newState = update.set(newState, 'list.failed', false);
          break;
      }
      case MYORDERS_SUCCESS: {
          newState = update.set(state, 'list.loading', false);
          newState = update.set(newState, 'list.failed', false);
          newState = update.set(newState, 'list.success', true);       
          newState = update.set(newState, 'list.data', action.response);       

          break;
      }
      case MYORDERS_FAILED: {
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