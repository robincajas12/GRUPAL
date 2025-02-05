package application.Models;


import java.util.List;

public interface IAccesoDatos<T> {
	public  int crear(T item);
	public boolean actualizar(T item);
	public boolean eliminar(int id);
	public List<T> obtenerTodos();
	
}
