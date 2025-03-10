import { useState } from "react";
import { api } from "../api/api";
import { useNavigate, Link } from "react-router-dom";

export default function RegisterPage() {
  const [userData, setUserData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    address: "",
  });

  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserData({ ...userData, [e.target.name]: e.target.value });
  };

  const handleRegister = async () => {
    try {
      await api.post("/auth/register", userData);
      navigate("/login");
    } catch (error) {
      alert("Error al registrar usuario.");
    }
  };

  return (
    <div className="flex min-h-screen items-center justify-center bg-white px-4">
      <div className="bg-white p-10 rounded-2xl shadow-2xl w-1/3 max-w-lg text-center">
        <h2 className="text-4xl font-extrabold text-gray-900">Registro</h2>
        <p className="text-gray-500 mt-3">Crea tu cuenta para continuar</p>

        <div className="mt-6 text-left">
          <label className="block text-sm font-medium text-gray-600">Nombre</label>
          <input
            name="firstName"
            className="mt-2 w-full p-4 bg-gray-200 text-gray-900 rounded-2xl outline-none focus:ring-2 focus:ring-blue-400 transition"
            placeholder="Nombre"
            onChange={handleChange}
          />
        </div>

        <div className="mt-4 text-left">
          <label className="block text-sm font-medium text-gray-600">Apellido</label>
          <input
            name="lastName"
            className="mt-2 w-full p-4 bg-gray-200 text-gray-900 rounded-2xl outline-none focus:ring-2 focus:ring-blue-400 transition"
            placeholder="Apellido"
            onChange={handleChange}
          />
        </div>

        <div className="mt-4 text-left">
          <label className="block text-sm font-medium text-gray-600">Correo Electrónico</label>
          <input
            name="email"
            type="email"
            className="mt-2 w-full p-4 bg-gray-200 text-gray-900 rounded-2xl outline-none focus:ring-2 focus:ring-blue-400 transition"
            placeholder="correo@ejemplo.com"
            onChange={handleChange}
          />
        </div>

        <div className="mt-4 text-left">
          <label className="block text-sm font-medium text-gray-600">Contraseña</label>
          <input
            name="password"
            type="password"
            className="mt-2 w-full p-4 bg-gray-200 text-gray-900 rounded-2xl outline-none focus:ring-2 focus:ring-blue-400 transition"
            placeholder="••••••••"
            onChange={handleChange}
          />
        </div>

        <div className="mt-4 text-left">
          <label className="block text-sm font-medium text-gray-600">Dirección</label>
          <input
            name="address"
            className="mt-2 w-full p-4 bg-gray-200 text-gray-900 rounded-2xl outline-none focus:ring-2 focus:ring-blue-400 transition"
            placeholder="Dirección"
            onChange={handleChange}
          />
        </div>

        <button
          className="w-full mt-6 bg-blue-600 text-white py-4 rounded-2xl text-lg font-semibold hover:bg-blue-700 transition duration-300"
          onClick={handleRegister}
        >
          Registrarse
        </button>

        

        <p className="text-sm text-gray-600 mt-6">
          ¿Ya tienes cuenta?{" "}
          <Link to="/login" className="text-blue-500 font-semibold hover:underline">
            Inicia sesión aquí
          </Link>
        </p>
      </div>
    </div>
  );
}
