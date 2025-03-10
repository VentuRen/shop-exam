import { useState } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { setUser } from "../features/authSlice"; // Asegúrate de que la ruta es correcta
import { api } from "../api/api";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      setLoading(true);
      const response = await api.post("/auth/login", { email, password });
      const token = response.data.token;
      console.log("TOKEN OBTENIDO:", token);

      localStorage.setItem("token", token);
      const userResponse = await api.get("/auth/me", {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` },
      });

      const user = userResponse.data;
      dispatch(setUser({ token, user }));
      localStorage.setItem("user", JSON.stringify(user));
      navigate("/dashboard");
    } catch (error) {
      console.log(error);
      alert("Error al iniciar sesión");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-white px-4">
      <div className="bg-white p-10 rounded-2xl shadow-2xl w-1/3 max-w-lg text-center">
        <h2 className="text-4xl font-extrabold text-gray-900">Iniciar Sesión</h2>
        <p className="text-gray-500 mt-3">Bienvenido de nuevo, ingresa tus datos</p>

        <div className="mt-6 text-left">
          <label className="block text-sm font-medium text-gray-600">Correo Electrónico</label>
          <input
            className="mt-2 w-full p-4 bg-gray-200 text-gray-900 rounded-2xl outline-none focus:ring-2 focus:ring-blue-400 transition"
            type="email"
            placeholder="correo@ejemplo.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div className="mt-6 text-left">
          <label className="block text-sm font-medium text-gray-600">Contraseña</label>
          <input
            className="mt-2 w-full p-4 !bg-gray-200 text-gray-900 !rounded-2xl outline-none focus:ring-2 focus:ring-blue-400 transition"
            type="password"
            placeholder="••••••••"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button
         
          className="w-full mt-20 bg-blue-600 text-white py-4 rounded-2xl text-lg font-semibold hover:bg-blue-700 transition duration-300"
          onClick={handleLogin}
          disabled={loading}
        >
          {loading ? "Iniciando..." : "Iniciar Sesión"}
        </button>

        <p className="text-sm text-gray-600 mt-6">
          ¿No tienes cuenta?{" "}
          <Link to="/register" className="text-blue-500 font-semibold hover:underline">
            Regístrate aquí
          </Link>
        </p>
      </div>
    </div>
  );
}
