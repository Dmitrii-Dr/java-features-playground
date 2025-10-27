### Description: 
A **module** is a new fundamental unit of Java code. It explicitly declares what it needs (requires) and what it offers (exports). Previoulsy if a class is declared public it is allowed for the world. It means that anyone who imports it can use it. With java modules it's allowed to hide internal classes in modules. 

### How to build

To compile: 
 - javac --module-source-path jpms/src -d jpms/out --module com.example.greeting.provider,org.example.helloapp
or
- javac --module-source-path jpms/src -d jpms/out --module com.example.greeting.provider, org.example.helloapp  

To run:  java --module-path  jpms/out -m org.example.helloapp/org.example.helloapp.Main

### Building custom light JRE
Using Java Modules allow to build custom JRE -  **jlink**

Currently, to run the application, we have two separate things:

1. Your compiled code in the out directory (which is very small, just a few kilobytes).
    
2. A full Java Development Kit (JDK) or Java Runtime Environment (JRE) installed on your machine (which is very large, 200-300 MB).

**jlink solves this problem.**

The goal of jlink is to create a single, self-contained, and minimal runtime image that includes **both** your application code and **only** the necessary parts of the JDK.

**On Linux or macOS:**
```
jlink --module-path out:$JAVA_HOME/jmods --add-modules org.example.helloapp --output my-custom-runtime --launcher hello=org.example.helloapp/org.example.helloapp.Main
```

**On Windows (using Command Prompt):**
```
jlink --module-path "out;%JAVA_HOME%\jmods" --add-modules org.example.helloapp --output my-custom-runtime --launcher hello=org.example.helloapp/org.example.helloapp.Main
```

The command we will build has several key parts:

1. --module-path: This is the most important part. It needs to know where to find all the modules it might need. This includes:
    
    - Our own compiled modules (which are in the out directory).
        
    - The platform modules that are part of the JDK itself. These are located in a jmods folder inside your Java installation directory.
        
2. --add-modules: This tells jlink which module is the "root" of our application. jlink will start with this module and trace all its requires directives to figure out the complete set of modules needed. For us, this is org.example.helloapp.
    
3. --output: This is simply the name of the directory where jlink will place the final runtime image. Let's call it my-custom-runtime.
    
4. --launcher (Optional but highly recommended): This is a fantastic convenience feature. It creates a simple startup script so you don't have to type the long java -m ... command. We'll tell it to create a script named hello that runs our Main class.

### **The Result: Examining the Output**

If the command succeeds, you will now see a new directory: my-custom-runtime. Let's look inside:
```
my-custom-runtime/
├── bin/
│   ├── hello          <-- Our custom launcher script!
│   └── java           <-- A native java executable for your OS
├── conf/
├── include/
├── legal/
└── lib/
    ├── server/
    └── modules        <-- A highly optimized representation of all the needed modules
```
You will see it's probably only **30-40 MB**! This is a massive reduction from the full 200+ MB JRE.

### **How to Run Your New, Self-Contained Application**
```
# On Linux/macOS
./my-custom-runtime/bin/hello

# On Windows
my-custom-runtime\bin\hello.bat
```



### module-info directive

| Directive                 | Purpose                                             | Use Case                                                        |
| :------------------------ | :-------------------------------------------------- | :-------------------------------------------------------------- |
| **`requires`**            | Depend on another module.                           | Using a library like `java.sql` or `com.google.gson`.           |
| **`exports`**             | Make a package's public types visible to others.    | Defining the public API of your library.                        |
| **`requires transitive`** | Make a dependency also available to your consumers. | A library returning types from one of its own dependencies.     |
| **`exports ... to`**      | Export a package only to specific "friend" modules. | A non-public API shared between components of a framework.      |
| **`opens`**               | Allow full reflective access to a package.          | Allowing a framework like Spring or Hibernate to see a package. |
| **`opens ... to`**        | Allow reflective access only to specific modules.   | Securely allowing *only* Hibernate to see your entities.        |
| **`uses`**                | Declare that you are a consumer of a service.       | An API module that needs an implementation (e.g., SLF4J).       |
| **`provides ... with`**   | Declare that you are a provider of a service.       | An implementation module for an API (e.g., Logback).            |
| **`requires static`**     | Declare a compile-time only dependency.             | 