package gov.cms.fiss.pricers.hha.resources;

/** Handles CBSA related utilities. */
public class HhaCbsaUtil {
  private HhaCbsaUtil() {}
  /**
   * Sanitize the incoming CBSA for use in pricing.
   *
   * @param cbsa the CBSA to sanitize
   * @return the sanitized CBSA
   */
  public static String sanitizeCbsa(String cbsa) {
    String sanitizedCbsa = cbsa;
    /* Remove the following:
     * 000 and 999 from beginning
     * spaces
     */
    if (sanitizedCbsa != null && sanitizedCbsa.length() != 2) {
      sanitizedCbsa = sanitizedCbsa.replaceFirst("^(999|000)", "");
    }

    return sanitizedCbsa;
  }
}
