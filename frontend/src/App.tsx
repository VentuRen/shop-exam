import { Routes, Route, useLocation } from "react-router-dom";
import Navbar from "./components/Navbar";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import CartPage from "./pages/CartPage";
import OrderPage from "./pages/OrderPage";
import PaymentSuccessPage from "./pages/PaymentSuccessPage";
import UserProfile from "./pages/UserProfile";

function Layout() {
  const location = useLocation();
  const showNavbar = location.pathname !== "/login" && location.pathname !== "/register";

  return (
    <div className="min-h-screen flex flex-col bg-white">
      {showNavbar && <Navbar />}
      <main className="flex-grow">
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/" element={<Dashboard />} />
          <Route path="/cart" element={<CartPage />} /> 
          <Route path="/order/:id" element={<OrderPage />} /> 
          <Route path="/payment-success/:id" element={<PaymentSuccessPage />} />
          <Route path="/profile" element={<UserProfile />} />

        </Routes>
      </main>
    </div>
  );
}

export default function App() {
  return <Layout />; 
}
