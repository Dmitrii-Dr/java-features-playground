package linecommand;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Client class demonstrating the use of sealed Option interface with records.
 * This class parses command-line arguments and processes file operations based on options.
 */
public class OptionParser {
    
    public static void main(String[] args) {
        System.out.println("=== Sealed Option Interface with Records Demo ===\n");
        
        // Example 1: Parse command-line style arguments
        String[] exampleArgs = {
            "--input", "source.txt",
            "--output", "destination.txt",
            "--max-lines", "100",
            "--print-line-numbers"
        };
        
        List<Option> options = parseOptions(exampleArgs);
        
        System.out.println("Parsed Options:");
        for (Option option : options) {
            processOption(option);
        }
        
        // Example 2: Create options directly using records
        System.out.println("\n=== Direct Option Creation ===");
        Option inputFile = new Option.InputFile(Paths.get("data", "input.txt"));
        Option outputFile = new Option.OutputFile(Paths.get("data", "output.txt"));
        Option maxLines = new Option.MaxLines(50);
        Option printNumbers = new Option.PrintLineNumbers();
        
        Option[] directOptions = {inputFile, outputFile, maxLines, printNumbers};
        
        for (Option option : directOptions) {
            processOption(option);
        }
        
        // Example 3: Demonstrate exhaustiveness with switch expression
        System.out.println("\n=== Processing Options with Switch Expression ===");
        for (Option option : options) {
            String description = getOptionDescription(option);
            System.out.println("Option: " + description);
        }
    }
    
    /**
     * Parses command-line arguments into Option instances.
     */
    private static List<Option> parseOptions(String[] args) {
        List<Option> options = new ArrayList<>();
        
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--input", "-i" -> {
                    if (i + 1 < args.length) {
                        options.add(new Option.InputFile(Paths.get(args[++i])));
                    }
                }
                case "--output", "-o" -> {
                    if (i + 1 < args.length) {
                        options.add(new Option.OutputFile(Paths.get(args[++i])));
                    }
                }
                case "--max-lines", "-m" -> {
                    if (i + 1 < args.length) {
                        options.add(new Option.MaxLines(Integer.parseInt(args[++i])));
                    }
                }
                case "--print-line-numbers", "-n" -> {
                    options.add(new Option.PrintLineNumbers());
                }
            }
        }
        
        return options;
    }
    
    /**
     * Processes an option using pattern matching with instanceof.
     */
    private static void processOption(Option option) {
        if (option instanceof Option.InputFile(Path path)) {
            System.out.println("  Input file: " + path);
        } else if (option instanceof Option.OutputFile(Path path)) {
            System.out.println("  Output file: " + path);
        } else if (option instanceof Option.MaxLines(int maxLines)) {
            System.out.println("  Max lines: " + maxLines);
        } else if (option instanceof Option.PrintLineNumbers()) {
            System.out.println("  Print line numbers: enabled");
        }
    }
    
    /**
     * Demonstrates exhaustiveness checking with switch expressions.
     * The compiler ensures all permitted types are handled.
     */
    private static String getOptionDescription(Option option) {
        return switch (option) {
            case Option.InputFile(Path path) -> 
                "InputFile -> " + path;
            case Option.OutputFile(Path path) -> 
                "OutputFile -> " + path;
            case Option.MaxLines(int maxLines) -> 
                "MaxLines -> " + maxLines;
            case Option.PrintLineNumbers() -> 
                "PrintLineNumbers -> enabled";
            // No default needed - compiler knows all cases are covered!
        };
    }
}

