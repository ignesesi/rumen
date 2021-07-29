<img src="https://webassets.telerikacademy.com/images/default-source/logos/telerik-academy.svg)" alt="logo" width="300px" style="margin-top: 20px;"/>

# BoardR - Task Organizing System

*Part 5* 

**(Important!: Start from Part 3 Solution)**

## 1. Description

**BoardR** is a task-management system which will evolve in the next several weeks. During the course of the project, we will follow the best practices of `Object-Oriented Programming` and `Design`.

## 2. Goals

Your task we will refactor the **BoardItem** class to serve as a true base class. We will also provide **interfaces** for the main components of our application

You will achieve this by applying the OOP principle of **Abstraction**.

## 3. BoardItem class

### Description

Currently, the purpose of this class is to serve as a base class for the **Task** and **Issue**.

Most of the logic in both classes is the same and we moved that logic into the base class so we can reuse it. However, while **Task** and **Issue** are specific and serve different purposes (tasks have assignees, issues have descriptions), the **BoardItem** may have become too general and now it represents an incomplete task/issue. So, creating instances of that class may lead to some problems where **it is not clear what kind of item we have in the board**. To prevent accidental instantiation of the class, we can mark it as `abstract`.

```java
LocalDate tomorrow = LocalDate.now().plusDays(1);
Issue issue = new Issue("App flow tests?", "We need to test the App!", tomorrow);
Task task = new Task("Test the application flow", "Pesho", tomorrow);

// this MUST not compile: "'BoardItem' is abstract; cannot be instantiated"
BoardItem item = new BoardItem("title", tomorrow);
```

One problem with reusing logic was that both **Task** and **Issue** have the same functionality for Advancing/Reverting statuses. For example, **Issues** should only be `Open` or `Verified`, while Tasks should be able to use all Statuses from `Todo` to `Verified`. Abstract classes provide us with the benefit that we can define `abstract` methods inside of them. Abstract methods are intended to be given implementation by classes that derive from the abstract class.

Go ahead and mark `advanceStatus()` and `revertStatus()` as abstract. You will notice that the compiler wants you to remove the body.

```java
// Abstract methods can have no body
public abstract void revertStatus();

public abstract void advanceStatus();
```

## 4. Issue class

### Description

If you go to the class now you will see that the compiler complains - "_Class 'Issue' must either be declared abstract or implement abstract method 'revertStatus()' in 'BoardItem'_".

> **Note**: This is because abstract methods MUST be implemented in derived classes. If you were not forced to do that, what would happen if you create an instance of the `Issue` class and call `advanceStatus()`? There will be no code to execute.

Provide implementation for both methods. `advanceStatus()` should set the status as `Verified` and do nothing if already `Verified`. `revertStatus()` should set `Verified` to `Open` and do nothing if already opened. Both methods should also log what they did. (Remember that in a previous activity we made `addEventLog()` is protected and you have access to it.)

#### Example

```java
LocalDate tomorrow = LocalDate.now().plusDays(1);
Issue issue = new Issue("App flow tests?", "We need to test the App!", tomorrow);

issue.revertStatus();
issue.advanceStatus();
issue.advanceStatus();
issue.revertStatus();

issue.displayHistory();
```

#### Output

```
[15-September-2020 18:05:45] Item created: 'App flow tests?', [Open | 2020-09-16]
[15-September-2020 18:05:45] Issue status already Open
[15-September-2020 18:05:45] Issue status set to Verified
[15-September-2020 18:05:45] Issue status already Verified
[15-September-2020 18:05:45] Issue status set to Open
```

## 5. Task class

### Description

This class must also implement the abstract methods. What is hugely beneficial is that the implementation can be different from the one in the `Issue` class.

The flow for the advance method is `Todo -> InProgress -> Done -> Verified`. The revert method has that flow reversed.

#### Example

```java
LocalDate tomorrow = LocalDate.now().plusDays(1);
Task task = new Task("App flow tests?", "Pesho", tomorrow);

task.revertStatus();
task.advanceStatus();
task.advanceStatus();
task.revertStatus();
task.advanceStatus();
task.advanceStatus();
task.advanceStatus();

task.displayHistory();
```

```
[18-May-2021 12:25:27] Item created: 'App flow tests?', [To Do | 2021-05-19]
[18-May-2021 12:25:27] Can't revert, already at To Do
[18-May-2021 12:25:27] Task status changed from To Do to In Progress
[18-May-2021 12:25:27] Task status changed from In Progress to Done
[18-May-2021 12:25:27] Task status changed from Done to In Progress
[18-May-2021 12:25:27] Task status changed from In Progress to Done
[18-May-2021 12:25:27] Task status changed from Done to Verified
[18-May-2021 12:25:27] Can't advance, already at Verified
```

## 6. Logging BoardItems

### Description

Currently, we are logging the history of each board item by calling 
the `displayHistory()` method from some instance. 
What if we want to log all history? 
The Board object maintains the collection of items, 
but it is private an inaccessible from the outside world. 
Fortunately, we can add a method to that class that does the logging.

### Methods

`void displayHistory()` - should be accessible from outside the class. 
This method must iterate over the items and access their history 
(through `item.displayHistory()`). Write that history to the console.

#### Examples

```java
LocalDate tomorrow = LocalDate.now().plusDays(1);
Task task = new Task("App flow tests?", "Pesho", tomorrow);
Issue issue = new Issue("Review tests", "Someone must review Pesho's tests.", tomorrow);

Board board = new Board();

board.addItem(task);
board.addItem(issue);
task.advanceStatus();
issue.advanceStatus();

board.displayHistory();
```

```
[15-September-2020 18:14:21] Item created: 'App flow tests?', [To Do | 2020-09-16]
[15-September-2020 18:14:21] Task status changed from To Do to In Progress
[15-September-2020 18:14:21] Item created: 'Review tests', [Open | 2020-09-16]
[15-September-2020 18:14:21] Issue status set to Verified
```

> **Notes**. The `displayHistory()` method is defined in the BoardItem class, that's why you can call that method. The Board is **not concerned** if the concrete instance behind the current **BoardItem** is a **Task** or an **Issue**.

