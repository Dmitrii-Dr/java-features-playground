// Define the module named org.example.helloapp
module org.example.helloapp {
    // Declare that this module depends on com.example.greeting.provider.
    // This makes the exported packages from that module visible here.
    requires com.example.greeting.provider;
}