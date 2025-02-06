package Generics;

import application.Models.IValidosParaCrud;
import javafx.scene.control.TableView;

public interface ICrud<T extends IValidosParaCrud>{
	public boolean Create(T item, TableView<T> t);
	public boolean Edit(T item,TableView<T> t);
	public boolean Delete(T item,TableView<T> t);
	public void More(TableView<T> t);
}
