package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculates the operating disproportionate share hospital for claims with a CBSA size of "R" and
 * with a provider type of 16, 17, 21, or 22. The share value will be capped at 12% (0.1200).
 *
 * <p>Implements the following logic (the calculation is delegated to the superclass, as it is
 * common code).
 *
 * <pre>
 * ***********************************************************
 * **7**   RURAL HOSPITALS SCH
 * ***  NOT TO EXCEED 12%
 *       IF W-CBSA-SIZE = 'R'
 *          IF (P-NEW-PROVIDER-TYPE = '16' OR '17' OR '21' OR '22')
 *                                AND H-WK-OPER-DSH &gt; .1499
 *                                AND H-WK-OPER-DSH &lt; .2020
 *          COMPUTE H-OPER-DSH ROUNDED = (H-WK-OPER-DSH - .15)
 *                                  * .65 + .025
 *         IF H-OPER-DSH &gt; .1200
 *                  MOVE .1200 TO H-OPER-DSH.
 *       IF W-CBSA-SIZE = 'R'
 *          IF (P-NEW-PROVIDER-TYPE = '16' OR '17' OR '21' OR '22')
 *                                AND H-WK-OPER-DSH &gt; .2019
 *          COMPUTE H-OPER-DSH ROUNDED = (H-WK-OPER-DSH - .202)
 *                                  * .825 + .0588
 *         IF H-OPER-DSH &gt; .1200
 *                  MOVE .1200 TO H-OPER-DSH.
 * </pre>
 *
 * <p>Converted from {@code 3900A-CALC-OPER-DSH} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateDisproportionateShareForRuralSchProviders
    extends CalculateDisproportionateShareWithCap {
  private static final String[] MATCHED_TYPES = new String[] {"16", "17", "21", "22"};

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    return StringUtils.equals(cbsaReference.getSize(), "R")
        && StringUtils.equalsAny(providerData.getProviderType(), MATCHED_TYPES);
  }
}
