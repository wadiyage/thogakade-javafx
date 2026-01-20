package controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.item.ItemServiceImpl;
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
import java.util.List;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        cbTitle.setItems(
                FXCollections.observableArrayList(
                        Arrays.asList("MR", "MS")
                )
        );
        loadCustomerData();

        tblCustomerView.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            assert t1 != null;

            Customer customer = new Customer(
                    ((CustomerTM) t1).getId(),
                    ((CustomerTM) t1).getTitle(),
                    ((CustomerTM) t1).getName(),
                    ((CustomerTM) t1).getDob(),
                    ((CustomerTM) t1).getSalary(),
                    ((CustomerTM) t1).getAddress(),
                    ((CustomerTM) t1).getCity(),
                    ((CustomerTM) t1).getProvince(),
                    ((CustomerTM) t1).getPostalCode()
            );
            setTextToValues(customer);
        });
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        createCustomer();
    }

    private void createCustomer() {
        String id = txtId.getText();
        String title = cbTitle.getValue().toString();
        String name = txtName.getText();
        LocalDate dob = dateDob.getValue();
        Double salary = Double.valueOf(txtSalary.getText());
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        Integer postalCode = Integer.valueOf(txtPostalCode.getText());

        Customer newCustomer = new Customer(id, title, name, dob, salary, address, city, province, postalCode);

        CustomerServiceImpl customerService = new CustomerServiceImpl();
        if(customerService.addCustomer(newCustomer)) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Added!").show();
            loadCustomerData();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Added!").show();
        }

    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadCustomerData();
    }

    void loadCustomerData() {
        List<Customer> customerList = new CustomerServiceImpl().getAll();
        ArrayList<CustomerTM> customerTMArrayList = new ArrayList<>();


        customerList.forEach(customer -> {
            customerTMArrayList.add(
                    new CustomerTM(
                            customer.getId(),
                            customer.getTitle(),
                            customer.getName(),
                            customer.getDob(),
                            customer.getSalary(),
                            customer.getAddress(),
                            customer.getCity(),
                            customer.getProvince(),
                            customer.getPostalCode()
                    )
            );
        });
        tblCustomerView.setItems(FXCollections.observableArrayList(customerList));

    }



    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE CustID=?");

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
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getInt(9)
            );

            setTextToValues(exitingCustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTextToValues(Customer exitingCustomer) {
        txtId.setText(exitingCustomer.getId());
        cbTitle.setValue(exitingCustomer.getTitle());
        txtName.setText(exitingCustomer.getName());
        dateDob.setValue(exitingCustomer.getDob());
        txtSalary.setText(String.valueOf(exitingCustomer.getSalary()));
        txtAddress.setText(exitingCustomer.getAddress());
        txtCity.setText(exitingCustomer.getCity());
        txtProvince.setText(exitingCustomer.getProvince());
        txtPostalCode.setText(String.valueOf(exitingCustomer.getPostalCode()));
    }
}
