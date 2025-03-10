import { createSlice } from "@reduxjs/toolkit";

interface UserState {
  token: string | null;
  user: {
    id: number;
    firstName: string;
    email: string;
    role: string;
  } | null;
}

const initialState: UserState = {
  token: localStorage.getItem("token") || null,
  user: JSON.parse(localStorage.getItem("user") || "null"),
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setUser: (state, action) => {
      state.token = action.payload.token;
      state.user = action.payload.user;
      localStorage.setItem("token", action.payload.token);
      localStorage.setItem("user", JSON.stringify(action.payload.user));
    },
    logout: (state) => {
      state.token = null;
      state.user = null;
      localStorage.removeItem("token");
      localStorage.removeItem("user");
    },
  },
});

export const { setUser, logout } = authSlice.actions;
export default authSlice.reducer;
