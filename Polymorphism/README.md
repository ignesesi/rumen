<img src="https://webassets.telerikacademy.com/images/default-source/logos/telerik-academy.svg)" alt="logo" width="300px" style="margin-top: 20px;"/>

# BoardR - Task Organizing System

*Part 4*

## 1. Description

**BoardR** is a task-management system which will evolve in the next several weeks. During the course of the project, we will follow the best practices of `Object-Oriented Programming` and `Design`.

## 2. Goals

Your task will be to provide implementation for the `Logger` interface and to override some of the behavior of `Task` and `Issue`.

You will achieve this by applying the OOP principle of **Polymorphism**

> **Notes:** You must have noticed that in the previous activities we were dealing with Tasks and Issues, which are two kinds of BoardItem. The Board class stores those two types into a collection of type BoardItem:

```java
/* Board.java */
public class Board {
      public void addItem(BoardItem item) { /* ... code ... */ }
}
```

```java
/* Main.java */
Task task = new Task("Write unit tests", "Pesho", LocalDate.now().plusDays(1));
Issue issue = new Issue("Review tests", "Someone must review Pesho's tests.", LocalDate.now().plusDays(1));

Board board = new Board();

board.addItem(task);  // treating type Task as type BoardItem
board.addItem(issue); // treating type Issue as type BoardItem
```

> **Notes:** From the Boards perspective, it has applied the **OO principle of Abstraction**, because it accepts all subtypes of a more abstract type - the `BoardItem`.
>
> On the other hand, each `Task` is polymorphic because it can be treated as a `BoardItem` or as a `Task`. The same applies for `Issue`. You will see many other examples throughout your career, where one line of code can be viewed as an example of two or more principles.

## 3. Logger

### Description

Instead of the `Board` logging to the console, we can move the logging logic into a dedicated class. This way, we can choose to log to a different place. For example, into a file and not to the console. However, if we have a call to `System.out.println()` inside `board.displayHistory()` there is no easy way to change that without editing the class.

> **Note**: When refactoring, we want to be able to edit **as few classes as possible**.

We can provide an abstraction to the Board that provides the signature for the necessary logging method. We can use an interface for that - `Logger`.

Create a new interface file - `Logger`.

### Methods

`void log(String value);` - this is enough, interface methods don't have implementation, they only define the signature.

We will implement the interface later.

> **Notes** - now that we have the interface, we can use it to control where the Board class logs items


## 4. ConsoleLogger class

### Description

Currently, the method `displayHistory()` in `Board` looks like this:

```java
public void displayHistory() {
    for (BoardItem item : items) {
      item.displayHistory();
}
```

It just calls a method that prints the history. Let's change it so that it receives the history and it decides how to print it.

In BoardItem.java:

```java
public String getHistory() {
    StringBuilder builder = new StringBuilder();

    for (EventLog event : history) {
      builder.append(event.viewInfo()).append(System.lineSeparator());
    }

    return builder.toString();
}
```

In Board.java:

```java
public void displayHistory() {
    for (BoardItem item : items) {
      System.out.println(item.getHistory());
    }
}
```

We reversed who prints the information by making `getHistory()` in `BoardItem` return a string with all the events and `displayHistory()` in `Board` printing it.

But we can improve on that more. How can we use the interface to remove the `System.out.println` from the there? We can pass it as a parameter to the method! The `Logger` has this method: `void log(String value);`. It declares that the `Logger` implementation will know how to log, you just need to pass them a **String**. So, we can do this:

```java
public void displayHistory(Logger logger) { // accept an Logger type
   for (BoardItem item : items) {
      // call the log() method and give it a string. (the viewHistory() method returns a String)
      logger.log(item.getHistory());
    }
}
```

Now, all that we need is a logger. We can create a `loggers` package and put out `Logger` interface in it. Next, let's create a `ConsoleLogger` class in the same package. Make `ConsoleLogger` implement `Logger`.

```java
public class ConsoleLogger implements Logger {

}
```

You will notice that the `Logger` is underlined as having an error with the message "_Class 'ConsoleLogger' must either be declared abstract or implement abstract method 'log(String)' in 'Logger'_". That's why we say the interfaces **act like contracts** - they will guarantee that classes implementing them provide implementation for all declared methods. Go ahead and add a `log()` method to the `ConsoleLogger` class. Implement so it writes the provided string to the console.

After you are done, this code should work:

```java
LocalDate tomorrow = LocalDate.now().plusDays(1);
Task task = new Task("Write unit tests", "Pesho", tomorrow);
Issue issue = new Issue("Review tests", "Someone must review Pesho's tests.", tomorrow);

Board board = new Board();

board.addItem(task);
board.addItem(issue);

ConsoleLogger logger = new ConsoleLogger();
board.displayHistory(logger); // pass a ConsoleLogger type where an Logger is expected:
```

```
[15-September-2020 20:12:58] Item created: 'Write unit tests', [To Do | 2020-09-16]

[15-September-2020 20:12:58] Item created: 'Review tests', [Open | 2020-09-16]
```

> **Note**: Now, in theory, **we can change where the Board logs** without touching the `displayHistory` method! We achieved this by applying the principles of **Abstraction** and **Polymorphism**.

## 5. viewInfo() overriding

### Description

Remember the method? It provides basic info about a BoardItem:

```java
LocalDate tomorrow = LocalDate.now().plusDays(1);
Task task = new Task("Write unit tests", "Pesho", tomorrow);
Issue issue = new Issue("Review tests", "Someone must review Pesho's tests.", tomorrow);

System.out.println(task.viewInfo());
System.out.println(issue.viewInfo());
```

```
'Write unit tests', [To Do | 2020-09-16]
'Review tests', [Open | 2020-09-16]
```

Currently, it logs information about the `title`, the `status`, and the `dueDate`. The method is defined inside the `BoardItem` and can access only properties defined for that class and **cannot** access properties defined in the `BoardItem` deriving classes, such as `Task` and `Issue`. However, `Tasks` have `assignee` and `Issues` have `description`. We want to add this useful information to the output of the `viewInfo()`. We can do this by extending the base behavior inside each deriving class. Java provides a feature called **overriding** that can do this.

We can extend `viewInfo()`'s behavior inside any of the deriving classes:

```java
@Override
public String viewInfo() {
     // get the common info
     String baseInfo = super.viewInfo();

     // add additional info, based on which subclass you are in
     return String.format("Task: %s, Assignee: %s", baseInfo, this.getAssignee());
}
```

You can now test the new, extended `viewInfo()`:

```java
LocalDate tomorrow = LocalDate.now().plusDays(1);
BoardItem task = new Task("Write unit tests", "Pesho", tomorrow);

System.out.println(task.viewInfo());
```

The output should be:

```none
Task: 'Write unit tests', [To Do | 2020-09-16], Assignee: Pesho
```

Override the `viewInfo()` method inside the `Issue` class. If you do everything correctly, this code should produce the following output:

```java
LocalDate tomorrow = LocalDate.now().plusDays(1);

BoardItem task = new Task("Write unit tests", "Pesho", tomorrow);
BoardItem issue = new Issue("Review tests", "Someone must review Pesho's tests.", tomorrow);

System.out.println(task.viewInfo());
System.out.println(issue.viewInfo());
```

```
Task: 'Write unit tests', [To Do | 2020-09-16], Assignee: Pesho
Issue: 'Review tests', [Open | 2020-09-16], Description: Someone must review Pesho's tests.
```

> **Note**: Notice how both task and issue are both inside a variable of type `BoardItem`. Although the method `ViewInfo` is called from the perspective of the base `BoardItem` type, the **most overridden** method is found each time. This is an example of *dynamic, runtime* **polymorphism**. Nice, huh?
