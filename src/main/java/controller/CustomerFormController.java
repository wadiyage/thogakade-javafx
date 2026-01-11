package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.tm.CustomerTM;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    public TableView tblCustomerView;
    public JFXComboBox cbTitle;
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        createCustomer();
    }

    private void createCustomer() {
        Integer id = Integer.valueOf(txtId.getText());
        String title = cbTitle.getValue().toString();
        String name = txtName.getText();
        String address = txtAddress.getText();
        Double salary = Double.valueOf(txtSalary.getText());
        LocalDate dob = dateDob.getValue();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        String postalCode = txtPostalCode.getText();

        Customer newCustomer = new Customer(id, title, name, address, salary, dob, city, province, postalCode);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/samples", "root", "S@nDaRU#97");
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, String.valueOf(newCustomer.getId()));
            preparedStatement.setString(2, newCustomer.getTitle());
            preparedStatement.setString(3, newCustomer.getName());
            preparedStatement.setString(4, newCustomer.getAddress());
            preparedStatement.setString(5, String.valueOf(newCustomer.getSalary()));
            preparedStatement.setString(6, String.valueOf(newCustomer.getDob()));
            preparedStatement.setString(7, newCustomer.getCity());
            preparedStatement.setString(8, newCustomer.getProvince());
            preparedStatement.setString(9, newCustomer.getPostalCode());

            if(preparedStatement.executeUpdate()>0) {
                Alert newAlert = new Alert(Alert.AlertType.INFORMATION, "Customer Added!");
                newAlert.show();
                loadCustomerData();
            } else {
                Alert newAlert = new Alert(Alert.AlertType.ERROR, "Not Added!");
                newAlert.show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadCustomerData();
    }

    void loadCustomerData() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/samples", "root", "S@nDaRU#97");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Customer");

            ArrayList<CustomerTM> customerTMArrayList = new ArrayList<>();
            while(resultSet.next()) {
                customerTMArrayList.add(
                        new CustomerTM(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getDouble(5),
                            resultSet.getDate(6).toLocalDate(),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9)
                        )
                );
                tblCustomerView.setItems(FXCollections.observableArrayList(customerTMArrayList));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbTitle.setItems(
                FXCollections.observableArrayList(
                        Arrays.asList("MR", "MS")
                )
        );
        loadCustomerData();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");

            preparedStatement.setString(1, txtId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE id=?");

            preparedStatement.setString(1, txtId.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            Customer exitingCustomer = new Customer(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5),
                    resultSet.getDate(6).toLocalDate(),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );

            setTextToValues(exitingCustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTextToValues(Customer exitingCustomer) {
        cbTitle.setValue(exitingCustomer.getTitle());
        txtName.setText(exitingCustomer.getName());
        dateDob.setValue(exitingCustomer.getDob());
        txtSalary.setText(String.valueOf(exitingCustomer.getSalary()));
        txtAddress.setText(exitingCustomer.getAddress());
        txtCity.setText(exitingCustomer.getCity());
        txtProvince.setText(exitingCustomer.getProvince());
        txtPostalCode.setText(exitingCustomer.getPostalCode());
    }
}
