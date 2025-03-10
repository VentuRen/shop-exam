import { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../store";
import { Link, useNavigate } from "react-router-dom";
import { api } from "../api/api";
import { logout } from "../features/authSlice"; // Importar acción de logout
import "../styles/Navbar.css";

export default function Navbar() {
  const user = useSelector((state: RootState) => state.auth.user);
  const [cartCount, setCartCount] = useState(0);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 🔹 Obtener el carrito del usuario
  const fetchCart = () => {
    if (!user) return;
    api.get("/cart", {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    })
      .then((res) => {
        const totalItems = res.data.reduce((acc: number, item: any) => acc + item.quantity, 0);
        setCartCount(totalItems);
      })
      .catch((err) => console.error("Error al obtener el carrito", err));
  };

  // 🔹 Llamar a `fetchCart` al cargar y cuando el usuario cambia
  useEffect(() => {
    fetchCart();
    window.addEventListener("cartUpdated", fetchCart);
    return () => window.removeEventListener("cartUpdated", fetchCart);
  }, [user]);

  // 🔹 Función de Logout
  const handleLogout = () => {
    localStorage.removeItem("token"); // Elimina el token
    localStorage.removeItem("user"); // Elimina datos del usuario
    dispatch(logout()); // Limpia el estado global
    navigate("/login"); // Redirigir a login
  };

  return (
    <nav className="navbar">
      <Link to="/dashboard" className="logo">🛍️ Tienda</Link>

      <div className="nav-options">
        {user ? (
          <>
            <Link to="/profile" style={{ textDecoration: "none", color: "white", fontSize: "16px", fontWeight: "bold" }}>
              👋 {user.firstName}
            </Link>

            <Link to="/cart" className="cart-button">
              🛒
              {cartCount > 0 && <span className="cart-count">{cartCount}</span>}
            </Link>

            {user.role === "ADMIN" && (
              <Link to="/add-product" className="admin-button">➕ Agregar Producto</Link>
            )}

            <button className="logout-button" onClick={handleLogout}>🚪 Salir</button>
          </>
        ) : (
          <Link to="/login" className="login-button">Iniciar Sesión</Link>
        )}
      </div>
    </nav>
  );
}
