import { useEffect, useState } from "react";
import { api } from "../api/api";
import { useSelector } from "react-redux";
import { RootState } from "../store";
import { useNavigate } from "react-router-dom";

interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  address: string;
}

export default function UserProfile() {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const [updatedUser, setUpdatedUser] = useState<User>({
    id: 0,
    firstName: "",
    lastName: "",
    email: "",
    address: "",
  });

  const navigate = useNavigate();
  const authUser = useSelector((state: RootState) => state.auth.user);

  useEffect(() => {
    api
      .get("/auth/me", {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      })
      .then((res) => {
        setUser(res.data);
        setUpdatedUser(res.data);
        setLoading(false);
      })
      .catch(() => {
        alert("Error al obtener la información del usuario.");
        setLoading(false);
      });
  }, []);

  const handleUpdate = () => {
    api
      .put(`/auth/${user?.id}`, updatedUser, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      })
      .then((res) => {
        setUser(res.data);
        alert("Información actualizada correctamente");
      })
      .catch(() => alert("Error al actualizar el usuario"));
  };

  if (loading) return <p style={{ textAlign: "center" }}>Cargando...</p>;

  return (
    <div style={{ width: "40%", margin: "0 auto", padding: "20px" }}>
      <h1 style={{ textAlign: "center", fontSize: "24px", marginBottom: "20px" }}>Mi Perfil</h1>

      <label>Nombre:</label>
      <input
        type="text"
        value={updatedUser.firstName}
        onChange={(e) => setUpdatedUser({ ...updatedUser, firstName: e.target.value })}
        style={{ width: "100%", padding: "10px", marginBottom: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
      />

      <label>Apellido:</label>
      <input
        type="text"
        value={updatedUser.lastName}
        onChange={(e) => setUpdatedUser({ ...updatedUser, lastName: e.target.value })}
        style={{ width: "100%", padding: "10px", marginBottom: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
      />

      <label>Email:</label>
      <input
        type="email"
        value={updatedUser.email}
        onChange={(e) => setUpdatedUser({ ...updatedUser, email: e.target.value })}
        style={{ width: "100%", padding: "10px", marginBottom: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
        disabled
      />

      <label>Dirección:</label>
      <input
        type="text"
        value={updatedUser.address}
        onChange={(e) => setUpdatedUser({ ...updatedUser, address: e.target.value })}
        style={{ width: "100%", padding: "10px", marginBottom: "10px", borderRadius: "5px", border: "1px solid #ccc" }}
      />

      <button
        onClick={handleUpdate}
        style={{ width: "100%", padding: "10px", backgroundColor: "#10b981", color: "white", borderRadius: "5px", border: "none", fontSize: "16px", cursor: "pointer" }}
      >
        Actualizar Información
      </button>
    </div>
  );
}
