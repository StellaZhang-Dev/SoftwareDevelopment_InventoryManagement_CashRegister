# Cash Register System

## Project Overview
This project implements a **Cash Register System** that allows users to manage inventory, process sales, and keep track of historical sales records. The system supports:

- **Adding items to inventory**
- **Removing items from inventory**
- **Displaying the current inventory**
- **Processing sales transactions**
- **Displaying sales history**
- **Sorting and displaying sales records**

The program is designed with modularity and reusability in mind, ensuring efficient data management and user interaction.

---

## Features Implemented
### 1. Inventory Management
- Add new items with random quantity and price.
- Remove items by specifying item ID.
- Display a structured list of available items.

### 2. Sales Transactions
- Process sales by specifying item ID and quantity.
- Update inventory upon successful transactions.
- Maintain a history of all sales, including date and transaction details.

### 3. Sales History Management
- Display all completed sales transactions.
- Sort and display sales records based on item IDs.

### 4. Error Handling
- Prevent invalid inputs (negative numbers, invalid IDs, non-numeric inputs).
- Ensure inventory cannot go negative.
- Handle sales exceeding available stock.

---

## Installation & Setup
### Prerequisites
- Java Development Kit (JDK) installed.
- Git installed for version control (if not already installed, download from [Git Website](https://git-scm.com/)).

### Clone Repository
```sh
# Clone the repository
git clone git@github.com:StellaZhang-Dev/SoftwareDevelopment_InventoryManagement_CashRegister.git
```

### Compile and Run
```sh
# Compile the Java file
javac Main.java

# Run the compiled program
java Main
```

---

## Usage Guide
### Program Workflow
1. **Start the program** – The system will display a menu.
2. **Select an option** – The user can add, remove, display inventory, register sales, view sales history, or sort sales records.
3. **Process user input** – The program processes the input and executes the requested action.
4. **Repeat or exit** – The user can continue with further transactions or exit by selecting the quit option.

### Menu Options
```
1. Insert items
2. Remove an item
3. Display a list of items
4. Register a sale
5. Display sales history
6. Sort and display sales history table
q. Quit
```

### Example Interactions
#### Adding Items to Inventory
```
How many items to add? 3
Item added successfully!
```

#### Registering a Sale
```
Enter item ID to sell and quantity: 1001 2
Sale registered successfully.
```

#### Displaying Sales History
```
Date        ItemID  Quantity  Total Price
2024-03-18  1001   2         400
```

---

## Code Structure
### **Main Class**
This serves as the entry point for the program and controls the main menu loop.

### **Core Functions**
#### **Inventory Management**
- `insertItems()` – Adds new items to the inventory.
- `removeItem()` – Removes an item based on ID.
- `printItems()` – Displays all available inventory.

#### **Sales Handling**
- `sellItem()` – Processes sales transactions.
- `printSales()` – Displays all sales records.
- `sortedTable()` – Sorts and displays sales records by item ID.

#### **Utility Functions**
- `input()` – Handles user input validation.
- `checkFull()` – Checks if the inventory array needs expansion.
- `extendArray()` – Expands the inventory array when required.

---

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## Author
**Stella Zhang**

For more details, visit my [GitHub Profile](https://github.com/StellaZhang-Dev).


