package Generics;

import application.Models.IValidosParaCrud;

public interface ICrud<T extends IValidosParaCrud>{
	public boolean Create(T item);
	public boolean Reload();
	public boolean Edit(T item);
	public boolean Delete(T item);
	public void More();
}
