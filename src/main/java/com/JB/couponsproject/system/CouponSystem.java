package com.JB.couponsproject.system;

import com.JB.couponsproject.dto.CompanyDto;
import com.JB.couponsproject.dto.CouponDto;
import com.JB.couponsproject.dto.CustomerDto;
import com.JB.couponsproject.entities.CompanyEntity;
import com.JB.couponsproject.entities.CouponEntity;
import com.JB.couponsproject.entities.CustomerEntity;
import com.JB.couponsproject.exceptions.ApplicationException;
import com.JB.couponsproject.login.LoginManager;
import com.JB.couponsproject.services.AdminService;
import com.JB.couponsproject.services.CompanyService;
import com.JB.couponsproject.services.CustomerService;
import com.JB.couponsproject.tests.AdminServiceTest;
import com.JB.couponsproject.tests.CompanyServiceTest;
import com.JB.couponsproject.tests.CustomerServiceTest;
import com.JB.couponsproject.util.ObjectMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.JB.couponsproject.util.MyScanner.*;

@Component
@RequiredArgsConstructor
public class CouponSystem implements CommandLineRunner {
    private final AdminServiceTest adminServiceTest;
    private final CompanyServiceTest companyServiceTest;
    private final CustomerServiceTest customerServiceTest;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        boolean quit = false;
        while (!quit) {
            printMainMenu();
            String answer = getStringInput("", Arrays.asList("1", "2", "3", "DOCS"));
            switch (answer) {
                case "1":
                    try {
                        openLoginMenu();
                    } catch (ApplicationException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try {
                        adminServiceTest.run();
                        companyServiceTest.run();
                        customerServiceTest.run();
                    } catch (ApplicationException e) {
                        e.printStackTrace();
                    }
                    break;
                case "3":
                    quit = true;
//TODO: make DailyJob stoppable or delete this option
                    break;
                case "DOCS":
                    System.out.println("Sorry, the docs is not available");
//TODO: Create Javadoc html or delete this option

//Uncomment and replace URL if Javadoc file was created
//                    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
//                        Desktop.getDesktop().browse(new URI("https://nitay12.github.io/coupon-project/"));
//                    }
            }
        }
    }

    private void openLoginMenu() throws ApplicationException {
        String answer = getStringInput("Please choose a client type, press R to return the previous menu", Arrays.asList("admin", "company", "customer", "R"));
        boolean quit = false;
        while (!quit) {
            switch (answer) {
                case "admin":
                    if (adminService.login(
                            getStringInput("Please enter email"),
                            getStringInput("Please enter password")
                    )
                    ) {
                        openAdminMenu();
                    }
                    break;
                case "company":
                    final String email = getStringInput("Please enter email");
                    if (companyService.login(
                            email,
                            getStringInput("Please enter password")
                    )) {
                        final CompanyDto loggedInCompany = adminService.getCompanyByEmail(email);
                        openCompanyMenu(loggedInCompany.getId());
                    }
                    break;
                case "customer":
                    System.out.println("Sorry the customer menu is not available");
//                    openCustomerMenu();
                case "R":
                    quit = true;
                    break;
            }
        }
    }

    private void openCustomerMenu() {
        boolean quit = false;
        while (!quit) {
            switch (
                    getStringInput("""
                                    Please choose an action from the list:
                                    1. Purchase coupon
                                    2. Print your purchased coupons (with optionally filters)
                                    R. Return to the main menu""",
                            Arrays.asList("1", "2", "R"))
            ) {
                case "1":
                    break;
                case "2":
                    break;
                case "R":
                    quit = true;
            }
        }
    }

    private void openCompanyMenu(Long companyId) throws ApplicationException {
        boolean quit = false;
        while (!quit) {
            System.out.println("""
                    Press 1 to add a new coupon
                    Press 2 to update a coupon
                    Press 3 to delete a coupon
                    press 4 to print all your company coupons (with optional filters)
                    press R to return to the main menu
                    """);
            String answer = getStringInput("Please choose an action from the menu above", Arrays.asList("1", "2", "3", "4", "R"));
            switch (answer) {
                case "1":
                    //Add coupon
                    final long newCouponId = companyService.addCoupon(createCoupon(), companyId);
                    System.out.println("Coupon with id:" + newCouponId + " was added");
                    break;
                case "2":
                    //Update coupon
                    final List<CouponEntity> companyCoupons = companyService.getCompanyCoupons(companyId);
                    System.out.println("Available coupons to update: " + companyCoupons);
                    final String couponToUpdateId = getStringInput("Choose coupon id from the list");
                    CouponDto couponToUpdate = companyService.getOneCoupon(companyId, Long.parseLong(couponToUpdateId));
                    if (couponToUpdate != null) {
                        couponToUpdate.setTitle(getStringInput("Enter new coupon title"));
                        companyService.updateCoupon(couponToUpdate, companyId);
                        System.out.println("Coupon with id" + couponToUpdateId + " was updated");
                    } else System.out.println("Coupon not found");
                    break;
                case "3":
                    //Delete coupon
                    System.out.println("Available coupons \n" + companyService.getCompanyCoupons(companyId));
                    final String couponToDelete = getStringInput(
                            "Choose coupon id from the list");
                    companyService.deleteCoupon(Long.parseLong(couponToDelete), companyId);
                    System.out.println("Coupon with id: " + couponToDelete + " was deleted");
                    break;
                case "4":
                    //Get company coupons
                    switch (getStringInput("""
                                    Choose a filter
                                    1. No filters
                                    2. Category filter
                                    3. Max Price filter""",
                            Arrays.asList("1", "2", "3")
                    )) {
                        case "1" -> System.out.println(companyService.getCompanyCoupons(companyId));
                        case "2" -> System.out.println(companyService.getCompanyCoupons(
                                getCategory("Please enter a category name"), companyId
                        ));
                        case "3" -> System.out.println(companyService.getCompanyCoupons(
                                getDoubleInput("Please enter max price"), companyId));
                    }
                    break;
                case "5":
                    quit = true;
                    break;
            }
        }
    }

    private void openAdminMenu() throws ApplicationException {
        boolean quit = false;
        System.out.println("Welcome admin");
        while (!quit) {
            System.out.println("""
                    Press 1 to create a new company
                    press 2 to update a company's email
                    Press 3 to get a company details
                    Press 4 to get all companies details
                    press 5 to delete a company 
                    press 6 to create a new customer
                    press 7 to update a customer's first name
                    Press 8 to get a customer details
                    press 9 to get all customers details
                    press 10 to delete a customer
                    Press R to return to the main menu
                    """);
            //TODO: make a getInt with range
            String answer = getStringInput("Please choose a task from the list above", Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "R"));
            switch (answer) {
                case "1" -> {
                    //Add company
                    CompanyDto company = createCompany();
                    final CompanyEntity companyEntity = ObjectMappingUtil.companyDtoToCompanyEntity(company);
                    final CompanyEntity newCompany = adminService.createCompany(companyEntity);
                    System.out.println(company.getName() + " was added to the DB (id:" + newCompany.getId() + ")");
                    break;
                }
                case "2" -> {
                    //Update company
                    final List<CompanyEntity> allCompanies = adminService.getAllCompanies();
                    System.out.println("All companies: " + allCompanies);
                    String compToUpdateId = getStringInput("Select a company id to update");
                    final CompanyDto compToUpdate = adminService.getCompanyById(Long.parseLong(compToUpdateId));
                    final String newEmail = getStringInput("Enter new company email");
                    compToUpdate.setEmail(newEmail);
                    adminService.updateCompany(compToUpdate);
                    System.out.println("Company email was updated");
                }
                case "3" -> {
                    //Get one company
                    final List<CompanyEntity> allCompanies = adminService.getAllCompanies();
                    System.out.println("All companies: " + allCompanies);
                    String companyToGetId = getStringInput("Enter company from the available companies list");
                    System.out.println(adminService.getCompanyById(Long.parseLong(companyToGetId)));
                    break;
                }
                case "4" -> {
                    //Get all companies
                    final List<CompanyEntity> allCompanies = adminService.getAllCompanies();
                    System.out.println("All companies details");
                    System.out.println(allCompanies.toString());
                }
                case "5" -> {
                    //Delete company
                    final List<CompanyEntity> allCompanies = adminService.getAllCompanies();
                    System.out.println("All companies: " + allCompanies);
                    String compToDeleteId = getStringInput("Select a company id to delete");
                    final CompanyDto companyToDelete = adminService.getCompanyById(Long.parseLong(compToDeleteId));
                    adminService.deleteCompany(companyToDelete);
                    System.out.println("Company with id " + compToDeleteId + " was deleted from the DB");
                }
                case "6" -> {
                    //Add customer
                    CustomerDto newCustomer = createCustomer();
                    final CustomerEntity customerEntity = ObjectMappingUtil.customerDtoToEntity(newCustomer);
                    final CustomerEntity addedCustomer = adminService.createCustomer(customerEntity);
                    System.out.println(newCustomer.getFirstName() + " was added to the DB. (id:" + addedCustomer.getId() + ")");
                }
                case "7" -> {
                    //Update customer
                    final List<CustomerEntity> allCustomers = adminService.getAllCustomers();
                    System.out.println("All customers" + allCustomers);
                    String custToUpdateId = getStringInput("Please choose a customer id from the list");
                    CustomerDto custToUpdate = adminService.getCustomerById(Long.parseLong(custToUpdateId));
                    String newFirstName = getStringInput("enter new customer first name");
                    custToUpdate.setFirstName(newFirstName);
                    adminService.updateCustomer(custToUpdate);
                    System.out.println("Customer with id:" + custToUpdateId + " first name is now - " + newFirstName);
                }
                case "8" -> {
                    //Get one customer
                    final List<CustomerEntity> allCustomers = adminService.getAllCustomers();
                    System.out.println("All customers" + allCustomers);
                    String custToGetId = getStringInput("Please choose a customer id from the list");
                    System.out.println(adminService.getCustomerById(Long.parseLong(custToGetId)));
                }
                case "9" -> {
                    //Get all customers
                    System.out.println("All customers details");
                    System.out.println(adminService.getAllCustomers());
                }
                case "10" -> {
                    final List<CustomerEntity> allCustomers = adminService.getAllCustomers();
                    System.out.println("All customers" + allCustomers);
                    String custToDeleteId = getStringInput("Please choose a customer id from the list");
                    CustomerDto custToDelete = adminService.getCustomerById(Long.parseLong(custToDeleteId));
                    adminService.deleteCustomer(custToDelete);
                }
                case "R" -> quit = true;
            }
        }

    }
//
//    private List<String> getAvailableCompId(AdminFacade adminFacade) throws CrudException, InterruptedException {
//        //Print available companies id
//        ArrayList<Company> allCompanies = adminFacade.getAllCompanies();
//        List<String> availableCompsId = new ArrayList<>();
//        for (Company comp :
//                allCompanies) {
//            availableCompsId.add(String.valueOf(comp.getId()));
//        }
//        return availableCompsId;
//    }
//
//    private List<String> getAvailableCustId(AdminFacade adminFacade) throws CrudException {
//        //Print available customers ids
//        ArrayList<Customer> allCustomers = adminFacade.getAllCustomers();
//        List<String> availableCustId = new ArrayList<>();
//        for (Customer customer :
//                allCustomers) {
//            availableCustId.add(String.valueOf(customer.getId()));
//        }
//        return availableCustId;
//    }

    private static void printMainMenu() {
        System.out.println("************************************\n" +
                           "MAIN MENU\n" +

                           "press 0 to change daily job mode \n" +
                           "Press 1 to log in\n" +
                           "Press 2 to start all tests\n" +
                           "Press 3 to quit\n" +
                           "Type DOCS to open the docs on your web browser\n" +
                           "************************************");
    }

    private static String getExpirationJobStatus(boolean isStopped) {
        String expirationJobStatus;
        if (isStopped) expirationJobStatus = "off";
        else expirationJobStatus = "on";
        return expirationJobStatus;
    }

    /**
     * Company creation method from user input
     *
     * @return generated Company object (without id)
     */
    private static CompanyDto createCompany() {
        System.out.println("Create new company");
        return CompanyDto.builder()
                .name(getStringInput("Please enter company name"))
                .email(getStringInput("Please enter company email"))
                .password(getStringInput("Please enter company password"))
                .build();
    }

    /**
     * Customer creation method from user input
     *
     * @return generated Customer object (without id)
     */
    public static CustomerDto createCustomer() {
        System.out.println("Create new customer");
        return CustomerDto.builder()
                .firstName(getStringInput("Please enter first name"))
                .lastName(getStringInput("Please enter last name"))
                .email(getStringInput("Please enter email"))
                .password(getStringInput("Please enter customer password"))
                .build();
    }

    /**
     * Coupon creation method from user input
     *
     * @return generated Coupon object (without id)
     */
    public static CouponDto createCoupon() {
        System.out.println("Create new coupon");
        return CouponDto.builder()
                .category(getCategory("Enter category name"))
                .title(getStringInput("Enter coupon title"))
                .description(getStringInput("Enter coupon description"))
                .startDate(LocalDate.of(
                        getIntInput("enter start year"),
                        getIntInput("enter start month"),
                        getIntInput("enter start day of month")
                ))
                .endDate(LocalDate.of(
                        getIntInput("enter end year"),
                        getIntInput("enter end month"),
                        getIntInput("enter end day of month")
                ))
                .amount(getIntInput("please enter coupon amount"))
                .price(getDoubleInput("please enter coupon price"))
                .image(getStringInput("Enter coupon image url"))
                .build();
    }
}