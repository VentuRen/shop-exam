import { useEffect, useState } from "react";
import { api } from "../api/api";
import { useSelector } from "react-redux";
import { RootState } from "../store";
import "../styles/Dashboard.css"; // Importamos los estilos CSS externos

interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  imageUrl: string;
}

export default function Dashboard() {
  const [products, setProducts] = useState<Product[]>([]);
  const [searchTerm, setSearchTerm] = useState(""); // Estado del filtro de búsqueda
  const user = useSelector((state: RootState) => state.auth.user);

  useEffect(() => {
    api.get("/products").then((res) => setProducts(res.data));
  }, []);

  const handleAddToCart = (productId: number) => {
    if (!user) {
      alert("Debes iniciar sesión para agregar productos al carrito.");
      return;
    }

    api
      .post("/cart", { productId, quantity: 1 }, {
        headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
      })
      .then(() => {
        alert("Producto agregado al carrito");
        window.dispatchEvent(new Event("cartUpdated"));
      })
      .catch(() => alert("Error al agregar producto"));
  };

  // 🔹 Filtrar productos según el término de búsqueda
  const filteredProducts = products.filter((product) =>
    product.name.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="dashboard-container">
      <h1 className="dashboard-title">Productos Disponibles</h1>

      
      <input
        type="text"
        placeholder="Buscar producto..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="search-input"
      />

      <div className="product-grid">
        {filteredProducts.length > 0 ? (
          filteredProducts.map((product) => (
            <div key={product.id} className="product-card">
              <img src={product.imageUrl} alt={product.name} className="product-image" />
              <h2 className="product-name">{product.name}</h2>
              <p className="product-description">{product.description}</p>
              <span className="product-price">${product.price}</span>

              <button className="add-to-cart-button" onClick={() => handleAddToCart(product.id)}>
                🛒 Agregar al Carrito
              </button>
            </div>
          ))
        ) : (
          <p className="no-results">No se encontraron productos.</p>
        )}
      </div>
    </div>
  );
}
