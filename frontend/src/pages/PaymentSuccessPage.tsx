import { useEffect, useState } from "react";
import { api } from "../api/api";
import { useParams, useNavigate } from "react-router-dom";

interface Order {
  id: number;
  status: string;
}

export default function PaymentSuccessPage() {
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

  if (!order) return <p style={{ textAlign: "center", fontSize: "20px" }}>Cargando...</p>;

  return (
    <div style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", height: "100vh", textAlign: "center" }}>
      <h1 style={{ fontSize: "32px", fontWeight: "bold", color: "#10b981" }}>¡Pago Exitoso! 🎉</h1>
      <p style={{ fontSize: "24px", marginTop: "10px" }}>Orden ID: <strong>{order.id}</strong></p>
      <p style={{ fontSize: "24px", fontWeight: "bold", color: order.status === "PAID" ? "#10b981" : "#ff4d4d" }}>
        Estado de la Orden: {order.status}
      </p>

      <button
        onClick={() => navigate("/dashboard")}
        style={{ marginTop: "20px", padding: "10px 20px", fontSize: "18px", backgroundColor: "#2563eb", color: "white", borderRadius: "5px", border: "none", cursor: "pointer" }}
      >
        Volver a la Tienda
      </button>
    </div>
  );
}
