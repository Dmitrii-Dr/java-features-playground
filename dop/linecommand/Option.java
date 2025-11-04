package linecommand;

import java.nio.file.Path;

/**
 * Sealed interface representing command-line options.
 * Only the four record types are permitted to implement this interface.
 */
sealed interface Option {

    record InputFile(Path path) implements Option { }

    record OutputFile(Path path) implements Option { }

    record MaxLines(int maxLines) implements Option { }

    record PrintLineNumbers() implements Option { }

}

