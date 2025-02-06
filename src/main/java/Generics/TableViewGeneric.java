package Generics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import application.Models.IValidosParaCrud;
import application.ViewsManager.WholeAppManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewGeneric<T extends IValidosParaCrud> {
    private Class<T> clazz;

    public TableViewGeneric(Class<T> classType) {
        this.clazz = classType;
    }

    public List<TableColumn<T, ?>> getColumnInformation() {
        Field[] myFields = clazz.getDeclaredFields();
        List<TableColumn<T, ?>> misTableColumns = new ArrayList<>(myFields.length);
        for (Field field : myFields) {
            TableColumn<T, ?> column = createColumn(field);
            if (column != null) {
                misTableColumns.add(column);
            }
        }
        return misTableColumns;
    }

    private TableColumn<T, ?> createColumn(Field field) {
        String fieldName = field.getName();
        TableColumn<T, ?> column = null;

        try {
            // Verificar si existe un getter para el campo
            Method getterMethod = clazz.getMethod("get" +capitalize(fieldName));
            
            if (field.getType().equals(String.class)) {
                column = new TableColumn<T, String>(capitalize(fieldName));
                column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
            } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                column = new TableColumn<T, Integer>(capitalize(fieldName));
                column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
            } else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                column = new TableColumn<T, Double>(capitalize(fieldName));
                column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
            } else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                column = new TableColumn<T, Boolean>(capitalize(fieldName));
                column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
            } else {
             
                column = new TableColumn<T, String>(capitalize(fieldName));
                column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
            }

            column.setText(capitalize(fieldName));
        } catch (NoSuchMethodException e) {
            System.err.println("No getter found for field: " + fieldName);
        }

        return column;
    }
    public void fillbuttonBar(ButtonBar buttonBar,ICrud<T> crud, TableView<T> tableView)
    {
    	Button btnCreate = new Button("Insert");
    	btnCreate.setOnAction(e->{
    		
    		crud.Create(tableView.getSelectionModel().getSelectedItem(), tableView);
    	});
    	
    	Button btnDelete = new Button("Delete");
    	btnDelete.setOnAction(e->{
    		crud.Delete(tableView.getSelectionModel().getSelectedItem(),tableView);
    	});
    	
    	Button btnEdit = new Button("Edit");
    	
    	btnEdit.setOnAction(e->{
    		crud.Edit(tableView.getSelectionModel().getSelectedItem(),tableView);
    	});
    	
    	
    	Button btnMore = new Button("More");
    	btnMore.setOnAction(e->{
    		crud.More(tableView);
    	});
    	
    	buttonBar.getButtons().addAll(btnCreate, btnEdit,btnDelete, btnMore);
    }
    public void fillTableView(TableView<T> tableView, Supplier<List<T>> supplier)
    {
   	 TableViewGeneric<T> tableViewGeneric = new TableViewGeneric<>(clazz);
        // Configura la tabla (este paso asegura que las columnas y los datos estén presentes)
        tableView.getColumns().setAll(tableViewGeneric.getColumnInformation());
        ObservableList<T> items = FXCollections.observableArrayList(supplier.get());
        for (TableColumn<?, ?> column : tableView.getColumns()) {
        	//tableView.getColumns().setAll(getColumnInformation());
            column.prefWidthProperty().bind(tableView.widthProperty().divide(tableView.getColumns().size()));
        }
        tableView.setItems(items);
        tableView.setOnMouseClicked(e->{
       	 tableView.getSelectionModel().getSelectedItem();
        });
    }
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}

