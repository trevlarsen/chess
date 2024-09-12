# My notes

## Javascript Fundamentals

### Datatypes

- byte (8)
- char (16)
  - var inventor = "James Gosling"
  - var inventor = new String("James Gosling)
  - can use either, but the first only has 
- boolean (1)


- short (16)
- int (32)
- long (64)


- float (32)
- double (64)

*Everything is a class in Java.*
- One class per file
- Class name has to match file name
- Can use classes from files in the same directory
- Can import classes in files located in other packages

```
public class Person {
    private String name;
    
    public Person(String name) {
        this.name = name;
    }
    
    public void sleep() {
        System.out.printf("%s is sleeping", name);
    }
}
```

### Arrays

```
String[] strings = new String[10];
int[] numbers = new int[5]

String[] names = {"James", "Tim", "Tina"}
```

### For loops

```
for (var i = 0; i < names.length; i++) {
    System.out.println(names[i]);
}

for (var name: names) {
    System.out.println(names);
}
```

### If Statements
```
if (count > 0) {
    double average = sum / count;
    System.out.println(average);
} else if (count == 0) {
    System.out.println(0);
} else {
    System.out.println("Huh?");
}
```

### Switches
``` 
String seasonName = switch (seasonCode) { // switch expression
    case 0 -> "Spring";
    case 1 -> "Summer";
    case 2 -> "Fall";
    case 3 -> "Winter";
    default -> {
        System.out.println("???");
        yield "";
    }
};

int numLetters = switch (seasonName) {
    case "Spring", "Summer", "Winter" -> 6;
    case "Fall" -> 4;
    default -> throw new IllegalArgumentException();
};
```

### While Loops
``` 
while (sum < target) {
    int next = generator.nextInt(10);
    sum += next;
    count++;
}

int next;
do {
    next = generator.nextInt(10);
    count++;
} while (next != target);

** break and continue statements can be used
break outer will exit nested loops at the level where 'outer:' is declared before the loop


# NEW:
# Skip code using a block label and a break statement
exit: {
    ...
    if (...) break exit;
    ...
}

** Variables scope extend only within the { } that they are created in (including loops)
You cannot have overlapping scopes for variables of the same name
```

### Math
``` 
Math.pow(x, y) = x^y
Math.sqrt(x)
Math.floorMod(x, y) = x % y


# To cast a datatype
double x = 3.75;
int n = (int) x;
```

### Strings (Immutable)
```
# To concatenate a few strings
String names = String.join(", ", "Peter", "Paul", "Mary");


# To construct large strings
var builder = new StringBuilder();
while (more strings) {
    builder.append(next string);
}
String result = builder.toString();

# Splicing (0 indexed, exclude last)
String greeting = "Hello, World!";
String location = greeting.substring(7, 12);

# Spliting
String names = "Peter, Paul, Mary";
String[] result = names.split(", ");

# Checking Equality (put any literal string first)
word1.equals(word2)
word1.equalsIgnoreCase(word2);

# Checking lexicographical order
first.compareTo(second)

# Parsing/Casting
String str = Double.toString(3.14);
double x = Double.parseDouble(str);

# Multiline
String prompt = """
Hello, my name is Hal. \
What is your name?
Please enter your name:"""

# Reading input strings
var in = new Scanner(System.in);
System.out.print("What is your name?");
String name = in.nextLine();
System.out.print("How old are you?");
int age = in.nextInt();

# Reading in secure data
Console terminal = System.console();
String username = terminal.readLine("User name: ");
char[] passwd = terminal.readPassword("Password: ");

# Formatting strings
System.out.printf("%8.2f", 1000.0 / 3.0);
System.out.printf("Hello, %s. Next year, you'll be %d.\n", name, age);
System.out.printf("%,+.2f", 100000.0 / 3.0);

** Most classes have a <class>.toString(<object>, base=10) method. 
Original classes need to have this overriden to prevent comparison using '=='
Concatenation with another string automatically calls this method
Line endings are normalized to have no space, use \s to force whitespace or tab inside string
```

### Arrays
``` 
var names = new String[100];
names.length
int[] primes = { 2, 3, 5, 7, 11, 13 }; ('new' not used for direct initialization)
primes = new int[] { 17, 19, 23, 29, 31 }; ('new' used for reassignment)

# Copying Arrays
int[] copiedPrimes = Arrays.copyOf(primes, primes.length);

# Methods
int[] copiedPrimes = Arrays.copyOf(primes, primes.length);
Arrays.sort(names);
Arrays.toString(primes)

# Multidimensional arrays
int[][] square = new int[4][4]; 
or
int[][] square = {
    { 16, 3, 2, 13 },
    { 5, 10, 11, 8 },
    { 9, 6, 7, 12 },
    { 4, 15, 14, 1}
};
int element = square[1][2];

** By default:  numeric arrays are filled with 0's
                boolean arrays are filled with false
o               bject arrays are filled with null
```

### Lists
``` 
# Three ways to do the same thing
ArrayList<String> friends = new ArrayList<String>();
var friends = new ArrayList<String>();
ArrayList<String> friends = new ArrayList<>();

# Edit Lists
friends.add("Peter");
friends.add("Paul");
friends.remove(1);
friends.add(0, "Paul");
String first = friends.get(0);
friends.set(1, "Mary");

# View lists
for (int i = 0; i < friends.size(); i++) {
    System.out.println(friends.get(i));
}

# Copying Lists
var copiedFriends = new ArrayList<>(friends);

# Methods
Collections.fill(friends, ""); 
Collections.sort(friends);
Collections.reverse(names);
Collections.shuffle(names);

# Use a wrapper for primitive datatypes (Integer instead of int)
var numbers = new ArrayList<Integer>();
numbers.add(42);
int first = numbers.get(0);
```