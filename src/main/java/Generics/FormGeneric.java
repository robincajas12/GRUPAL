package Generics;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.lang.reflect.Field;

public class FormGeneric<T> {
	
    private GridPane gridPane;
    private Map<String, TextField> fieldMap;
    private FormGeneric(Class<T> myClase, GridPane gridPane, String ignoreFields) {
        this.gridPane = gridPane;
        this.fieldMap = new HashMap<>();
        Field[] myFields = myClase.getDeclaredFields();
        for (int i = 0; i < myFields.length; i++) {
            Field field = myFields[i];
            if(!ignoreFields.contains(field.getName()))
            {

                    Label label = new Label(field.getName() + ":");
                    TextField textField = new TextField();
                    fieldMap.put(field.getName(), textField);
                    
                    gridPane.add(label, 0, i);
                    gridPane.add(textField, 1, i);
            }
        }
    }
    private FormGeneric(Class<T> myClase, GridPane gridPane,T item ,String ignoreFields, String fieldsThatDontChange) {
        this.gridPane = gridPane;
        this.fieldMap = new HashMap<>();
        Field[] myFields = myClase.getDeclaredFields();
        for (int i = 0; i < myFields.length; i++) {
            Field field = myFields[i];
            if(!ignoreFields.contains(field.getName()))
            {
            	Label label = new Label(field.getName() + ":");
                TextField textField = new TextField();
                textField.setPromptText(field.getName());
                fieldMap.put(field.getName(), textField);
                gridPane.add(label, 0, i);
                gridPane.add(textField, 1, i);
                Method miMethod;
				try {
					miMethod = item.getClass().getDeclaredMethod(field.getName());
	                textField.setText(String.valueOf( miMethod.invoke(item)));
				} catch (Exception e) {
					e.printStackTrace();
				}
            	if(fieldsThatDontChange.contains(field.getName())) textField.setEditable(false);
            }
        }
    }
    public static <S> GridPane createFilledForm(Class<S> myClass, Consumer<Map<String, TextField>> submitAction,S item,String ignoreFields,String fieldsThatDontchanger) throws InstantiationException, IllegalAccessException
    {
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        FormGeneric<S> addGeneric = new FormGeneric<>(myClass, gridPane, item,ignoreFields,fieldsThatDontchanger);
        Button submit = new Button("submit");

        submit.setOnAction(e-> submitAction.accept(addGeneric.fieldMap));
        int rowCount = addGeneric.gridPane.getRowCount();
        addGeneric.gridPane.add(submit, 1, rowCount);
        return addGeneric.gridPane;
		
    	
    }
    public static <S> GridPane createForm(Class<S> myClass, Consumer<Map<String, TextField>> submitAction, String ignoreFields) throws InstantiationException, IllegalAccessException
    {
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        FormGeneric<S> addGeneric = new FormGeneric<>(myClass, gridPane, ignoreFields);
        Button submit = new Button("submit");

        submit.setOnAction(e-> submitAction.accept(addGeneric.fieldMap));
        int rowCount = addGeneric.gridPane.getRowCount();
        addGeneric.gridPane.add(submit, 1, rowCount);
        return addGeneric.gridPane;
    }

}
