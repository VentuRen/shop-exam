import { useEffect, useState } from "react";
import { api } from "../api/api";
import { useParams, useNavigate } from "react-router-dom";

interface Order {
  id: number;
  shippingAddress: string;
  status: string;
  details: { productName: string; price: number; quantity: number }[]; // 🔹 Se ajusta a la API
}

export default function OrderPage() {
  const { id } = useParams();
  const [order, setOrder] = useState<Order | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    api.get(`/orders/${id}`, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    })
      .then((res) => setOrder(res.data))
      .catch(() => alert("Error al cargar la orden"));
  }, [id]);

  const handlePayment = () => {
    api.post("/payments", { orderId: id, paymentMethod: "CREDIT_CARD" }, {
      headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
    })
      .then(() => {
        alert("Pago realizado con éxito");
        navigate(`/payment-success/${id}`);
      })
      .catch(() => alert("Error al procesar el pago"));
  };

  if (!order) return <p style={{ textAlign: "center" }}>Cargando...</p>;

  // 🔹 Calcular total manualmente
  const totalAmount = order.details.reduce((total, item) => total + item.price * item.quantity, 0);

  return (
    <div style={{ width: "60%", margin: "0 auto", padding: "20px" }}>
      <h1 style={{ textAlign: "center", fontSize: "24px", marginBottom: "20px" }}>Detalle del Pedido</h1>

      <p><strong>Dirección de Envío:</strong> {order.shippingAddress}</p>
      <p><strong>Estado:</strong> {order.status}</p>

      <h2 style={{ marginTop: "20px" }}>Productos</h2>
      {order.details.map((item, index) => (
        <div key={index} style={{ display: "flex", alignItems: "center", marginBottom: "15px", borderBottom: "1px solid #ccc", paddingBottom: "10px" }}>
          <div style={{ flexGrow: 1 }}>
            <h3>{item.productName}</h3> {/* 🔹 Se ajusta a la API */}
            <p>Cantidad: {item.quantity}</p>
            <p>Precio: ${item.price}</p>
          </div>
        </div>
      ))}

      <p style={{ fontSize: "18px", fontWeight: "bold", marginTop: "20px" }}>Total a Pagar: ${totalAmount.toFixed(2)}</p>

      <button
        onClick={handlePayment}
        style={{ marginTop: "20px", width: "100%", padding: "10px", backgroundColor: "#10b981", color: "white", borderRadius: "5px", border: "none", fontSize: "16px", cursor: "pointer" }}
      >
        Pagar Ahora
      </button>
    </div>
  );
}
