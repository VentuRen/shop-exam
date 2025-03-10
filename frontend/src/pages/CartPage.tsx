import { useEffect, useState } from "react";
import { api } from "../api/api";
import { useSelector } from "react-redux";
import { RootState } from "../store";
import { useNavigate } from "react-router-dom";

interface CartItem {
  id: number;
  productName: string;
  quantity: number;
  price: number;
}

export default function CartPage() {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [shippingAddress, setShippingAddress] = useState("");
  const user = useSelector((state: RootState) => state.auth.user);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = () => {
    api.get("/cart", {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    })
      .then((res) => setCartItems(res.data))
      .catch((err) => console.error("Error al obtener el carrito", err));
  };

  const handleConfirmOrder = () => {
    if (!shippingAddress) {
      alert("Por favor, ingresa una dirección de envío.");
      return;
    }

    api.post("/orders", { shippingAddress }, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    })
      .then((res) => {
        alert("Orden confirmada");
        navigate(`/order/${res.data.id}`);
      })
      .catch(() => alert("Error al confirmar la orden"));
  };

  const handleRemoveFromCart = (id: number) => {
    api.delete(`/cart/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    })
      .then(() => {
        alert("Producto eliminado del carrito");
        fetchCart(); // 🔹 Refrescar el carrito después de eliminar
      })
      .catch(() => alert("Error al eliminar producto"));
  };

  return (
    <div style={{ width: "60%", margin: "0 auto", padding: "20px" }}>
      <h1 style={{ textAlign: "center", fontSize: "24px", marginBottom: "20px" }}>
        Carrito de Compras
      </h1>

      {cartItems.length === 0 ? (
        <p style={{ textAlign: "center" }}>Tu carrito está vacío.</p>
      ) : (
        <div>
          {cartItems.map((item) => (
            <div key={item.id} style={{ display: "flex", alignItems: "center", marginBottom: "15px", borderBottom: "1px solid #ccc", paddingBottom: "10px" }}>
              <div style={{ flexGrow: 1 }}>
                <h3>{item.productName}</h3>
                <p>Cantidad: {item.quantity}</p>
                <p>Precio: ${item.price}</p>
              </div>
              <button
                onClick={() => handleRemoveFromCart(item.id)}
                style={{ backgroundColor: "#ff4d4d", color: "white", border: "none", padding: "8px 12px", borderRadius: "5px", cursor: "pointer" }}
              >
                Eliminar
              </button>
            </div>
          ))}

          <div style={{ marginTop: "20px" }}>
            <label style={{ fontWeight: "bold" }}>Dirección de Envío:</label>
            <input
              type="text"
              value={shippingAddress}
              onChange={(e) => setShippingAddress(e.target.value)}
              style={{ width: "100%", padding: "10px", marginTop: "5px", borderRadius: "5px", border: "1px solid #ccc" }}
            />
          </div>

          <button
            onClick={handleConfirmOrder}
            style={{ marginTop: "20px", width: "100%", padding: "10px", backgroundColor: "#2563eb", color: "white", borderRadius: "5px", border: "none", fontSize: "16px", cursor: "pointer" }}
          >
            Confirmar Pedido
          </button>
        </div>
      )}
    </div>
  );
}
