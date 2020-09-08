import update from '../../helpers/update';
import UserProfileViewService from "./UserProfileViewService"
//action types
export const USERPROFILE_VIEW_STARTED = "USERPROFILE_VIEW_STARTED";
export const USERPROFILE_VIEW_SUCCESS = "USERPROFILE_VIEW_SUCCESS";
export const USERPROFILE_VIEW_FAILED = "USERPROFILE_VIEW_FAILED";


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
        UserProfileViewService.getInfo(model)
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
            type: USERPROFILE_VIEW_STARTED
        }
    },  
    success: (response) => {
        return {
            type: USERPROFILE_VIEW_SUCCESS,
            response:response.data          
        }
    },      
    failed: (error) => {
        return {           
            type: USERPROFILE_VIEW_FAILED,
            errors: error
        }
    }
  }

export const userProfileViewReducer = (state = initialState, action) => { 
  let newState = state;

  switch (action.type) {

      case USERPROFILE_VIEW_STARTED: {
          newState = update.set(state, 'list.loading', true);
          newState = update.set(newState, 'list.success', false);
          newState = update.set(newState, 'list.failed', false);
          break;
      }
      case USERPROFILE_VIEW_SUCCESS: {
          newState = update.set(state, 'list.loading', false);
          newState = update.set(newState, 'list.failed', false);
          newState = update.set(newState, 'list.success', true);       
          newState = update.set(newState, 'list.data', action.response);       

          break;
      }
      case USERPROFILE_VIEW_FAILED: {
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