
//action types
export const GET_LIST_MICRO_CREDIT_STARTED = 'microCredit/GET_LIST_MICRO_CREDIT_STARTED';
export const GET_LIST_MICRO_CREDIT_SUCCESS = 'microCredit/GET_LIST_MICRO_CREDIT_SUCCESS';
export const GET_LIST_MICRO_CREDIT_FAILED = 'microCredit/GET_LIST_MICRO_CREDIT_FAILED';


const initialState = {
    data: {},
    loading: false,
    error: {},
}

//reducer
export const microCreditReducer = (state = initialState, action) => {
    switch (action.type) {
        case GET_LIST_MICRO_CREDIT_STARTED: {
            return {
                ...state,
                loading: true,
                error: {}
            }
        }
        case GET_LIST_MICRO_CREDIT_SUCCESS: {
            return {
                ...state,
                loading: false,
                data: action.payload,
            }
        }
        case GET_LIST_MICRO_CREDIT_FAILED: {
            return {
                ...state,
                loading: true,
                error: action.error
            }
        }
    }
}

//action creator

export const listMicroCreditActions = {
    started: () => {
        return {
            type: GET_LIST_MICRO_CREDIT_STARTED,
        };
    },
    success: response => {
        return {
            type: GET_LIST_MICRO_CREDIT_SUCCESS,
            payload: response.data,
        };
    },
    failed: (response) => {
        return {
            type: GET_LIST_MICRO_CREDIT_FAILED,
            error: response.data,
        };
    }
}