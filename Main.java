import java.util.Scanner;
import java.util.Date;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * Describe briefly your program in steps.
 *
 * 1. Main method initializes arrays and calls the menu method in a loop.
 * 2. Menu method displays options and gets user input.
 * 3. Based on user input, call specific methods:
 *    a. insertItems: Add new items to inventory.
 *    b. removeItem: Remove items from inventory.
 *    c. printItems: Display all items.
 *    d. sellItem: Register a sale and update inventory.
 *    e. printSales: Display sales history.
 *    f. sortedTable: Display sorted sales history.
 * 4. Each method performs specific tasks as defined in the assignment.
 *
 @author Stella Zhang
 */
public class Main {

    // Constants for the item array
    public static final int ITEM_ID = 0;
    public static final int ITEM_COUNT = 1;
    public static final int ITEM_PRICE = 2;
    public static final int ITEM_COLUMN_SIZE = 3;
    public static final int INITIAL_ITEM_SIZE = 10;

    // Constants for the sales array
    public static final int SALE_ITEM_ID = 0;
    public static final int SALE_ITEM_COUNT = 1;
    public static final int SALE_ITEM_PRICE = 2;
    public static final int SALE_COLUMN_SIZE = 3;
    public static final int MAX_SALES = 1000;

    // Other constants
    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_4 = 4;
    public static final int MENU_ITEM_5 = 5;
    public static final int MENU_ITEM_6 = 6;
    public static final int MENU_ITEM_Q = -1;

    public static final int INITIAL_ITEM_NUMBER = 999;

    // Constants for random number ranges
    public static final int RANDOM_QUANTITY_MIN = 1;
    public static final int RANDOM_QUANTITY_MAX = 10;
    public static final int RANDOM_PRICE_MIN = 100;
    public static final int RANDOM_PRICE_RANGE = 901;



    private static Scanner userInputScanner = new Scanner(System.in);

    /**
     * This method should be used only for unit testing on CodeGrade. Do not change this method!
     * Swaps userInputScanner with a custom scanner object bound to a test input stream
     *
     * @param inputScanner  test scanner object
     */
    public static void injectInput(final Scanner inputScanner) {
        userInputScanner = inputScanner;
    }

    /**
     * Main method to run the cash register system.
     * It initializes the required data structures and starts the main menu loop.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        int[][] items = new int[INITIAL_ITEM_SIZE][ITEM_COLUMN_SIZE]; // Data structure to store items
        int[][] sales = new int[MAX_SALES][SALE_COLUMN_SIZE]; // Data structure to store sales
        Date[] saleDates = new Date[MAX_SALES]; // Data structure to store sale dates
        int lastItemNumber = INITIAL_ITEM_NUMBER; // Keep track of last added ItemNumber
        System.out.println("This is Marked Assignment 4");


        int noOfItems = 0;
        int noOfSales = 0;

        while (true) {
            int choice = menu();
            switch (choice) {
                case MENU_ITEM_1:
                    System.out.print("How many items to add? ");
                    int numItemsToAdd = input();
                    if (numItemsToAdd > 0) {
                        if (checkFull(items, numItemsToAdd)) {
                            items = extendArray(items, numItemsToAdd);
                        }
                        items = insertItems(items, lastItemNumber, noOfItems, numItemsToAdd);
                        noOfItems += numItemsToAdd;
                        lastItemNumber += numItemsToAdd;
                    } else {
                        System.out.println("Cannot add 0 or negative items.");
                    }
                    break;
                case MENU_ITEM_2:
                    System.out.print("Enter item ID to remove: ");
                    int itemIdToRemove = input();
                    int result = removeItem(items, itemIdToRemove);
                    if (result == 0) {
                        System.out.println("Item removed successfully.");
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                case MENU_ITEM_3:
                    printItems(items);
                    break;
                case MENU_ITEM_4:
                    System.out.print("Enter item ID to sell and quantity: ");
                    String[] inputs = userInputScanner.nextLine().trim().split(" ");
                    if (inputs.length != 2) {
                        System.out.println("invalid");
                        break;
                    }
                    try {
                        int itemIdToSell = Integer.parseInt(inputs[0]);
                        int quantityToSell = Integer.parseInt(inputs[1]);

                        int sellResult = sellItem(sales, saleDates, items, itemIdToSell, quantityToSell);
                        if (sellResult == 0) {
                            System.out.println("Sale registered successfully.");
                        } else if (sellResult == -1) {
                            System.out.println("Item not found.");
                        } else {
                            System.out.println("Failed to sell specified amount.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid");
                    }
                    break;

                case MENU_ITEM_5:
                    printSales(sales, saleDates, noOfSales);
                    break;
                case MENU_ITEM_6:
                    sortedTable(sales, saleDates, noOfSales);
                    break;
                case MENU_ITEM_Q:
                    System.out.println("Exiting Cash Register System.");
                    return;
                default:
                    System.out.println("Invalid selection. Try again.");
            }
        }
    }

    /**
     * Displays the menu options and gets user input.
     *
     * @return the selected menu option as an integer
     */
    public static int menu() {
        System.out.println("1. Insert items");
        System.out.println("2. Remove an item");
        System.out.println("3. Display a list of items");
        System.out.println("4. Register a sale");
        System.out.println("5. Display sales history");
        System.out.println("6. Sort and display sales history table");
        System.out.println("q. Quit");
        System.out.print("Your Selection: ");
        String input = userInputScanner.nextLine().trim();
        if (input.equals("q")) {
            return MENU_ITEM_Q;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid");
            return -1;
        } catch (NoSuchElementException e) {
            System.out.println("Input stream closed or no input available.");
            return -1;
        }
    }

    /**
     * Reads user input and parses it to an integer.
     *
     * @return the parsed integer, or -1 if input is invalid
     */
    public static int input() {
        try {
            String input = userInputScanner.nextLine().trim();
            if (input.matches(".*(-?\\d+).*")) {
                return Integer.parseInt(input.replaceAll("[^\\d-]", ""));
            } else {
                System.out.println("Invalid");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid");
            return -1;
        } catch (NoSuchElementException e) {
            System.out.println("Input stream closed or no input available.");
            return -1;
        }
    }

    /**
     * Checks if the item array is full.
     *
     * @param items the item array
     * @param noOfItemsToAdd the current number of items in the array
     * @return true if the array is full, false otherwise
     */
    public static boolean checkFull(final int[][] items, final int noOfItemsToAdd) {
        int availableSpace = items.length - countNonEmptyItems(items);
        return availableSpace < noOfItemsToAdd;
    }

    /**
     * Counts non-empty items in the array.
     *
     * @param items the item array
     * @return the number of non-empty items
     */
    private static int countNonEmptyItems(final int[][] items) {
        int count = 0;
        for (int[] item : items) {
            if (item[ITEM_ID] > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * Extends the size of the item array to accommodate more items.
     *
     * @param items the original item array
     * @param noOfItemsToAdd the number of items currently in the array
     * @return a new item array with increased capacity
     */
    public static int[][] extendArray(final int[][] items, final int noOfItemsToAdd) {
        int requiredSize = items.length + noOfItemsToAdd;
        int newSize = Math.max(items.length, requiredSize);
        int[][] newItems = new int[newSize][ITEM_COLUMN_SIZE];
        System.arraycopy(items, 0, newItems, 0, items.length);
        return newItems;
    }

    /**
    * Overloaded method to insert items into the inventory.
    * This version assumes a default number of items to add (1).
    *
    * @param items the current item array
    * @param lastItemNumber the ID of the last added item
    * @param numItemsToAdd the current number of items in the inventory
    * @return the updated item array
    */
    public static int[][] insertItems(final int[][] items, final int lastItemNumber, final int numItemsToAdd) {
        return insertItems(items, lastItemNumber, 0, numItemsToAdd);
    }

    /**
     * Inserts new items into the inventory.
     *
     * @param items the current item array
     * @param lastItemNumber the ID of the last added item
     * @param noOfItems the current number of items in the inventory
     * @param numItemsToAdd the number of items to add
     * @return the updated number of items after insertion
     */
    public static int[][] insertItems(final int[][] items, final int lastItemNumber, final int noOfItems, final int numItemsToAdd) {
        int[][] newItems = items;
        if (newItems.length < noOfItems + numItemsToAdd) {
            newItems = extendArray(newItems, noOfItems + numItemsToAdd - newItems.length);
        }

        Random random = new Random();
        for (int i = 0; i < numItemsToAdd; i++) {
            int newIndex = noOfItems + i;
            newItems[newIndex][ITEM_ID] = lastItemNumber + i + 1;
            newItems[newIndex][ITEM_COUNT] = random.nextInt(RANDOM_QUANTITY_MAX - RANDOM_QUANTITY_MIN + 1) + RANDOM_QUANTITY_MIN;
            newItems[newIndex][ITEM_PRICE] = random.nextInt(RANDOM_PRICE_RANGE) + RANDOM_PRICE_MIN;
        }
        return newItems;
    }

    /**
     * Removes an item from the inventory.
     *
     * @param items the current item array
     * @param itemId the ID of the item to remove
     * @return 0 if successful, -1 if the item is not found
     */
    public static int removeItem(final int[][] items, final int itemId) {
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemId) {
                items[i][ITEM_ID] = 0;
                items[i][ITEM_COUNT] = 0;
                items[i][ITEM_PRICE] = 0;
                return 0;
            }
        }
        System.out.println("Could not find");
        return -1;
    }

    /**
     * Displays the list of items in the inventory.
     *
     * @param items the current item array
     */
    public static void printItems(final int[][] items) {
        System.out.println("ItemID  Quantity  Price");
        for (int[] item : items) {
            if (item[ITEM_ID] > 0) {
                System.out.printf("%d    %d    %d\n", item[ITEM_ID], item[ITEM_COUNT], item[ITEM_PRICE]);
            }
        }
    }

    /**
     * Registers a sale and updates the inventory.
     *
     * @param sales the sales array
     * @param salesDate the sales date array
     * @param items the current item array
     * @param itemIdToSell the ID of the item to sell
     * @param amountToSell the quantity of the item to sell
     * @return 0 if successful, -1 if the item is not found, or remaining quantity if insufficient
     */
    public static int sellItem(final int[][] sales, final Date[] salesDate, final int[][] items, final int itemIdToSell, final int amountToSell) {
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemIdToSell) {
                if (items[i][ITEM_COUNT] >= amountToSell) {
                    items[i][ITEM_COUNT] -= amountToSell;

                    for (int j = 0; j < sales.length; j++) {
                        if (sales[j][SALE_ITEM_ID] == 0) {
                            sales[j][SALE_ITEM_ID] = itemIdToSell;
                            sales[j][SALE_ITEM_COUNT] = amountToSell;
                            sales[j][SALE_ITEM_PRICE] = amountToSell * items[i][ITEM_PRICE];
                            salesDate[j] = new Date();
                            return 0;
                        }
                    }
                    System.out.println("Sales array is full.");
                    return -1;
                }
                return items[i][ITEM_COUNT];
            }
        }
        System.out.println("Could not find");
        return -1;
    }

    /**
     * Displays the sales history.
     *
     * @param sales the sales array
     * @param salesDate the sales date array
     * @param noOfSales the number of sales to display
     */
    public static void printSales(final int[][] sales, final Date[] salesDate, final int noOfSales) {
        System.out.println("Date        ItemID  Quantity  Total Price");
        for (int i = 0; i < noOfSales; i++) {
            if (sales[i][SALE_ITEM_ID] > 0) {
                System.out.printf("%s  %d  %d  %d\n", salesDate[i], sales[i][SALE_ITEM_ID], sales[i][SALE_ITEM_COUNT], sales[i][SALE_ITEM_PRICE]);
            }
        }
    }

    /**
     * Sorts and displays the sales history table.
     *
     * @param sales the sales array
     * @param salesDate the sales date array
     * @param noOfSales the number of sales to sort and display
     */
    public static void sortedTable(final int[][] sales, final Date[] salesDate, final int noOfSales) {
        for (int i = 0; i < noOfSales - 1; i++) {
            for (int j = 0; j < noOfSales - i - 1; j++) {
                if (sales[j][SALE_ITEM_ID] > sales[j + 1][SALE_ITEM_ID]) {
                    int[] temp = sales[j];
                    sales[j] = sales[j + 1];
                    sales[j + 1] = temp;

                    Date tempDate = salesDate[j];
                    salesDate[j] = salesDate[j + 1];
                    salesDate[j + 1] = tempDate;
                }
            }
        }
        printSales(sales, salesDate, noOfSales);
    }
}
