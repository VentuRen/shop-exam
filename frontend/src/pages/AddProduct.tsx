import { useState } from "react";
import { api } from "../api/api";
import { useSelector } from "react-redux";
import { RootState } from "../store";

export default function AddProduct() {
  const [product, setProduct] = useState({ name: "", description: "", price: 0, imageUrl: "" });
  const user = useSelector((state: RootState) => state.auth.user);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setProduct({ ...product, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (user?.role !== "ADMIN") {
      alert("No tienes permisos para agregar productos.");
      return;
    }
    try {
      await api.post("/products", product);
      alert("Producto agregado correctamente.");
    } catch (error) {
      alert("Error al agregar producto.");
    }
  };

  return (
    <div className="flex flex-col items-center mt-10">
      <h1 className="text-2xl font-bold mb-4">Agregar Producto</h1>
      <input name="name" placeholder="Nombre" onChange={handleChange} className="border p-2 mb-2" />
      <input name="description" placeholder="Descripción" onChange={handleChange} className="border p-2 mb-2" />
      <input name="price" type="number" placeholder="Precio" onChange={handleChange} className="border p-2 mb-2" />
      <input name="imageUrl" placeholder="URL de Imagen" onChange={handleChange} className="border p-2 mb-2" />
      <button className="bg-green-500 text-white p-2" onClick={handleSubmit}>Crear Producto</button>
    </div>
  );
}
