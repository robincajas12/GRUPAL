package application.Models;

import java.util.List;

public interface IJoin<T>{
	
	List<T> getFullSchema();

}
