package weiweibot.exceptions;

/**
 * Unchecked application exception for Weiweibot-specific errors.
 *
 * <p>Use this to signal user-facing or domain-related problems that should
 * abort the current operation without requiring callers to declare or catch
 * a checked exception.</p>
 */
public class WeiExceptions extends RuntimeException {
    public WeiExceptions(String str) {
        super(str);
    }
}
